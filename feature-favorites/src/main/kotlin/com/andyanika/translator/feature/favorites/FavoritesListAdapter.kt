package com.andyanika.translator.feature.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import core.models.FavoriteModel

internal class FavoritesListAdapter(
    private val action: (FavoriteModel) -> Unit
) : RecyclerView.Adapter<FavoritesViewHolder>() {

    private var dataSet = mutableListOf<FavoriteModel>()

    fun setData(newData: List<FavoriteModel>?) {
        val oldData: List<FavoriteModel> = dataSet.toList()

        dataSet.clear()
        newData?.let(dataSet::addAll)
        val diffUtilsCallback = DiffUtilsCallback(dataSet, oldData)
        DiffUtil.calculateDiff(diffUtilsCallback).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.listview_row, parent, false)
        return FavoritesViewHolder(itemView, action)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val translateResult = dataSet[position]
        holder.bind(translateResult)
    }

    override fun getItemCount(): Int = dataSet.size
}
