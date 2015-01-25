package ac.codebuddy.hacktarl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HealthCareCheckStatus extends Fragment{
	View v;
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		 v = inflater.inflate(R.layout.health_care_check_status, container, false);
        return v;
        
    }

}
