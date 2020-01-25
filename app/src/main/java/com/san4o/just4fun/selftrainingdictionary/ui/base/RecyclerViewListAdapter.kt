package com.san4o.just4fun.selftrainingdictionary.ui.base

import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewListAdapter<VH : RecyclerView.ViewHolder, Item> : RecyclerView.Adapter<VH>() {

    abstract fun setItem(holder: VH, item: Item)


    private val items = ArrayList<Item>()

    fun refreshItems(items: List<Item>){
        this.items.clear()
        this.items.addAll(items)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item: Item = items[position]
        setItem(holder, item)
    }
}