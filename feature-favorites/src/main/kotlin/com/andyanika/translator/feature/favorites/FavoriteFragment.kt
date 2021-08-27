package com.andyanika.translator.feature.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.andyanika.translator.feature.favorites.databinding.FragmentFavoritesBinding
import core.models.FavoriteModel
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FavoriteFragment : ScopeFragment() {
    private val vm by viewModel<FavoritesViewModel>()
    private val action = { model: FavoriteModel -> vm.removeFavorite(model) }
    private val adapter by inject<FavoritesListAdapter> { parametersOf(action) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(FragmentFavoritesBinding.bind(view)) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }


        vm.data.observe(viewLifecycleOwner, adapter::setData)
        vm.load()
    }
}
