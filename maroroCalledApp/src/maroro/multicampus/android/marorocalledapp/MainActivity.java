package maroro.multicampus.android.marorocalledapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String data=getIntent().getStringExtra("MY_KEY");
		TextView tv=(TextView)findViewById(R.id.title);
		if(data!=null){
			tv.setText(data);
		}
	}
}
