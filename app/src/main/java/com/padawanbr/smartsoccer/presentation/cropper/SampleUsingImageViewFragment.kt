package com.padawanbr.smartsoccer.presentation.cropper

import ImagePickerUtils.saveImageUri
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.canhub.cropper.CropImageView.CropResult
import com.canhub.cropper.CropImageView.OnCropImageCompleteListener
import com.canhub.cropper.CropImageView.OnSetImageUriCompleteListener
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.databinding.FragmentCropImageViewBinding
import com.padawanbr.smartsoccer.presentation.cropper.optionsdialog.SampleOptionsBottomSheet
import com.padawanbr.smartsoccer.presentation.ui.groups.GroupFragmentArgs
import com.padawanbr.smartsoccer.presentation.ui.groups.GroupFragmentDirections

class SampleUsingImageViewFragment : Fragment(), SampleOptionsBottomSheet.Listener,
  OnSetImageUriCompleteListener, OnCropImageCompleteListener {
  private var _binding: FragmentCropImageViewBinding? = null
  private val binding get() = _binding!!

  private var options: CropImageOptions? = null
  private val openPicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
    binding.cropImageView.setImageUriAsync(uri)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    setHasOptionsMenu(true)
    _binding = FragmentCropImageViewBinding.inflate(layoutInflater, container, false)
    return binding.root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    binding.cropImageView.setOnSetImageUriCompleteListener(null)
    binding.cropImageView.setOnCropImageCompleteListener(null)
    _binding = null
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setOptions()

    binding.cropImageView.setOnSetImageUriCompleteListener(this)
    binding.cropImageView.setOnCropImageCompleteListener(this)

    if (savedInstanceState == null) {
      binding.cropImageView.imageResource = R.drawable.default_image
    }

    binding.settings.setOnClickListener {
      SampleOptionsBottomSheet.show(childFragmentManager, options, this)
    }

    binding.searchImage.setOnClickListener {
      openPicker.launch("image/*")
    }

    binding.reset.setOnClickListener {
      binding.cropImageView.resetCropRect()
      binding.cropImageView.imageResource = R.drawable.default_image
      onOptionsApplySelected(CropImageOptions())
    }
  }

  override fun onOptionsApplySelected(options: CropImageOptions) {
    this.options = options

    binding.cropImageView.setImageCropOptions(options)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.menu_cropper, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    R.id.action_cropper_crop -> {
      binding.cropImageView.croppedImageAsync()
      true
    }

    R.id.action_cropper_rotate -> {
      binding.cropImageView.rotateImage(90)
      true
    }

    R.id.action_cropper_flip_horizontally -> {
      binding.cropImageView.flipImageHorizontally()
      true
    }

    R.id.action_cropper_flip_vertically -> {
      binding.cropImageView.flipImageVertically()
      true
    }

    else -> super.onOptionsItemSelected(item)
  }

  override fun onSetImageUriComplete(view: CropImageView, uri: Uri, error: Exception?) {
    if (error != null) {
//      Timber.tag("AIC-Sample").e(error, "Failed to load image by URI")
      Toast.makeText(activity, "Image load failed: " + error.message, Toast.LENGTH_LONG)
        .show()
    }
  }


  private val args by navArgs<SampleUsingImageViewFragmentArgs>()

  override fun onCropImageComplete(view: CropImageView, result: CropResult) {
    if (result.error == null) {
      val imageBitmap = if (binding.cropImageView.cropShape == CropImageView.CropShape.OVAL) {
        result.bitmap?.let(CropImage::toOvalBitmap)
      } else {
        result.bitmap
      }

      saveImageUri(requireContext(), result.uriContent!!, args.groupId)
      findNavController().popBackStack()

    } else {
//      Timber.tag("AIC-Sample").e(result.error, "Failed to crop image")
      Toast
        .makeText(activity, "Crop failed: ${result.error?.message}", Toast.LENGTH_SHORT)
        .show()
    }
  }

  private fun setOptions() {
    binding.cropImageView.cropRect = Rect(100, 300, 500, 1200)
    onOptionsApplySelected(CropImageOptions())
  }
}
