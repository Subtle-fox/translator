package com.andyanika.translator.feature.translate;

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
import com.andyanika.translator.common.models.ui.DisplayTranslateResult;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.widget.RxTextView;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class TranslationFragment extends DaggerFragment implements TranslationView {
    @Inject
    TranslationPresenter presenter;

    @Inject
    ScreenRouter router;

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
    }

    @Override
    public void onStart() {
        super.onStart();

        presenter.subscribe(textObservable);
        srcLangBtn.setOnClickListener(v -> router.navigateTo(Screens.SELECT_LANGUAGE, Extras.MODE_SRC));
        dstLangBtn.setOnClickListener(v -> router.navigateTo(Screens.SELECT_LANGUAGE, Extras.MODE_DST));
        swapLangBtn.setOnClickListener(v -> presenter.swapDirection());
        clearBtn.setOnClickListener(v -> presenter.clear());
        retryBtn.setOnClickListener(v -> presenter.translate(editInput.getText().toString()));
    }

    @Override
    public void onStop() {
        presenter.dispose();
        retryBtn.setOnClickListener(null);
        srcLangBtn.setOnClickListener(null);
        dstLangBtn.setOnClickListener(null);

        super.onStop();
    }

    @Override
    public void showNotFound() {
        txtTranslated.setText(R.string.translation_not_found);
    }

    @Override
    public void showTranslation(DisplayTranslateResult response) {
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
    public void setSrcLabel(String text) {
        srcLangBtn.setText(text);
    }

    @Override
    public void setDstLabel(String text) {
        dstLangBtn.setText(text);
    }

    @Override
    public void showOffline() {
        offlineIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideOffline() {
        offlineIcon.setVisibility(View.INVISIBLE);
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
    public void clearResult() {
        editInput.getText().clear();
    }

    @Override
    public void clearTranslation() {
        txtTranslated.setText("");
    }

    @Override
    public void onDestroy() {
        presenter.dispose();
        presenter = null;
        super.onDestroy();
    }
}
