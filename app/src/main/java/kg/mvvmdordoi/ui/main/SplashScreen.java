package kg.mvvmdordoi.ui.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.Locale;

import kg.mvvmdordoi.R;
import kg.mvvmdordoi.network.Lang;
import kg.mvvmdordoi.network.UserToken;
import kg.mvvmdordoi.ui.auth.login.AuthActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        String lang = Lang.INSTANCE.get(this);

        assert lang != null;
        if (lang.equals("1")){
            Locale locale = new Locale("ky");
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            getBaseContext().getResources().updateConfiguration(configuration, null);
            setTitle(R.string.app_name);
        }else {
            Locale locale = new Locale("ru");
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            getBaseContext().getResources().updateConfiguration(configuration, null);
            setTitle(R.string.app_name);
        }
        Intent i;
        if (!UserToken.INSTANCE.hasToken(this)) {
            i = new Intent(this, AuthActivity.class);

        }else {
            i = new Intent(this, MainActivity.class);

        }

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
