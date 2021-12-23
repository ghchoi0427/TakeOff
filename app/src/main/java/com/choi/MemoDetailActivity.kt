package com.choi

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.choi.takeoff.GlobalApplication
import com.choi.takeoff.adapter.MemoListAdapter
import com.choi.takeoff.databinding.ActivityMemoDetailBinding
import com.choi.takeoff.db.entity.Memo
import com.choi.takeoff.ui.newmemo.NewMemoViewModel
import com.choi.takeoff.ui.newmemo.NewMemoViewModelFactory

class MemoDetailActivity : AppCompatActivity() {

    private val newMemoViewModel: NewMemoViewModel by viewModels {
        NewMemoViewModelFactory((application as GlobalApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMemoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val memoId: Int = intent.getIntExtra(MemoListAdapter.MEMO_OBJECT_ID, -1)
        val memo: Memo = newMemoViewModel.memoById(memoId)

        binding.imageMemo.setImageURI(Uri.parse(memo.picture))
        binding.textMemoDetailTime.text = memo.time
        binding.textMemoDetailContent.text = memo.content
    }
}