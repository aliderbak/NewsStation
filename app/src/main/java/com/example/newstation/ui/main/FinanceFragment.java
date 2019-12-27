package com.example.newstation.ui.main;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newstation.R;
import com.example.newstation.database.AppDatabase;
import com.example.newstation.database.SportTable;
import com.example.newstation.finance.ListFinanceAdapter;
import com.example.newstation.news.DetailsActivity;
import com.example.newstation.news.Function;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.newstation.MainActivity.textView1;

public class FinanceFragment extends Fragment {
    private static final String TAG = "Finance";

    private PageViewModel pageViewModel;

    AppDatabase database;


    /////////////////////
    String API_KEY = "ff8c03c87d0048fb8ce9209c6239d52c"; // ### YOUE NEWS API HERE ###
    String NEWS_SOURCE = "techcrunch"; // Other news source code at: https://newsapi.org/sources
    String COUNTRY = "tr";
    String category = "business";
    public ListView listNews;
    ProgressBar loader;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_URL = "url";
    public static final String KEY_URLTOIMAGE = "urlToImage";
    public static final String KEY_PUBLISHEDAT = "publishedAt";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public FinanceFragment() {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static FinanceFragment newInstance() {
        return new FinanceFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        pageViewModel.setIndex(TAG);
        database = AppDatabase.getDatabaseInstance(getActivity().getBaseContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
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
            getDatafromDatabase newTask = new getDatafromDatabase();
            newTask.execute();
            //Toast.makeText(getActivity().getBaseContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
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
            dataList.clear();

            if (xml.length() > 10) { // Just checking if not empty

                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("articles");
                    String num = "" + jsonArray.length() + "";
                    textView1.setText(num);

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

                        SportTable sportTable = new SportTable(jsonObject.optString(KEY_AUTHOR), jsonObject.optString(KEY_TITLE)
                                , jsonObject.optString(KEY_DESCRIPTION), jsonObject.optString(KEY_URL)
                                , jsonObject.optString(KEY_URLTOIMAGE), jsonObject.optString(KEY_PUBLISHEDAT), "finance");
                        try {
                            database.sportDao().insertOne(sportTable);
                        } catch (SQLiteConstraintException e) {
                            Log.e("Insert problem", e.toString());
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity().getBaseContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                ListFinanceAdapter adapter = new ListFinanceAdapter(FinanceFragment.this, dataList);
                listNews.setAdapter(adapter);

                listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent i = new Intent(getActivity(), DetailsActivity.class);
                        i.putExtra("url", dataList.get(+position).get(KEY_URL));
                        startActivity(i);
                    }
                });

            } else {
                Toast.makeText(getActivity().getBaseContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class getDatafromDatabase extends AsyncTask<ArrayList, Void, ArrayList> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dataList.clear();
        }

        @Override
        protected ArrayList doInBackground(ArrayList... lists) {

            Cursor cursor = database.query("SELECT  * FROM sport WHERE tag = 'finance'", null);

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                HashMap<String, String> map = new HashMap<>();
                map.put(KEY_AUTHOR, cursor.getString(cursor.getColumnIndex(KEY_AUTHOR)));
                map.put(KEY_TITLE, cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                map.put(KEY_DESCRIPTION, cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                map.put(KEY_URL, cursor.getString(cursor.getColumnIndex(KEY_URL)));
                map.put(KEY_URLTOIMAGE, cursor.getString(cursor.getColumnIndex(KEY_URLTOIMAGE)));
                map.put(KEY_PUBLISHEDAT, cursor.getString(cursor.getColumnIndex("publisherAt")));

                Log.e("List8", cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                Log.e("Adet", String.valueOf(cursor.getCount()));
                dataList.add(map);

            }
            cursor.close();

            return dataList;


        }


        @Override
        protected void onPostExecute(ArrayList d) {

            Log.e("ArraySize", String.valueOf(d.size()));

            ListFinanceAdapter adapter = new ListFinanceAdapter(FinanceFragment.this, d);

            listNews.setAdapter(adapter);
            listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Toast.makeText(getActivity().getBaseContext(), "İnternet bağlantısı yok", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


}
