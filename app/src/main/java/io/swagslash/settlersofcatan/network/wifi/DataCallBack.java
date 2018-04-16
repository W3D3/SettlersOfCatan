package io.swagslash.settlersofcatan.network.wifi;

import android.util.Log;

import com.peak.salut.Callbacks.SalutDataCallback;

public class DataCallBack implements SalutDataCallback {

    SalutDataCallback actActivity;

    public DataCallBack(SalutDataCallback actActivity) {
        this.actActivity = actActivity;
    }

    public SalutDataCallback getActActivity() {
        return actActivity;
    }

    public void setActActivity(SalutDataCallback actActivity) {
        this.actActivity = actActivity;
    }

    @Override
    public void onDataReceived(Object o) {
        Log.d("CallBack","Hell Yeah");
        actActivity.onDataReceived(o);
    }
}
