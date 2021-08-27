package com.andyanika.translator.feature.favorites

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andyanika.translator.feature.favorites.databinding.ListviewRowBinding
import core.models.FavoriteModel

internal class FavoritesViewHolder(
    itemView: View,
    private val action: (FavoriteModel) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: FavoriteModel) = with(ListviewRowBinding.bind(itemView)) {
        val (textSrc, textDst, direction) = model.translateResult

        title.text = textSrc
        description.text = textDst
        txtLangSrc.text = direction.src.toString()
        txtLangDst.text = direction.dst.toString()
        btnFavorite.apply {
            setImageResource(R.drawable.ic_close)
            setOnClickListener { action.invoke(model) }
        }
    }
}
