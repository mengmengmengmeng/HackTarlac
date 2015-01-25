package ac.codebuddy.hacktarl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TourismPage extends Activity{
	List<TourismList> listTourism = new ArrayList<TourismList>();;
	ListView tourismView;
	loadTourism list = null;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tourism_page);
		tourismView = (ListView) findViewById(R.id.tourismLists);
		new getTourism().execute();

		
	}
	
	class loadTourism extends ArrayAdapter<TourismList>{
		public loadTourism() {
			// TODO Auto-generated constructor stub
			super(TourismPage.this, android.R.layout.simple_list_item_1, listTourism);
		}
	
	
		public View getView(int position, View convertView, ViewGroup parent){
			ViewHolder holder;
	
			if(convertView ==null){
				LayoutInflater inflater=TourismPage.this.getLayoutInflater();
				convertView=inflater.inflate(R.layout.tourism_list, null);
				
				
				holder= new ViewHolder(convertView);
				
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder)convertView.getTag();
				
			}

			holder.populateFrom(listTourism.get(position));

			return(convertView);
		}
	}
	
	class ViewHolder{
		public TextView message = null;
		
		
		public ViewHolder(View notification_list) {
			// TODO Auto-generated constructor stub
			
			message=(TextView)notification_list.findViewById(R.id.textView1);
		}
		
		void populateFrom(TourismList s) /*throws java.text.ParseException*/{

			message.setText(s.getName()+"And Sample for Longer name Inc. Co. LTD");
		}
	}
	
	public class getTourism extends AsyncTask<Void, Integer, Boolean>
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
				
				str = sh.makeServiceCall(url, ServiceHandler.GET);
				
				try{
					json_data = new JSONArray(str);
					
					for (int i=0; i< json_data.length(); i++){
						JSONObject obj_data = json_data.getJSONObject(i);
						TourismList data = new TourismList();
						
						data.setClinics(obj_data.getString("id"), obj_data.getString("name"),
								obj_data.getString("rhu_code"),obj_data.getString("created_at"),
								obj_data.getString("updated_at"));
										
						listTourism.add(data);
						
					}
				}catch(JSONException e){
					successRetrieve = false;
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
				list = new loadTourism();
				tourismView.setAdapter(list);
				tourismView.setOnItemClickListener(new OnItemClickListener() {
					@Override 
				    public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
				    { 
						Intent intent = new Intent(TourismPage.this, TourismDetails.class);
						intent.putExtra("id", listTourism.get(position).getId());
						intent.putExtra("name", listTourism.get(position).getName());
						intent.putExtra("created_at", listTourism.get(position).getCreatedAt());
						intent.putExtra("updated_at", listTourism.get(position).getUpdatedAt());
						intent.putExtra("rhu_code", listTourism.get(position).getRhuCode());
						startActivity(intent);
				    }
				});
				list.notifyDataSetChanged();
			}
		}

		
	}

}
