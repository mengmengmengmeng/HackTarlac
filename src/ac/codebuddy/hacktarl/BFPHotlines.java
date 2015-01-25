package ac.codebuddy.hacktarl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class BFPHotlines extends Fragment{
	SharedPreferences preferences;
	List<BFPList> listBFP= new ArrayList<BFPList>();
	ListView bfpView;
	loadBFP list = null;
	ProgressBar spinner;
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		preferences = getActivity().getSharedPreferences("Tokens", Context.MODE_PRIVATE);
		View v = inflater.inflate(R.layout.bfp_hotlines, container, false);
		bfpView = (ListView) v.findViewById(R.id.bfpLists);
		spinner =  (ProgressBar) v.findViewById(R.id.spinner);
		spinner.setVisibility(View.VISIBLE);
		new getBFP().execute();
		return v;
		
	}
	
	class loadBFP extends ArrayAdapter<BFPList>{
		public loadBFP() {
			// TODO Auto-generated constructor stub
			super(getActivity(), android.R.layout.simple_list_item_1, listBFP);
		}
	
	
		public View getView(int position, View convertView, ViewGroup parent){
			ViewHolder holder;
	
			if(convertView ==null){
				LayoutInflater inflater=getActivity().getLayoutInflater();
				convertView=inflater.inflate(R.layout.bfp_list, null);
				
				
				holder= new ViewHolder(convertView);
				
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder)convertView.getTag();
				
			}

			holder.populateFrom(listBFP.get(position));

			return(convertView);
		}
	}
	
	class ViewHolder{
		public TextView station = null, contact = null, marshal = null, landline = null;
		
		
		public ViewHolder(View notification_list) {
			// TODO Auto-generated constructor stub
			
			station=(TextView)notification_list.findViewById(R.id.station);
			contact=(TextView)notification_list.findViewById(R.id.contact_number);
			landline=(TextView)notification_list.findViewById(R.id.landline);
			marshal=(TextView)notification_list.findViewById(R.id.marshal);
		}
		
		void populateFrom(BFPList s) /*throws java.text.ParseException*/{
			station.setText(s.getStation());
			contact.setText(s.getContactNumber());
			landline.setText(s.getLandline());
			marshal.setText(s.getFireMarshal());
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
				
				//if(isNetworkAvailable()){
				//	str = sh.makeServiceCall(url, ServiceHandler.GET);
					
				//	Editor editor = preferences.edit();
				//	editor.putString("BFPData", str.toString());
				//	editor.commit();
				//}

				try {
					JSONArray newJson = new JSONArray(preferences.getString("BFPData", "[]"));
					for (int i=0; i< newJson.length(); i++){
						JSONObject obj_data = newJson.getJSONObject(i);
						BFPList data = new BFPList();
						
						data.setBFP(obj_data.getString("id"), obj_data.getString("station_name"),
								obj_data.getString("fire_marshal"),obj_data.getString("contact_number"),
								obj_data.getString("landline"));
										
						listBFP.add(data);
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Log.v("asdasd", preferences.getString("BFPData", ""));
				
				
				
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
				spinner.setVisibility(View.GONE);
				list = new loadBFP();
				bfpView.setAdapter(list);
				
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
