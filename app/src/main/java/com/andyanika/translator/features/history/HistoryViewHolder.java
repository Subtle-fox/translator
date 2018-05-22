package com.andyanika.translator.features.history;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andyanika.translator.R;
import com.andyanika.translator.common.models.TranslationRowModel;
import com.andyanika.translator.ui.OnClickListener;

public class HistoryViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;
    private ImageButton favoriteButton;

    HistoryViewHolder(View itemView, final OnClickListener clickListener) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.description);
        favoriteButton = itemView.findViewById(R.id.btn_favorite);
        favoriteButton.setImageResource(R.drawable.ic_star);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(getAdapterPosition());
            }
        });
    }

    public void bind(TranslationRowModel model) {
        title.setText(model.translateResult.textSrc);
        description.setText(model.translateResult.textTranslated);
        int color = model.isFavorite ? Color.RED : Color.BLACK;
        favoriteButton.setColorFilter(color);
    }
}
