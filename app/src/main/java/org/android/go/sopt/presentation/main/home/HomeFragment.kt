package org.android.go.sopt.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.android.go.sopt.data.api.ApiFactory
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.presentation.main.home.reqres.ReqresAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var reqresAdapter: ReqresAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        fetchUserList()
    }

    private fun fetchUserList() {
        lifecycleScope.launch {
            val response = ApiFactory.reqresService.getUsers(2)
            if (response.isSuccessful) {
                reqresAdapter?.submitList(response.body()?.data)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        reqresAdapter = null
        super.onDestroyView()
    }

    private fun initRecyclerView() {
        reqresAdapter = ReqresAdapter()

        binding.rvReqres.apply {
            adapter = reqresAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}