package com.choi.takeoff.util

import android.content.Context

class StorageManager {

    companion object {
        fun writeFile(filename: String?, byteArray: ByteArray?, context: Context) {
            context.openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(byteArray)
            }
        }

        fun readFile(filename: String?, context: Context): ByteArray {
            return context.openFileInput(filename).readBytes()
        }

        fun deleteFile(filename: String?, context: Context) {
            context.deleteFile(filename)
        }

    }
}