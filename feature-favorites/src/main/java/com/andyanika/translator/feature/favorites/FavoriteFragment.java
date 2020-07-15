package com.andyanika.translator.feature.favorites;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class FavoriteFragment extends DaggerFragment {
    private final static int LIMIT = 100;
    private FavoritesViewModel viewModel;

    @Inject
    FavoritesListAdapter adapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoritesViewModel.class);
        viewModel.data.observe(this, adapter::setData);
        viewModel.load(LIMIT);
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.subscribeItemClick(adapter.getObservable());
    }

    @Override
    public void onStop() {
        viewModel.unsubscribeItemClick();
        super.onStop();
    }
}
