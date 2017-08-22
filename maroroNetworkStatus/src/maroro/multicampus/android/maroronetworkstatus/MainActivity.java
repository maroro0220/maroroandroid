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
	boolean isWifiAvail=info.isAvailable();//wifi���ɿ���
	boolean isWifiConn=info.isConnected();//wifi���ӿ���
	info=connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	boolean isMobileAvail=info.isAvailable();//����� ��Ʈ��ũ ���ɿ���
	boolean isMobileConn=info.isConnected();//����� ��Ʈ��ũ ���ӿ���
	String str="���� ���ӵ� ��Ʈ��ũ�� wifi�� ��:"+"Avail="+isWifiAvail+", ����:"+isWifiConn+
			"���� ���ӵ� ��Ʈ��ũ�� ������϶�: "+"Avail= "+isMobileAvail+", ����:"+isMobileConn;
	status.setText(str);
	}
}
