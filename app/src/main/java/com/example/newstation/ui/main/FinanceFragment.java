package com.example.newstation.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newstation.R;
import com.example.newstation.ui.main.Items;
import com.example.newstation.ui.main.PageViewModel;
import com.example.newstation.ui.main.listAdapter;

import java.util.ArrayList;

public class FinanceFragment extends Fragment {
    private static final String TAG = "Finance";

    private PageViewModel pageViewModel;
    RecyclerView recyclerView;
    ArrayList<Items> items;
    private listAdapter ListAdapter;

    public FinanceFragment() {
        // Required empty public constructor
    }

    public static FinanceFragment newInstance() {
        return new FinanceFragment();
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
