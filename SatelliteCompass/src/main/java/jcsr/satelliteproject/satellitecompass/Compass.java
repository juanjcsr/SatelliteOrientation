package jcsr.satelliteproject.satellitecompass;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Compass implements SensorEventListener {
	private static final String TAG = "Compass";

	private SensorManager sensorManager;
	private Sensor gsensor;
	private Sensor msensor;
	private float[] mGravity = new float[3];
	private float[] mGeomagnetic = new float[3];
	private float azimuth = 0f;
    private float pitch = 0f;
    private float roll = 0f;
	private float currectAzimuth = 0;

	// compass arrow to rotate
	public ImageView arrowView = null;
    public TextView azimuthText= null;
    public ImageView azimuthView = null;

    public TextView pitchText = null;
    public TextView rollText = null;
    ToneGenerator toneG;
    ToneGenerator toneP;
	public Compass(Context context) {
		sensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		gsensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		msensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneP = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
	}

	public void start() {
		sensorManager.registerListener(this, gsensor,
				SensorManager.SENSOR_DELAY_GAME);
		sensorManager.registerListener(this, msensor,
				SensorManager.SENSOR_DELAY_GAME);

	}

	public void stop() {
		sensorManager.unregisterListener(this);
	}

	private void adjustArrow() {
		if (arrowView == null) {
			Log.i(TAG, "arrow view is not set");
			return;
		}

		Log.i(TAG, "will set rotation from " + currectAzimuth + " to "
				+ azimuth);

		Animation an = new RotateAnimation(-currectAzimuth, -azimuth,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		currectAzimuth = azimuth;

		an.setDuration(500);
		an.setRepeatCount(0);
		an.setFillAfter(true);

		arrowView.startAnimation(an);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		final float alpha = 0.97f;

		synchronized (this) {
			if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

				mGravity[0] = alpha * mGravity[0] + (1 - alpha)
						* event.values[0];
				mGravity[1] = alpha * mGravity[1] + (1 - alpha)
						* event.values[1];
				mGravity[2] = alpha * mGravity[2] + (1 - alpha)
						* event.values[2];

				// mGravity = event.values;

				// Log.e(TAG, Float.toString(mGravity[0]));
			}

			if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
				// mGeomagnetic = event.values;

				mGeomagnetic[0] = alpha * mGeomagnetic[0] + (1 - alpha)
						* event.values[0];
				mGeomagnetic[1] = alpha * mGeomagnetic[1] + (1 - alpha)
						* event.values[1];
				mGeomagnetic[2] = alpha * mGeomagnetic[2] + (1 - alpha)
						* event.values[2];
				// Log.e(TAG, Float.toString(event.values[0]));

			}

			float R[] = new float[9];
			float I[] = new float[9];
			boolean success = SensorManager.getRotationMatrix(R, I, mGravity,
					mGeomagnetic);
			if (success) {
				float orientation[] = new float[3];
				SensorManager.getOrientation(R, orientation);
				Log.d(TAG, "azimuth (rad): " + azimuth);
				azimuth = (float) Math.toDegrees(orientation[0]); // Azimuth
                pitch = (float) Math.toDegrees(orientation[1]);
                roll = (float)Math.toDegrees(orientation[2]);

				azimuth = (azimuth + 360) % 360;
                /*pitch = (pitch + 360 ) % 360;
                roll = (roll + 360) % 360;*/
				Log.d(TAG, "azimuth (deg): " + azimuth);

				adjustArrow();
                if (azimuth >= 45 && azimuth <=70) {

                    toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                    if (-pitch > 40 && -pitch < 60) {
                        toneP.startTone(ToneGenerator.TONE_CDMA_HIGH_L);
                    }
                    azimuthText.setText("AZ!!! = " + String.valueOf(azimuth));
                    pitchText.setText("PI = " + String.valueOf(pitch));
                    rollText.setText("RLL = " + String.valueOf(roll));
                } else {
                    azimuthText.setText("AZ = " + String.valueOf(azimuth));
                    pitchText.setText("PI = " + String.valueOf(pitch));
                    rollText.setText("RLL = " + String.valueOf(roll));
                }

			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
}
