package com.osama.gentlepalette;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class MainActivity extends AppCompatActivity {
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Glide.with(this)
                .load("http://i0.kym-cdn.com/photos/images/original/000/272/391/185.gif")
                .asBitmap()
                .into(new BitmapImageViewTarget(findViewById(R.id.cover)) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        dynamicToolbarColor(resource);
                        super.setResource(resource);
                    }
                });
    }

    private void dynamicToolbarColor(Bitmap bitmap) {
        Palette.from(bitmap).generate(palette -> {
            findViewById(R.id.c1).setBackgroundColor(palette.getDominantColor(ContextCompat.getColor(this, android.R.color.white)));
            GentlePalette.generate(palette.getDominantColor(ContextCompat.getColor(this, android.R.color.white)), Palettes.FLAT_COLORS,
                    findViewById(R.id.c3)::setBackgroundColor);

            findViewById(R.id.c2).setBackgroundColor(palette.getVibrantColor(ContextCompat.getColor(this, android.R.color.white)));
            GentlePalette.generate(palette.getVibrantColor(ContextCompat.getColor(this, android.R.color.white)), Palettes.FLAT_COLORS,
                    findViewById(R.id.c4)::setBackgroundColor);


            GentlePalette.generate(palette.getDominantColor(ContextCompat.getColor(this, android.R.color.white)), Palettes.FLAT_COLORS,
                    findViewById(R.id.toolBar)::setBackgroundColor);
        });


//        Palette.from(bitmap).generate(palette -> toolbarLayout.setContentScrimColor(palette.getVibrantColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))));
    }


}
