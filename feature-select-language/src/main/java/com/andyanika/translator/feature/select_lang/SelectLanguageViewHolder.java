package com.andyanika.translator.feature.select_lang;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.andyanika.translator.common.models.UiLanguageModel;

import io.reactivex.subjects.Subject;

public class SelectLanguageViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;
    private View currentIcon;

    SelectLanguageViewHolder(View itemView, Subject<Integer> subject) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.description);
        currentIcon = itemView.findViewById(R.id.view_current);
        itemView.setOnClickListener(v -> {
            subject.onNext(getAdapterPosition());
            subject.onComplete();
        });
    }

    public void bind(UiLanguageModel model) {
        title.setText(model.code.toString());
        description.setText(model.description);
        currentIcon.setVisibility(model.isCurrent ? View.VISIBLE : View.GONE);
    }
}