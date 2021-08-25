package com.andyanika.translator.feature.select;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andyanika.translator.common.constants.Extras;

import javax.inject.Inject;


public class SelectLanguageFragment extends Fragment {
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
        viewModel.data.observe(getViewLifecycleOwner(), adapter::setData);
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
