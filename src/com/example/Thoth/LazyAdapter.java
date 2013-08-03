package com.example.Thoth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<String> data;
    private Integer year;
    private static LayoutInflater inflater = null;

    public LazyAdapter(Activity a, ArrayList<String> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public LazyAdapter(Activity a, ArrayList<String> d, Integer y) {
        activity = a;
        data = d;
        year = y;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.overview_list_item, null);

        TextView text = (TextView)vi.findViewById(R.id.textView);
        ImageView image = (ImageView)vi.findViewById(R.id.imageView);

        final String card = data.get(position);

        int drawableID = activity.getResources().getIdentifier("thumb_" + Utils.GetResourceName(card), "drawable", activity.getPackageName());
        int stringID = activity.getResources().getIdentifier(Utils.GetResourceName(card), "string", activity.getPackageName());

        String shortInfo = "";
        if (year == null)
            shortInfo =  activity.getResources().getString(stringID).split("<br/>")[0].replaceAll("<center>", "").replaceAll("</center>", "");
        else {
            shortInfo =  "<center><h1>" + String.valueOf(year + position) + "</h1></center><br/>" + activity.getResources().getString(stringID).split("</center>")[0].replaceAll("<center>", "");
        }

        text.setText(Html.fromHtml(shortInfo));
        image.setImageResource(drawableID);

        return vi;
    }
}