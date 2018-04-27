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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.andyanika.translator.R;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.di.component.HistoryFragmentComponent;
import com.andyanika.translator.di.module.HistoryFragmentModule;
import com.andyanika.translator.ui.adapter.HistoryListAdapter;
import com.andyanika.translator.ui.view.HistoryView;
import com.andyanika.translator.ui.vm.HistoryViewModel;

import javax.inject.Inject;
import java.util.List;

public class HistoryFragment extends Fragment implements HistoryView {
    @Inject
    HistoryListAdapter adapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private HistoryTextWatcher textWatcher;
    private EditText editInput;
    private RecyclerView recyclerView;

    private void prepareComponent(MainActivity mainActivity) {
        HistoryFragmentComponent fragmentComponent = mainActivity.getActivityComponent().plus(new HistoryFragmentModule(this));
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
        return inflater.inflate(R.layout.fragment_history, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HistoryViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(HistoryViewModel.class);
        viewModel.data.observe(this, new Observer<List<TranslateResult>>() {
            @Override
            public void onChanged(@Nullable List<TranslateResult> translateResults) {
                adapter.setData(translateResults);
            }
        });
        viewModel.load();

        textWatcher = new HistoryTextWatcher(viewModel);

        editInput = view.findViewById(R.id.edit_input);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        editInput.addTextChangedListener(textWatcher);
    }

    @Override
    public void onStop() {
        editInput.removeTextChangedListener(textWatcher);
        super.onStop();
    }

    class HistoryTextWatcher implements TextWatcher {
        private HistoryViewModel viewModel;

        HistoryTextWatcher(HistoryViewModel viewModel) {
            this.viewModel = viewModel;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            viewModel.filter(s.toString());
        }
    }
}
