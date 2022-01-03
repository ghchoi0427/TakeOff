package com.choi.takeoff

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.choi.takeoff.databinding.ActivityMemoDetailBinding
import com.choi.takeoff.db.entity.Memo
import com.choi.takeoff.ui.home.HomeAdapter
import com.choi.takeoff.ui.memo.MemoViewModel
import com.choi.takeoff.ui.memo.NewMemoViewModelFactory
import com.choi.takeoff.util.Converters
import com.choi.takeoff.util.StorageManager

class MemoDetailActivity : AppCompatActivity() {

    private val memoViewModel: MemoViewModel by viewModels {
        NewMemoViewModelFactory((application as GlobalApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMemoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewMemoContent = binding.memoContent
        val memoId: Int = intent.getIntExtra(HomeAdapter.MEMO_OBJECT_ID, -1)
        val memo: Memo = memoViewModel.memoById(memoId)
        binding.toolbarLayout.title = memo.time

        if (memo.picture != null) {
            binding.toolbarLayout.background = BitmapDrawable(
                Converters.byteArrayToBitmap(
                    StorageManager.readFile(
                        memo.picture,
                        applicationContext
                    )
                )
            )
        } else {
            binding.appBar.setLiftable(false)
        }

        viewMemoContent.textMemoDetailContent.text = memo.content
        viewMemoContent.textMemoDetailMood.text = memo.mood.toString()
        viewMemoContent.progressMood.progress = memo.mood!!
    }
}