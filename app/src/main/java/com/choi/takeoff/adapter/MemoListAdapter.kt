package com.choi.takeoff.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.choi.takeoff.R
import com.choi.takeoff.db.entity.Memo

class MemoListAdapter : ListAdapter<Memo, MemoListAdapter.MemoViewHolder>(MemosComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        return MemoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.content)
    }

    class MemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val memoItemView: TextView = itemView.findViewById(R.id.textView_recycler)

        fun bind(text: String?) {
            memoItemView.text = text.toString()
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

}