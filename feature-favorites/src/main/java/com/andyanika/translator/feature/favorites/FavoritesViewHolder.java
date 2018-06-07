package com.andyanika.translator.feature.favorites;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.UiTranslationModel;

import io.reactivex.Observer;

public class FavoritesViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;
    private TextView langSrc;
    private TextView langDst;

    FavoritesViewHolder(View itemView, final Observer<Integer> clickObserver) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.description);
        langSrc = itemView.findViewById(R.id.txt_lang_src);
        langDst = itemView.findViewById(R.id.txt_lang_dst);
        ImageButton favoriteButton = itemView.findViewById(R.id.btn_favorite);
        favoriteButton.setImageResource(R.drawable.ic_close);
        favoriteButton.setOnClickListener(v -> clickObserver.onNext(getAdapterPosition()));
    }

    public void bind(UiTranslationModel model) {
        TranslateResult result = model.translateResult;

        title.setText(result.textSrc);
        description.setText(result.textTranslated);
        langSrc.setText(result.direction.src.toString());
        langDst.setText(result.direction.dst.toString());
    }
}
