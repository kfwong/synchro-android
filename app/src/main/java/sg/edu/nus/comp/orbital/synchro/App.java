package sg.edu.nus.comp.orbital.synchro;

import android.app.Application;
import android.content.Context;

/**
 * Created by kfwong on 6/2/16.
 * This class is used for initialize variables for the application
 */
public class App extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.appContext = this;

        //TODO: set previously stored authtoken to SynchroAPI if user has logged in before
        // for dev only. remove for prod.
        SynchroAPI.authenticate("7611C051D15041A1114D55CBB57DCE54881F5E6347DD88D8DF0588E6AE6EB54B95E7328873CDA22240F07518C84C340836D33AEB5AA3B4632DD02C900879E99721102752A400CA99A31EF560D157A37876AA8039272DC75BFA174048088009C917647199DC3D2581DD85E88B706D524F6D48A7041CBC214288CEF098C0AE5DE36F080F60BF67E7D716AEF40DC47DDF742FB683DCE9F05939B36ABA6D790B87F988A65FE2153962C262F4B7C92326C301EAE0DCC21AEB3B1464F577F646AB0D7187101B34E216A1EC1F650E1C4B7047FF765595E7BAF616D71591F8619090D2F1E870410343CB4D2CF2A9A90980038F9F");
    }

    public static Context getContext(){
        return appContext;
    }
}
