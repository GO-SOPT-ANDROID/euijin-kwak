package org.android.go.sopt.presentation.main.player.dialog

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import coil.load
import org.android.go.sopt.databinding.DialogMusicBinding
import org.android.go.sopt.domain.entity.music.SoptPostMusicBody
import org.android.go.sopt.util.ContentUriRequestBody
import org.android.go.sopt.util.DialogUtil

class MusicDialog : DialogFragment() {

    companion object {
        fun getInstance(): MusicDialog {
            //임시 인스턴스용
            val bundle = Bundle()
            return MusicDialog().apply {
                arguments = bundle
            }
        }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent())
    { imageUri: Uri? ->
        this.imageUri = imageUri ?: Uri.EMPTY
        binding.ivAddImage.load(imageUri)
    }

    private var imageUri = Uri.EMPTY
    private var onSuccessDataInsert: ((SoptPostMusicBody) -> Unit)? = null

    private var _binding: DialogMusicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.window?.apply {
            requestFeature(Window.FEATURE_NO_TITLE)
        }
        _binding = DialogMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        context?.let {
            DialogUtil.dialogWidthPercent(it, dialog)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initViews() {
        binding.btComplete.setOnClickListener {
            if (isMusicDataEmpty()) {
                onSuccessDataInsert?.invoke(
                    SoptPostMusicBody(
                        title = binding.etMusicName.text.toString(),
                        singer = binding.etSingerName.text.toString(),
                        image = ContentUriRequestBody(requireContext(), imageUri).toFormData()
                    )
                )
                dismiss()
            }
        }
        binding.ivAddImage.setOnClickListener {
            launcher.launch("image/*")
        }
    }

    private fun isMusicDataEmpty() = binding.etMusicName.text.toString().isNotEmpty() && binding.etSingerName.text.toString().isNotEmpty()

    fun setOnItemClickListener(onSuccessDataInsert: (SoptPostMusicBody) -> Unit) {
        this.onSuccessDataInsert = onSuccessDataInsert
    }
}