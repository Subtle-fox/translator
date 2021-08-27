package com.andyanika.translator.feature.favorites

import androidx.recyclerview.widget.DiffUtil
import core.models.FavoriteModel

class DiffUtilsCallback internal constructor(
    private val newData: List<FavoriteModel>,
    private val oldData: List<FavoriteModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].id == newData[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSame(oldItemPosition, newItemPosition)
    }
}
