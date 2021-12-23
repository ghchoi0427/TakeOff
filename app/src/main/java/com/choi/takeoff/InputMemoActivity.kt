package com.choi.takeoff

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.choi.takeoff.databinding.ActivityInputMemoBinding

class InputMemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputMemoBinding
    val SELECT_PICTURE = 200
    var imageUri: String? = null

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
                    .putExtra(EXTRA_PICTURE, imageUri)
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

        val buttonPicture = binding.buttonUploadPicture
        buttonPicture.setOnClickListener {
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_OPEN_DOCUMENT
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            imageUri = data?.data.toString()
            val imagePreview = binding.imagePreview
            imagePreview.setImageURI(Uri.parse(imageUri))

            checkPermission(Uri.parse(imageUri))
        }
    }

    private fun checkPermission(uri: Uri) {

        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION

        val resInfoList: List<ResolveInfo> =
            packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        resInfoList.forEach {
            val packageName: String = it.activityInfo.packageName
            applicationContext.grantUriPermission(packageName,
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION)
            applicationContext.grantUriPermission(packageName,
                uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }

    }

    companion object {
        const val EXTRA_REPLY = "com.choi.takeoff.memolistsql.REPLY"
        const val EXTRA_PICTURE = "com.choi.takeoff.memolistsql.PICTURE"
    }
}