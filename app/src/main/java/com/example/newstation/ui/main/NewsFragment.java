package com.example.newstation.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.newstation.R;
import com.example.newstation.ui.main.Movie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment  {




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
        // Inflate the layout for this fragment


        View root = inflater.inflate(R.layout.fragment_main, container, false);

         recyclerView = (RecyclerView) root.findViewById(R.id.section_label);
         final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);



        recyclerView.setLayoutManager(layoutManager);
        createList();








        /*final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }
    private void createList(){
        items = new ArrayList<>();
        items.add(new Items("first", "fdgdfg"));
        items.add(new Items("second", "dfgdf"));
        items.add(new Items("third", "dfgdfg"));
        items.add(new Items("fourth", "gfhdfgfd"));
        items.add(new Items("fifth", "dfhdgjdg"));
        items.add(new Items("first", "fghdgfh"));
        items.add(new Items("second", "fghgfhgf"));
        items.add(new Items("third", "fghfgdfgh"));
        items.add(new Items("fourth", "fdghgfhgf"));
        items.add(new Items("fifth", "fghfhdf"));
        items.add(new Items("first", "fgfgjfgj"));
        items.add(new Items("second", "shshgfh"));
        items.add(new Items("third", "gfjdfgj"));
        items.add(new Items("fourth", "dfgjfg"));
        items.add(new Items("fifth", "dfgjrthg"));
        items.add(new Items("first", "jdfgf"));
        items.add(new Items("second", "dfjgdfg"));
        items.add(new Items("third", "fdjdfg"));
        items.add(new Items("fourth", "dfjgjf"));
        items.add(new Items("fifth", "fgjgfjf"));

        ListAdapter = new listAdapter(items,getActivity().getBaseContext());
        recyclerView.setAdapter(ListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setNestedScrollingEnabled(false);
    }



}
