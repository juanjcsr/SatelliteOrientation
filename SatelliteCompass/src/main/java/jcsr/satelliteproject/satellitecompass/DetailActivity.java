package jcsr.satelliteproject.satellitecompass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JuanCarlos on 4/13/14.
 */
public class DetailActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener{

    private static final String SAT_URL = "http://covers.openlibrary.org/b/id/";
    TextView mNombreSat;
    ListView mDetalleOrbitas;
    JSONObject jsonObject;
    JSONRowAdapter rowAdapter;


    Button botonSatellite;


    String tempJson = "\n" +
            "{\n" +
            "\"passes\":[\n" +
            "{\n" +
            "\"AOS_Fecha\":\"2014/04/13\",\n" +
            "\"AOS_Hora\":\"01:37:28\",\n" +
            "\"TCA_Fecha\":\"2014/04/13\",\n" +
            "\"TCA_Hora\":\"01:41:17\",\n" +
            "\"LOS_Fecha\":\"2014/04/13\",\n" +
            "\"LOS_Hora\":\"01:45:07\",\n" +
            "\"Duration\":\"00:07:38\",\n" +
            "\"MaxEl\":\"27.39\",\n" +
            "\"AOSAz\":\"332.75\",\n" +
            "\"LOZAz\":\"126.24\",\n" +
            "\"Orbit\":\"2257\"\n" +
            "},\n" +
            "{\n" +
            "\"AOS_Fecha\":\"2014/04/13\",\n" +
            "\"AOS_Hora\":\"15:57:01\",\n" +
            "\"TCA_Fecha\":\"2014/04/13\",\n" +
            "\"TCA_Hora\":\"16:00:55\",\n" +
            "\"LOS_Fecha\":\"2014/04/13\",\n" +
            "\"LOS_Hora\":\"16:04:49\",\n" +
            "\"Duration\":\"00:07:47\",\n" +
            "\"MaxEl\":\"66.08\",\n" +
            "\"AOSAz\":\"221.93\",\n" +
            "\"LOZAz\":\"36.07\",\n" +
            "\"Orbit\":\"2267\"\n" +
            "},\n" +
            "{\n" +
            "\"AOS_Fecha\":\"2014/04/14\",\n" +
            "\"AOS_Hora\":\"01:22:16\",\n" +
            "\"TCA_Fecha\":\"2014/04/14\",\n" +
            "\"TCA_Hora\":\"01:26:02\",\n" +
            "\"LOS_Fecha\":\"2014/04/14\",\n" +
            "\"LOS_Hora\":\"01:29:49\",\n" +
            "\"Duration\":\"00:07:33\",\n" +
            "\"MaxEl\":\"44.77\",\n" +
            "\"AOSAz\":\"327.30\",\n" +
            "\"LOZAz\":\"133.63\",\n" +
            "\"Orbit\":\"2273\"\n" +
            "},\n" +
            "{\n" +
            "\"AOS_Fecha\":\"2014/04/14\",\n" +
            "\"AOS_Hora\":\"15:38:53\",\n" +
            "\"TCA_Fecha\":\"2014/04/14\",\n" +
            "\"TCA_Hora\":\"15:42:35\",\n" +
            "\"LOS_Fecha\":\"2014/04/14\",\n" +
            "\"LOS_Hora\":\"15:46:18\",\n" +
            "\"Duration\":\"00:07:25\",\n" +
            "\"MaxEl\":\"40.32\",\n" +
            "\"AOSAz\":\"227.57\",\n" +
            "\"LOZAz\":\"31.75\",\n" +
            "\"Orbit\":\"2283\"\n" +
            "},\n" +
            "{\n" +
            "\"AOS_Fecha\":\"2014/04/15\",\n" +
            "\"AOS_Hora\":\"01:01:45\",\n" +
            "\"TCA_Fecha\":\"2014/04/15\",\n" +
            "\"TCA_Hora\":\"01:05:22\",\n" +
            "\"LOS_Fecha\":\"2014/04/15\",\n" +
            "\"LOS_Hora\":\"01:09:00\",\n" +
            "\"Duration\":\"00:07:14\",\n" +
            "\"MaxEl\":\"59.05\",\n" +
            "\"AOSAz\":\"324.72\",\n" +
            "\"LOZAz\":\"137.08\",\n" +
            "\"Orbit\":\"2289\"\n" +
            "},\n" +
            "{\n" +
            "\"AOS_Fecha\":\"2014/04/15\",\n" +
            "\"AOS_Hora\":\"15:14:16\",\n" +
            "\"TCA_Fecha\":\"2014/04/15\",\n" +
            "\"TCA_Hora\":\"15:17:45\",\n" +
            "\"LOS_Fecha\":\"2014/04/15\",\n" +
            "\"LOS_Hora\":\"15:21:13\",\n" +
            "\"Duration\":\"00:06:57\",\n" +
            "\"MaxEl\":\"35.70\",\n" +
            "\"AOSAz\":\"228.62\",\n" +
            "\"LOZAz\":\"30.84\",\n" +
            "\"Orbit\":\"2299\"\n" +
            "},\n" +
            "{\n" +
            "\"AOS_Fecha\":\"2014/04/16\",\n" +
            "\"AOS_Hora\":\"00:33:57\",\n" +
            "\"TCA_Fecha\":\"2014/04/16\",\n" +
            "\"TCA_Hora\":\"00:37:19\",\n" +
            "\"LOS_Fecha\":\"2014/04/16\",\n" +
            "\"LOS_Hora\":\"00:40:40\",\n" +
            "\"Duration\":\"00:06:43\",\n" +
            "\"MaxEl\":\"45.32\",\n" +
            "\"AOSAz\":\"326.73\",\n" +
            "\"LOZAz\":\"134.57\",\n" +
            "\"Orbit\":\"2305\"\n" +
            "},\n" +
            "{\n" +
            "\"AOS_Fecha\":\"2014/04/16\",\n" +
            "\"AOS_Hora\":\"14:40:47\",\n" +
            "\"TCA_Fecha\":\"2014/04/16\",\n" +
            "\"TCA_Hora\":\"14:43:59\",\n" +
            "\"LOS_Fecha\":\"2014/04/16\",\n" +
            "\"LOS_Hora\":\"14:47:10\",\n" +
            "\"Duration\":\"00:06:23\",\n" +
            "\"MaxEl\":\"60.46\",\n" +
            "\"AOSAz\":\"222.20\",\n" +
            "\"LOZAz\":\"35.75\",\n" +
            "\"Orbit\":\"2314\"\n" +
            "},\n" +
            "{\n" +
            "\"AOS_Fecha\":\"2014/04/16\",\n" +
            "\"AOS_Hora\":\"23:56:18\",\n" +
            "\"TCA_Fecha\":\"2014/04/16\",\n" +
            "\"TCA_Hora\":\"23:59:10\",\n" +
            "\"LOS_Fecha\":\"2014/04/17\",\n" +
            "\"LOS_Hora\":\"00:02:02\",\n" +
            "\"Duration\":\"00:05:44\",\n" +
            "\"MaxEl\":\"16.78\",\n" +
            "\"AOSAz\":\"337.07\",\n" +
            "\"LOZAz\":\"121.75\",\n" +
            "\"Orbit\":\"2321\"\n" +
            "},\n" +
            "{\n" +
            "\"AOS_Fecha\":\"2014/04/17\",\n" +
            "\"AOS_Hora\":\"13:55:35\",\n" +
            "\"TCA_Fecha\":\"2014/04/17\",\n" +
            "\"TCA_Hora\":\"13:58:11\",\n" +
            "\"LOS_Fecha\":\"2014/04/17\",\n" +
            "\"LOS_Hora\":\"14:00:48\",\n" +
            "\"Duration\":\"00:05:12\",\n" +
            "\"MaxEl\":\"18.15\",\n" +
            "\"AOSAz\":\"201.73\",\n" +
            "\"LOZAz\":\"52.73\",\n" +
            "\"Orbit\":\"2330\"\n" +
            "},\n" +
            "{\n" +
            "\"AOS_Fecha\":\"2014/04/18\",\n" +
            "\"AOS_Hora\":\"23:25:17\",\n" +
            "\"TCA_Fecha\":\"2014/04/18\",\n" +
            "\"TCA_Hora\":\"23:26:37\",\n" +
            "\"LOS_Fecha\":\"2014/04/18\",\n" +
            "\"LOS_Hora\":\"23:27:52\",\n" +
            "\"Duration\":\"00:02:34\",\n" +
            "\"MaxEl\":\"31.01\",\n" +
            "\"AOSAz\":\"325.10\",\n" +
            "\"LOZAz\":\"137.23\",\n" +
            "\"Orbit\":\"2353\"\n" +
            "}\n" +
            "],\n" +
            "\"orbit\":[\n" +
            "{\n" +
            "\"Name\":\" Orbit 2257\",\n" +
            "\"Time_Fecha\":\"2014/04/13\",\n" +
            "\"Time_Hora\":\"01:37:28\",\n" +
            "\"AZ\":\"332.75\",\n" +
            "\"El\":\"0.00\",\n" +
            "\"Range\":\"0.00\",\n" +
            "\"Footp\":\"1744\"\n" +
            "},\n" +
            "{\n" +
            "\"Name\":\" Orbit 2257\",\n" +
            "\"Time_Fecha\":\"2014/04/13\",\n" +
            "\"Time_Hora\":\"01:37:51\",\n" +
            "\"AZ\":\"334.30\",\n" +
            "\"El\":\"1.50\",\n" +
            "\"Range\":\"1.50\",\n" +
            "\"Footp\":\"1585\"\n" +
            "},\n" +
            "{\n" +
            "\"Name\":\" Orbit 2257\",\n" +
            "\"Time_Fecha\":\"2014/04/13\",\n" +
            "\"Time_Hora\":\"01:38:14\",\n" +
            "\"AZ\":\"336.19\",\n" +
            "\"El\":\"3.17\",\n" +
            "\"Range\":\"3.17\",\n" +
            "\"Footp\":\"1427\"\n" +
            "},\n" +
            "{\n" +
            "\"Name\":\" Orbit 2257\",\n" +
            "\"Time_Fecha\":\"2014/04/13\",\n" +
            "\"Time_Hora\":\"01:38:37\",\n" +
            "\"AZ\":\"338.54\",\n" +
            "\"El\":\"5.04\",\n" +
            "\"Range\":\"5.04\",\n" +
            "\"Footp\":\"1271\"\n" +
            "},\n" +
            "{\n" +
            "\"Name\":\" Orbit 2257\",\n" +
            "\"Time_Fecha\":\"2014/04/13\",\n" +
            "\"Time_Hora\":\"01:39:00\",\n" +
            "\"AZ\":\"341.54\",\n" +
            "\"El\":\"7.21\",\n" +
            "\"Range\":\"7.21\",\n" +
            "\"Footp\":\"1118\"\n" +
            "},\n" +
            "{\n" +
            "\"Name\":\" Orbit 2257\",\n" +
            "\"Time_Fecha\":\"2014/04/13\",\n" +
            "\"Time_Hora\":\"01:39:23\",\n" +
            "\"AZ\":\"345.50\",\n" +
            "\"El\":\"9.77\",\n" +
            "\"Range\":\"9.77\",\n" +
            "\"Footp\":\"969\"\n" +
            "}\n" +
            "],\n" +
            "\"Satelite\":\"Uno\"\n" +
            "}\n";

