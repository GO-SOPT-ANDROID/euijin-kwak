package org.android.go.sopt.presentation.main.home.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import org.android.go.sopt.databinding.DialogAddBinding
import org.android.go.sopt.extension.getExtParcelable
import org.android.go.sopt.presentation.model.MusicItem
import org.android.go.sopt.util.DialogUtil

class AddDialog() : DialogFragment() {

    companion object {
        const val MUSIC_DATA = "MUSIC_DATA"
    }

    private var currentId: Int? = null
    private var onSuccessDataInsert: ((MusicItem) -> Unit)? = null

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
        initData()
    }

    private fun initData() {
        arguments?.getExtParcelable(MUSIC_DATA, MusicItem::class.java)?.let {
            currentId = it.id
            with(binding) {
                etMusicName.setText(it.musicName)
                etSingerName.setText(it.singerName)
                etImageUrl.setText(it.imageUrl)
            }
        }
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
                    MusicItem(
                        id = currentId ?: 0,
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

    fun setOnItemClickListener(onSuccessDataInsert: (MusicItem) -> Unit) {
        this.onSuccessDataInsert = onSuccessDataInsert
    }


}