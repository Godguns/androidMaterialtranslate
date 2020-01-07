package com.example.ds6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FoodAdapter extends ArrayAdapter<word> {
    private int resourceId;
    public FoodAdapter(Context context, int textViewResourceId,
                        List<word> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        word fruit = getItem(position); // 获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
       // ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
        TextView textView = (TextView) view.findViewById(R.id.starii);
      //  fruitImage.setImageResource(fruit.getImageId());
        textView.setText(fruit.getWords());
        return view;
    }
}