    private static final String QUERY_URL = "http://openlibrary.org/works/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sat_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mNombreSat = (TextView)findViewById(R.id.nombre_sat);

        botonSatellite = (Button)findViewById(R.id.boton_to_satellite);
        botonSatellite.setOnClickListener(this);

        mDetalleOrbitas = (ListView)findViewById(R.id.lista_detalles_orbitas);
        rowAdapter = new JSONRowAdapter(this, getLayoutInflater());
        mDetalleOrbitas.setAdapter(rowAdapter);
        mDetalleOrbitas.setOnItemClickListener(this);
        //desempacar cosas de la actividad principal

        try {
            //jsonObject = new JSONObject(this.getIntent().getExtras().getString("objetoJson"));
            jsonObject = new JSONObject(tempJson);
            rowAdapter.updateData(jsonObject.optJSONArray("passes"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String coverID = this.getIntent().getExtras().getString("coverID");
        mNombreSat.setText(jsonObject.optString("Satelite"));
        Log.d("EXTRASSS", coverID);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent detailIntent = new Intent(this, CompassActivity.class);
        startActivity(detailIntent);
    }


    @Override
    public void onClick(View view) {
        Intent detailIntent = new Intent(this, CompassActivity.class);
        startActivity(detailIntent);
    }
}
