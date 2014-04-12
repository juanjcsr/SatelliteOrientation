package com.jcsr.satellite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.jcsr.compass.R;

import java.util.ArrayList;

/**
 * Created by JuanCarlos on 4/12/14.
 */
public class HomeActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener{
    Button mainButton;
    TextView mainTextView;
    EditText mBuscaSatelite;
    ListView mSatelliteView;
    ArrayAdapter mArrayAdapter;
    ArrayList mArrayList = new ArrayList();

    ShareActionProvider mShareActionProvider;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);
        if (shareItem != null) {
            mShareActionProvider = (ShareActionProvider)shareItem.getActionProvider();
        }
        setShareIntent();
        return true;
    }

    private void setShareIntent() {
        if (mShareActionProvider != null ) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Estoy escuchando un Satellite");
            shareIntent.putExtra(Intent.EXTRA_TEXT, mainTextView.getText());

            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mainTextView = (TextView)findViewById(R.id.main_textview);
        mainButton = (Button)findViewById(R.id.main_button);
        mBuscaSatelite = (EditText)findViewById(R.id.etext_satellite);

        mSatelliteView = (ListView)findViewById(R.id.satellite_listview);
        mArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                mArrayList);
        mSatelliteView.setAdapter(mArrayAdapter);
        mSatelliteView.setOnItemClickListener(this);


        mainButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        mainTextView.setText(mBuscaSatelite.getText().toString() + " para buscar!");
        mArrayList.add(mBuscaSatelite.getText().toString());
        mArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        Log.d("yayyy", pos + " : " + mArrayList.get(pos));
    }
}