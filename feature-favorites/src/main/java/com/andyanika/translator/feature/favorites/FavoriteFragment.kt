package com.andyanika.translator.feature.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andyanika.translator.common.models.FavoriteModel
import javax.inject.Inject

class FavoriteFragment : Fragment() {
    private var viewModel: FavoritesViewModel? = null

    @Inject
    var adapter: FavoritesListAdapter? = null

    @Inject
    var viewModelFactory: ViewModelProvider.Factory? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoritesViewModel::class.java)
        viewModel!!.data.observe(viewLifecycleOwner, { newData: List<FavoriteModel?>? -> adapter!!.setData(newData) })
        viewModel!!.load(LIMIT)
    }

    override fun onStart() {
        super.onStart()
        viewModel!!.subscribeItemClick(adapter!!.observable)
    }

    override fun onStop() {
        viewModel!!.unsubscribeItemClick()
        super.onStop()
    }

    companion object {
        private const val LIMIT = 100
    }
}
