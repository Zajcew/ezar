package com.ezar.xBikeChallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Emil on 2014-06-03.
 */
public class ListItemAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public ListItemAdapter(Context context, String[] values) {
        super(context, R.layout.menuitem_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.menuitem_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(values[position]);

        switch (position) {
            case 0:
                imageView.setImageResource(R.drawable.ride);
                break;
        }
        return rowView;
    }

}
