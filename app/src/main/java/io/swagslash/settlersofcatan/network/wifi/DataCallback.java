package io.swagslash.settlersofcatan.network.wifi;

import android.util.Log;

import com.peak.salut.Callbacks.SalutDataCallback;

public class DataCallback implements SalutDataCallback {

    public static IDataCallback actActivity;

    @Override
    public void onDataReceived(Object o) {
        if(actActivity != null){
            actActivity.onDataReceived(o);
        }
    }

    public interface IDataCallback{
        void onDataReceived(Object data);
    }
}
