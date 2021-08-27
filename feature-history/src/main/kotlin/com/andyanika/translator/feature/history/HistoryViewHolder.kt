package com.andyanika.translator.feature.history

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andyanika.translator.feature.history.databinding.ListviewRowBinding
import core.models.FavoriteModel
import core.models.TranslateResult

internal class HistoryViewHolder(
    itemView: View,
    private val action: (FavoriteModel) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: FavoriteModel) = with(ListviewRowBinding.bind(itemView)) {
        val result = model.translateResult
        title.text = result.textSrc
        description.text = result.textDst
        txtLangSrc.text = result.direction.src.toString()
        txtLangDst.text = result.direction.dst.toString()

        btnFavorite.apply {
            setImageResource(R.drawable.ic_star)
            setColorFilter(if (model.isFavorite) Color.RED else Color.BLACK)
            setOnClickListener { action.invoke(model) }
        }
    }
}
