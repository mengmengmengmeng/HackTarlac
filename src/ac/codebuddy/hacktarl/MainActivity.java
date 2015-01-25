package ac.codebuddy.hacktarl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ac.codebuddy.hacktarl.BFPHotlines.loadBFP;
import ac.codebuddy.hacktarl.HealthCareCenters.loadClinics;
import ac.codebuddy.hacktarl.PNPHotlines.loadPNP;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnClickListener {
	SharedPreferences preferences;
	List<ClinicList> listClinics = new ArrayList<ClinicList>();
	List<BFPList> listBFP= new ArrayList<BFPList>();
	ProgressDialog progDl;
	Writer writer = new StringWriter();
	PrintWriter printWriter = new PrintWriter(writer);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button healthcare = (Button) findViewById(R.id.healthcare);
		Button disaster = (Button) findViewById(R.id.disaster);
		Button education = (Button) findViewById(R.id.education);
		Button employment = (Button) findViewById(R.id.employment);
		Button tourism = (Button) findViewById(R.id.tourism);
		
		healthcare.setOnClickListener(this);
		disaster.setOnClickListener(this);
		education.setOnClickListener(this);
		employment.setOnClickListener(this);
		tourism.setOnClickListener(this);
		
		preferences = getSharedPreferences("Tokens", Context.MODE_PRIVATE);
		
		String check = preferences.getString("CheckIfFIrstLogin", "");
		if(check.equals("")){
			new getClinics().execute();
		}
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId())
        {
	        case R.id.healthcare:{
	        	Intent intent = new Intent(this, HealthCarePage.class);
				startActivity(intent);
	            break;
	        }
	        case R.id.tourism:{
	        	Intent intent = new Intent(this, TourismPage.class);
				startActivity(intent);
	            break;
	        }
	        case R.id.disaster:{
	        	Intent intent = new Intent(this, DisasterPage.class);
				startActivity(intent);
	            break;
	        }
	        /*
	        case R.id.education:{
	        	Intent intent = new Intent(this, SolaneAuthenticityCheckerActivity.class);
				startActivity(intent);
	        	break;
	        }
	        
	        case R.id.employment:{
	        	Intent intent = new Intent(this, SolaneKitchenEMinderOneActivity.class);
				startActivity(intent);
	        	break;
	        }
	        */
        }
		
	}
	public class getClinics extends AsyncTask<Void, Integer, Boolean>
	{
		String str ="";
		JSONArray json_data;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progDl = new ProgressDialog(MainActivity.this);
			progDl.setTitle("Loading templates....");
			progDl.setCancelable(false);
			progDl.show();
			
		}
				
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			boolean successRetrieve = false;
			
			try{
				ServiceHandler sh = new ServiceHandler();
				String url = getString(R.string.apicall)+"hospitals";

				str = sh.makeServiceCall(url, ServiceHandler.GET);
					
				Editor editor = preferences.edit();
				editor.putString("HealthCareCentersData", str.toString());
				editor.commit();

				Log.v("TAG", preferences.getString("HealthCareCentersData", ""));
				successRetrieve = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				successRetrieve = true;
			}
			return successRetrieve;
			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result){
				new getBFP().execute();
			}
		}

		
	}
	
	public class getBFP extends AsyncTask<Void, Integer, Boolean>
	{
		String str ="";
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
		}
				
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			boolean successRetrieve = false;
			
			try{
				ServiceHandler sh = new ServiceHandler();
				String url = getString(R.string.apicall)+"bfp";
				
					str = sh.makeServiceCall(url, ServiceHandler.GET);
					
					Editor editor = preferences.edit();
					editor.putString("BFPData", str.toString());
					editor.commit();
				
				Log.v("asdasd", preferences.getString("BFPData", ""));
				
				successRetrieve = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				successRetrieve = true;
			}
			return successRetrieve;
			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result){
				new getPNP().execute();
			}
		}
		
	}
	
	public class getPNP extends AsyncTask<Void, Integer, Boolean>
	{
		String str ="";
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
		}
				
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			boolean successRetrieve = false;
			
			try{
				ServiceHandler sh = new ServiceHandler();
				String url = getString(R.string.apicall)+"pnp";
					str = sh.makeServiceCall(url, ServiceHandler.GET);
					
					Editor editor = preferences.edit();
					editor.putString("PNPData", str.toString());
					editor.commit();
				
				Log.v("asdasd", preferences.getString("PNPData", ""));
				
				successRetrieve = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				successRetrieve = true;
			}
			return successRetrieve;
			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result){
				progDl.dismiss();
				Editor editor = preferences.edit();
				editor.putString("CheckIfFIrstLogin", "ASDFSA");
				editor.commit();
			}
		}
	}
	
    public void showMessage(String text){
    	AlertDialog.Builder alertbox = new AlertDialog.Builder(MainActivity.this);
        alertbox.setMessage(text);
        alertbox.setCancelable(false);
        alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        alertbox.show();
    }
    
	private boolean isNetworkAvailable() {
		ConnectivityManager connec = (ConnectivityManager)
			    getSystemService(Context.CONNECTIVITY_SERVICE);

			  // ARE WE CONNECTED TO THE NET
			  if ( connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
			       connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED )
			  {
			    // MESSAGE TO SCREEN FOR TESTING (IF REQ)
			    //Toast.makeText(this, connectionType + ” connected”, Toast.LENGTH_SHORT).show();
			    return true;
			  }
			  else if ( connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
			    ||  connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED  )
			  {
			    return false;
			  }

			  return false;
	}
}
