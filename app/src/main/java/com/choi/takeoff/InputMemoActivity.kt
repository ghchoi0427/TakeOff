package com.choi.takeoff

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.choi.takeoff.databinding.ActivityInputMemoBinding
import com.choi.takeoff.ui.mood.MoodFragment
import com.choi.takeoff.util.Converters
import com.choi.takeoff.util.StorageManager

class InputMemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputMemoBinding
    private val SELECT_PICTURE = 200
    private var imageFileName: String? = null
    private val buttonDeletePicture by lazy { binding.buttonDeletePreviewImage }
    private val imagePreview by lazy { binding.imagePreview }
    var mood: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInputMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editText = binding.edtMemoContent

        val buttonConfirm = binding.fabConfirmInput
        buttonConfirm.setOnClickListener {
            val moodFragment = MoodFragment()
            moodFragment.show(supportFragmentManager, "TAG_MOOD")
            val replyIntent = Intent()
            val content = editText.text.toString()
            replyIntent.putExtra(EXTRA_REPLY, content)
                .putExtra(EXTRA_PICTURE, imageFileName)
                .putExtra(EXTRA_MOOD, mood)
            setResult(Activity.RESULT_OK, replyIntent)
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
            i.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
        }

        buttonDeletePicture.setOnClickListener {
            imagePreview.visibility = View.GONE
            StorageManager.deleteFile(imageFileName, applicationContext)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            val imageByteArray = data?.data?.let { Converters.uriToByteArray(it, contentResolver) }
            imageFileName = java.util.UUID.randomUUID().toString()

            applicationContext.openFileOutput(imageFileName, Context.MODE_PRIVATE).use {
                it.write(imageByteArray)
            }
            StorageManager.writeFile(imageFileName, imageByteArray, applicationContext)
            imagePreview.setImageBitmap(imageByteArray?.let { Converters.byteArrayToBitmap(it) })
            imagePreview.visibility = View.VISIBLE
            buttonDeletePicture.visibility = View.VISIBLE
        }
    }


    private fun checkPermission(uri: Uri) {

        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION

        val resInfoList: List<ResolveInfo> =
            packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        resInfoList.forEach {
            val packageName: String = it.activityInfo.packageName
            applicationContext.grantUriPermission(
                packageName,
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            applicationContext.grantUriPermission(
                packageName,
                uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
        }

    }

    companion object {
        const val EXTRA_REPLY = "com.choi.takeoff.memolistsql.REPLY"
        const val EXTRA_PICTURE = "com.choi.takeoff.memolistsql.PICTURE"
        const val EXTRA_MOOD = "com.choi.takeoff.memolistsql.MOOD"
    }
}