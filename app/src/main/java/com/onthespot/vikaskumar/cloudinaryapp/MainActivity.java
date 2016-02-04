package com.onthespot.vikaskumar.cloudinaryapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Transformation;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    Cloudinary cloudinary;
    Bitmap bmp;
    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map config = new HashMap();
        config.put("cloud_name", "dio1mknsg");
        config.put("api_key", "893874711413129");
        config.put("api_secret", "z16pZ07nrRpuNgA3eRCUPBV5bpI");
        cloudinary = new Cloudinary(config);
        Resources res = this.getResources();
        int id = R.drawable.rsz_bg_cover2;
        String filepath = null;
        try {
            filepath = writetoExtStorage(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        file = new File(filepath);

        bmp = BitmapFactory.decodeResource(res, id);
        //saveBitmap(bmp,"poster",this);


        AsyncTaskDemo task = new AsyncTaskDemo();
        task.execute();
    }

    private String writetoExtStorage(int id) throws IOException {
        Bitmap bm = BitmapFactory.decodeResource( getResources(), id);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

        File file = new File(extStorageDirectory, "ic_launcher.jpg");
        Log.i("tag",file.getAbsolutePath());
        FileOutputStream outStream = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        outStream.flush();
        outStream.close();
        return file.getAbsolutePath();
    }

    private class AsyncTaskDemo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {

                cloudinary.uploader().upload(file, ObjectUtils.asMap("public_id", "sample_id"));
                //cloudinary.url().generate("sample_remote.jpg");
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("vikas",e +"");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
        }

        @Override
        protected void onCancelled() {

            super.onCancelled();
        }

    }
}
