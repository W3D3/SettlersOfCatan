package io.swagslash.settlersofcatan;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeDetector implements SensorEventListener {

    private static final float SHAKE_THRESHOLD = 1.65F;
    private static final int SHAKE_TIMEOUT = 2400;
    private long previousShake;
    private long currentShake;

    private ShakeListener listener;

    public void setShakeListener(ShakeListener l){
        listener = l;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0] / SensorManager.GRAVITY_EARTH;
        float y = sensorEvent.values[1] / SensorManager.GRAVITY_EARTH;
        float z = sensorEvent.values[2] / SensorManager.GRAVITY_EARTH;

        float g = (float) Math.sqrt(x * x + y * y + z * z);

        if (g > SHAKE_THRESHOLD) {
            currentShake = System.currentTimeMillis();
            if (previousShake + SHAKE_TIMEOUT < currentShake) {
                listener.onShake();
            }
            previousShake = currentShake;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // nothing
    }
}
