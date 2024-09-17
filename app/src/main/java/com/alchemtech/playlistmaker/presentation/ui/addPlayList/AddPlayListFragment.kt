package com.alchemtech.playlistmaker.presentation.ui.addPlayList

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.MakePlayListBinding
import com.alchemtech.playlistmaker.presentation.ui.fillBy
import com.alchemtech.playlistmaker.presentation.ui.main.StartActivity
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
    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = MakePlayListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeRenderState()
        prepareBackBut()
        false.bottomNavigatorVisibility()
        prepareProgressBar()
        prepareCreateButton()
        preparePictureLayOut()
        prepareNameText()
        prepareDescriptionText()
        prepareBackPress()
    }

    override fun onResume() {
        super.onResume()
        false.bottomNavigatorVisibility()
    }

    override fun onDetach() {
        super.onDetach()
        binding = null
    }


    override fun onStop() {
        true.bottomNavigatorVisibility()
        super.onStop()
    }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            setUriToModel(uri)
            this.uri = uri
        }

    private fun Boolean.bottomNavigatorVisibility() {
        (activity as StartActivity).bottomNavigationVisibility(this)
    }


    private fun prepareBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isEnabled) {
                        if (!nameEditText?.text.isNullOrEmpty()
                            || !descriptionEditText?.text.isNullOrEmpty()
                            || uri != null
                        ) {
                            getCheckForCloseOpenWindow()
                        } else {
                            this.isEnabled = false
                            findNavController().popBackStack()
                        }
                    } else {
                        this.isEnabled = false
                        findNavController().popBackStack()
                    }
                }

            }
        )
    }


    private fun prepareProgressBar() {
        progressBar = binding?.progressBar
        progressBar?.isVisible = false
    }

    private fun prepareCreateButton() {
        createBut = binding?.createBut
        createBut?.setOnClickListener {
            val name = nameEditText?.text.toString()
            showBottomMessage(getString(R.string.playListAdded, name))
            actionCreateBut()
        }
    }

    private fun actionCreateBut() {
        viewModel.savePlayList()
    }

    private fun preparePictureLayOut() {
        binding?.picAdding?.setOnClickListener {
            getPictureUri()
        }
    }

    private fun getPictureUri() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            requestPermissionApiUpper33()
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            requestPermissionApiUnder33()
        } else {
            choosePicture()
        }
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun requestPermissionApiUpper33() {
        lifecycleScope.launch {
            requester.request(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
                .collect { result ->
                    when (result) {
                        is PermissionResult.Granted -> {
                            choosePicture()
                        }

                        is PermissionResult.Denied.DeniedPermanently -> {}

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
            .setTitle(getString(R.string.getPermissionTitle))
            .setMessage(
                getString(R.string.getPermissionMassage)
            )
            .setNeutralButton(getString(R.string.cancel)) { _, _ -> }
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                requestPermissionApiUpper33()
            }
            .show()
    }

    private fun requestPermissionApiUnder33() {
        lifecycleScope.launch {
            requester.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .collect { result ->
                    when (result) {
                        is PermissionResult.Granted -> {
                            choosePicture()
                        }

                        is PermissionResult.Denied.DeniedPermanently -> {}

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
        pickMedia.launch(
            PickVisualMediaRequest(
                ActivityResultContracts
                    .PickVisualMedia
                    .ImageOnly
            )
        )
    }

    private fun setUriToModel(uri: Uri?) {
        viewModel.setUri(uri)
    }

    private fun setPicture(uri: Uri?) {
        binding?.picAdding?.fillBy(uri, requireContext())
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getPermissionOpenWindow() {
        MaterialAlertDialogBuilder(requireContext())
            .setBackground(resources.getDrawable((R.drawable.background)))
            .setTitle(getString(R.string.getPermissionTitle))
            .setMessage(
                getString(R.string.getPermissionMassage)
            )
            .setNeutralButton(getString(R.string.cancel)) { _, _ -> }
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                openAppPermission()
            }
            .show()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getCheckForCloseOpenWindow() {
        MaterialAlertDialogBuilder(requireContext())
            .setBackground(resources.getDrawable((R.drawable.background)))
            .setTitle(getString(R.string.cancelAddPlayListTitle))
            .setMessage(
                getString(R.string.cancelAddPlayListMassage)
            )
            .setNeutralButton(getString(R.string.cancelAddPlayListCancelButton)) { _, _ -> }
            .setPositiveButton(getString(R.string.cancelAddPlayYesButton)) { _, _ ->
                findNavController().popBackStack()
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
                viewModel.setName(text.toString())
                nameEditText?.isActivated = !text.isNullOrEmpty()
                nameTitle?.isVisible = !text.isNullOrEmpty()
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
                viewModel.setDescription(text.toString())
                descriptionEditText?.isActivated = !text.isNullOrEmpty()
                descriptionTitle?.isVisible = !text.isNullOrEmpty()
            }
        }
    }


    private fun observeRenderState() {
        viewModel.observeRenderState().observe(getViewLifecycleOwner()) {
            render(it)
        }
    }

    private fun render(state: AddPlayListState) {
        when (state) {
            is AddPlayListState.Exit ->
                findNavController().popBackStack()

            is AddPlayListState.Loading ->
                progressBar?.isVisible = true

            is AddPlayListState.SetPic ->
                setPicture(state.uri)
        }
    }

    private fun showBottomMessage(message: String) {
        (activity as StartActivity).bottomSheetShowMessage(message)
    }
}