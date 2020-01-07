package com.example.ds6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity{
   // EditText ed;
    Button button;
    ArrayAdapter<String> adapter;
    private List<Msg> fruitList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent=getIntent();
        String add =   intent.getStringExtra("ddd");

       // button = (Button)findViewById(R.id.button);
        FruitAdapter adapter = new FruitAdapter(

                Main2Activity.this,R.layout.cards,
                fruitList

        );
        Msg apple1 = new Msg("hello world");
        fruitList.add(apple1);
        Msg apple2 = new Msg("resolution");
        fruitList.add(apple2);
        Msg apple3 = new Msg("蜂窝状的");
        fruitList.add(apple3);
        Msg apple4 = new Msg("teher");
        fruitList.add(apple4);
        Msg apple = new Msg(add);
        fruitList.add(apple);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
      //  ed = (EditText)findViewById(R.id.edit);
//        adapter = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_list_item_1);
//        ListView listView = (ListView)findViewById(R.id.list_view);
//        listView.setAdapter(adapter);//关联适配器

//        adapter.add("");
//        adapter.add("");
//        adapter.add("看书");
//        adapter.add("吃饭");
//        adapter.add("散步");

    }
}