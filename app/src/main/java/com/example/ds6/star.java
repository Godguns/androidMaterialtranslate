package com.example.ds6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.yalantis.beamazingtoday.interfaces.AnimationType;
import com.yalantis.beamazingtoday.interfaces.BatModel;
import com.yalantis.beamazingtoday.listeners.BatListener;
import com.yalantis.beamazingtoday.listeners.OnItemClickListener;
import com.yalantis.beamazingtoday.listeners.OnOutsideClickedListener;
import com.yalantis.beamazingtoday.ui.adapter.BatAdapter;
import com.yalantis.beamazingtoday.ui.animator.BatItemAnimator;
import com.yalantis.beamazingtoday.ui.callback.BatCallback;
import com.yalantis.beamazingtoday.ui.widget.BatRecyclerView;
import com.yalantis.beamazingtoday.util.TypefaceUtil;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;
import org.litepal.util.Const;

import java.util.ArrayList;
import java.util.List;

public class star extends AppCompatActivity  {
    FoodAdapter adapter;
    String dd;
    ListView listView;
    private boolean isopen =true;
    private BatRecyclerView mRecyclerView;
    private BatAdapter mAdapter;
    private List<BatModel> mGoals;
    private BatItemAnimator mAnimator;
    private List<word> wordList = new ArrayList<word>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        Intent intent=getIntent();
    dd= intent.getStringExtra("ddd");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
TextView textView1=findViewById(R.id.text_title);
        TextView textView2=findViewById(R.id.text_title2);
        ((TextView) findViewById(R.id.text_title)).setTypeface(TypefaceUtil.getAvenirTypeface(this));
        Connector.getDatabase();
        Book book=new Book();
        book.setWord(dd);
        Toast.makeText(star.this,book.getWord(),Toast.LENGTH_SHORT).show();
        book.save();
        textView1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Book book=new Book();
        book.setWord(dd);
        Toast.makeText(star.this,book.getWord(),Toast.LENGTH_SHORT).show();
        book.save();
       // DataSupport.deleteAll(Book.class);
    }
});
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 DataSupport.deleteAll(Book.class);
              //  listView.setadapter(adapter);
                // adapter.notifyDataSetChanged();
            }
        });

        List<Book>books= DataSupport.findAll(Book.class);
        for (Book book1:books){
            word a = new word(book1.getWord());
          //  word a = new word(book.getWord());
            wordList.add(a);
        }
adapter = new FoodAdapter(star.this,R.layout.itemstar,wordList);
        listView = (ListView) findViewById(R.id.starlist);
        listView.setAdapter(adapter);
       // autoFlash();
        adapter.notifyDataSetChanged();





    }
    private void autoFlash(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isopen){
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                   // data.add("text");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });

                }
            }
        });

        thread.start();


    }


}
