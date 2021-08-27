package com.andyanika.translator.feature.select

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andyanika.translator.feature.select.databinding.ListviewRowSelectLanguageBinding
import core.models.ui.DisplayLanguageModel

internal class SelectLanguageViewHolder(
    itemView: View,
    private val action: (DisplayLanguageModel) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: DisplayLanguageModel) = with(ListviewRowSelectLanguageBinding.bind(itemView)) {
        title.text = model.code.toString()
        description.text = model.description
        selectedIcon.visibility = if (model.isSelected) View.VISIBLE else View.GONE
        itemView.setOnClickListener { action.invoke(model) }
    }
}
