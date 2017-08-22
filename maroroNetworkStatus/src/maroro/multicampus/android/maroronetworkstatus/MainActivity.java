package maroro.multicampus.android.maroronetworkstatus;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView status; ConnectivityManager connectivity;
	NetworkInfo info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	status=(TextView)findViewById(R.id.txt_status);
	connectivity=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	info=connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	boolean isWifiAvail=info.isAvailable();//wifi啊瓷咯何
	boolean isWifiConn=info.isConnected();//wifi立加咯何
	info=connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	boolean isMobileAvail=info.isAvailable();//葛官老 匙飘况农 啊瓷咯何
	boolean isMobileConn=info.isConnected();//葛官老 匙飘况农 立加咯何
	String str="泅犁 立加等 匙飘况农啊 wifi老 嫐:"+"Avail="+isWifiAvail+", 立加:"+isWifiConn+
			"泅犁 立加等 匙飘况农啊 葛官老老锭: "+"Avail= "+isMobileAvail+", 立加:"+isMobileConn;
	status.setText(str);
	}
}
