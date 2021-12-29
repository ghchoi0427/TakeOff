package com.choi.takeoff.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.choi.takeoff.GlobalApplication
import com.choi.takeoff.R
import com.choi.takeoff.adapter.PhotoAdapter
import com.choi.takeoff.databinding.FragmentGalleryBinding
import com.choi.takeoff.ui.memo.NewMemoViewModel
import com.choi.takeoff.ui.memo.NewMemoViewModelFactory

class GalleryFragment : Fragment() {
    private val newMemoViewModel: NewMemoViewModel by viewModels {
        NewMemoViewModelFactory((activity?.application as GlobalApplication).repository)
    }
    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentGalleryBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview_photo)
        val adapter = PhotoAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            GridLayoutManager(context, 2)
        newMemoViewModel.allMemos.observe(this.viewLifecycleOwner, { memos ->
            memos?.let {
                adapter.submitList(it)
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}