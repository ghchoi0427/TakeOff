package com.choi.takeoff.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.choi.takeoff.GlobalApplication
import com.choi.takeoff.InputMemoActivity
import com.choi.takeoff.R
import com.choi.takeoff.databinding.FragmentHomeBinding
import com.choi.takeoff.db.entity.Memo
import com.choi.takeoff.ui.memo.NewMemoViewModel
import com.choi.takeoff.ui.memo.NewMemoViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {

    private val newMemoFragmentRequestCode = 1
    private val newMemoViewModel: NewMemoViewModel by viewModels {
        NewMemoViewModelFactory((activity?.application as GlobalApplication).repository)
    }

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val fab = root.findViewById<FloatingActionButton>(R.id.fab_add)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview_memo)
        val adapter = HomeAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity).also {
            it.stackFromEnd = true
            it.reverseLayout = true
        }
        newMemoViewModel.allMemos.observe(this.viewLifecycleOwner, { memos ->
            memos?.let {
                adapter.submitList(it)
            }
        })
        fab.setOnClickListener {
            val intent = Intent(activity, InputMemoActivity::class.java)
            startActivityForResult(intent, newMemoFragmentRequestCode)
        }
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var content: String? = ""
        var imageUri: String? = null
        var mood: Int? = null

        if (requestCode == newMemoFragmentRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(InputMemoActivity.EXTRA_REPLY)?.let { reply -> content = reply }
            data?.getStringExtra(InputMemoActivity.EXTRA_PICTURE)?.let { reply -> imageUri = reply }
            data?.getIntExtra(InputMemoActivity.EXTRA_MOOD, 50)?.let { reply -> mood = reply }

            val memo = Memo(
                content,
                imageUri,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM월dd일 HH:mm")).toString(),
                mood,
                null
            )
            //TODO: tags 추가
            newMemoViewModel.insert(memo)
        } else {
            Toast.makeText(context, R.string.input_canceled, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}