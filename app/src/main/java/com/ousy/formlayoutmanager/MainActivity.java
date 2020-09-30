package com.ousy.formlayoutmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ousy.formlayoutmanager.editForm.EditFormActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTestClick(View view){
        startActivity(new Intent(this, TestFormActivity.class));
    }

    public void onHClick(View view){
        startActivity(new Intent(this, HFormActivity.class));
    }

    public void onVClick(View view){
        startActivity(new Intent(this, VFormActivity.class));
    }

    public void onHVClick(View view){
        startActivity(new Intent(this, HVFormActivity.class));
    }

    public void onTFormClick(View view){
        startActivity(new Intent(this, TFormActivity.class));
    }

    public void onHByTypeClick(View view){
        startActivity(new Intent(this, HForm2Activity.class));
    }

    public void onIssuesClick(View view){
        startActivity(new Intent(this, IssuesActivity.class));
    }

    public void onEditTypeClick(View view){
        startActivity(new Intent(this, EditFormActivity.class));
    }
}
