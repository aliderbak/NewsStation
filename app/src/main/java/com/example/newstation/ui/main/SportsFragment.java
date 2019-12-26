package com.example.newstation.ui.main;

import android.database.Cursor;
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
import com.example.newstation.database.AppDatabase;
import com.example.newstation.database.SportTable;
import com.example.newstation.news.Function;
import com.example.newstation.news.ListNewsAdapter;
import com.example.newstation.sport.ListRoomSportAdapter;
import com.example.newstation.sport.ListSportAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.newstation.MainActivity.textView2;

public class SportsFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Items> items;
    private listAdapter ListAdapter;
    String API_KEY = "ff8c03c87d0048fb8ce9209c6239d52c"; // ### YOUE NEWS API HERE ###
    String NEWS_SOURCE = "techcrunch"; // Other news source code at: https://newsapi.org/sources
    String COUNTRY = "tr";
    String category = "sports";
    public ListView listNews;
    ProgressBar loader;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_URL = "url";
    public static final String KEY_URLTOIMAGE = "urlToImage";
    public static final String KEY_PUBLISHEDAT = "publishedAt";
    AppDatabase database;

    private static final String TAG = "Sport";

    private PageViewModel pageViewModel;
    public SportsFragment() {
        // Required empty public constructor
    }

    public static SportsFragment newInstance() {
        return new SportsFragment();
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
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        listNews = (ListView) view.findViewById(R.id.listNews);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        if (Function.isNetworkAvailable(getActivity().getBaseContext())) {

            DownloadNews newTask = new DownloadNews();
            newTask.execute();

      }
        ////////////////////////////////////////////////
      else {
          getDatafromDatabase newTask = new getDatafromDatabase();
          newTask.execute();
        }
//
//            Cursor cursor = database.query("SELECT * FROM sport",null);
//            if (cursor.moveToFirst()){
//                 while (cursor.moveToNext()) {
//                     HashMap<String, String> map = new HashMap<>();
//                     map.put(KEY_AUTHOR, cursor.getString(cursor.getColumnIndex(KEY_AUTHOR)));
//                     map.put(KEY_TITLE, cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
//                     map.put(KEY_DESCRIPTION, cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
//                     map.put(KEY_URL, cursor.getString(cursor.getColumnIndex(KEY_URL)));
//                     map.put(KEY_URLTOIMAGE, cursor.getString(cursor.getColumnIndex(KEY_URLTOIMAGE)));
//                     map.put(KEY_PUBLISHEDAT, cursor.getString(cursor.getColumnIndex("publisherAt")));
//                     dataList.add(map);
//                     Log.e("List8", cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
//                 }
//            }
//
//
//
////             List<SportTable> all = database.sportDao().getAll();
////             if(all.size()>1){
////
////                 Toast.makeText(getActivity().getBaseContext(), "Done", Toast.LENGTH_LONG).show();
////             }
////             else
////            Toast.makeText(getActivity().getBaseContext(), "failed", Toast.LENGTH_LONG).show();
//        }
//        ListSportAdapter adapter = new ListSportAdapter(SportsFragment.this, dataList);
//
//        listNews.setAdapter(adapter);
//////////////////////////////////////////////////


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
            AppDatabase.destroyInstance();
            dataList.clear();


            if (xml.length() > 10) { // Just checking if not empty

                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("articles");
                    textView2.setText(""+jsonArray.length()+"");


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

                        //Database
                        //////////////
                        SportTable sportTable = new SportTable(jsonObject.optString(KEY_AUTHOR),jsonObject.optString(KEY_TITLE)
                                ,jsonObject.optString(KEY_DESCRIPTION),jsonObject.optString(KEY_URL)
                                ,jsonObject.optString(KEY_URLTOIMAGE),jsonObject.optString(KEY_PUBLISHEDAT),"sports");
                        database.sportDao().insertOne(sportTable);


                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity().getBaseContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                ListSportAdapter adapter = new ListSportAdapter(SportsFragment.this, dataList);
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

    class getDatafromDatabase extends AsyncTask<ArrayList,Void,ArrayList>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dataList.clear();
        }
        @Override
        protected ArrayList doInBackground(ArrayList... lists) {

            Cursor cursor = database.query("SELECT * FROM sport WHERE tag = 'sports'",null);

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
        //@RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(ArrayList d){

            Log.e("ArraySize", String.valueOf(d.size()));
            //ArrayList<HashMap<String,String>> da = (ArrayList<HashMap<String,String>>) d.stream().limit(20).collect(Collectors.toList());

           // Log.e("ArraySize", String.valueOf(da.size()));
            ListSportAdapter adapter = new ListSportAdapter(SportsFragment.this, d);

            listNews.setAdapter(adapter);

        }
    }



}
