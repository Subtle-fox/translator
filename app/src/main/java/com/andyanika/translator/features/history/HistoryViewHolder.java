package com.andyanika.translator.features.history;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.andyanika.translator.R;
import com.andyanika.translator.common.models.TranslateResult;

public class HistoryViewHolder extends RecyclerView.ViewHolder {
	private TextView title;
	private TextView description;

	public HistoryViewHolder(View itemView) {
		super(itemView);
		title = itemView.findViewById(R.id.title);
		description = itemView.findViewById(R.id.description);
	}

	public void bind(TranslateResult result) {
		title.setText(result.translated);
		description.setText(result.source);
	}
}
