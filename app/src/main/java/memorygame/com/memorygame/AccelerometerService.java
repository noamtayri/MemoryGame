package memorygame.com.memorygame;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

public class AccelerometerService extends Service implements SensorEventListener{

    private SensorManager sm;
    private Sensor sensor;
    private final IBinder mBinder = new SensorLocalBinder();

    private float[] startPoints;

    public AccelerometerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, sensor, 5000000);
        startPoints = null;

        return mBinder;
    }

    public void onDestroy(){
        sm.unregisterListener(this);
    }


    public class SensorLocalBinder extends Binder {
        AccelerometerService getService() {
            return AccelerometerService.this;
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(startPoints == null){
            startPoints = new float[3];
            startPoints[0] = Math.abs(event.values[0]);
            startPoints[1] = Math.abs(event.values[1]);
            startPoints[2] = Math.abs(event.values[2]);
        }
        if(Math.abs(event.values[0]) - startPoints[0] > FinalVariables.ROTATE_DIFF ||
                Math.abs(event.values[1]) - startPoints[1] > FinalVariables.ROTATE_DIFF ||
                Math.abs(event.values[2]) - startPoints[2] > FinalVariables.ROTATE_DIFF){
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
            GameActivity.turnBack();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
