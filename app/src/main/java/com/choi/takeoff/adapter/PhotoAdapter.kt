package com.choi.takeoff.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.choi.takeoff.MemoDetailActivity
import com.choi.takeoff.R
import com.choi.takeoff.db.entity.Memo


class PhotoAdapter :
    ListAdapter<Memo, PhotoAdapter.PhotoViewHolder>(MemoListAdapter.MemosComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val photoItemView: ImageView = itemView.findViewById(R.id.imageview_recycler)

        fun bind(memo: Memo?) {
            try {
                photoItemView.setImageURI(Uri.parse(memo?.picture))
            } catch (e: Exception) {
                e.printStackTrace()
            }

            photoItemView.setOnClickListener {
                Intent(itemView.context, MemoDetailActivity::class.java).apply {
                    putExtra(MEMO_OBJECT_ID, memo?.rowid)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { itemView.context.startActivity(this) }
            }
        }

        companion object {
            fun create(parent: ViewGroup): PhotoViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_photo, parent, false)
                return PhotoViewHolder(view)
            }
        }
    }

    companion object {
        const val MEMO_OBJECT_ID = "MEMO_OBJECT_ID"
    }

}