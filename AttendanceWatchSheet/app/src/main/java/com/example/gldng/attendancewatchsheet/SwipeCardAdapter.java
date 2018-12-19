package com.example.gldng.attendancewatchsheet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class SwipeCardAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList mSwipeCardArrayList;

    public SwipeCardAdapter(Context context, LayoutInflater layoutInflater, ArrayList<SwipeCard> swipeCardArraylist) {
        this.mContext = context;
        this.mLayoutInflater = layoutInflater;
        this.mSwipeCardArrayList = swipeCardArraylist;
    }

    @Override
    public int getCount() {
        return mSwipeCardArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSwipeCardArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.card_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView1 = (TextView) convertView.findViewById(R.id.cardStudentNameLable);
            viewHolder.textView2 = (TextView) convertView.findViewById(R.id.cardCourseNameLabel);
            viewHolder.textView3 = (TextView) convertView.findViewById(R.id.cardDateLabel);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SwipeCard sw = (SwipeCard) mSwipeCardArrayList.get(position);

        viewHolder.textView1.setText(sw.getName());
        viewHolder.textView2.setText(sw.getCourseName());
        viewHolder.textView3.setText(sw.getToday().toString());

        return convertView;
    }

    private static class ViewHolder {
        public TextView textView1,textView2,textView3;
    }
}