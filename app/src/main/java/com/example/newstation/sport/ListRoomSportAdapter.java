package com.example.newstation.sport;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.newstation.R;
import com.example.newstation.database.SportTable;
import com.example.newstation.ui.main.SportsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListRoomSportAdapter implements ListAdapter {
    private SportsFragment activity;
    private ArrayList<HashMap<String, String>> data;

    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    public ListRoomSportAdapter(SportsFragment a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    public int getCount() {
        return data.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        com.example.newstation.sport.ListRoomSportViewHolder holder = null;
        if (convertView == null) {
            holder = new com.example.newstation.sport.ListRoomSportViewHolder();
            convertView = LayoutInflater.from(activity.getActivity().getBaseContext()).inflate(
                    R.layout.list_row, parent, false);
            holder.galleryImage = (ImageView) convertView.findViewById(R.id.galleryImage);
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.sdetails = (TextView) convertView.findViewById(R.id.sdetails);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (com.example.newstation.sport.ListRoomSportViewHolder) convertView.getTag();
        }
        holder.galleryImage.setId(position);
        holder.author.setId(position);
        holder.title.setId(position);
        holder.sdetails.setId(position);
        holder.time.setId(position);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        ////////room

        try{
            holder.author.setText((CharSequence) song.get(SportsFragment.KEY_AUTHOR));
            holder.title.setText((CharSequence) song.get(SportsFragment.KEY_TITLE));
            holder.time.setText((CharSequence) song.get(SportsFragment.KEY_PUBLISHEDAT));
            holder.sdetails.setText((CharSequence) song.get(SportsFragment.KEY_DESCRIPTION));

            if(song.get(SportsFragment.KEY_URLTOIMAGE).toString().length() < 5)
            {
                holder.galleryImage.setVisibility(View.GONE);
            }else{
                Picasso.get()
                        .load(String.valueOf(song.get(SportsFragment.KEY_URLTOIMAGE)))
                        .resize(300, 200)
                        .centerCrop()
                        .into(holder.galleryImage);
            }
        }catch(Exception e) {}
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}

class ListRoomSportViewHolder {
    ImageView galleryImage;
    TextView author, title, sdetails, time;
}