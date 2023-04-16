package org.android.go.sopt.presentation.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.model.MusicData
import org.android.go.sopt.presentation.main.MainActivity
import org.android.go.sopt.presentation.main.MainTitleAdapter
import org.android.go.sopt.presentation.main.MusicListAdapter
import org.android.go.sopt.util.MusicList

class HomeFragment : Fragment(), MainActivity.OnReselectListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var tracker: SelectionTracker<String>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onReselect() {
        binding.rvMusicList.smoothScrollToPosition(0)
    }

    private fun initAdapter() {
        val mainTitleAdapter = MainTitleAdapter()
        val musicListAdapter = MusicListAdapter()
        initRecyclerView(mainTitleAdapter, musicListAdapter)
    }

    private fun initRecyclerView(mainTitleAdapter: MainTitleAdapter, musicListAdapter: MusicListAdapter) {
        with(binding.rvMusicList) {
            adapter = ConcatAdapter(mainTitleAdapter, musicListAdapter)
            layoutManager = LinearLayoutManager(requireContext())
        }
        tracker = SelectionTracker.Builder(
            "musicSelection",
            binding.rvMusicList,
            MusicKeyProvider(MusicList.musicList.toMutableList()),
            MyItemDetailsLookup(binding.rvMusicList),
            StorageStrategy.createStringStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        musicListAdapter.tracker = tracker
        setMusicData(musicListAdapter)
    }

    private fun setMusicData(musicListAdapter: MusicListAdapter) {
        musicListAdapter.submitList(MusicList.musicList)
    }

    class MusicKeyProvider(private val musicList: MutableList<MusicData>) :
        ItemKeyProvider<String>(SCOPE_MAPPED) {

        override fun getKey(position: Int): String {
            return try {
                musicList[position].musicName
            } catch (e:Exception) {
                Log.e("MusicKeyProvider", "getKey: $e")
                ""
            }
        }

        override fun getPosition(key: String): Int {
            return musicList.indexOfFirst { it.musicName == key }
        }
    }
    class MyItemDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<String>() {
        override fun getItemDetails(event: MotionEvent): ItemDetails<String>? {
            val view = recyclerView.findChildViewUnder(event.x, event.y)
            if (view != null) {
                return (recyclerView.getChildViewHolder(view) as? MusicListAdapter.MainViewHolder)?.getItemDetails()
            }
            return null
        }
    }
}