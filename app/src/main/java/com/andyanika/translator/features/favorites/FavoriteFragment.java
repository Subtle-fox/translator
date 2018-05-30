package com.andyanika.translator.features.favorites;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andyanika.translator.R;
import com.andyanika.translator.features.favorites.di.FavoriteFragmentComponent;
import com.andyanika.translator.features.favorites.di.FavoriteFragmentModule;
import com.andyanika.translator.ui.MainActivity;

import javax.inject.Inject;

public class FavoriteFragment extends Fragment implements FavoriteView {
    @Inject
    FavoritesListAdapter adapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private FavoritesViewModel viewModel;

    private void prepareComponent(MainActivity mainActivity) {
        FavoriteFragmentComponent fragmentComponent = mainActivity.getActivityComponent().plus(new FavoriteFragmentModule());
        fragmentComponent.inject(this);
    }

    @Override
    public void onAttach(Context context) {
        prepareComponent(((MainActivity) context));
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoritesViewModel.class);
        viewModel.data.observe(this, adapter::setData);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.subscribeItemClick(adapter.getSubject());
    }

    @Override
    public void onStop() {
        viewModel.unsubscribeItemClick();
        super.onStop();
    }
}
