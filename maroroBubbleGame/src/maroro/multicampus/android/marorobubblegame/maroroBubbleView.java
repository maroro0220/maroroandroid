package maroro.multicampus.android.marorobubblegame;


import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

//class WaterBall{//�Ѿ�
//	public int x,y,rad;//��ǥ, ������
//	public boolean dead=false;  //�Ѿ� ���� ����
//	public Bitmap imgBall; //�Ѿ� �̹��� ���
//	private int width,height;//view�� ũ��
//	private int speed;
//	public WaterBall(Context context,int _x, int _y, int _width,int _height){
//		x=_x; y=_y; width=_width; height=_height;
//		imgBall=BitmapFactory.decodeResource(context.getResources(), R.drawable.w0);
//		rad=imgBall.getWidth()/2;
//		speed=20;
//		moveBall();
//	}
//	public void moveBall(){
//		y=y-speed;
//		if(y<0) dead=true;
//	}
//	
//}

public class maroroBubbleView extends SurfaceView implements Callback{
	BubbleThread bThread; SurfaceHolder mHolder;

	public void stopGame(){
		bThread.stopThread();
	}
	public void pauseGame(){
		bThread.pauseResume(true);
	}
	public void resumeGame(){
		bThread.pauseResume(false);
	}
	public void restartGame(){
		bThread.stopThread();
		bThread=null;
		bThread=new BubbleThread(mHolder,mContext);
		bThread.start();
	}
	public maroroBubbleView(Context context) {
		super(context);
		mContext=context;

		
		SurfaceHolder sh = getHolder();
		sh.addCallback(this);
		mHolder = sh;
		bThread=new BubbleThread(mHolder,context);
	}
	Context mContext;

@Override
	public boolean onTouchEvent(MotionEvent event) {
	if(event.getAction()==MotionEvent.ACTION_DOWN){
		synchronized(mHolder){
//				int x = (int)event.getX();
//				int y = (int)event.getY();
//				bThread.makeBubbles(x, y);
			bThread.makeWaterBall();
		}
	}
	return true;
	}

	class BubbleThread extends Thread implements SensorEventListener{

		String m_str = "";
		SensorManager sensorManager;
		boolean canRun =true;
		boolean isWait =false;
		int width,height;Bitmap imgBack;
		Bitmap imgSpider;
		int sw,sh;
		int mx=0,my=0;
		long lastTime;
		public void stopThread(){
			canRun=false;
			synchronized (this) {
				this.notify();
			}
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}

		public void pauseResume(boolean wait){//�Ͻ�����/��⵿
			isWait=wait;   
			synchronized (this) {
				this.notify();
			}
		}
		SurfaceHolder mHolder; Context mContext;
		

		
		ArrayList<Score> mScore=
				new ArrayList<Score>();
		ArrayList<WaterBall> wBall=
				new ArrayList<WaterBall>();
		int tot=0;
		Paint paint=new Paint();
		public void drawCharacters(Canvas canvas){
			canvas.drawBitmap(imgBack,0,0,null);
			for(Bubble tmp : mBall){
				canvas.drawBitmap(tmp.imgBall,tmp.x-tmp.rad,tmp.y-tmp.rad, null);
			}
			for(SmallBubble tmp: sBall){
				canvas.drawBitmap(tmp.imgBall,tmp.x-tmp.rad,tmp.y-tmp.rad, null);
			}	for(WaterBall tmp: wBall){
				canvas.drawBitmap(tmp.imgBall,tmp.x-tmp.rad,tmp.y-tmp.rad, null);
			}
			for(Score tmp:mScore){
				canvas.drawText("+100",tmp.x-20,tmp.y-10,tmp.paint);
			}
			canvas.drawText("����:"+tot, 10, 30, paint);
			canvas.drawBitmap(imgSpider, mx-sw, my-sh,null);
		}
		
