package org.android.go.sopt.presentation.main.gallery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.model.FakeRepoResponse
import org.android.go.sopt.presentation.main.gallery.github.GitRepoAdapter

class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private val gitRepoAdapter by lazy {
        GitRepoAdapter { url ->
            Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                startActivity(this)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = parseData()
        initViews(data)
        initAdapter(data)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun parseData(): List<FakeRepoResponse.FakeRepoResponseItem> {
        val jsonString = requireContext().assets.open("fake_repo_list.json").reader().readText()
        return parseJson(jsonString)
    }

    private fun initViews(data: List<FakeRepoResponse.FakeRepoResponseItem>) {
        with(binding) {
            val userData = data.first().owner
            tvName.text = userData.login
            ivProfile.load(userData.avatar_url) {
                transformations(CircleCropTransformation())
            }
        }
    }

    private fun initAdapter(data: List<FakeRepoResponse.FakeRepoResponseItem>) {
        binding.rvRepoList.apply {
            adapter = gitRepoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        gitRepoAdapter.submitList(data)
    }

    private fun parseJson(jsonString: String): List<FakeRepoResponse.FakeRepoResponseItem> {
        return Json.decodeFromString(jsonString)
    }
}