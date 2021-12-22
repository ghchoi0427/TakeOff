package com.choi.takeoff

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.choi.takeoff.databinding.ActivityInputMemoBinding

class InputMemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputMemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInputMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editText = binding.edtMemoContent

        val buttonConfirm = binding.fabConfirmInput
        buttonConfirm.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editText.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val content = editText.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, content)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        val buttonCancel = binding.fabCancelInput
        buttonCancel.setOnClickListener {
            val replyIntent = Intent()
            setResult(Activity.RESULT_CANCELED, replyIntent)
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.choi.takeoff.memolistsql.REPLY"
    }
}