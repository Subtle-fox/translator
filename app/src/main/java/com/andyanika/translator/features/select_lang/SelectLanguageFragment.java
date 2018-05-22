package com.andyanika.translator.features.select_lang;

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
import com.andyanika.translator.di.component.SelectLanguageComponent;
import com.andyanika.translator.di.module.SelectLanguageModule;
import com.andyanika.translator.ui.MainActivity;

import javax.inject.Inject;

public class SelectLanguageFragment extends Fragment implements SelectLanguageView {
    @Inject
    SelectLanguageListAdapter adapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private void prepareComponent(MainActivity mainActivity) {
        SelectLanguageComponent fragmentComponent = mainActivity.getActivityComponent().plus(new SelectLanguageModule(this));
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
        return inflater.inflate(R.layout.fragment_select_language, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SelectLanguageViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelectLanguageViewModel.class);
        viewModel.data.observe(this, adapter::setData);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
