package com.andyanika.translator.feature.select_lang;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andyanika.resources.Extras;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.DaggerFragment;

public class SelectLanguageFragment extends DaggerFragment {
    @Inject
    SelectLanguageListAdapter adapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private SelectLanguageViewModel viewModel;

    public static SelectLanguageFragment create(String mode) {
        Bundle extra = new Bundle();
        extra.putString(Extras.SELECT_MODE, mode);
        SelectLanguageFragment fragment = new SelectLanguageFragment();
        fragment.setArguments(extra);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        Activity mainActivity = (Activity) context;
        mainActivity.setTitle(R.string.title_select_language);
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelectLanguageViewModel.class);
        viewModel.data.observe(this, adapter::setData);
        if (savedInstanceState == null) {
            viewModel.setMode(getArguments().getString(Extras.SELECT_MODE));
            viewModel.loadData();
        }
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
