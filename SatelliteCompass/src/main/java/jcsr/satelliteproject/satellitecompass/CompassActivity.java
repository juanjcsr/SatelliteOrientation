package jcsr.satelliteproject.satellitecompass;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;




public class CompassActivity extends FragmentActivity {

	private static final String TAG = "CompassActivity";

	private Compass compass;

    TextView textoAzm;
    TextView textoPitch;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compass);

		compass = new Compass(this);
		compass.arrowView = (ImageView) findViewById(R.id.main_image_arrow);
        compass.azimuthText = (TextView)findViewById(R.id.textAzimuth);
        //compass.azimuthView = (ImageView)findViewById(R.id.azimuth_arrow);

        compass.pitchText = (TextView)findViewById(R.id.textPitch);

        textoAzm = (TextView)findViewById(R.id.textView);
        textoPitch = (TextView)findViewById(R.id.textView2);

        textoAzm.setText("AZ = " + getIntent().getStringExtra("azm"));
        textoPitch.setText("EL = " +getIntent().getStringExtra("pitch"));
        //compass.rollText = (TextView)findViewById(R.id.textRoll);
	}



	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "start compass");
		compass.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		compass.stop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		compass.start();
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "stop compass");
		compass.stop();
	}

}
