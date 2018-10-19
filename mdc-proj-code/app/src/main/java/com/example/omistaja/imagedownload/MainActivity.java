package com.example.omistaja.imagedownload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView;
    private ProgressBar progressBar;

    DownloadImage downloadImage;

    private String url = "https://s3.eu-west-2.amazonaws.com/optimawatermeters/01102018_01/b827eb27fc01.01102018010103.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        findViewById(R.id.button).setOnClickListener(this);

        downloadImage = new DownloadImage();


    }

    private void setImage(Drawable drawable) {
        imageView.setBackground(drawable);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.button) {
            downloadImage.execute(url);
        }
    }

    private class DownloadImage extends AsyncTask<String, Integer, Drawable> {

        @Override
        protected Drawable doInBackground(String... arg0) {
            return downloadImage(arg0[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Drawable image) {
            super.onPostExecute(image);
            setImage(image);
            progressBar.setVisibility(View.GONE);
        }

        private Drawable downloadImage(String _url) {
            URL url;
            BufferedOutputStream out;
            InputStream in;
            BufferedInputStream buf;

            try {
                url = new URL(_url);
                in = url.openStream();

                buf = new BufferedInputStream(in);

                Bitmap bMap = BitmapFactory.decodeStream(buf);
                if(in != null) {
                    in.close();
                }
                if (buf != null) {
                    buf.close();
                }

                return new BitmapDrawable(bMap);

            } catch (Exception e) {
                Log.e("Error reading file", e.toString());
            }

            return null;

        }
    }
}
