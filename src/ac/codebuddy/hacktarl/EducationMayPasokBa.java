package ac.codebuddy.hacktarl;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class EducationMayPasokBa extends Fragment{
	View v;
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		 v = inflater.inflate(R.layout.education_may_pasok_ba, container, false);
        return v;
        
    }

}
