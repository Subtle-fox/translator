package com.andyanika.translator.features.select_lang;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.andyanika.translator.R;
import com.andyanika.translator.common.models.LanguageRowModel;
import com.andyanika.translator.ui.ListItemClickListener;

public class SelectLanguageViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;
    private View currentIcon;

    SelectLanguageViewHolder(View itemView, ListItemClickListener clickListener) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.description);
        currentIcon = itemView.findViewById(R.id.view_current);
        itemView.setOnClickListener(v -> clickListener.onClick(getAdapterPosition()));
    }

    public void bind(LanguageRowModel model) {
        title.setText(model.code.toString());
        description.setText(model.description);
        currentIcon.setVisibility(model.isCurrent ? View.VISIBLE : View.GONE);
    }
}