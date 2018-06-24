package io.swagslash.settlersofcatan;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowSensorManager;

import static org.robolectric.Shadows.shadowOf;

@Ignore
@RunWith(RobolectricTestRunner.class)
public class ShakeTest {

    private String shouldSay = "test";
    private static final int VAL = 42;
    private SensorEvent se;
    private ShakeDetector sd;
    private ShakeListener sl;
    private int testValue = 0;

    @Before
    public void init() {
        /*
        se = Mockito.mock(SensorEvent.class);
        try {
            Field vals = SensorEvent.class.getField("values");
            vals.setAccessible(true);
            float[] sensorVals = {5.2f, 4.2f, -14.2f};
            try {
                vals.set(se, sensorVals);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        sd = new ShakeDetector();
        sd.setShakeListener(new ShakeListener() {
            @Override
            public void onShake() {
                System.out.println("test");
                testValue = VAL;
            }
        });
        */

        sd = new ShakeDetector();
        sd.setShakeListener(new ShakeListener() {
            @Override
            public void onShake() {
                System.out.println(shouldSay);
                testValue = VAL;
            }
        });

        Application app = RuntimeEnvironment.application;
        SensorManager sm = (SensorManager) app.getSystemService(Context.SENSOR_SERVICE);
        ShadowSensorManager ssm = shadowOf(sm);
        Sensor s = Shadow.newInstanceOf(Sensor.class);
        ssm.addSensor(Sensor.TYPE_ACCELEROMETER, s);
        ssm.registerListener(sd, s, 1);
    }

    @Test
    @Ignore
    public void checkShakeListener() {
        sd.onSensorChanged(se);
        Assert.assertSame(VAL, testValue);
    }
}
