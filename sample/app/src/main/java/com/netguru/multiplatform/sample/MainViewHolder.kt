package com.netguru.multiplatform.sample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_todolist.*

class MainViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_todolist, parent, false)),
    LayoutContainer {

    override val containerView: View? = itemView

    internal fun bind(item: String) {
        itemTextView.text = item
    }
}
