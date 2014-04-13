package jcsr.satelliteproject.satellitecompass;

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
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import jcsr.satelliteproject.satellitecompass.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by JuanCarlos on 4/12/14.
 */
public class HomeActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener{
    Button mainButton;
    TextView mainTextView;
    EditText mBuscaSatelite;
    ListView mSatelliteView;
    //ArrayAdapter mArrayAdapter;
    JSONAdapter mJsonAdapter;
    ArrayList mArrayList = new ArrayList();

    ShareActionProvider mShareActionProvider;

    private static final String QUERY_URL = "http://openlibrary.org/search.json?q=";

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
        /* mArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                mArrayList);
        mSatelliteView.setAdapter(mArrayAdapter);*/
        mJsonAdapter = new JSONAdapter(this, getLayoutInflater());
        mSatelliteView.setAdapter(mJsonAdapter);

        mSatelliteView.setOnItemClickListener(this);


        mainButton.setOnClickListener(this);

        querySatellites("Hobbit");

    }

    @Override
    public void onClick(View view) {
        mainTextView.setText(mBuscaSatelite.getText().toString() + " para buscar!");
        //mArrayList.add(mBuscaSatelite.getText().toString());
        //mArrayAdapter.notifyDataSetChanged();
        querySatellites(mBuscaSatelite.getText().toString());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        //Log.d("yayyy", pos + " : " + mArrayList.get(pos));
        JSONObject jsonObject = (JSONObject) mJsonAdapter.getItem(pos);
        String coverID = jsonObject.optString("cover_i", "");
        String json = jsonObject.toString();
        Log.d("JSONCLICKED", jsonObject.toString());

        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("coverID", coverID);
        detailIntent.putExtra("objetoJson", json);

        //Agregar mas cosas
        startActivity(detailIntent);
    }


    /*************************************************************************************/
    private void querySatellites(String searchString) {
        String urlString = "";

        try {
            urlString = URLEncoder.encode(searchString, "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //Cliente async
        AsyncHttpClient client = new AsyncHttpClient();
        //Obtener JSON
        Log.d("URLQUERY ", QUERY_URL + urlString);
        client.get(QUERY_URL + urlString,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        //super.onSuccess(response);
                        Toast.makeText(getApplicationContext(), "Listo!", Toast.LENGTH_LONG).show();
                        Log.d("respuesta:", response.toString());
                        //Manda los resultados a la Lista
                        mJsonAdapter.updateData(response.optJSONArray("docs"));
                    }
                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                        Toast.makeText(getApplicationContext(), "Error " + statusCode + " " +
                                throwable.getMessage(), Toast.LENGTH_LONG ).show();
                        Log.e("respuesta error:", statusCode + " " + throwable.getMessage() );
                    }
                });
    }
}