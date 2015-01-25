package ac.codebuddy.hacktarl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class ZplashScreen extends Activity{
	private static int SPLASH_TIME_OUT = 2000;
	
	@Override
	protected void onCreate(Bundle SavedInstanceState){
		super.onCreate(SavedInstanceState);
		setContentView(R.layout.activity_splash);
		
		new Handler().postDelayed(new Runnable(){
			
			@Override
			public void run(){
				Intent i = new Intent(ZplashScreen.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		}, SPLASH_TIME_OUT);
	}

}