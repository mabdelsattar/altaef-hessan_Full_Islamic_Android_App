package com.daralmathour.altaefhessan;

/**
 * Created by codelab on 3/20/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daralmathour.altaefhessan.Models.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<Movie> moviesList;
    public Context _context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView movieImage;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.moviename);
            movieImage = (ImageView) view.findViewById(R.id.movieimg);
        }
    }


    public MoviesAdapter(List<Movie> moviesList,Context context) {
        this.moviesList = moviesList;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.movieImage.setImageResource(movie.getImgUrl());

        ((View)holder.movieImage.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + movie.getUrl()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + movie.getUrl()));
                try {
                    _context.startActivity(appIntent);
                } catch (Exception ex) {
                    _context.startActivity(webIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}