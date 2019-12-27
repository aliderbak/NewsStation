package com.example.newstation.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.newstation.R;
import com.example.newstation.ui.main.NewsFragment;
import com.example.newstation.ui.main.WeatherFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ListWeatherAdapter extends BaseAdapter {
    private WeatherFragment activity;
    private ArrayList<HashMap<String, String>> data;

    public ListWeatherAdapter(WeatherFragment a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        com.example.newstation.weather.ListWeatherViewHolder holder = null;
        if (convertView == null) {
            holder = new com.example.newstation.weather.ListWeatherViewHolder();
            convertView = LayoutInflater.from(activity.getActivity().getBaseContext()).inflate(
                    R.layout.list_row, parent, false);
            holder.galleryImage = (ImageView) convertView.findViewById(R.id.galleryImage);
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.sdetails = (TextView) convertView.findViewById(R.id.sdetails);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (com.example.newstation.weather.ListWeatherViewHolder) convertView.getTag();
        }
        holder.galleryImage.setId(position);
        holder.author.setId(position);
        holder.title.setId(position);
        holder.sdetails.setId(position);
        holder.time.setId(position);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        try{
            holder.author.setText(song.get(WeatherFragment.KEY_AUTHOR));
            holder.title.setText(song.get(WeatherFragment.KEY_TITLE));
            holder.time.setText(song.get(WeatherFragment.KEY_PUBLISHEDAT));
            holder.sdetails.setText(song.get(WeatherFragment.KEY_DESCRIPTION));

            if(song.get(WeatherFragment.KEY_URLTOIMAGE).toString().length() < 5)
            {
                holder.galleryImage.setVisibility(View.GONE);
            }else{
                Picasso.get()
                        .load(song.get(WeatherFragment.KEY_URLTOIMAGE))
                        .resize(300, 200)
                        .centerCrop()
                        .into(holder.galleryImage);
            }
        }catch(Exception e) {}
        return convertView;
    }
}

class ListWeatherViewHolder {
    ImageView galleryImage;
    TextView author, title, sdetails, time;
}