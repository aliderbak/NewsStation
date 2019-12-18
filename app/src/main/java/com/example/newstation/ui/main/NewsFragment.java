package com.example.newstation.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.newstation.R;
import com.example.newstation.news.APIInterfaceNews;
import com.example.newstation.news.ApiClient;
import com.example.newstation.news.ArticleNews;
import com.example.newstation.news.NewsAdapter;
import com.example.newstation.news.ResponseModelNews;
import com.example.newstation.ui.main.Movie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment  {
    private static final String API_KEY="ff8c03c87d0048fb8ce9209c6239d52c";




    RecyclerView recyclerView;
    ArrayList<Items>items;
    private listAdapter ListAdapter;
    private Context context;
    private static final String TAG = "News";

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

        // For API






        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        pageViewModel.setIndex(TAG);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main,container,false);




    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView mainRecycler = getActivity().findViewById(R.id.section_label);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mainRecycler.setLayoutManager(linearLayoutManager);

        final APIInterfaceNews newsService = ApiClient.getClient().create(APIInterfaceNews.class);
        newsService.getLatestNews("top-headlines","us",API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseModelNews -> {
                    List<ArticleNews> articleNews = responseModelNews.getArticles();
                    NewsAdapter newsAdapter = new NewsAdapter(articleNews);
                    mainRecycler.setAdapter(newsAdapter);
                });


    }
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




