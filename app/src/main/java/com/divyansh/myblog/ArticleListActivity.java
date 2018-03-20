package com.divyansh.myblog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArticleListAdapter articleListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        articleListAdapter = new ArticleListAdapter();

        recyclerView = findViewById(R.id.recyclerView);
        //Step 1 - create an adapter
        recyclerView.setAdapter(articleListAdapter);
        //Step 2 - set a layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Todo: Progress Indicator

        final AuthenticationActivity auth = new AuthenticationActivity();
        auth.showProgressDialog(true);
        ApiManager.getApiInterface().getArticles()
                .enqueue(new Callback<List<Article>>() {
                    @Override
                    public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                        auth.showProgressDialog(false);
                        if(response.isSuccessful()){
                            articleListAdapter.setData(response.body());
                        }else {
                            //show alert
                            auth.ShowAlert("Failed","Try again later");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Article>> call, Throwable t) {
                        //show alert
                        auth.showProgressDialog(false);
                        auth.ShowAlert("Failed","Try again later");
                    }
                });
    }

    public class ArticleListAdapter extends RecyclerView.Adapter<ArticleItemViewHolder>{

        List<Article> articleList = new ArrayList<>();

        @Override
        public ArticleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflating the view - layout_article.xml
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_article,parent,false);
            return new ArticleItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ArticleItemViewHolder holder, final int position) {
            //setting the data
            holder.articleName.setText(articleList.get(position).getHeading());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ArticleListActivity.this,"Article Clicked :"+articleList.get(position).getHeading(),Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return articleList.size();
        }

        public void setData(List<Article> data){
            this.articleList = data;
            this.notifyDataSetChanged();
        }
    }
}
