package com.choi.takeoff.ui.memory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.choi.takeoff.databinding.FragmentMemoryBinding

class MemoryFragment : Fragment() {

    private lateinit var memoryViewModel: MemoryViewModel
    private var _binding: FragmentMemoryBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        memoryViewModel =
            ViewModelProvider(this).get(MemoryViewModel::class.java)

        _binding = FragmentMemoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}