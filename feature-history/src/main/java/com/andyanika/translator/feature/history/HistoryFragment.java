package com.andyanika.translator.feature.history;

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
import android.widget.EditText;

import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.DaggerFragment;

public class HistoryFragment extends DaggerFragment {
    private final static int LIMIT = 100;

    @Inject
    HistoryListAdapter adapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private HistoryViewModel viewModel;
    private EditText editInput;
    private View clearBtn;
    private InitialValueObservable<CharSequence> textObservable;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editInput = view.findViewById(R.id.edit_input);
        textObservable = RxTextView.textChanges(editInput);
        clearBtn = view.findViewById(R.id.btn_clear);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HistoryViewModel.class);
        viewModel.data.observe(this, adapter::setData);
        viewModel.showClearBtn.observe(this, show -> {
            if (show) {
                clearBtn.setVisibility(View.VISIBLE);
            } else {
                clearBtn.setVisibility(View.INVISIBLE);
                editInput.setText("");
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        clearBtn.setOnClickListener(v -> viewModel.showClearBtn.setValue(false));
        viewModel.subscribeSearch(textObservable, LIMIT);
        viewModel.subscribeItemClick(adapter.getObservable());
    }

    @Override
    public void onStop() {
        clearBtn.setOnClickListener(null);
        viewModel.unsubscribe();
        super.onStop();
    }
}
