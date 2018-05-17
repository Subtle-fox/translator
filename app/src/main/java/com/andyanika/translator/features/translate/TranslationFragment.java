package com.andyanika.translator.features.translate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.andyanika.translator.R;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.di.component.TranslationFragmentComponent;
import com.andyanika.translator.di.module.TranslationFragmentModule;
import com.andyanika.translator.ui.MainActivity;

import javax.inject.Inject;

public class TranslationFragment extends Fragment implements TranslationView {
    @Inject
    TranslationPresenter presenter;

    @Inject
    TranslationTextWatcher textWatcher;

    private EditText editInput;
    private TextView txtTranslated;
    private View progress;
    private View errorLayout;
    private View clearBtn;
    private View retryBtn;

    private void prepareComponent(MainActivity mainActivity) {
        TranslationFragmentComponent fragmentComponent = mainActivity.getActivityComponent().plus(new TranslationFragmentModule(this));
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
        return inflater.inflate(R.layout.fragment_translation, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editInput = view.findViewById(R.id.edit_input);
        txtTranslated = view.findViewById(R.id.txt_translated);
        progress = view.findViewById(R.id.search_progress);
        errorLayout = view.findViewById(R.id.error_layout);
        retryBtn = view.findViewById(R.id.btn_retry);
        clearBtn = view.findViewById(R.id.btn_clear);
    }

    @Override
    public void onStart() {
        super.onStart();
        editInput.addTextChangedListener(textWatcher);
        retryBtn.setOnClickListener(v -> presenter.translate(editInput.getText().toString()));
        clearBtn.setOnClickListener(v -> presenter.clear());
    }

    @Override
    public void onStop() {
        editInput.removeTextChangedListener(textWatcher);
        retryBtn.setOnClickListener(null);
        clearBtn.setOnClickListener(null);
        super.onStop();
    }

    @Override
    public void showTranslation(TranslateResult response) {
        txtTranslated.setText(response.textTranslated);
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorLayout() {
        errorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorLayout() {
        errorLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showClearBtn() {
        clearBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideClearBtn() {
        clearBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void clearTranslation() {
        txtTranslated.setText("");
    }

    @Override
    public void onDestroy() {
        presenter = null;
        textWatcher = null;
        super.onDestroy();
    }
}
