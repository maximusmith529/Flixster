package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }



    //Inflate layout from XML and return to holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);

        return new ViewHolder(movieView);
    }
    //Populate data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get movie position
        Movie movie = movies.get(position);
        //bind the movie data into the VM
        holder.bind(movie);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvOverView;
        ImageView tvPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPoster = (ImageView) itemView.findViewById(R.id.tvPoster);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverView = (TextView) itemView.findViewById(R.id.tvOverview);

            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            String imageURL;

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                imageURL = movie.getBackdropPath();
            else
                imageURL = movie.getPosterPath();
            if(movie.getOverview().length()>510)
                tvOverView.setText(movie.getOverview().substring(0,510)+"...");
            else
                tvOverView.setText(movie.getOverview());
            tvTitle.setText(movie.getTitle());
            Glide.with(context)
                    .load(imageURL)
                    .placeholder(R.drawable.placeholder)
                    .into(tvPoster);
            //Glide.with(context).load(movie.getPosterPath()).into(tvPoster);
        }

        @Override
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Movie movie = movies.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);
        }
    }
    }
}
