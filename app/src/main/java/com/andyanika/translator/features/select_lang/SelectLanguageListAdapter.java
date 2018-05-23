package com.andyanika.translator.features.select_lang;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andyanika.translator.R;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.LanguageDirection;
import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.ui.OnClickListener;
import com.andyanika.translator.ui.Screens;
import com.andyanika.usecases.SelectLanguageUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@FragmentScope
public class SelectLanguageListAdapter extends RecyclerView.Adapter<SelectLanguageViewHolder> {
    private ArrayList<LanguageCode> data = new ArrayList<>();
    private SelectLanguageUseCase selectLanguageUseCase;
    private Router router;

    @Inject
    public SelectLanguageListAdapter(SelectLanguageUseCase selectLanguageUseCase, Router router) {
        this.selectLanguageUseCase = selectLanguageUseCase;
        this.router = router;
    }

    private final OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(final int position) {
            selectLanguageUseCase.run(new LanguageDirection(LanguageCode.RU, LanguageCode.EN));
            router.backTo(Screens.TRANSLATION_SCREEN);
        }
    };

    public void setData(@Nullable List<LanguageCode> newData) {
        data = newData == null ? new ArrayList<>() : new ArrayList<>(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SelectLanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_row_select_language, parent, false);
        return new SelectLanguageViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectLanguageViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
