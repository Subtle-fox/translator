package com.andyanika.translator.feature.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andyanika.translator.common.models.FavoriteModel
import com.andyanika.translator.common.scopes.FragmentScope
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject
import java.util.*
import javax.inject.Inject

@FragmentScope
class FavoritesListAdapter @Inject internal constructor(subject: Subject<Int?>) :
    RecyclerView.Adapter<FavoritesViewHolder>() {
    private var data: ArrayList<FavoriteModel>
    private val subject: Subject<Int?>

    val observable: Observable<FavoriteModel>
        get() = subject.map { position: Int? -> data[position!!] }

    fun setData(newData: List<FavoriteModel?>?) {
        val oldData: List<FavoriteModel> = data
        data = if (newData == null) ArrayList() else ArrayList(newData)
        val diffUtilsCallback = DiffUtilsCallback(data, oldData)
        DiffUtil.calculateDiff(diffUtilsCallback).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.listview_row, parent, false)
        return FavoritesViewHolder(itemView, subject)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val translateResult = data[position]
        holder.bind(translateResult)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    init {
        data = ArrayList()
        this.subject = subject
    }
}
