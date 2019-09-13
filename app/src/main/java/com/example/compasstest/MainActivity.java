package com.example.compasstest;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    static int TYPE_ORIENTATION = 3;

    private ImageView image;
    private float currentAngle = 0.0f;

    private SensorManager mSensorManager;

    TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById( R.id.compassImg);

        heading = findViewById( R.id.heading );

        mSensorManager = ( SensorManager ) getSystemService( SENSOR_SERVICE );
    }

    public void onResume(){
        super.onResume();
        //registing listener for system's orientation
        mSensorManager.registerListener( this, mSensorManager.getDefaultSensor( TYPE_ORIENTATION ),
                SensorManager.SENSOR_DELAY_GAME);

    }

    public void onPause(){
        super.onPause();
        //stop the listener and save battery
        mSensorManager.unregisterListener( this );
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //get the angle around the z-axis
        float angle = Math.round( sensorEvent.values[0]);

        heading.setText("Heading angle: " + Float.toString( angle ) + " degrees.");

        //rotate animation
        RotateAnimation rotateAnimation = new RotateAnimation(
                currentAngle,
                - angle,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        );

        //time for animation to take place
        rotateAnimation.setDuration( 100 );

        //set the animation after the end of reservation status
        rotateAnimation.setFillAfter( true );

        //start the animation
        image.startAnimation( rotateAnimation );
        currentAngle = -angle;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
