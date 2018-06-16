package io.swagslash.settlersofcatan.utility;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import io.swagslash.settlersofcatan.pieces.items.Resource;

public class TradeHelper {
    public static Resource.ResourceType convertStringToResource(String type) {
        Resource.ResourceType tmp = Resource.ResourceType.NOTHING;
        try {
            tmp = Resource.ResourceType.valueOf(type.toUpperCase());
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(new LogRecord(Level.WARNING, e.getMessage()));
        }
        return tmp;
    }
}
