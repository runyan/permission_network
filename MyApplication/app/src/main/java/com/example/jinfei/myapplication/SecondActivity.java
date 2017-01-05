package com.example.jinfei.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Glide.with(this).load("http://nuuneoi.com/uploads/source/playstore/cover.jpg")
                .fitCenter()
                .thumbnail(0.1f)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
//                .crossFade()
                .animate(R.anim.item_alpha_in)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
