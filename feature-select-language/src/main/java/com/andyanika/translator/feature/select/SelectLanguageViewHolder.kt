package com.andyanika.translator.feature.select

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andyanika.translator.common.models.ui.DisplayLanguageModel

internal class SelectLanguageViewHolder(
    itemView: View,
    private val action: (DisplayLanguageModel) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val description: TextView = itemView.findViewById(R.id.description)
    private val currentIcon: View = itemView.findViewById(R.id.view_current)

    fun bind(model: DisplayLanguageModel) {
        title.text = model.code.toString()
        description.text = model.description
        currentIcon.visibility = if (model.isSelected) View.VISIBLE else View.GONE
        itemView.setOnClickListener { action.invoke(model) }
    }
}
