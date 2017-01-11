package com.chronology;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Jordan on 1/10/2017.
 */
public class myAdapter extends BaseAdapter {

    private Context mContext;
    String[] items;
    LayoutInflater inflater;

    myAdapter (Context c){
        mContext = c;
        inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    myAdapter (Context c, String[] s){
        mContext = c;
        items = s;
        inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return items.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.scene, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.gridItemText);
        textView.setText(this.items[position]);

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
