package com.chronology;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Jordan on 1/20/2017.
 */
public class projectListAdapter extends BaseAdapter {

    String[] items;
    private Context context;
    private LayoutInflater layoutInflater;


    projectListAdapter(Context c){
        context = c;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    projectListAdapter(Context c, String[] array){
        context = c;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        items = array;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = null;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.scene, null);
        }

        textView = (TextView) convertView.findViewById(R.id.projectItem);
        textView.setText(this.items[position]);
        return convertView;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        if(getCount() < position + 1){
            return null;
        }
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
