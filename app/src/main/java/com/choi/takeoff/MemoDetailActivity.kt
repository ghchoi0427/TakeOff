package com.choi.takeoff

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.choi.takeoff.adapter.MemoListAdapter
import com.choi.takeoff.databinding.ActivityMemoDetailBinding
import com.choi.takeoff.db.entity.Memo
import com.choi.takeoff.ui.memo.NewMemoViewModel
import com.choi.takeoff.ui.memo.NewMemoViewModelFactory
import com.choi.takeoff.util.Converters

class MemoDetailActivity : AppCompatActivity() {

    private val newMemoViewModel: NewMemoViewModel by viewModels {
        NewMemoViewModelFactory((application as GlobalApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMemoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewMemoContent = binding.memoContent
        val memoId: Int = intent.getIntExtra(MemoListAdapter.MEMO_OBJECT_ID, -1)
        val memo: Memo = newMemoViewModel.memoById(memoId)

        if (memo.picture == null) {
            viewMemoContent.imageMemo.visibility = View.GONE
        } else {
            viewMemoContent.imageMemo.visibility = View.VISIBLE
            try {
                val byteRead: ByteArray = applicationContext.openFileInput(memo.picture).readBytes()
                viewMemoContent.imageMemo.setImageBitmap(Converters.byteArrayToBitmap(byteRead))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        viewMemoContent.textMemoDetailTime.text = memo.time
        viewMemoContent.textMemoDetailContent.text = memo.content
        viewMemoContent.textMemoDetailMood.text = memo.mood.toString()
        //TODO: indicate mood by something else than text
    }
}