package com.alchemtech.playlistmaker.presentation.ui.addPlayList

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.util.TypedValueCompat.dpToPx
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.MakePlayListBinding
import com.alchemtech.playlistmaker.presentation.ui.main.StartActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.markodevcic.peko.PermissionRequester
import com.markodevcic.peko.PermissionResult
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPlayListFragment : Fragment() {

    private val viewModel: AddPlayListViewModel by viewModel()
    private var binding: MakePlayListBinding? = null
    val requester = PermissionRequester.instance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = MakePlayListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    // создаём событие с результатом и передаём в него PickVisualMedia()
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback вызовется, когда пользователь выберет картинку
            setPicture(uri)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeRenderState()
        prepareBackBut()
        (activity as StartActivity).bottomNavigationVisibility(false)
        binding?.createBut?.isEnabled = false


        binding?.playListDescriptionTitle?.visibility = View.GONE
        binding?.playListDescription?.isEnabled = true

        binding?.playListplayListNameTitle?.visibility = View.GONE
        binding?.playListName?.isEnabled = true

        getPictureUri()
    }

    private fun getPictureUri() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            reguestPermissinApiUpper33()
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            requestPermissonApiUnder33()
        } else {
            choosePicture()
        }
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun reguestPermissinApiUpper33() {
        lifecycleScope.launch {
            requester.request(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
                .collect { result ->
                    when (result) {
                        is PermissionResult.Granted -> {
                            choosePicture()
                            println("------------------")
                        }

                        is PermissionResult.Denied.DeniedPermanently -> {
                            getPermissionOpenWindow()
                        }

                        is PermissionResult.Denied.NeedsRationale -> {
                            getPermissionOpenWindow()
                        }

                        is PermissionResult.Cancelled -> {
                            return@collect
                        }
                    }
                }
        }
    }

    private fun requestPermissonApiUnder33() {
        lifecycleScope.launch {
            requester.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .collect { result ->
                    when (result) {
                        is PermissionResult.Granted -> {
                            choosePicture()
                        }

                        is PermissionResult.Denied.DeniedPermanently -> {
                        }

                        is PermissionResult.Denied.NeedsRationale -> {
                            getPermissionOpenWindow()
                        }

                        is PermissionResult.Cancelled -> {
                            return@collect
                        }
                    }
                }
        }
    }


    private fun choosePicture() {
        // TODO:
// Вызываем метод launch и передаём параметр, чтобы предлагались только картинки
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

    }

    private fun setPicture(uri: Uri?) {
        if (uri != null) {
            val albumCover: ImageView? = binding?.picAdding
            if (albumCover != null) {
                Glide.with(requireContext())
                    .load(uri)
                    .placeholder(R.drawable.track_album_default_big)
                    .centerCrop()
                    .transform(
                        RoundedCorners(
                            dpToPx(8f, requireContext().resources.displayMetrics).toInt()
                        )
                    )
                    .into(albumCover)
            }

            Log.d("PhotoPicker", "Выбранный URI: $uri")
        } else {
            Log.d("PhotoPicker", "Ничего не выбрано")
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getPermissionOpenWindow() {
        MaterialAlertDialogBuilder(requireContext())
            .setBackground(resources.getDrawable((R.drawable.background)))
            .setTitle("Заголовок") // Заголовок диалога
            .setMessage("Описание") // Описание диалога
            .setNeutralButton("Отмена") { dialog, which -> // Добавляет кнопку «Отмена»
                // Действия, выполняемые при нажатии на кнопку «Отмена»
            }
            .setNegativeButton("Нет") { dialog, which -> // Добавляет кнопку «Нет»
                // Действия, выполняемые при нажатии на кнопку «Нет»
            }
            .setPositiveButton("Да") { dialog, which -> // Добавляет кнопку «Да»
                // Действия, выполняемые при нажатии на кнопку «Да»
            }
            .show()
    }

    private fun prepareBackBut() {
        binding?.preview?.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDetach() {
        super.onDetach()
        binding = null
    }


    override fun onStop() {
        super.onStop()
        (activity as StartActivity).bottomNavigationVisibility(true)
    }


    private fun observeRenderState() {
        val a = viewModel.observeRenderState()
        a.observe(getViewLifecycleOwner()) {
            render(it)
        }
    }

    private fun render(state: AddPlayListState) {
        when (state) {

            else -> {}
        }
    }
}