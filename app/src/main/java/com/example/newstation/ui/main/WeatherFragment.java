package com.example.newstation.ui.main;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newstation.R;
import com.example.newstation.news.Function;
import com.example.newstation.news.ListNewsAdapter;
import com.example.newstation.weather.ListWeatherAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class WeatherFragment extends Fragment {
    private static final String TAG = "Weather";
    RecyclerView recyclerView;
    ArrayList<Items> items;
    private listAdapter ListAdapter;

    String API_KEY = "ff8c03c87d0048fb8ce9209c6239d52c"; // ### YOUE NEWS API HERE ###
    String NEWS_SOURCE = "techcrunch"; // Other news source code at: https://newsapi.org/sources
    String COUNTRY = "tr";
    String category = "health";
    public ListView listNews;
    ProgressBar loader;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_URL = "url";
    public static final String KEY_URLTOIMAGE = "urlToImage";
    public static final String KEY_PUBLISHEDAT = "publishedAt";

    private PageViewModel pageViewModel;
    public WeatherFragment() {
        // Required empty public constructor
    }

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        pageViewModel.setIndex(TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        listNews = (ListView) view.findViewById(R.id.listNews);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        if (Function.isNetworkAvailable(getActivity().getBaseContext())) {
            DownloadNews newsTask = new DownloadNews();
            newsTask.execute();
        } else {
            Toast.makeText(getActivity().getBaseContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }



    }
    class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            String xml = Function.excuteGet("https://newsapi.org/v2/top-headlines?country=" + COUNTRY + "&category=" + category + "&sortBy=top&apiKey=" + API_KEY);
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            if (xml.length() > 10) { // Just checking if not empty

                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
                        map.put(KEY_AUTHOR, jsonObject.optString(KEY_AUTHOR));
                        map.put(KEY_TITLE, jsonObject.optString(KEY_TITLE));
                        map.put(KEY_DESCRIPTION, jsonObject.optString(KEY_DESCRIPTION));
                        map.put(KEY_URL, jsonObject.optString(KEY_URL));
                        map.put(KEY_URLTOIMAGE, jsonObject.optString(KEY_URLTOIMAGE));
                        map.put(KEY_PUBLISHEDAT, jsonObject.optString(KEY_PUBLISHEDAT));
                        dataList.add(map);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity().getBaseContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                ListWeatherAdapter adapter = new ListWeatherAdapter(WeatherFragment.this, dataList);
                listNews.setAdapter(adapter);

                listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
//                            Intent i = new Intent(MainActivity.this, DetailsActivity.class);
//                            i.putExtra("url", dataList.get(+position).get(KEY_URL));
//                            startActivity(i);
                    }
                });

            } else {
                Toast.makeText(getActivity().getBaseContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
