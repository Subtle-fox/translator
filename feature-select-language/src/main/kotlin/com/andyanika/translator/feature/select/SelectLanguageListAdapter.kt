package com.andyanika.translator.feature.select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import core.models.ui.DisplayLanguageModel

internal class SelectLanguageListAdapter constructor(
    private val action: (DisplayLanguageModel) -> Unit
) : RecyclerView.Adapter<SelectLanguageViewHolder?>() {

    private var dataSet = mutableListOf<DisplayLanguageModel>()

    fun setData(newData: List<DisplayLanguageModel>?) {
        if (newData == null) {
            dataSet.clear()
        } else {
            dataSet.addAll(newData)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectLanguageViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.listview_row_select_language, parent, false)

        return SelectLanguageViewHolder(itemView, action)
    }

    override fun onBindViewHolder(holder: SelectLanguageViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size
}
