package io.swagslash.settlersofcatan.network.wifi;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.SalutDevice;

public class DiscoveryCallback implements SalutCallback{

    public static IDiscoveryCallback activity;

    @Override
    public void call() {
        if(activity != null){
            activity.call();
        }
    }

    public interface IDiscoveryCallback{
        void call();
    }
}
