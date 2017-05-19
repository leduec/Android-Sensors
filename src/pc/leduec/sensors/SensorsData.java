package pc.leduec.sensors;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SensorsData extends Activity {

	private LocationManager mLocationManager;
	private SensorManager mSensorManager;

	//(1) gps
	private TextView mTextViewGps;
	//(2) accelerometer
	private TextView mTextViewAccelerometer;
	//(3) magnetometer
	private TextView mTextViewMagneticField;
	//(4) proximity
	private TextView mTextViewProximity;
	//(5) brightness
	private TextView mTextViewLight;
	//(6) gyroscope
	private TextView mTextViewGyroscope;

	//(1) gps
	private LocationListener mListenerGps;
	//(2) accelerometer
	private SensorEventListener mEventListenerAccelerometer;
	//(3) magnetometer
	private SensorEventListener mEventListenerMagneticField;
	//(4) proximity
	private SensorEventListener mEventListenerProximity;
	//(5) brightness
	private SensorEventListener mEventListenerLight;
	//(6) gyroscope
	private SensorEventListener mEventListenerGyroscope;
	
	//(1) gps (initialized to prevent errors)
	public static double[] valuesGps = {0,0,0};
	//(2) accelerometer
	public static float[] valuesAccelerometer;
	//(3) magnetometer
	public static float[] valuesMagnetometer;
	//(4) proximity
	public static float[] valuesProximity;
	//(5) brightness
	public static float[] valuesBrightness;
	//(6) gyroscope
	public static float[] valuesGyroscope;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//(1) gps
		mTextViewGps = (TextView) findViewById(R.id.text_gps);
		//(2) accelerometer
		mTextViewAccelerometer = (TextView) findViewById(R.id.text_accelerometer);
		//(3) magnetometer
		mTextViewMagneticField = (TextView) findViewById(R.id.text_magnetic_field);
		//(4) proximity
		mTextViewProximity = (TextView) findViewById(R.id.text_proximity);
		//(5) brightness
		mTextViewLight = (TextView) findViewById(R.id.text_light);
		//(6) gyroscope
		mTextViewGyroscope = (TextView) findViewById(R.id.text_gyroscope);
		
		mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		initListeners();
		
		//Navigation
		Button selections = (Button) findViewById(R.id.selections);
		selections.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent selectionIntent = new Intent(v.getContext(), Selections.class);
				v.getContext().startActivity(selectionIntent);				
			}
		});
		Button recording = (Button) findViewById(R.id.recording);
		recording.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent selectionIntent = new Intent(v.getContext(), Recording.class);
				v.getContext().startActivity(selectionIntent);				
			}
		});
	
		//clean finishing
		Button exit = (Button)findViewById(R.id.exit);
		exit.setOnClickListener(new OnClickListener(){public void onClick(View v){finish();}});
	}

	private void initListeners() {
		//(1) gps
		mListenerGps = new LocationListener() {
			
			public void onLocationChanged(Location location) {
				double[] temp = {location.getLongitude(),location.getLatitude(),location.getAltitude()};
				valuesGps = temp;
				mTextViewGps.setText("GPS: " + valuesGps[0]
						+ ", " + valuesGps[1] + ", " + valuesGps[2]);
			}
			
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}
			public void onProviderEnabled(String provider) {	
			}
			public void onProviderDisabled(String provider) {
			}
		};
		//(2) accelerometer
		mEventListenerAccelerometer = new SensorEventListener() {
			
			public void onSensorChanged(SensorEvent event) {
				valuesAccelerometer = event.values;
				mTextViewAccelerometer.setText("Accelerometer: " + valuesAccelerometer[0]
						+ ", " + valuesAccelerometer[1] + ", " + valuesAccelerometer[2]);
			}

			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}
		};
		//(3) magnetometer
		mEventListenerMagneticField = new SensorEventListener() {

			public void onSensorChanged(SensorEvent event) {
				valuesMagnetometer = event.values;
				mTextViewMagneticField.setText("Compass: " + valuesMagnetometer[0] + ", "
						+ valuesMagnetometer[1] + ", " + valuesMagnetometer[2]);
			}

			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}
		};
		//(4) proximity
		mEventListenerProximity = new SensorEventListener() {

			public void onSensorChanged(SensorEvent event) {
				valuesProximity = event.values;
				mTextViewProximity.setText("Proximity: " + valuesProximity[0]);
			}

			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}
		};
		//(5) brightness
		mEventListenerLight = new SensorEventListener() {

			public void onSensorChanged(SensorEvent event) {
				valuesBrightness = event.values;
				mTextViewLight.setText("Light: " + valuesBrightness[0]);
			}

			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}
		};
		//(6) gyroscope
		mEventListenerGyroscope = new SensorEventListener() {

			public void onSensorChanged(SensorEvent event) {
				valuesGyroscope = event.values;
				mTextViewGyroscope.setText("Gyroscope: " + valuesGyroscope[0] + ", "
						+ valuesGyroscope[1] + ", " + valuesGyroscope[2]);
			}

			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}
		};
	}

	@Override
	protected void onResume() {
		super.onResume();
		//(1) gps
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0.1f, mListenerGps);
		//(2) accelerometer
		mSensorManager.registerListener(mEventListenerAccelerometer,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		//(3) magnetometer
		mSensorManager.registerListener(mEventListenerMagneticField,
				mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				SensorManager.SENSOR_DELAY_NORMAL);
		//(4) proximity
		mSensorManager.registerListener(mEventListenerProximity,
				mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
				SensorManager.SENSOR_DELAY_NORMAL);
		//(5) brightness
		mSensorManager.registerListener(mEventListenerLight,
				mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
				SensorManager.SENSOR_DELAY_NORMAL);
		//(6) gyroscope
		mSensorManager.registerListener(mEventListenerGyroscope,
				mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onDestroy() {
		//(1) gps
		mLocationManager.removeUpdates(mListenerGps);
		//(2) accelerometer
		mSensorManager.unregisterListener(mEventListenerAccelerometer);
		//(3) magnetometer
		mSensorManager.unregisterListener(mEventListenerMagneticField);
		//(4) proximity
		mSensorManager.unregisterListener(mEventListenerProximity);
		//(5) brightness
		mSensorManager.unregisterListener(mEventListenerLight);
		//(6) gyroscope
		mSensorManager.unregisterListener(mEventListenerGyroscope);
		super.onDestroy();
	}
}