		public void onSensorChanged(SensorEvent event) {
			//���� ����:
			//���: �ܸ��⸦ �������� ���¿��� ���ۺ��۵��� 
			//��:�ܸ��⸦ ���� ���¿���, ȭ���� �� �� Ȥ�� �ݴ�������� ������
			//��ġ:�ܸ��⸦ �����ٰ�, ������ �ϴ� ������
		synchronized(this){//���� ������ ���ÿ� �߻��ϴϱ� ���ʴ�� �����ϵ��� �Ϸ��� 
			switch(event.sensor.getType()){
			case Sensor.TYPE_ORIENTATION:
				float heading=event.values[0];
				float pitch=event.values[1];
				float roll=event.values[2];
				m_str="���Ⱚ";
				m_str=m_str+"Heading:"+String.valueOf(heading);
				m_str=m_str+", Pitch:"+String.valueOf(pitch);
				m_str=m_str+", Roll:"+String.valueOf(roll);
				mx=mx-(int)roll; //-90~90
				my=my-(int)pitch;//-180~180
				if(mx<=0) mx=0;
				if(my<=0) my=0;
				if(mx>=(getWidth()-50))
					mx=getWidth()-50;
				if(my>=(getHeight()-50))
					my=getHeight()-50;
				
				break;
				
				
			}
		}invalidate();
	}

//		imgSpider=BitmapFactory.decodeResource(getResources(),R.drawable.)
		public void checkCollision(){
			int x1,y1,x2,y2;
			for(WaterBall water:wBall){
				x1=water.x;y1=water.y;//�Ѿ� ��ǥ
				for(Bubble tmp:mBall){
					x2=tmp.x;y2=tmp.y;//ǳ����ǥ
					if(Math.abs(x1-x2)<tmp.rad&&Math.abs(y1-y2)<tmp.rad){
						makeSmallBubbles(tmp.x,tmp.y);
						mScore.add(new Score(tmp.x,tmp.y));
						tot=tot+100;
						tmp.dead=true;water.dead=true;
					}//if 
				}//��� ǳ���� ���� �ݺ�
			}
		}
		public void makeWaterBall(){
			long thisTime=System.currentTimeMillis();
			if(thisTime-lastTime>=300){
				wBall.add(new WaterBall(mContext,mx,my,width,height));
			}//0.3�� ���� 1���� �߻�
			lastTime=thisTime;
			
		}
		public void moveCharacters(){//��� ĳ�����̵�
			//ū �񴩹�� �̵�
			for(int i=mBall.size()-1;i>=0;i--){
				mBall.get(i).moveBubble();
				if(mBall.get(i).dead)
					mBall.remove(i);
			}
			//���� �񴩹�路 �̵�
			for(int i=sBall.size()-1;i>=0;i--){
				sBall.get(i).moveBall();
				if(sBall.get(i).dead)
					sBall.remove(i);
			}
			//�Ѿ��̵�
			for(int i=wBall.size()-1;i>=0;i--){
				wBall.get(i).moveBall();
				if(wBall.get(i).dead)
					wBall.remove(i);
			}
			for(int i=mScore.size()-1;i>=0;i--){
				
			}
		}
		ArrayList<Bubble> mBall=new ArrayList<Bubble>();
		BubbleThread(SurfaceHolder holder,Context ctx){
			mHolder=holder; mContext=ctx;
			sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
			sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
					SensorManager.SENSOR_DELAY_GAME);
			Display display=((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			width=display.getWidth();
			height=display.getHeight()-50;
			imgBack=BitmapFactory.decodeResource(getResources(),R.drawable.sky);//����̹��� ����
			imgBack=Bitmap.createScaledBitmap(imgBack,width,height,false);//������ ���߱�
			///////////////////////////////////
			imgSpider=BitmapFactory.decodeResource(
					getResources(),R.drawable.spider1);
				sw = imgSpider.getWidth()/2;
				sh = imgSpider.getHeight()/2;
			
				paint.setTextSize(50);
				paint.setColor(Color.WHITE);
				paint.setAntiAlias(true);
				/////////////////////////
			setFocusable(true);
		}
		
		public void moveBubbles(){
			for(int i=mBall.size()-1;i>=0;i--){//run���� ȣ��
				mBall.get(i).moveBubble();//ǳ���̵�
				if(mBall.get(i).dead==true){
				makeSmallBubbles(mBall.get(i).x,mBall.get(i).y);
					mBall.remove(i);   //�浹Ƚ���� 3�̻��̸� ����		
				}
				}
			for(int i=sBall.size()-1;i>=0;i--){
				sBall.get(i).moveBall();
				if(sBall.get(i).dead==true)
					sBall.remove(i);
			}
		}
		public void makeBubbles(int x,int y){
			//ǳ���� �������� �޼���, ȭ�� ��ġ�� �� ȣ��
			boolean flag = false;
			for(Bubble tmp:mBall){
				if(Math.pow(tmp.x-x,2)+Math.pow(tmp.y-y, 2)<=(Math.pow(tmp.rad,2))){
					//x�� ���� +y�� ����<�������� �����̸� ������
					tmp.dead=true;//��ġ�� ǳ�� ����
					flag=true;
				}//ǳ�� ��ġ ��
			}//�ݺ���
			if(flag==false){
				mBall.add(new Bubble(mContext,x,y,width,height));
			}
			}
		ArrayList<SmallBubble> sBall=new ArrayList<SmallBubble>();
		public void makeSmallBubbles(int x, int y){
			Random rnd =new Random();
			int count=rnd.nextInt(9)+7;//7~15
			for(int i=1;i<=count;i++){
				int ang=rnd.nextInt(360);//����
				sBall.add(new SmallBubble(mContext,x,y,ang,width,height));
			}
		}
		public void makeBubbles(){
			Random rnd=new Random();
			if(mBall.size()>50) return;  
//			int x= -50;
			int x= rnd.nextInt(101)+50;
			int y= rnd.nextInt(201)+50;//50~250
			mBall.add(new Bubble(mContext,x,y,width,height));
		}

		
		public void run() {
			Canvas canvas=null;
			while(canRun){
				canvas=mHolder.lockCanvas();
				try{
					synchronized(mHolder){
//						moveBubbles();
						makeBubbles();
						moveCharacters();
						checkCollision();
						drawCharacters(canvas);
						
					}
					
				}finally{
					if(canvas!=null)
						mHolder.unlockCanvasAndPost(canvas);
				}//try
				synchronized (this) {
					if(isWait){
						try{
							wait();
						}catch(Exception e){}
					}
				}
			}//while
			
		}	//run

	}//������
	
	
	
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		bThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean done=true;
		while(done){
			try{
				bThread.join();
				done=false;
			}catch(Exception e){}
		}
	}

}
