package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    // the movie to display
    Movie movie;
    private ActivityMainBinding binding;
    // the view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView detailPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_simple.xml -> ActivitySimpleBinding
        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_movie_details);
        /*// layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);

        // resolve the view objects
        tvTitle = binding.tvTitle;*/

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        detailPoster = (ImageView) findViewById(R.id.detailPoster);
        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        //set tvPoster
        /*Glide.with(this)
                .load(movie.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .into(detailPoster);*/
        int radius = 30; // corner radius, higher value = more rounded
        int margin = 30; // crop margin, set to 0 for corners with no crop
        Glide.with(this)
                .load(movie.getPosterPath())
                .centerCrop() // scale image to fill the entire ImageView
                .transform(new RoundedCorners(margin))
                .into(detailPoster);


        // set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);
    }
}