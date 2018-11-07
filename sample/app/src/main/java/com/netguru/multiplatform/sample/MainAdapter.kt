package com.netguru.multiplatform.sample

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlin.properties.Delegates

class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {

    internal var itemsList by Delegates.observable(emptyList<String>()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(parent)

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) = holder.bind(itemsList[position])

    override fun getItemCount() = itemsList.count()
}
