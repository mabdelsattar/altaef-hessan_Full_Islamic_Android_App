package com.daralmathour.altaefhessan.Activities;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.EditText;

import com.daralmathour.altaefhessan.AppConfigurations;
import com.daralmathour.altaefhessan.AyatInfo.AllAyatInforamation;
import com.daralmathour.altaefhessan.AyatInfo.AyahInformation;
import com.daralmathour.altaefhessan.R;

import java.util.ArrayList;
import java.util.List;

public class SearchQuranActivity extends AppCompatActivity {

    RecyclerView requestrecycleview;
    SearchAdapter adapter;
    List<SearchViewModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_quran);

        //Build Adapter
        //Build your Model

        data = new ArrayList<SearchViewModel>();

        AllAyatInforamation allAyatInforamation=null;
        allAyatInforamation = new AllAyatInforamation();
        AppConfigurations appConfigurations =new AppConfigurations();


        for(int i= 0 ; i< allAyatInforamation.AllQuranPages.size() ; i++)
        {
            for(int k=0;k<allAyatInforamation.AllQuranPages.get(i).pageAyat.size();k++)
            {
                AyahInformation ayah= allAyatInforamation.AllQuranPages.get(i).pageAyat.get(k);
                data.add(new SearchViewModel(ayah.ayahNumber+"",ayah.soraNumber+"",ayah.ayahContent,appConfigurations.allSoar.get(ayah.soraNumber-1).Name));
            }
        }



        adapter = new SearchAdapter(data,this);
        requestrecycleview = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        requestrecycleview.setLayoutManager(mLayoutManager);
        requestrecycleview.setItemAnimator(new DefaultItemAnimator());
        requestrecycleview.setAdapter(adapter);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        requestrecycleview.addItemDecoration(divider);
        adapter.notifyDataSetChanged();



        SearchView searchView = (SearchView) findViewById(R.id.search);
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        //searchEditText.setTextColor(Color.BLACK);
        //searchEditText.setHintTextColor(Color.GRAY);
        searchEditText.setActivated(true);
        searchView.setFocusable(true);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //adapter.getFilter().filter(newText);
                adapter.getFilter().filter(newText);
                adapter.notifyDataSetChanged();
                return false;
            }


        });


    }
}
