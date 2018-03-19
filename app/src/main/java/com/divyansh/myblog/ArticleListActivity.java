package com.divyansh.myblog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ArticleListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        recyclerView = findViewById(R.id.recyclerView);
        //Step 1 - create an adapter
        recyclerView.setAdapter(new ArticleListAdapter());
        //Step 2 - set a layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public class ArticleListAdapter extends RecyclerView.Adapter<ArticleItemViewHolder>{

        String[] articleList = {
                "article1",
                "article2",
                "article3",
                "article4",
                "article5"
        };

        @Override
        public ArticleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflating the view - layout_article.xml
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_article,parent,false);
            return new ArticleItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ArticleItemViewHolder holder, int position) {
            //setting the data
            holder.articleName.setText(articleList[position]);
        }

        @Override
        public int getItemCount() {
            return articleList.length;
        }
    }
}
