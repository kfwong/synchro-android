package com.synchro.synchro_prototype01;

import android.content.Context;

/**
 * Created by angja_000 on 26/5/2016.
 *
 *  for storing URLs for requests
 */
public class StoreUrl {
    private static String ivle_validate;

    public StoreUrl(Context context) {
        StoreData data = new StoreData(context);
        ivle_validate = "https://ivle.nus.edu.sg/api/Lapi.svc/Validate?APIKey="+ data.getApiKey() +
                "&Token="+ data.getAuthToken();
    }

    public String getValidateUrl() {return ivle_validate;}
}
