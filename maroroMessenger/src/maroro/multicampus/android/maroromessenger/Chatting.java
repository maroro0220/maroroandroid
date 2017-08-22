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
//	Queue<String> sendMessageQueue = new LinkedList<String>(); // 상대방으로 보낼 메시지를
//																// 쌓아두는 역할
//	Handler handler = new Handler() { // 핸들러 자체도 하나의 스레드. 핸들러는 스레드와 스레드를 연결해주는
//		// 스레드가 서버로부터 데이터를 받아서 다른 스레드로 보내야하는데 스레드가 스레드를 호출 못해서 핸들러를 통해서 가야함.
//		// 주사위를 돌릴 떄도
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
//		// 스레드 생성 및 실행
//		MessageReceiver mr = new MessageReceiver();
//		mr.start();
//		MessageSender ms = new MessageSender();
//		ms.start();
//	}// oncreate
//		///////////// 메시지 송신 스레드//////////////
//
//	class MessageSender extends Thread {// 메세저 전송용 스레드
//		public void run() {
//			String message = null;
//			PrintWriter out = null;// 매세지 출력용 객체 선언
//			BufferedWriter bw = null;// 메세지 출력용 객체 선언
//			OutputStream os = null;// 메세지 출력용 객체 선언
//			OutputStreamWriter osw = null;// 메세지 출력용 객체 선언
//			while (runningMessageSender) {
//
//				if (mClientSocket != null && out == null) {// 접속이 되어있지만, 출력용 객체가
//															// 없는 경우
//
//					try {
//						os = mClientSocket.getOutputStream();// 출력용
//																// 객체(OutputStream)생성
//						osw = new OutputStreamWriter(os);// 향상된 기능을 가지고 있는
//															// OutputStreamWriter
//															// 생성
//						bw = new BufferedWriter(osw);// 버퍼를 사용하는 BufferedWriter
//														// 생성
//						out = new PrintWriter(bw, true);// 출력에 특화된 PrintWriter
//														// 생성
//					} catch (IOException e) {
//
//						e.printStackTrace();
//
//					}
//
//				} else if (mClientSocket == null && out != null) {// 접속이 해제되어
//																	// 있고, 출력용
//																	// 객체가 존재하는
//																	// 경우
//
//					out.close();
//					out = null;
//				}
//				if (mClientSocket != null && out != null) {// 접속되어 있고, 출력용 객체가
//														// 존재하는 경우
//					if (isFirst) {// PC의 멀티서버와 접속할 때 필요함
//						out.println(mClientSocket.getInetAddress().toString());
//						out.flush();
//						isFirst = false;
//					}
//					message = sendMessageQueue.poll();// 출력할 메세지를 가지고 있는
//														// Queue객체에서 메세지 획득
//					if (message != null) {// 메세지가 존재하는 경우
//						out.println(message);// 메세지 출력
//						out.flush();// 메모리를 사용하는 경우, 메모리를 비워야 실제로 메세지가 출력된다.
//						addMessageToListView(head_my_message + message);// 출력한
//																		// 메세지를
//																		// ListView에
//																		// 출력하도록
//																		// addMessageToListView
//																		// 메서드로
//																		// 전달
//					}
//				}
//				try {
//					Thread.sleep(1);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			if (mode == MainActivity.CLIENT_MODE) {// 클라이언트로 동작하는 경우
//				if (mClientSocket != null) {// 접속된 소켓이 있는 경우
//					try {// 객체 제거
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
//			} else if (mode == MainActivity.SERVER_MODE) {// 서버로 동작하는 경우
//
//				if (mServerSocket != null) {// 접속된 소켓이 있는 경우
//
//					try {// 객체 제거
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
//	}// End of 스레드
//
//	////////////////////////////////////////////
//
//	/////////////// 수신 스레드////////////////
//
//	class MessageReceiver extends Thread {// 채팅 메세지를 수신하는 스레드
//
//		public void run() {
//
//			BufferedReader br = null;// 메세지 수신에 사용할 객체
//
//			InputStream is = null;// 메세지 수신에 사용할 객체
//
//			InputStreamReader isr = null;// 메세지 수신에 사용할 객체
//
//			while (runningMessageReceiver) {
//
//				try {
//
//					if (mClientSocket == null && mode == MainActivity.SERVER_MODE) {
//
//						// 서버로 동작 중인 경우
//
//						mClientSocket = mServerSocket.accept();// 클라이언트로 부터 요청을
//																// 수락
//						addMessageToListView(mClientSocket.getInetAddress()
//								.getHostAddress()
//								+ "와(과) 연결되었습니다.");// addMessageToListView 메서드로
//													// 출력될 문장을 전송
//					}
//					if (mClientSocket == null)// 클라이언트가 존재하지 않으면 다시 반복
//						continue;
//					is = mClientSocket.getInputStream();// 연결된 소켓에서 메세지 수신용
//														// 객체(InputStream)을 획득
//					isr = new InputStreamReader(is);// 좀 더 향상된 기능의 메세지 수신용
//													// 객체(InputStreamReader)를 생성
//					if (br == null) {
//						br = new BufferedReader(isr);// 메모리를 이용해서 수신 메세지를 처리할 수
//														// 있는
//														// 객체(BufferedReader)를
//														// 생성
//					}
//					String data = br.readLine();// 수신된 메세지를 읽음
//					if (data == null) {// 수신된 메세지가 없는 경우
//						br.close();
//						br = null;
//						addMessageToListView("상대와의 접속이 끊겼습니다.");
//						if (mode == MainActivity.CLIENT_MODE) {
//							finish();
//						}
//					} else {// 수신된 메세지가 있는 경우
//						addMessageToListView(head_your_message + data);// 수신된
//																		// 메세지를
//																		// ListView에
//																		// 출력
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
//			if (br != null) {// 사용한 객체를 제거
//				try {
//					br.close();
//					br = null;
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (isr != null) {// 사용한 객체를 제거
//				try {
//					isr.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				isr = null;
//			}
//			if (is != null) {// 사용한 객체를 제거
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
//	/////////////// 기타 메서드//////////////////////
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
//				// en.hasMoreElement() 의 반환값이 true일 동안 무한반복
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
//			Message message = handler.obtainMessage();// 핸들러를 호출할 때 필요한 Message
//														// 객체를 생성
//			message.what = ADD_MESSAGE;// Message 객체에 있는 what 변수에 ADD_MESSAGE 값을
//										// 저장
//			message.arg1 = 0;// Message 객체에 있는 arg1 변수에 0을 저장
//			message.arg2 = 0;// Message 객체에 있는 arg2 변수에 0을 저장
//			message.obj = data;// Message 객체에 있는 obj 변수에 문자열을 저장
//			// 핸들러에 의해서 처리할 데이터들을 what, arg1, arg2, obj라는 변수에 저장한다.
//			handler.sendMessage(message);// 위에서 생성한 Message 객체를 이용해서 핸들러 호출
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
//				mAdapter.add("서버로 실행되었습니다." + "서버의 IP는" + getLocalAddress());
//			}
//		} catch (Exception e) {
//		}
//	}
//	public void runAsClient() {
//		try {
//			if (mClientSocket == null) {
//				mClientSocket = new Socket(REMOTE_SERVER_IP, PORT);
//				mAdapter.add("서버에 접속되었습니다." + "서버의 IP는" + REMOTE_SERVER_IP);
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
	private final String head_my_message="나: ";
	private final String head_your_message="너: ";
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
					Log.i("socket", "CONNECT_BY_CLIENT 예외:"+e.getMessage());
				}
				break;
			case CONNECT_BY_SERVER:
				try{
					mServerSocket = new ServerSocket(PORT);
				}catch(IOException e){
					Log.i("socket", "CONNECT_BY_SERVER 예외:"+e.getMessage());
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

				

				mAdapter.add("서버로 실행되었습니다."

					+ "서버의 IP는 "+getLocalIpAddress()

					+ "입니다.");

			}

		}catch(Exception e){

			

		}

	}

	

	private void runAsClient(){

		try{

			if(mClientSocket == null){

				Log.i("socket", "runAsClient before new Socket()");

//				mClientSocket = new Socket(REMOTE_SERVER_IP,PORT);//소켓 접속은 스레드에서 해야 한다.

				Message message=handler.obtainMessage();

				message.what = CONNECT_BY_CLIENT;

				handler.sendMessage(message);



				mAdapter.add("서버에 접속되었습니다.서버의 IP는 "+REMOTE_SERVER_IP+

					"포트번호는 "+PORT+"입니다.");

				Log.i("socket", "서버와 포트:"+REMOTE_SERVER_IP+","+PORT);

			}else{

				Log.i("socket", "mClientSocket is not null");

			}

		}catch(Exception e){

			Log.i("socket", "예외발생:"+e.getMessage());

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

				//접속은 되어있지만,출력객체가 없는 경우

					try{

						os=mClientSocket.getOutputStream();

						osw=new OutputStreamWriter(os);

						bw=new BufferedWriter(osw);

						out=new PrintWriter(bw,true);

					}catch(IOException e){}

				}else if(mClientSocket==null&&out!=null){

				//접속은 해제되었고,출력객체가 있는 경우

					out.close(); //출력객체를 닫음

				}

				if(mClientSocket!=null&&out!=null){

				//접속되어있고, 출력객체가 있는 경우

					if(isFirst){//접속되고 처음인 경우

						out.println(mClientSocket

							.getInetAddress().toString());

						//접속된 IP주소를 출력

						out.flush();//출력객체를 비움

						isFirst = false;

					}

					message=sendMessageQueue.poll();

					//출력할 메세지를 가지고있는 객체로부터

					//메세지를 획득

					if(message != null){

						out.println(message);//출력

						out.flush();//출력객체를 비움

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

			//핸들러 호출

		}//전달할 문자열이 있을 때만 출력

	}//문자열을 ListView에 출력

	class MessageReceiver extends Thread{

		public void run() {

			BufferedReader br = null;

			//메세지 수신에 사용되는 객체

			InputStream is = null;

			//메세지 수신에 사용되는 스트림객체

			InputStreamReader isr = null;

			//메세지 수신에 사용되는 향상된 스트림객체

			while(runningMessageReceiver){

				try{

					if(mClientSocket == null &&

						mode==MainActivity.SERVER_MODE){

						//서버로 동작중인 경우

						Log.i("server", "MessageReceiver에서 mServerSocket.accept()...");

						mClientSocket=mServerSocket.accept();

						//클리아언트로 부터 요청을 수락

						addMessageToListView(

							mClientSocket.getInetAddress()

							.getHostAddress()+"와 연결되었습니다."

							);//ListView로 메세지를 출력

					}

					if(mClientSocket == null) continue;

					//클라이언트가 존재하지 않는 경우는 다시 반복

					is = mClientSocket.getInputStream();

					isr = new InputStreamReader(is);

					if(br == null){

						br = new BufferedReader(isr);

					}

					String data = br.readLine();//수신된메세지읽음

					if(data == null){

						br.close(); br = null;

						addMessageToListView("상대와의 접속이 "

							+ "끊겼습니다.");

						if(mode==MainActivity.CLIENT_MODE){

							finish();//어플리케이션 종료

						}

					}else{

						addMessageToListView(head_your_message+

						data);//수신된 메세지를ListView에 출력 

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

		Intent it = getIntent();//인텐트 수신

		Bundle extra = it.getExtras();//데이터수신

		mode = extra.getInt("mode");//mode에 저장된 값획득

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

						//메세지 전송

						sendMessage(message);

						message_box.setText("");

					}

				}

			});

	}

}