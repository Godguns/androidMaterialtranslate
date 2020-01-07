package com.example.ds6;



import android.content.Context;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class FruitAdapter extends ArrayAdapter<Msg> {
    private static final String TAG = "FruitAdapter";


    private  int resourceId;
    public FruitAdapter( Context context,
                         int textViewResourceId,
                         List<Msg> objects) {
        super(context,textViewResourceId, objects);
        resourceId = textViewResourceId;

    }


    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        Log.d(TAG, "position: "+position);
        Msg fruit = getItem(position); //获取当前Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(
                resourceId,parent,false
        );

      //  ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
        TextView fruitName = (TextView) view.findViewById(R.id.card1);

       // fruitImage.setImageResource(fruit.getImageId());
        fruitName.setText(fruit.getContent());
        return view;
    }
}

