package com.example.newstation.ui.main;

import android.content.Context;
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
import com.example.newstation.news.Function;
import com.example.newstation.news.ListNewsAdapter;
import com.example.newstation.news.NewsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.newstation.MainActivity.textView3;

public class NewsFragment extends Fragment {
    //private static final String API_KEY="ff8c03c87d0048fb8ce9209c6239d52c";
    RecyclerView recyclerView;
    ArrayList<Items> items;
    private listAdapter ListAdapter;
    private Context context;
    private static final String TAG = "News";
public static  int totalResult = 0;
AppDatabase database;

    /////////////////////
    String API_KEY = "ff8c03c87d0048fb8ce9209c6239d52c"; // ### YOUE NEWS API HERE ###
    String NEWS_SOURCE = "techcrunch"; // Other news source code at: https://newsapi.org/sources
    String COUNTRY = "tr";
    String category = "general";
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

    ////////////////////

    final NewsAdapter newsAdapter = new NewsAdapter();
    private PageViewModel pageViewModel;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getDatabaseInstance(getActivity().getBaseContext());

        // For API
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
            getDatafromDatabase newTask = new getDatafromDatabase();
           newTask.execute();
            //Toast.makeText(getActivity().getBaseContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }

//        final RecyclerView mainRecycler = getActivity().findViewById(R.id.section_label);
//        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//        mainRecycler.setLayoutManager(linearLayoutManager);
//        mainRecycler.setAdapter(newsAdapter);
//        final APIInterfaceNews newsService = ApiClient.getClient().create(APIInterfaceNews.class);
//        newsService.getLatestNews("top-headlines","us",API_KEY)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(responseModelNews -> {
////                    List<ArticleNews> articleNews = responseModelNews.getArticles();
//////                    NewsAdapter newsAdapter = new NewsAdapter(articleNews);
////                    newsAdapter.setData(articleNews);
////                    mainRecycler.setAdapter(newsAdapter);
//                });
//


//    private void createList(){
//        items = new ArrayList<>();
//        items.add(new Items("first", "fdgdfg"));
//        items.add(new Items("second", "dfgdf"));
//        items.add(new Items("third", "dfgdfg"));
//        items.add(new Items("fourth", "gfhdfgfd"));
//        items.add(new Items("fifth", "dfhdgjdg"));
//        items.add(new Items("first", "fghdgfh"));
//        items.add(new Items("second", "fghgfhgf"));
//        items.add(new Items("third", "fghfgdfgh"));
//        items.add(new Items("fourth", "fdghgfhgf"));
//        items.add(new Items("fifth", "fghfhdf"));
//        items.add(new Items("first", "fgfgjfgj"));
//        items.add(new Items("second", "shshgfh"));
//        items.add(new Items("third", "gfjdfgj"));
//        items.add(new Items("fourth", "dfgjfg"));
//        items.add(new Items("fifth", "dfgjrthg"));
//        items.add(new Items("first", "jdfgf"));
//        items.add(new Items("second", "dfjgdfg"));
//        items.add(new Items("third", "fdjdfg"));
//        items.add(new Items("fourth", "dfjgjf"));
//        items.add(new Items("fifth", "fgjgfjf"));

        //ListAdapter = new listAdapter(items,getActivity().getBaseContext());
        //recyclerView.setAdapter(ListAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        // recyclerView.setNestedScrollingEnabled(false);

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
               // AppDatabase.destroyInstance();
                dataList.clear();

                if (xml.length() > 10) { // Just checking if not empty

                    try {
                        JSONObject jsonResponse = new JSONObject(xml);
                        JSONArray jsonArray = jsonResponse.optJSONArray("articles");
                        totalResult = jsonArray.length();
                        String num = ""+totalResult+"";
                        textView3.setText(num);

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
                                    ,jsonObject.optString(KEY_URLTOIMAGE),jsonObject.optString(KEY_PUBLISHEDAT),"news");

                            try{
                                database.sportDao().insertOne(sportTable);
                            }
                            catch (SQLiteConstraintException e){
                                Log.e("Insert problem",e.toString());
                            }

                        }
                    } catch (JSONException e) {
                        Toast.makeText(getActivity().getBaseContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                    }

                    ListNewsAdapter adapter = new ListNewsAdapter(NewsFragment.this, dataList);
                    listNews.setAdapter(adapter);

                    listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
//                           Intent i = new Intent(getContext(),DetailsActivity.class);
//                         i.putExtra("url", dataList.get(+position).get(KEY_URL));
//                          startActivity(i);
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

            Cursor cursor = database.query("SELECT  * FROM sport WHERE tag = 'news'",null);

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
            ListNewsAdapter adapter = new ListNewsAdapter(NewsFragment.this, d);

            listNews.setAdapter(adapter);

        }
    }
    }


