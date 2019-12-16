package com.example.newstation.news;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.newstation.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
    private List<ArticleNews> articleArrayList;
    private Context context;
    public NewsAdapter(List<ArticleNews> articleArrayList){
        this.articleArrayList=articleArrayList;
    }
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recrow,viewGroup,false);
        return new NewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder viewHolder, final int position){
        final ArticleNews articleNews = articleArrayList.get(position);
        if(!TextUtils.isEmpty(articleNews.getTitle())){
            viewHolder.titleText.setText(articleNews.getTitle());
        }
        if(!TextUtils.isEmpty(articleNews.getDescription())) {
            viewHolder.descriptionText.setText(articleNews.getDescription());
        }
        viewHolder.artilceAdapterParentLinear.setTag(articleNews);
        viewHolder.artilceAdapterParentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,articleArrayList.get(position).getTitle() +" Clicked",Toast.LENGTH_LONG).show();

            }
        });

    }
    @Override
    public int getItemCount(){
        return articleArrayList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleText;
        private TextView descriptionText;
        private LinearLayout artilceAdapterParentLinear;
        ViewHolder(View view){
            super(view);
            titleText=view.findViewById(R.id.productName);

            descriptionText = view.findViewById(R.id.productDescription);
            artilceAdapterParentLinear = view.findViewById(R.id.rowlayout);
        }

    }
}
