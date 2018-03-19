package com.divyansh.myblog;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Divyansh on 19-03-2018.
 */

public class ArticleItemViewHolder extends RecyclerView.ViewHolder{

    TextView articleName;

    public ArticleItemViewHolder(View itemView) {
        super(itemView);

        articleName = itemView.findViewById(R.id.articleName);
    }
}
