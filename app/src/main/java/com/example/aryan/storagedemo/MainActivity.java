package com.example.aryan.storagedemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private TextView mTxtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTxtInfo = (TextView) findViewById(R.id.txtInfo);

        SharedPreferences prefs = getSharedPreferences("my_prefs", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Name","Using getSharedPreference");
        editor.putInt("Code",101);
        editor.commit();

        String name = prefs.getString("Name","Not Available");
        int code = prefs.getInt("Code",-1);

        Toast.makeText(this,"Name : "+name +" Code : "+code,Toast.LENGTH_SHORT).show();

        prefs = getPreferences(AppCompatActivity.MODE_PRIVATE);
        editor = prefs.edit();
        editor.putString("Name","Using getPreferences");
        editor.putInt("Code",101);
        editor.commit();

        String Name = prefs.getString("Name","Not Available");
        int Code = prefs.getInt("Code",-1);

        Toast.makeText(this,"Name : "+Name +" Code : "+Code,Toast.LENGTH_SHORT).show();

        mTxtInfo.setText("");
        //Internal Storage

        File appRoot = getFilesDir();
        mTxtInfo.append(appRoot.getAbsolutePath());

        File cache = getCacheDir();
        mTxtInfo.append("\n"+ cache.getAbsolutePath());

        try {
            FileOutputStream fout = null;
            fout = openFileOutput("hello.txt", Activity.MODE_PRIVATE | Activity.MODE_APPEND );
            BufferedOutputStream bout = new BufferedOutputStream(fout);
            bout.write("\n\nThis is text demo".getBytes());
            bout.close();
            FileInputStream fin = openFileInput("hello.txt");
            BufferedInputStream bin = new BufferedInputStream(fin);
            byte[] bytes = new byte[1024];
            int count;
            while ((count = bin.read(bytes)) != -1) {
                mTxtInfo.append(new String(bytes, 0, count));
            }
            bin.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        //External Storage

        mTxtInfo.setText("");
        File appExtRoot = Environment.getExternalStorageDirectory();
        mTxtInfo.append("\n\n"+appExtRoot.getAbsolutePath());

        File file = getExternalFilesDir("hello.txt");
        mTxtInfo.append("\n\n"+file.getAbsolutePath());
    }
}
