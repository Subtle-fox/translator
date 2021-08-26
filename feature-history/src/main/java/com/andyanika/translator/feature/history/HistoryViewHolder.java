package com.andyanika.translator.feature.history;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andyanika.translator.common.models.FavoriteModel;
import com.andyanika.translator.common.models.TranslateResult;

import io.reactivex.rxjava3.core.Observer;

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
        TranslateResult result = model.getTranslateResult();

        title.setText(result.getTextSrc());
        description.setText(result.getTextDst());
        langSrc.setText(result.getDirection().getSrc().toString());
        langDst.setText(result.getDirection().getDst().toString());
        int color = model.isFavorite() ? Color.RED : Color.BLACK;
        favoriteButton.setColorFilter(color);
    }
}
