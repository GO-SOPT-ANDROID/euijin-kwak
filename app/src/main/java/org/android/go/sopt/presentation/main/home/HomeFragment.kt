package org.android.go.sopt.presentation.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.presentation.main.home.music.SoptMusicAdapter
import org.android.go.sopt.presentation.main.player.MusicFragment
import org.android.go.sopt.presentation.main.player.dialog.AddDialog
import org.android.go.sopt.presentation.main.player.dialog.MusicDialog

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var soptMusicAdapter: SoptMusicAdapter? = null
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
    }

    override fun onDestroyView() {
        _binding = null
        soptMusicAdapter = null
        super.onDestroyView()
    }

    private fun initObserve() {
        viewModel.homeStateFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED).onEach {
            when (it) {
                is HomeState.UnInitialized -> {
                    initView()
                    initRecyclerView()
                    viewModel.getMusicList()
                }

                is HomeState.Loading -> {}

                is HomeState.SuccessGetMusicList -> {
                    soptMusicAdapter?.submitList(it.data.musicList)
                }

                is HomeState.SuccessPostMusic -> {
                    viewModel.getMusicList()
                }

                is HomeState.Error -> {
                    Log.e("HomeFragment", "Error")
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initView() {
        binding.fbAdd.setOnClickListener {
            MusicDialog.getInstance().apply {
                setOnItemClickListener {
                    viewModel.postMusic(it)
                }
            }.let { parentFragmentManager.beginTransaction().add(it, MusicFragment.ADD_DIALOG).commitAllowingStateLoss() }
        }
    }

    private fun initRecyclerView() {
        soptMusicAdapter = SoptMusicAdapter()

        binding.rvReqres.apply {
            adapter = soptMusicAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}