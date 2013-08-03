package com.example.Thoth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: waves
 * Date: 02.08.13
 * Time: 2:20
 * To change this template use File | Settings | File Templates.
 */
public class GrowResultActivity extends Activity {
    ArrayList<String> cards;

    ListView listView;
    LazyAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grow_result);

        String date = getIntent().getStringExtra("Date");
        Log.i("Test", date);
        Integer daymonth = Integer.valueOf(date.substring(0, 2)) + Integer.valueOf(date.substring(2,4)) + 1; // Month starts from 00
        Integer personal = digitsSum(daymonth + Integer.valueOf(date.substring(4)));
        Integer soul = personal;
        if (personal == 22) {
            personal = 0;
            soul = 4;
        }
        else {
            if (personal > 21) {
                personal = digitsSum(personal);
                soul = personal;
            }
            else {
                if (soul > 9)
                    soul = digitsSum(soul);
                else
                    soul = personal;
            }
        }

        cards = new ArrayList<String>();

        for (int i=0; i<100; i++) {
            Integer code = digitsSum(daymonth + Integer.valueOf(date.substring(4)) + i);
            if (code > 21)
                code = Integer.valueOf(String.valueOf(code).charAt(0)) + Integer.valueOf(String.valueOf(code).charAt(1));
            cards.add(Utils.GetCardCode(code));
        }

        listView = ((ListView) findViewById(R.id.listView));
        adapter = new LazyAdapter(this, cards, Integer.valueOf(date.substring(4)));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), OverviewActivity.class);
                intent.putExtra("CardName", String.valueOf(cards.get(position)));
                view.getContext().startActivity(intent);
            }
        });

        ImageView image1 = (ImageView) findViewById(R.id.imageView);
        ImageView image2 = (ImageView) findViewById(R.id.imageView1);

        Log.i("Test", String.valueOf(soul));
        Log.i("Test", Utils.GetCardCode(soul));

        int drawableID1 = getResources().getIdentifier("thumb_" + Utils.GetResourceName(Utils.GetCardCode(soul)), "drawable", getPackageName());
        int drawableID2 = getResources().getIdentifier("thumb_" + Utils.GetResourceName(Utils.GetCardCode(personal)), "drawable", getPackageName());

        image1.setImageResource(drawableID1);
        image2.setImageResource(drawableID2);

        final int final_soul = soul, final_personal = personal;
        image1.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OverviewActivity.class);
                intent.putExtra("CardName", String.valueOf(Utils.GetCardCode(final_soul)));
                view.getContext().startActivity(intent);
            }
        });
        image2.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OverviewActivity.class);
                intent.putExtra("CardName", String.valueOf(Utils.GetCardCode(final_personal)));
                view.getContext().startActivity(intent);
            }
        });
    }

    int digitsSum(int n) {
        int sum = 0;
        while (n != 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }
}