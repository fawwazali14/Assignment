package com.example.fetech.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fetech.R
import com.example.fetech.models.items_data
import com.example.fetech.models.items_dataItem


class SectionedRecyclerViewAdapter(private val dataList: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    class SectionHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sectionHeaderTextView: TextView = itemView.findViewById(R.id.sectionTitleTextView)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        val itemIfTextView: TextView = itemView.findViewById(R.id.itemidTextView)
    }



    override fun getItemViewType(position: Int): Int {
        return if (dataList[position] is Int) {
            VIEW_TYPE_SECTION_HEADER
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SECTION_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.sec_header, parent, false)
                SectionHeaderViewHolder(view)
            }
            VIEW_TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.sec_items, parent, false)
                ItemViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        when (holder) {
            is SectionHeaderViewHolder -> {
                val sectionHeader = item as Int
                holder.sectionHeaderTextView.text = "List ID  $sectionHeader"
            }
            is ItemViewHolder -> {
                val itemData = item as items_dataItem
                holder.itemNameTextView.text = "Name - ${itemData.name}"
                holder.itemIfTextView.text = "ID -  ${itemData.id}"



            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    companion object {
        const val VIEW_TYPE_SECTION_HEADER = 0
        const val VIEW_TYPE_ITEM = 1
    }
}

