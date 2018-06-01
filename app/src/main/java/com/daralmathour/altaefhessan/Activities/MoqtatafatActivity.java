package com.daralmathour.altaefhessan.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.daralmathour.altaefhessan.Models.Movie;
import com.daralmathour.altaefhessan.MoviesAdapter;
import com.daralmathour.altaefhessan.R;

import java.util.ArrayList;
import java.util.List;

public class MoqtatafatActivity extends AppCompatActivity {

    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moqtatafat);

        getSupportActionBar().setTitle("مقتطفات ايمانية");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MoviesAdapter(movieList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareMovieData();

    }

    private void prepareMovieData() {

        Movie movie = new Movie("السديس يزلزل منبر الحرم بخطبة فكرية وبلغةٍ فصيحة قوية\n","_VfHsTqTkMc",R.drawable.s1);
        movieList.add(movie);

        movie = new Movie("الغيرة على الأعراض\n","S9vQNKTMHBs",R.drawable.s2);
        movieList.add(movie);

        movie = new Movie("أغرب توبة يحكيها الشيخ العريفي\n","szD8kO2XKwQ",R.drawable.s3);
     //   movieList.add(movie);

        movie = new Movie("الحياة في القبور | خطبة الجمعة د.محمد العريفي\n","a_XPSm4ZVkE",R.drawable.s4);
       // movieList.add(movie);


        mAdapter.notifyDataSetChanged();
    }
}
