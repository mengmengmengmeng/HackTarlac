package ac.codebuddy.hacktarl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class HealthCareCenters extends Fragment{
	List<ClinicList> listClinics = new ArrayList<ClinicList>();
	ListView clinicsView;
	SharedPreferences preferences;
	loadClinics list = null;
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.health_care_centers, container, false);
		preferences = getActivity().getSharedPreferences("Tokens", Context.MODE_PRIVATE);
		clinicsView = (ListView) v.findViewById(R.id.clinicLists);
		new getClinics().execute();
		return v;
		
	}
	
	class loadClinics extends ArrayAdapter<ClinicList>{
		public loadClinics() {
			// TODO Auto-generated constructor stub
			super(getActivity(), android.R.layout.simple_list_item_1, listClinics);
		}
	
	
		public View getView(int position, View convertView, ViewGroup parent){
			ViewHolder holder;
	
			if(convertView ==null){
				LayoutInflater inflater=getActivity().getLayoutInflater();
				convertView=inflater.inflate(R.layout.clinic_list, null);
				
				
				holder= new ViewHolder(convertView);
				
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder)convertView.getTag();
				
			}

			holder.populateFrom(listClinics.get(position));

			return(convertView);
		}
	}
	
	class ViewHolder{
		public TextView name = null;
		public TextView address = null;
		
		
		public ViewHolder(View notification_list) {
			// TODO Auto-generated constructor stub
			
			name=(TextView)notification_list.findViewById(R.id.name);
			address=(TextView)notification_list.findViewById(R.id.address);
		}
		
		void populateFrom(ClinicList s) /*throws java.text.ParseException*/{

			name.setText(s.getName());
			address.setText(s.getAddress());
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
			
		}
				
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			boolean successRetrieve = false;
			
			try{
				ServiceHandler sh = new ServiceHandler();
				String url = getString(R.string.apicall)+"hospitals";
				
				//if(isNetworkAvailable()){
					//str = sh.makeServiceCall(url, ServiceHandler.GET);
					
					//Editor editor = preferences.edit();
					//editor.putString("HealthCareCentersData", str.toString());
					//editor.commit();
				//}
				Log.v("TAG", preferences.getString("HealthCareCentersData", ""));
				try{
					JSONArray newJson = new JSONArray(preferences.getString("HealthCareCentersData", "[]"));
					
					for (int i=0; i< newJson.length(); i++){
						JSONObject obj_data = newJson.getJSONObject(i);
						ClinicList data = new ClinicList();
						
						data.setClinics(obj_data.getString("id"), obj_data.getString("name"),
								obj_data.getString("rhu_code"),obj_data.getString("address"),
								obj_data.getString("contact_number"),obj_data.getString("created_at"),
								obj_data.getString("updated_at"));
										
						listClinics.add(data);
						
					}
				}catch(JSONException e){
					successRetrieve = true;
					e.printStackTrace();
				}
				
				successRetrieve = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return successRetrieve;
			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result){
				list = new loadClinics();
				clinicsView.setAdapter(list);
				clinicsView.setOnItemClickListener(new OnItemClickListener() {
					@Override 
				    public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
				    {
						Intent intent = new Intent(getActivity(), ClinicDetails.class);
						intent.putExtra("id", listClinics.get(position).getId());
						intent.putExtra("name", listClinics.get(position).getName());
						intent.putExtra("created_at", listClinics.get(position).getCreatedAt());
						intent.putExtra("address", listClinics.get(position).getAddress());
						intent.putExtra("contact", listClinics.get(position).getContact());
						intent.putExtra("updated_at", listClinics.get(position).getUpdatedAt());
						intent.putExtra("rhu_code", listClinics.get(position).getRhuCode());
						startActivity(intent);
				    }
				});
				list.notifyDataSetChanged();
			}
		}

		
	}
	private boolean isNetworkAvailable() {
		ConnectivityManager connec = (ConnectivityManager)
			    getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

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
