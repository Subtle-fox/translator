package com.andyanika.translator.feature.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import core.models.FavoriteModel

internal class HistoryListAdapter(
    private val action: (FavoriteModel) -> Unit
) : RecyclerView.Adapter<HistoryViewHolder>() {

    private var dataSet = mutableListOf<FavoriteModel>()

    fun setData(data: List<FavoriteModel>?) {
        dataSet.clear()
        if (data != null) {
            dataSet.addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.listview_row, parent, false)
        return HistoryViewHolder(itemView, action)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val translateResult: FavoriteModel = dataSet[position]
        holder.bind(translateResult)
    }

    override fun getItemCount(): Int = dataSet.size
}
