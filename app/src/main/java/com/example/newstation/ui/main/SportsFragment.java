package com.example.newstation.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newstation.R;

import java.util.ArrayList;

public class SportsFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Items> items;
    private listAdapter ListAdapter;

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
        return root;
    }

    private void createList(){
        items = new ArrayList<>();
        items.add(new Items("sixth", "الأول"));
        items.add(new Items("seven", "الثاني"));
        items.add(new Items("eight", "الثالث"));
        items.add(new Items("nine", "الرابع"));
        items.add(new Items("ten", "الخامس"));

        ListAdapter = new listAdapter(items,getActivity().getBaseContext());
        recyclerView.setAdapter(ListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setNestedScrollingEnabled(false);
    }

}
