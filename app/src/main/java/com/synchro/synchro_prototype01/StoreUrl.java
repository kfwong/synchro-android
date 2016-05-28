package com.synchro.synchro_prototype01;

import android.content.Context;

/**
 * Created by angja_000 on 26/5/2016.
 *
 *  for storing URLs for requests
 */
public class StoreUrl {
    //ivle urls
    private static String ivle_login;
    private static String ivle_login_success;
    private static String ivle_validate;
    private static String ivle_requestUserProfile;

    /*synchro-api urls
        note: need to set headers to call properly
    */
    private static String api_resync;
    private static String api_requestUserProfile;

    public StoreUrl(Context context) {
        StoreData data = new StoreData(context);
        ivle_login = "https://ivle.nus.edu.sg/api/login/?apikey=PK3n2PGjXR4OooZPZyelQ";
        ivle_login_success = "https://ivle.nus.edu.sg/api/login/login_result.ashx?apikey=PK3n2PGjXR4OooZPZyelQ&r=0";
        ivle_validate = "https://ivle.nus.edu.sg/api/Lapi.svc/Validate?APIKey="+ data.getApiKey() +
                "&Token="+ data.getAuthToken();
        ivle_requestUserProfile = "https://ivle.nus.edu.sg/api/Lapi.svc/Profile_View?APIKey=" + data.getApiKey() +
                "&AuthToken="+ data.getAuthToken();

        api_resync = "https://ec2-52-77-240-7.ap-southeast-1.compute.amazonaws.com/api/v1/me/resync";
        api_requestUserProfile = "https://ec2-52-77-240-7.ap-southeast-1.compute.amazonaws.com/api/v1/me";
    }

    public String getLoginUrl() {return ivle_login;}
    public String getLoginSuccessUrl() {return ivle_login_success;}
    public String getValidateUrl() {return ivle_validate;}
    public String getRequestUserProfileUrl() {return ivle_requestUserProfile;}

    public String getApiResyncUrl() {return api_resync;}
    public String getApiRequestUserProfile() {return api_requestUserProfile;}

}
