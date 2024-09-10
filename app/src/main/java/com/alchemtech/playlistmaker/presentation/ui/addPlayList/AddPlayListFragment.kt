package com.alchemtech.playlistmaker.presentation.ui.addPlayList

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.util.TypedValueCompat.dpToPx
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
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
    private val requester = PermissionRequester.instance()
    private var nameEditText: EditText? = null
    private var nameTitle: TextView? = null
    private var descriptionEditText: EditText? = null
    private var descriptionTitle: TextView? = null
    private var createBut: Button? = null
    private var progressBar: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = MakePlayListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            setUriToModel(uri)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeRenderState()
        prepareBackBut()
        (activity as StartActivity).bottomNavigationVisibility(false)

        prepareProgressBar()
        prepareCreateBut()
        preparePicLay()
        prepareNameText()
        prepareDescriptionText()


    }

    private fun prepareProgressBar() {
        progressBar = binding?.progressBar
        progressBar?.isVisible = false
    }

    private fun prepareCreateBut() {
        createBut = binding?.createBut
        createBut?.setOnClickListener {
            actionCreateBut()
        }
    }

    private fun actionCreateBut() {
        viewModel.savePlayList()
    }

    private fun preparePicLay() {
        binding?.picAdding?.setOnClickListener {
            getPictureUri()
        }
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
                        }

                        is PermissionResult.Denied.DeniedPermanently -> {
//                            Toast.makeText(
//                                requireContext(),
//                                getString(R.string.askForRestartFragment), Toast.LENGTH_SHORT
//                            ).show()
                        }

                        is PermissionResult.Denied.NeedsRationale -> {
                            getPermissionOpenWindowUpper33()
                        }

                        is PermissionResult.Cancelled -> {
                            return@collect
                        }
                    }
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getPermissionOpenWindowUpper33() {
        MaterialAlertDialogBuilder(requireContext())
            .setBackground(resources.getDrawable((R.drawable.background)))
            .setTitle(getString(R.string.getPermissionTitle)) // Заголовок диалога
            .setMessage(
                getString(R.string.getPermissionMassage)
            ) // Описание диалога
            .setNeutralButton(getString(R.string.cancel)) { _, _ -> // Добавляет кнопку «Отмена»
                // Действия, выполняемые при нажатии на кнопку «Отмена»
            }
            .setNegativeButton(getString(R.string.no)) { _, _ -> // Добавляет кнопку «Нет»
                // Действия, выполняемые при нажатии на кнопку «Нет»
            }
            .setPositiveButton(getString(R.string.yes)) { _, _ -> // Добавляет кнопку «Да»
                reguestPermissinApiUpper33()
            }
            .show()
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
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

    }

    private fun setUriToModel(uri: Uri?) {
        viewModel.setUri(uri)
    }

    private fun setPicture(uri: Uri?) {
        Log.d("PhotoPicker", "Выбранный URI: $uri") // TODO:  
        if (uri != null) {
            val albumCover: ImageView? = binding?.picAdding
            if (albumCover != null) {
                Glide.with(requireContext())
                    .load(uri)
                    .fitCenter()
                    .transform(
                        RoundedCorners(
                            dpToPx(8f, requireContext().resources.displayMetrics).toInt()
                        )
                    )
                    .into(albumCover)
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getPermissionOpenWindow() {
        MaterialAlertDialogBuilder(requireContext())
            .setBackground(resources.getDrawable((R.drawable.background)))
            .setTitle(getString(R.string.getPermissionTitle)) // Заголовок диалога
            .setMessage(
                getString(R.string.getPermissionMassage)
            ) // Описание диалога
            .setNeutralButton(getString(R.string.cancel)) { _, _ -> // Добавляет кнопку «Отмена»
                // Действия, выполняемые при нажатии на кнопку «Отмена»
            }
            .setNegativeButton(getString(R.string.no)) { _, _ -> // Добавляет кнопку «Нет»
                // Действия, выполняемые при нажатии на кнопку «Нет»
            }
            .setPositiveButton(getString(R.string.yes)) { _, _ -> // Добавляет кнопку «Да»
                openAppPermission()
            }
            .show()
    }

    private fun openAppPermission() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.fromParts("package", requireContext().packageName, null)
        requireContext().startActivity(intent)
    }

    private fun prepareBackBut() {
        binding?.preview?.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun prepareNameText() {
        binding?.let {
            nameEditText = it.playListName
            nameEditText?.isActivated = false
            nameTitle = it.playListplayListNameTitle
            nameTitle?.isVisible = false
            nameEditText?.doOnTextChanged { text, _, _, _ ->
                nameEditText?.isActivated = !text.isNullOrEmpty()
                nameTitle?.isVisible = !text.isNullOrEmpty()
                viewModel.setName(text.toString())
                createBut?.isEnabled = !text.isNullOrEmpty()
            }
        }
    }

    private fun prepareDescriptionText() {
        binding?.let {
            descriptionEditText = it.playListDescription
            descriptionEditText?.isActivated = false
            descriptionTitle = it.playListDescriptionTitle
            descriptionTitle?.isVisible = false
            descriptionEditText?.doOnTextChanged { text, _, _, _ ->
                descriptionEditText?.isActivated = !text.isNullOrEmpty()
                descriptionTitle?.isVisible = !text.isNullOrEmpty()
                viewModel.setDescription(text.toString())
            }
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
            is AddPlayListState.Exit ->
                requireActivity().onBackPressed()

            is AddPlayListState.Loading ->
                progressBar?.isVisible = true

            is AddPlayListState.SetPic ->
                setPicture(state.uri)
        }
    }
}