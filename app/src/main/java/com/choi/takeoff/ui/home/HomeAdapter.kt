package com.choi.takeoff.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.choi.takeoff.DeleteMemoFragment
import com.choi.takeoff.MemoDetailActivity
import com.choi.takeoff.R
import com.choi.takeoff.db.entity.Memo
import com.choi.takeoff.util.Converters
import com.choi.takeoff.util.StorageManager


class HomeAdapter :
    ListAdapter<Memo, HomeAdapter.MemoViewHolder>(MemosComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        return MemoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class MemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageRecyclerItem: ImageView = itemView.findViewById(R.id.image_recycler_item)
        val title: TextView = itemView.findViewById(R.id.text_recycler_title)
        val time: TextView = itemView.findViewById(R.id.text_recycler_time)

        fun bind(memo: Memo?) {
            memo?.picture?.let {
                val bytes = StorageManager.readFile(it, itemView.context)
                imageRecyclerItem.setImageBitmap(Converters.byteArrayToReducedBitmap(bytes,4))
            }

            title.text = memo?.content
            time.text = memo?.time

            itemView.setOnClickListener {
                Intent(itemView.context, MemoDetailActivity::class.java).apply {
                    putExtra(MEMO_OBJECT_ID, memo?.rowid)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { itemView.context.startActivity(this) }
            }

            itemView.setOnLongClickListener {
                val activity: AppCompatActivity = itemView.context as AppCompatActivity
                val mFragment = memo?.rowid?.let { it1 -> DeleteMemoFragment(it1) }
                mFragment?.show(activity.supportFragmentManager, "confirm delete")

                true
            }
        }

        companion object {
            fun create(parent: ViewGroup): MemoViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return MemoViewHolder(view)
            }
        }
    }

    class MemosComparator : DiffUtil.ItemCallback<Memo>() {
        override fun areItemsTheSame(oldItem: Memo, newItem: Memo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Memo, newItem: Memo): Boolean {
            return oldItem.rowid == newItem.rowid
        }
    }

    companion object {
        val MEMO_OBJECT_ID = "MEMO_OBJECT_ID"
    }

}