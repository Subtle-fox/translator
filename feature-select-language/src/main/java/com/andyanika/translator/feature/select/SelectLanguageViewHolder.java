package com.andyanika.translator.feature.select;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.andyanika.translator.common.models.ui.DisplayLanguageModel;

import io.reactivex.rxjava3.subjects.Subject;

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

    public void bind(DisplayLanguageModel model) {
        title.setText(model.getCode().toString());
        description.setText(model.getDescription());
        currentIcon.setVisibility(model.isSelected() ? View.VISIBLE : View.GONE);
    }
}
