package com.example.xiaomao.sqlitebyrx.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.xiaomao.sqlitebyrx.R;

public class MainActivity extends AppCompatActivity {

    public void jumpWriteActivity(View view){
        FileIOActivity.start(this);
    }

    public void jumpWriteActivity2(View view){
        FileIOActivity2.start(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }




}
