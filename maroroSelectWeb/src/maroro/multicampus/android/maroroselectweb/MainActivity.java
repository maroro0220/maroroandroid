package maroro.multicampus.android.maroroselectweb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	EditText input; TextView msg;
	public static String defaultUrl="http://www.google.com";
	Handler handler=new Handler();
	Button btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		input=(EditText)findViewById(R.id.editText1);
		input.setText(defaultUrl);
		msg=(TextView)findViewById(R.id.textView1);
		btn=(Button)findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			//스레드 생성 및 실행
			String url=input.getText().toString();
			ConnectThread ct =new ConnectThread(url);
			ct.start();
			}
		});
	}//oncreate end
	public String request(String url){
		StringBuilder output= new StringBuilder();
		try{
			URL myUrl=new URL(url);
			HttpURLConnection conn =(HttpURLConnection)myUrl.openConnection();
			if(conn!=null){
				int status=conn.getResponseCode();
				if(status==HttpURLConnection.HTTP_OK){
					BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
					String line = null;
					while(true){
						line=reader.readLine();
						if(line==null)break;
						output.append(line+"\n");
					}//while
					reader.close();
					conn.disconnect();
				}//정상 접속인 경우
			}//if end
		}catch(Exception e){}
		return output.toString();
	}
	class ConnectThread extends Thread{
		String urlStr;
	ConnectThread(String urlStr){
			this.urlStr=urlStr;
		}
		public void run(){
			try{
				final String output =request(urlStr);
				handler.post(new Runnable(){

					public void run() {
						// TODO Auto-generated method stub
						msg.setText(output);
					}
				});
			}catch(Exception e){}
		}
	}//thread end
	
	
}
