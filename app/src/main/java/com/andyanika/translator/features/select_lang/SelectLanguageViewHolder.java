package com.andyanika.translator.features.select_lang;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andyanika.translator.R;
import com.andyanika.translator.common.models.LanguageCode;

public class SelectLanguageViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;

    SelectLanguageViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.description);
    }

    public void bind(LanguageCode code) {
        title.setText(code.toString());
    }
}
