package org.android.go.sopt.presentation.main.home

import android.os.Bundle
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
import org.android.go.sopt.presentation.state.UIState
import org.android.go.sopt.presentation.main.home.reqres.ReqresAdapter

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var reqresAdapter: ReqresAdapter? = null
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
        reqresAdapter = null
        super.onDestroyView()
    }

    private fun initObserve() {
        viewModel.userListStateFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED).onEach {
            when (it) {
                is UIState.UnInitialized -> {
                    initRecyclerView()
                    viewModel.getUsers(2)
                }

                is UIState.Loading -> {}

                is UIState.Success -> {
                    reqresAdapter?.submitList(it.data.data)
                }

                is UIState.Error -> {}
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initRecyclerView() {
        reqresAdapter = ReqresAdapter()

        binding.rvReqres.apply {
            adapter = reqresAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}