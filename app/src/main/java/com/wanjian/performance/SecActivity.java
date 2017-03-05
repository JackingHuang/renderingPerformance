package com.wanjian.performance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import wanjian.renderingperformance.RenderingPerformance;

public class SecActivity extends AppCompatActivity {

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);

        findViewById(R.id.but).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SecActivity.class));
            }
        });

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(new MyAdapter());

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item, mListView, false);
            }
            ImageView imageView = (ImageView) ((ViewGroup) convertView).getChildAt(0);
            TextView textView = (TextView) ((ViewGroup) convertView).getChildAt(1);

            if (position % 2 == 0) {
                imageView.setImageResource(R.drawable.img);
            } else {
                imageView.setImageResource(R.drawable.zl);
            }
            textView.setText("Rendering Performance " + position + "  " + Math.random());


            return convertView;
        }
    }


}
