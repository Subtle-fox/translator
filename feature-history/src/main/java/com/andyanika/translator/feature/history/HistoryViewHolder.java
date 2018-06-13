package com.andyanika.translator.feature.history;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andyanika.translator.common.models.FavoriteModel;
import com.andyanika.translator.common.models.TranslateResult;

import io.reactivex.Observer;

public class HistoryViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;
    private TextView langSrc;
    private TextView langDst;
    private ImageButton favoriteButton;

    HistoryViewHolder(View itemView, final Observer<Integer> clickObserver) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.description);
        langSrc = itemView.findViewById(R.id.txt_lang_src);
        langDst = itemView.findViewById(R.id.txt_lang_dst);
        favoriteButton = itemView.findViewById(R.id.btn_favorite);
        favoriteButton.setImageResource(R.drawable.ic_star);
        favoriteButton.setOnClickListener(v -> clickObserver.onNext(getAdapterPosition()));
    }

    public void bind(FavoriteModel model) {
        TranslateResult result = model.translateResult;

        title.setText(result.textSrc);
        description.setText(result.textDst);
        langSrc.setText(result.direction.src.toString());
        langDst.setText(result.direction.dst.toString());
        int color = model.isFavorite ? Color.RED : Color.BLACK;
        favoriteButton.setColorFilter(color);
    }
}
