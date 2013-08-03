package com.example.Thoth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: waves
 * Date: 25.07.13
 * Time: 2:09
 * To change this template use File | Settings | File Templates.
 */
public class OverviewListActivity extends Activity {

    ArrayList<String> cards;

    ListView listView;
    LazyAdapter adapter;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_list);

        cards = new ArrayList<String>();

        for (int i=0; i<78; i++)
            cards.add(Utils.GetCardCode(i));

        listView = ((ListView) findViewById(R.id.listView));
        adapter = new LazyAdapter(this, cards);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), OverviewActivity.class);
                intent.putExtra("CardName", String.valueOf(position));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy()
    {
        listView.setAdapter(null);
        super.onDestroy();
    }
}
