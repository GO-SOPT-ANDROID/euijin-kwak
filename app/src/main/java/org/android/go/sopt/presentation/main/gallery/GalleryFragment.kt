package org.android.go.sopt.presentation.main.gallery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.android.go.sopt.data.model.git.FakeRepoResponse
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.presentation.main.gallery.github.GitProfileAdapter
import org.android.go.sopt.presentation.main.gallery.github.GitRepoAdapter

@AndroidEntryPoint
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
    private var gitProfileAdapter: GitProfileAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(parseData())
    }

    override fun onDestroyView() {
        gitProfileAdapter = null
        _binding = null
        super.onDestroyView()
    }

    private fun initAdapter(data: List<FakeRepoResponse.FakeRepoResponseItem>) {
        gitProfileAdapter = data.firstOrNull()?.let {
            GitProfileAdapter(Pair(it.owner.avatar_url, it.owner.login))
        } ?: GitProfileAdapter(null)

        binding.rvRepoList.apply {
            adapter = ConcatAdapter(gitProfileAdapter, gitRepoAdapter)
            layoutManager = LinearLayoutManager(requireContext())
        }

        gitRepoAdapter.submitList(data)
    }

    private fun parseData(): List<FakeRepoResponse.FakeRepoResponseItem> {
        val jsonString = requireContext().assets.open("fake_repo_list.json").reader().readText()
        return Json.decodeFromString(jsonString)
    }
}