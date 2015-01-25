package ac.codebuddy.hacktarl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ClinicDetails extends Activity{
	TextView name, updated_at, address, contact;
	TimeAgo mytimeAgo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clinic_details);
		View backgroundImage = findViewById(R.id.background);
		Drawable background = backgroundImage.getBackground();
		background.setAlpha(60);
		
		getActionBar().setTitle(getIntent().getStringExtra("name"));
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String dateInString = getIntent().getStringExtra("updated_at");
		Date date = null;
		mytimeAgo = new TimeAgo(this);
		try {
			date = formatter.parse(dateInString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String ago = mytimeAgo.getTimeAgo(date, this);
		
		name = (TextView) findViewById(R.id.name);
		updated_at = (TextView) findViewById(R.id.updated_at);
		address = (TextView) findViewById(R.id.address);
		contact = (TextView) findViewById(R.id.contact);
		name.setText(getIntent().getStringExtra("name"));
		updated_at.setText("Updated "+ago);
		address.setText(getIntent().getStringExtra("address"));
		contact.setText(getIntent().getStringExtra("contact"));
		
		
	}
	
	

}
