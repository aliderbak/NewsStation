package com.example.newstation.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.newstation.R;

import java.util.ArrayList;

public class listAdapter extends RecyclerView.Adapter<listAdapter.MyViewHolder> {

    private ArrayList<Items> items;
    private LayoutInflater inflater;
    private Context ctx;
    public listAdapter(ArrayList <Items> items, Context ctx) {
        this.inflater = LayoutInflater.from(ctx);
        this.items = items;
        this.ctx=ctx;
    }

    @Override
    public listAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View view;
        view = inflater.inflate(R.layout.recrow, parent ,false);
        listAdapter.MyViewHolder holder = new listAdapter.MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final  int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText(items.get(position).getProductName());
        holder.auther.setText(items.get(position).getProductDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx,items.get(position).getProductName() +" Clicked",Toast.LENGTH_LONG).show();
            }
        });

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title,auther;
        public MyViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.productName);
            auther=itemView.findViewById(R.id.productDescription);


        }

    }



    @Override
    public int getItemCount() {
        return items.size();
    }

}
