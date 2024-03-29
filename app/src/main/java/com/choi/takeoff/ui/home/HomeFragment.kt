package com.choi.takeoff.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.choi.takeoff.InputMemoActivity
import com.choi.takeoff.MainActivity
import com.choi.takeoff.R
import com.choi.takeoff.databinding.FragmentHomeBinding
import com.choi.takeoff.db.entity.Memo
import com.choi.takeoff.ui.memo.MemoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.sql.Timestamp

class HomeFragment : Fragment() {

    private val newMemoFragmentRequestCode = 1
    private lateinit var memoViewModel: MemoViewModel

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

        memoViewModel = (activity as MainActivity).memoViewModel
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
        memoViewModel.allMemos.observe(this.viewLifecycleOwner, { memos ->
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
        var imageFileName: String? = null
        var mood: Int? = null

        if (requestCode == newMemoFragmentRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(InputMemoActivity.EXTRA_REPLY)?.let { reply -> content = reply }
            data?.getStringExtra(InputMemoActivity.EXTRA_PICTURE)?.let { reply -> imageFileName = reply }
            data?.getIntExtra(InputMemoActivity.EXTRA_MOOD, -1)?.let { reply -> mood = reply }

            val memo = Memo(
                content,
                imageFileName,
                Timestamp(System.currentTimeMillis()).toString(),
                mood,
                null
            )
            //TODO: tags 추가
            memoViewModel.insert(memo)
        } else {
            Toast.makeText(context, R.string.input_canceled, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}