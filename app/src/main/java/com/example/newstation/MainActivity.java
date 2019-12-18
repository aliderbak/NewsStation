package com.example.newstation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newstation.news.ArticleNews;
import com.example.newstation.news.ResponseModelNews;
import com.example.newstation.ui.main.ApiClient;
import com.example.newstation.news.APIInterfaceNews;
import com.example.newstation.ui.main.Movie;
import com.example.newstation.ui.main.MovieResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;



import com.example.newstation.ui.main.SectionsPagerAdapter;

import java.util.List;

import berlin.volders.badger.BadgeDrawable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private BadgeDrawable badge;
    private final static String API_KEY = "ff8c03c87d0048fb8ce9209c6239d52c";
    private static final String TAG = MainActivity.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //For API





        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);


        final TabLayout tabs = findViewById(R.id.tabs);

        tabs.setupWithViewPager(viewPager);
        //tabs.getTabAt(0).setIcon(R.drawable.ic_newspaper);
        //tabs.getTabAt(1).setIcon(R.drawable.ic_bars);
        //tabs.getTabAt(2).setIcon(R.drawable.ic_soccer_ball_variant);
        //tabs.getTabAt(3).setIcon(R.drawable.ic_cloudy_day);

        tabs.getTabAt(0).setCustomView(R.layout.notification_badge);
        final TextView textView = (TextView) tabs.getTabAt(0).getCustomView().findViewById(R.id.textBadge);
        textView.setText("5");
        ImageView imageView = (ImageView)tabs.getTabAt(0).getCustomView().findViewById(R.id.iconBadge);
        imageView.setImageResource(R.drawable.ic_newspaper);

        tabs.getTabAt(1).setCustomView(R.layout.notification_badge);
        TextView textView1 = (TextView) tabs.getTabAt(1).getCustomView().findViewById(R.id.textBadge);
        textView1.setText("99");
        ImageView imageView1 = (ImageView)tabs.getTabAt(1).getCustomView().findViewById(R.id.iconBadge);
        imageView1.setImageResource(R.drawable.ic_bars);

        tabs.getTabAt(2).setCustomView(R.layout.notification_badge);
        TextView textView2 = (TextView) tabs.getTabAt(2).getCustomView().findViewById(R.id.textBadge);
        textView2.setText("35");
        ImageView imageView2 = (ImageView)tabs.getTabAt(2).getCustomView().findViewById(R.id.iconBadge);
        imageView2.setImageResource(R.drawable.ic_soccer_ball_variant);

        tabs.getTabAt(3).setCustomView(R.layout.notification_badge);
        final TextView textView3 = (TextView) tabs.getTabAt(3).getCustomView().findViewById(R.id.textBadge);
        textView3.setText("13");
        ImageView imageView3 = (ImageView)tabs.getTabAt(3).getCustomView().findViewById(R.id.iconBadge);
        imageView3.setImageResource(R.drawable.ic_cloudy_day);


        tabs.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                int position = tab.getPosition();
                final TextView textView4 =(TextView)tabs.getTabAt(position).getCustomView().findViewById(R.id.textBadge);
                textView4.setVisibility(View.GONE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                final TextView textView5 =(TextView)tabs.getTabAt(position).getCustomView().findViewById(R.id.textBadge);
                textView5.setVisibility(View.VISIBLE);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });





    }


}