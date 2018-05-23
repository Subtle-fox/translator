package com.andyanika.translator.features.favorites;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andyanika.translator.R;
import com.andyanika.translator.common.models.TranslationRowModel;
import com.andyanika.translator.ui.OnClickListener;

public class FavoritesViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;

    FavoritesViewHolder(View itemView, final OnClickListener clickListener) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.description);
        ImageButton favoriteButton = itemView.findViewById(R.id.btn_favorite);
        favoriteButton.setImageResource(R.drawable.ic_close);
        favoriteButton.setOnClickListener(v -> clickListener.onClick(getAdapterPosition()));
    }

    public void bind(TranslationRowModel model) {
        title.setText(model.translateResult.textSrc);
        description.setText(model.translateResult.textTranslated);
    }
}
