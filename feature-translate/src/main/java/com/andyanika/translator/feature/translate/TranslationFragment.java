package com.andyanika.translator.feature.translate;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andyanika.translator.common.constants.Extras;
import com.andyanika.translator.common.constants.Screens;
import com.andyanika.translator.common.interfaces.ScreenRouter;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class TranslationFragment extends DaggerFragment {
    @Inject
    ScreenRouter router;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private EditText editInput;
    private TextView txtTranslated;
    private View progress;
    private View errorLayout;
    private View clearBtn;
    private View retryBtn;
    private View offlineIcon;
    private Button srcLangBtn;
    private Button dstLangBtn;
    private ImageButton swapLangBtn;
    private InitialValueObservable<CharSequence> textObservable;
    private TranslationViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_translation, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editInput = view.findViewById(R.id.edit_input);
        textObservable = RxTextView.textChanges(editInput);

        txtTranslated = view.findViewById(R.id.txt_translated);
        progress = view.findViewById(R.id.search_progress);
        errorLayout = view.findViewById(R.id.error_layout);
        retryBtn = view.findViewById(R.id.btn_retry);
        clearBtn = view.findViewById(R.id.btn_clear);
        srcLangBtn = view.findViewById(R.id.btn_lang_src);
        dstLangBtn = view.findViewById(R.id.btn_lang_dst);
        swapLangBtn = view.findViewById(R.id.btn_lang_swap);
        offlineIcon = view.findViewById(R.id.icon_offline);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TranslationViewModel.class);
        observe(viewModel);
    }

    @Override
    public void onStart() {
        super.onStart();

        viewModel.subscribe(textObservable);
        srcLangBtn.setOnClickListener(v -> router.navigateTo(Screens.SELECT_LANGUAGE, Extras.MODE_SRC));
        dstLangBtn.setOnClickListener(v -> router.navigateTo(Screens.SELECT_LANGUAGE, Extras.MODE_DST));
        swapLangBtn.setOnClickListener(v -> viewModel.swapDirection());
        clearBtn.setOnClickListener(v -> setSource(""));
        retryBtn.setOnClickListener(v -> viewModel.translate(editInput.getText().toString()));
    }

    @Override
    public void onStop() {
        viewModel.unsubscribe();
        retryBtn.setOnClickListener(null);
        srcLangBtn.setOnClickListener(null);
        dstLangBtn.setOnClickListener(null);

        super.onStop();
    }

    private void observe(TranslationViewModel viewModel) {
        viewModel.showClearBtn.observe(this, this::showClearBtn);
        viewModel.showProgress.observe(this, this::showProgress);
        viewModel.isError.observe(this, this::showErrorLayout);
        viewModel.isOffline.observe(this, this::showOffline);
        viewModel.srcLabel.observe(this, this::setSrcLabel);
        viewModel.dstLabel.observe(this, this::setDstLabel);
        viewModel.dstString.observe(this, this::setTranslation);
        viewModel.srcString.observe(this, this::setSource);
    }

    int toVisibility(Boolean isVisible) {
        return isVisible != null && isVisible ? View.VISIBLE : View.INVISIBLE;
    }

    public void setTranslation(CharSequence textTranslation) {
        txtTranslated.setText(textTranslation);
    }

    public void setSource(CharSequence charSequence) {
        editInput.setText(charSequence);
    }

    public void showProgress(Boolean isVisible) {
        progress.setVisibility(toVisibility(isVisible));
    }

    public void showErrorLayout(Boolean isVisible) {
        errorLayout.setVisibility(toVisibility(isVisible));
    }

    public void setSrcLabel(String text) {
        srcLangBtn.setText(text);
    }

    public void setDstLabel(String text) {
        dstLangBtn.setText(text);
    }

    public void showOffline(Boolean isVisible) {
        offlineIcon.setVisibility(toVisibility(isVisible));
    }

    public void showClearBtn(Boolean isVisible) {
        clearBtn.setVisibility(toVisibility(isVisible));
    }
}
