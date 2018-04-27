package com.andyanika.translator.ui;

import android.arch.lifecycle.Observer;
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
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.di.component.FavoriteFragmentComponent;
import com.andyanika.translator.di.module.FavoriteFragmentModule;
import com.andyanika.translator.ui.adapter.HistoryListAdapter;
import com.andyanika.translator.ui.view.FavoriteView;
import com.andyanika.translator.ui.vm.FavoritesViewModel;

import javax.inject.Inject;
import java.util.List;

public class FavoriteFragment extends Fragment implements FavoriteView {
    @Inject
    HistoryListAdapter adapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private RecyclerView recyclerView;

    private void prepareComponent(MainActivity mainActivity) {
        FavoriteFragmentComponent fragmentComponent = mainActivity.getActivityComponent().plus(new FavoriteFragmentModule(this));
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

        FavoritesViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoritesViewModel.class);
        viewModel.data.observe(this, new Observer<List<TranslateResult>>() {
            @Override
            public void onChanged(@Nullable List<TranslateResult> translateResults) {
                adapter.setData(translateResults);
            }
        });
        viewModel.load();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
