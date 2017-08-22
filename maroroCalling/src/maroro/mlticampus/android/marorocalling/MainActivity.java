package maroro.mlticampus.android.marorocalling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn=(Button)findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent it=new Intent("marorop.abc.def");
				it.putExtra("MY_KEY","DATAaaaaaa");
				startActivityForResult(it,1);
			}
		});
	}
}
