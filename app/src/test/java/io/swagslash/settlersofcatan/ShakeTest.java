package io.swagslash.settlersofcatan;

import android.hardware.SensorEvent;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

public class ShakeTest {

    private static final int VAL = 42;
    private SensorEvent se;
    private ShakeDetector sd;
    private ShakeListener sl;
    private int testValue = 0;

    @Before
    public void init() {
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
    }

    @Test
    @Ignore
    public void checkShakeListener() {
        sd.onSensorChanged(se);
        Assert.assertSame(VAL, testValue);
    }
}
