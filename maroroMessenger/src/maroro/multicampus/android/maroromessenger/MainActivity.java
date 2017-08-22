package maroro.multicampus.android.maroromessenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	public static final int SERVER_MODE=0x00;
	public static final int CLIENT_MODE=0x01;
	Button conn_server,run_as_server;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		conn_server=(Button)findViewById(R.id.connect_to_server);
		conn_server.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText server_ip=(EditText)findViewById(R.id.server_ip);
				String ip=server_ip.getText().toString();
				EditText server_port=(EditText)findViewById(R.id.server_port);
				String port=server_port.getText().toString();
				Intent it=new Intent(MainActivity.this,Chatting.class);
				it.putExtra("mode",CLIENT_MODE);
				it.putExtra("ip", ip);
				it.putExtra("port", port);
				startActivity(it);
				
			}
		});
		run_as_server=(Button)findViewById(R.id.run_as_server);
		run_as_server.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent(MainActivity.this,Chatting.class);
				it.putExtra("mode", SERVER_MODE);
				startActivity(it);
			}
		});
	}
}
