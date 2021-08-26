package com.andyanika.translator.feature.favorites

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andyanika.translator.common.models.FavoriteModel
import io.reactivex.rxjava3.core.Observer

class FavoritesViewHolder internal constructor(itemView: View, clickObserver: Observer<Int?>) :
    RecyclerView.ViewHolder(itemView) {
    private val title: TextView
    private val description: TextView
    private val langSrc: TextView
    private val langDst: TextView
    fun bind(model: FavoriteModel) {
        val (textSrc, textDst, direction) = model.translateResult
        title.text = textSrc
        description.text = textDst
        langSrc.text = direction.src.toString()
        langDst.text = direction.dst.toString()
    }

    init {
        title = itemView.findViewById(R.id.title)
        description = itemView.findViewById(R.id.description)
        langSrc = itemView.findViewById(R.id.txt_lang_src)
        langDst = itemView.findViewById(R.id.txt_lang_dst)
        val favoriteButton = itemView.findViewById<ImageButton>(R.id.btn_favorite)
        favoriteButton.setImageResource(R.drawable.ic_close)
        favoriteButton.setOnClickListener { v: View? -> clickObserver.onNext(adapterPosition) }
    }
}
