package org.android.go.sopt.presentation.main.home.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import org.android.go.sopt.databinding.DialogAddBinding
import org.android.go.sopt.domain.entity.MusicData
import org.android.go.sopt.util.DialogUtil

class AddDialog(private val onSuccessDataInsert: (MusicData) -> Unit) : DialogFragment() {

    private var _binding: DialogAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.window?.apply {
            requestFeature(Window.FEATURE_NO_TITLE)
        }
        _binding = DialogAddBinding.inflate(inflater, container, false)
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
                onSuccessDataInsert(
                    MusicData(
                        musicName = binding.etMusicName.text.toString(),
                        singerName = binding.etSingerName.text.toString(),
                        imageUrl = binding.etImageUrl.text.toString()
                    )
                )
                dismiss()
            }
        }
    }

    private fun isMusicDataEmpty() =
                binding.etMusicName.text.toString().isNotEmpty() &&
                binding.etSingerName.text.toString().isNotEmpty() &&
                binding.etImageUrl.text.toString().isNotEmpty()


}