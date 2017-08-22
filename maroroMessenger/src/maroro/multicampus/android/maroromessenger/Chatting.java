package maroro.multicampus.android.maroromessenger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
//
//public class Chatting extends Activity {
//	private boolean isFirst = true;
//	private final int ADD_MESSAGE = 0;
//	private int PORT = 8888;
//	private String REMOTE_SERVER_IP = "";
//	private ListView listView;
//	private EditText message_box;
//	private ArrayAdapter<String> mAdapter;
//	private final String head_my_message = "I: ";
//	private final String head_your_message = "U: ";
//	private boolean runningMessageReceiver = false;
//	private boolean runningMessageSender = false;
//	private ServerSocket mServerSocket = null;
//	private Socket mClientSocket = null;
//	private int mode;
//	Queue<String> sendMessageQueue = new LinkedList<String>(); // �������� ���� �޽�����
//																// �׾Ƶδ� ����
//	Handler handler = new Handler() { // �ڵ鷯 ��ü�� �ϳ��� ������. �ڵ鷯�� ������� �����带 �������ִ�
//		// �����尡 �����κ��� �����͸� �޾Ƽ� �ٸ� ������� �������ϴµ� �����尡 �����带 ȣ�� ���ؼ� �ڵ鷯�� ���ؼ� ������.
//		// �ֻ����� ���� ����
//		public void handleMessage(Message msg) {
//
//		}
//	};
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.chatting_screen);
//		ArrayList<String> arrayList = new ArrayList<String>();
//		listView = (ListView) findViewById(R.id.message_history);
//		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
//		listView.setAdapter(mAdapter);
//		message_box = (EditText) findViewById(R.id.send_message);
//		Button send_message = (Button) findViewById(R.id.send_message);
//		send_message.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				String message=message_box.getText().toString();
//				if(message.length()>0){
//					sendMessage(message);
//					message_box.setText("");
//				}
//			}
//		});
//		Intent it = getIntent();
//		Bundle extra = it.getExtras();
//		mode = extra.getInt("mode");
//		switch (mode) {
//		case MainActivity.SERVER_MODE:
//			runAsServer();
//			break;
//		case MainActivity.CLIENT_MODE:
//			REMOTE_SERVER_IP = extra.getString("IP");
//			PORT = Integer.parseInt(extra.getString("port"));
//			runAsClient();
//			break;
//		}
//		runningMessageReceiver = true;
//		runningMessageSender = true;
//		// ������ ���� �� ����
//		MessageReceiver mr = new MessageReceiver();
//		mr.start();
//		MessageSender ms = new MessageSender();
//		ms.start();
//	}// oncreate
//		///////////// �޽��� �۽� ������//////////////
//
//	class MessageSender extends Thread {// �޼��� ���ۿ� ������
//		public void run() {
//			String message = null;
//			PrintWriter out = null;// �ż��� ��¿� ��ü ����
//			BufferedWriter bw = null;// �޼��� ��¿� ��ü ����
//			OutputStream os = null;// �޼��� ��¿� ��ü ����
//			OutputStreamWriter osw = null;// �޼��� ��¿� ��ü ����
//			while (runningMessageSender) {
//
//				if (mClientSocket != null && out == null) {// ������ �Ǿ�������, ��¿� ��ü��
//															// ���� ���
//
//					try {
//						os = mClientSocket.getOutputStream();// ��¿�
//																// ��ü(OutputStream)����
//						osw = new OutputStreamWriter(os);// ���� ����� ������ �ִ�
//															// OutputStreamWriter
//															// ����
//						bw = new BufferedWriter(osw);// ���۸� ����ϴ� BufferedWriter
//														// ����
//						out = new PrintWriter(bw, true);// ��¿� Ưȭ�� PrintWriter
//														// ����
//					} catch (IOException e) {
//
//						e.printStackTrace();
//
//					}
//
//				} else if (mClientSocket == null && out != null) {// ������ �����Ǿ�
//																	// �ְ�, ��¿�
//																	// ��ü�� �����ϴ�
//																	// ���
//
//					out.close();
//					out = null;
//				}
//				if (mClientSocket != null && out != null) {// ���ӵǾ� �ְ�, ��¿� ��ü��
//														// �����ϴ� ���
//					if (isFirst) {// PC�� ��Ƽ������ ������ �� �ʿ���
//						out.println(mClientSocket.getInetAddress().toString());
//						out.flush();
//						isFirst = false;
//					}
//					message = sendMessageQueue.poll();// ����� �޼����� ������ �ִ�
//														// Queue��ü���� �޼��� ȹ��
//					if (message != null) {// �޼����� �����ϴ� ���
//						out.println(message);// �޼��� ���
//						out.flush();// �޸𸮸� ����ϴ� ���, �޸𸮸� ����� ������ �޼����� ��µȴ�.
//						addMessageToListView(head_my_message + message);// �����
//																		// �޼�����
//																		// ListView��
//																		// ����ϵ���
//																		// addMessageToListView
//																		// �޼����
//																		// ����
//					}
//				}
//				try {
//					Thread.sleep(1);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			if (mode == MainActivity.CLIENT_MODE) {// Ŭ���̾�Ʈ�� �����ϴ� ���
//				if (mClientSocket != null) {// ���ӵ� ������ �ִ� ���
//					try {// ��ü ����
//						mClientSocket.shutdownOutput();
//
//						mClientSocket.close();
//
//						mClientSocket = null;
//
//					} catch (IOException e) {
//
//						e.printStackTrace();
//
//					}
//
//				}
//
//			} else if (mode == MainActivity.SERVER_MODE) {// ������ �����ϴ� ���
//
//				if (mServerSocket != null) {// ���ӵ� ������ �ִ� ���
//
//					try {// ��ü ����
//
//						if (mClientSocket != null)
//
//							mClientSocket.shutdownInput();
//
//						mServerSocket.close();
//
//						mServerSocket = null;
//
//					} catch (IOException e) {
//
//						e.printStackTrace();
//
//					}
//				}
//			}
//			if (out != null) {
//				out.close();
//				out = null;
//			}
//			if (bw != null) {
//				try {
//					bw.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				bw = null;
//			}
//			if (osw != null) {
//				try {
//					osw.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				osw = null;
//			}
//			if (os != null) {
//				try {
//					os.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				os = null;
//			}
//		}
//	}// End of ������
//
//	////////////////////////////////////////////
//
//	/////////////// ���� ������////////////////
//
//	class MessageReceiver extends Thread {// ä�� �޼����� �����ϴ� ������
//
//		public void run() {
//
//			BufferedReader br = null;// �޼��� ���ſ� ����� ��ü
//
//			InputStream is = null;// �޼��� ���ſ� ����� ��ü
//
//			InputStreamReader isr = null;// �޼��� ���ſ� ����� ��ü
//
//			while (runningMessageReceiver) {
//
//				try {
//
//					if (mClientSocket == null && mode == MainActivity.SERVER_MODE) {
//
//						// ������ ���� ���� ���
//
//						mClientSocket = mServerSocket.accept();// Ŭ���̾�Ʈ�� ���� ��û��
//																// ����
//						addMessageToListView(mClientSocket.getInetAddress()
//								.getHostAddress()
//								+ "��(��) ����Ǿ����ϴ�.");// addMessageToListView �޼����
//													// ��µ� ������ ����
//					}
//					if (mClientSocket == null)// Ŭ���̾�Ʈ�� �������� ������ �ٽ� �ݺ�
//						continue;
//					is = mClientSocket.getInputStream();// ����� ���Ͽ��� �޼��� ���ſ�
//														// ��ü(InputStream)�� ȹ��
//					isr = new InputStreamReader(is);// �� �� ���� ����� �޼��� ���ſ�
//													// ��ü(InputStreamReader)�� ����
//					if (br == null) {
//						br = new BufferedReader(isr);// �޸𸮸� �̿��ؼ� ���� �޼����� ó���� ��
//														// �ִ�
//														// ��ü(BufferedReader)��
//														// ����
//					}
//					String data = br.readLine();// ���ŵ� �޼����� ����
//					if (data == null) {// ���ŵ� �޼����� ���� ���
//						br.close();
//						br = null;
//						addMessageToListView("������ ������ ������ϴ�.");
//						if (mode == MainActivity.CLIENT_MODE) {
//							finish();
//						}
//					} else {// ���ŵ� �޼����� �ִ� ���
//						addMessageToListView(head_your_message + data);// ���ŵ�
//																		// �޼�����
//																		// ListView��
//																		// ���
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				} finally {
//				}
//				try {
//					Thread.sleep(1);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			if (br != null) {// ����� ��ü�� ����
//				try {
//					br.close();
//					br = null;
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (isr != null) {// ����� ��ü�� ����
//				try {
//					isr.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				isr = null;
//			}
//			if (is != null) {// ����� ��ü�� ����
//				try {
//					is.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				is = null;
//			}
//		}
//	}
//	///////////////////////////////////////////////////////
//
//	/////////////// ��Ÿ �޼���//////////////////////
//	private String getLocalIpAddress() {
//
//		try {
//
//			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
//
//				// NetworkInterface : This class is used to represent a network
//				// interface of the local device.
//
//				// getNetworkInterfaces() : Gets a list of all network
//				// interfaces available on the local system or null if no
//				// interface is available.
//
//				// en.hasMoreElement() �� ��ȯ���� true�� ���� ���ѹݺ�
//
//				NetworkInterface intf = en.nextElement();
//
//				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
//					InetAddress inetAddress = enumIpAddr.nextElement();
//					if (!inetAddress.isLoopbackAddress()) {		if(!inetAddress.getHostAddress()
//							.toString().contains(":")){
//					return inetAddress.getHostAddress()
//							.toString();
//					}
//					}
//				}
//			}
//		} catch (SocketException ex) {}
//		return null;
//	}
//	private void sendMessage(String message) {
//		sendMessageQueue.offer(message);
//	}
//	private void addMessageToListView(String data) {
//		if (data != null && data.length() > 0) {
//			Message message = handler.obtainMessage();// �ڵ鷯�� ȣ���� �� �ʿ��� Message
//														// ��ü�� ����
//			message.what = ADD_MESSAGE;// Message ��ü�� �ִ� what ������ ADD_MESSAGE ����
//										// ����
//			message.arg1 = 0;// Message ��ü�� �ִ� arg1 ������ 0�� ����
//			message.arg2 = 0;// Message ��ü�� �ִ� arg2 ������ 0�� ����
//			message.obj = data;// Message ��ü�� �ִ� obj ������ ���ڿ��� ����
//			// �ڵ鷯�� ���ؼ� ó���� �����͵��� what, arg1, arg2, obj��� ������ �����Ѵ�.
//			handler.sendMessage(message);// ������ ������ Message ��ü�� �̿��ؼ� �ڵ鷯 ȣ��
//		}
//	}
//	////////////////////////////////////////////
//
//	private String getLocalAddress() {
//		return null;
//	}
//
//	public void runAsServer() {
//		try {
//			if (mServerSocket == null) {
//				mServerSocket = new ServerSocket(PORT);
//				mAdapter.add("������ ����Ǿ����ϴ�." + "������ IP��" + getLocalAddress());
//			}
//		} catch (Exception e) {
//		}
//	}
//	public void runAsClient() {
//		try {
//			if (mClientSocket == null) {
//				mClientSocket = new Socket(REMOTE_SERVER_IP, PORT);
//				mAdapter.add("������ ���ӵǾ����ϴ�." + "������ IP��" + REMOTE_SERVER_IP);
//			}
//		} catch (Exception e) {
//		}
//	}
//
//}
public class Chatting extends Activity {
	private boolean isFirst = true;
	private final int ADD_MESSAGE = 0;
	private final int CONNECT_BY_CLIENT = 1;
	private final int CONNECT_BY_SERVER = 2;
	private int PORT = 2000;
	private String REMOTE_SERVER_IP = "";
	private ListView listView;
	private EditText message_box;
	private ArrayAdapter<String> mAdapter;
	private final String head_my_message="��: ";
	private final String head_your_message="��: ";
	private boolean runningMessageReceiver=false;
	private boolean runningMessageSender=false;
	private ServerSocket mServerSocket = null;
	private Socket mClientSocket = null;
	private int mode;
	Queue<String> sendMessageQueue=
			new LinkedList<String>();
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch(msg.what){
			case ADD_MESSAGE :
				mAdapter.add((String)msg.obj);
				break;
			case CONNECT_BY_CLIENT:
				try{
					new Thread(new Runnable(){
						public void run() {
							try{
								mClientSocket = new Socket(REMOTE_SERVER_IP,PORT);
							} catch(Exception e){}
						}
					}).start();
				}catch(Exception e){
					Log.i("socket", "CONNECT_BY_CLIENT ����:"+e.getMessage());
				}
				break;
			case CONNECT_BY_SERVER:
				try{
					mServerSocket = new ServerSocket(PORT);
				}catch(IOException e){
					Log.i("socket", "CONNECT_BY_SERVER ����:"+e.getMessage());
				}
				break;
			}
		}
	};
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(runningMessageReceiver){
			runningMessageReceiver = false;
		}
		if(runningMessageSender){
			runningMessageSender = false;
		}
	}
	private String getLocalIpAddress(){
		try{
			for(Enumeration<NetworkInterface> en = 
				NetworkInterface.getNetworkInterfaces();
					en.hasMoreElements();){
				NetworkInterface intf=en.nextElement();
				for(Enumeration<InetAddress> enumIpAddr=

					intf.getInetAddresses();

					enumIpAddr.hasMoreElements();){

					InetAddress inetAddress=

							enumIpAddr.nextElement();

					if(!inetAddress.isLoopbackAddress()){

						if(!inetAddress.getHostAddress()

								.toString().contains(":")){

						return inetAddress.getHostAddress()

								.toString();

						}

					}

				}

			}

		}catch(SocketException e){

			

		}

		return null;

	}

	private void runAsServer(){

		try{

			if(mServerSocket == null){

				mServerSocket = new ServerSocket(PORT);

				

				mAdapter.add("������ ����Ǿ����ϴ�."

					+ "������ IP�� "+getLocalIpAddress()

					+ "�Դϴ�.");

			}

		}catch(Exception e){

			

		}

	}

	

	private void runAsClient(){

		try{

			if(mClientSocket == null){

				Log.i("socket", "runAsClient before new Socket()");

//				mClientSocket = new Socket(REMOTE_SERVER_IP,PORT);//���� ������ �����忡�� �ؾ� �Ѵ�.

				Message message=handler.obtainMessage();

				message.what = CONNECT_BY_CLIENT;

				handler.sendMessage(message);



				mAdapter.add("������ ���ӵǾ����ϴ�.������ IP�� "+REMOTE_SERVER_IP+

					"��Ʈ��ȣ�� "+PORT+"�Դϴ�.");

				Log.i("socket", "������ ��Ʈ:"+REMOTE_SERVER_IP+","+PORT);

			}else{

				Log.i("socket", "mClientSocket is not null");

			}

		}catch(Exception e){

			Log.i("socket", "���ܹ߻�:"+e.getMessage());

		}

	}



	class MessageSender extends Thread{

		public void run() {

			String message = null;	PrintWriter out = null;

			BufferedWriter bw = null;

			OutputStream os = null;

			OutputStreamWriter osw = null;

			while(runningMessageSender){

				if(mClientSocket != null&& out == null){

				//������ �Ǿ�������,��°�ü�� ���� ���

					try{

						os=mClientSocket.getOutputStream();

						osw=new OutputStreamWriter(os);

						bw=new BufferedWriter(osw);

						out=new PrintWriter(bw,true);

					}catch(IOException e){}

				}else if(mClientSocket==null&&out!=null){

				//������ �����Ǿ���,��°�ü�� �ִ� ���

					out.close(); //��°�ü�� ����

				}

				if(mClientSocket!=null&&out!=null){

				//���ӵǾ��ְ�, ��°�ü�� �ִ� ���

					if(isFirst){//���ӵǰ� ó���� ���

						out.println(mClientSocket

							.getInetAddress().toString());

						//���ӵ� IP�ּҸ� ���

						out.flush();//��°�ü�� ���

						isFirst = false;

					}

					message=sendMessageQueue.poll();

					//����� �޼����� �������ִ� ��ü�κ���

					//�޼����� ȹ��

					if(message != null){

						out.println(message);//���

						out.flush();//��°�ü�� ���

						addMessageToListView(

							head_my_message+message);

					}

				}//end of If

				try{

					Thread.sleep(10);

				}catch(InterruptedException e){}

			}//End of while

			if(mode==MainActivity.CLIENT_MODE){

				if(mClientSocket !=null){

					try{

						mClientSocket.shutdownInput();

						mClientSocket.close();

					}catch(IOException e){}

				}

			}else if(mode==MainActivity.SERVER_MODE){

				if(mServerSocket != null){

					try{

						if(mClientSocket != null){

							mClientSocket.shutdownInput();

						}

						mServerSocket.close();

					}catch(IOException e){}

				}

			}

			if(out!= null){	out.close();}

			if(bw!= null){

				try{bw.close();}catch(IOException e){}

			}

			if(osw!= null){

				try{osw.close();}catch(IOException e){}

			}

			if(os!= null){

				try{os.close();}catch(IOException e){}

			}

		}

	}

	private void addMessageToListView(String data){

		if(data != null && data.length()>0){

			Message message=handler.obtainMessage();

			message.what = ADD_MESSAGE;

			message.arg1 = 0;

			message.arg2 = 0;

			message.obj = data;

			handler.sendMessage(message);

			//�ڵ鷯 ȣ��

		}//������ ���ڿ��� ���� ���� ���

	}//���ڿ��� ListView�� ���

	class MessageReceiver extends Thread{

		public void run() {

			BufferedReader br = null;

			//�޼��� ���ſ� ���Ǵ� ��ü

			InputStream is = null;

			//�޼��� ���ſ� ���Ǵ� ��Ʈ����ü

			InputStreamReader isr = null;

			//�޼��� ���ſ� ���Ǵ� ���� ��Ʈ����ü

			while(runningMessageReceiver){

				try{

					if(mClientSocket == null &&

						mode==MainActivity.SERVER_MODE){

						//������ �������� ���

						Log.i("server", "MessageReceiver���� mServerSocket.accept()...");

						mClientSocket=mServerSocket.accept();

						//Ŭ���ƾ�Ʈ�� ���� ��û�� ����

						addMessageToListView(

							mClientSocket.getInetAddress()

							.getHostAddress()+"�� ����Ǿ����ϴ�."

							);//ListView�� �޼����� ���

					}

					if(mClientSocket == null) continue;

					//Ŭ���̾�Ʈ�� �������� �ʴ� ���� �ٽ� �ݺ�

					is = mClientSocket.getInputStream();

					isr = new InputStreamReader(is);

					if(br == null){

						br = new BufferedReader(isr);

					}

					String data = br.readLine();//���ŵȸ޼�������

					if(data == null){

						br.close(); br = null;

						addMessageToListView("������ ������ "

							+ "������ϴ�.");

						if(mode==MainActivity.CLIENT_MODE){

							finish();//���ø����̼� ����

						}

					}else{

						addMessageToListView(head_your_message+

						data);//���ŵ� �޼�����ListView�� ��� 

					}

				}catch(IOException e){}

				finally{}

				try{

					Thread.sleep(10);

				}catch(InterruptedException e){

					

				}

			}//End of While

			if(br!=null){

				try{br.close();}catch(Exception e){}

			}

			if(isr!=null){

				try{isr.close();}catch(Exception e){}

			}

			if(is != null){

				try{is.close();}catch(Exception e){}

			}

		}

	}

	

	private void sendMessage(String message){

		sendMessageQueue.offer(message);

	}

	

	@Override

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.chatting_screen);

		ArrayList<String> arrayList = 

			new ArrayList<String>();

		listView = (ListView)

			findViewById(R.id.message_history);

		mAdapter=new ArrayAdapter<String>(

			Chatting.this,

			android.R.layout.simple_list_item_1,

			arrayList);

		listView.setAdapter(mAdapter);

		message_box=(EditText)

			findViewById(R.id.message_box);

		Button send_message=(Button)

			findViewById(R.id.send_message);

		Intent it = getIntent();//����Ʈ ����

		Bundle extra = it.getExtras();//�����ͼ���

		mode = extra.getInt("mode");//mode�� ����� ��ȹ��

		switch(mode){

		case MainActivity.SERVER_MODE:

			runAsServer(); break;

		case MainActivity.CLIENT_MODE:

			REMOTE_SERVER_IP=extra.getString("ip");

			PORT = Integer.parseInt(extra.getString("port"));

			Log.i("socket", "CLIENT_MODE:"+REMOTE_SERVER_IP+","+PORT);

			runAsClient(); break;

		}

		runningMessageReceiver = true;

		MessageReceiver mr = new MessageReceiver();

		Thread threadReceiver = new Thread(mr);

		threadReceiver.start();

		runningMessageSender = true;

		MessageSender ms = new MessageSender();

		Thread threadSender = new Thread(ms);

		threadSender.start();

		

		send_message.setOnClickListener(

			new OnClickListener(){

				public void onClick(View v) {

					String message=message_box

						.getText().toString();

					if(message.length()>0){

						//�޼��� ����

						sendMessage(message);

						message_box.setText("");

					}

				}

			});

	}

}