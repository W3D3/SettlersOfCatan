package io.swagslash.settlersofcatan;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowSensorManager;

import java.lang.reflect.Field;

import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class ShakeTest {

    private Sensor s;
    private ShadowSensorManager ssm;
    private ShakeDetector sd;

    private String shouldSay = "test";
    private String testValue;

    private float[] sensorVals = {5.2f, 4.2f, -14.2f};

    @Before
    public void init() {
        sd = new ShakeDetector();
        ShakeListener sl = new ShakeListener() {
            @Override
            public void onShake() {
                testValue = shouldSay;
            }
        };
        sd.setShakeListener(sl);

        SensorManager sm = (SensorManager) RuntimeEnvironment.application.getSystemService(Context.SENSOR_SERVICE);
        ssm = shadowOf(sm);
        s = ssm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        ssm.registerListener(sd, s, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Test
    public void hasListener() {
        Assert.assertTrue(ssm.hasListener(sd));
    }

    @Test
    public void checkShakeListener() {
        try {
            sd.onSensorChanged(getAccelerometerEventWithValues(s, sensorVals));
            Assert.assertSame(shouldSay, testValue);
            // just for the coverage
            sd.onAccuracyChanged(s, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * taken from
     * https://stackoverflow.com/questions/34530865/how-to-mock-motionevent-and-sensorevent-for-unit-testing-in-android
     *
     * @param s             Sensor to read SensorEvent from
     * @param desiredValues Values to read
     */
    private SensorEvent getAccelerometerEventWithValues(Sensor s, float[] desiredValues) throws Exception {
        SensorEvent sensorEvent = Mockito.mock(SensorEvent.class);

        Field sensorField = SensorEvent.class.getField("sensor");
        sensorField.setAccessible(true);
        sensorField.set(sensorEvent, s);

        Field valuesField = SensorEvent.class.getField("values");
        valuesField.setAccessible(true);
        valuesField.set(sensorEvent, desiredValues);

        return sensorEvent;
    }
}
