//package maroro.multicampus.android.marorobubbleanimation;
//
//import java.util.Random;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.graphics.Paint;
//class Score{
//	public int x,y;
//	public Paint paint;
//	private int loop=0;
//	private int color=Color.WHITE;
//	public Score(int _x,int _y){
//		x=_x;y=_y; loop=0; paint=new Paint();
//		paint.setColor(color);
//		paint.setTextSize(50);
//		paint.setAntiAlias(true);
//		move();
//	}
//	public boolean move(){
//		y=y-4;
//		if(y< -20||loop>100) return false;
//		loop++;
//		if(loop%4==0)
//		{
//			color=(Color.WHITE+Color.YELLOW)-color;
//			paint.setColor(color);
//		}
//		return true;
//	}
//}
//class WaterBall{//�Ѿ� Ŭ����
//	public int x,y,rad;//��ǥ,������
//	public boolean dead = false;//�Ѿ� ���� ����
//	public Bitmap imgBall;//�Ѿ� �̹�����
//	private int width,height;//View�� ũ��
//	private int speed;//�̵� �ӵ�
//	public WaterBall(Context context,int _x,
//			int _y,int _width,int _height){
//		x=_x; y=_y; width=_width; height=_height;
//		imgBall=BitmapFactory.decodeResource(
//		context.getResources(),R.drawable.w0);
//		rad=imgBall.getWidth()/2;
//		speed=8;
//		moveBall();
//	}
//	public void moveBall(){
//		y = y - speed;
//		if(y<0) dead = true;
//	}
//}
//class SmallBubble{
//	public int x,y,rad;
//	public Bitmap imgBall;
//	public boolean dead=false;
//	private int width,height;
//	private int cx,cy;
//	private int cr;
//	private double r;
//	private int speed;
//	private int num;
//	private int life;
//	public SmallBubble(Context context, int _x,int _y,int ang,int _width,int _height){
//		cx=_x;cy=_y;width=_width;height=_height;
//		r=ang*Math.PI/180;
//		Random rnd = new Random();
//		speed=rnd.nextInt(3)+2;//2~6
//		rad=rnd.nextInt(10)+5;//5~14
//		num=rnd.nextInt(6);//0~5
//		life=rnd.nextInt(31)+20;
//		imgBall=BitmapFactory.decodeResource(context.getResources(),R.drawable.b0+num);
//		imgBall=Bitmap.createScaledBitmap(imgBall,rad*2, rad*2, false);
//		cr=10;
//		moveBall();
//	}
//	public void moveBall(){
//		life--;
//		cr=cr+speed;
//		x=(int)(cx+Math.cos(r)*cr);
//		y=(int)(cy+Math.cos(r)*cr);
//		if(x<-rad||x>width+rad||y<-rad||y>height+rad||life<=0)
//			dead=true;
//		//ǳ���� ���� �΋H���ų� �����ֱⰡ 0�̸� �Ҹ�
//	}
//}
//public class Bubble {
//	public int x,y,rad;//��ǥ,������
//	public Bitmap imgBall;
//	public boolean dead=false;
//	private int _rad;//���� ������
//	private int sx,sy;//�̵� ���� �� �ӵ�
//	private int width,height;//View�� ���� ����
//	private Bitmap[] bubbles=new Bitmap[6];
//	private int imgNum=0;//�̹�����ȣ
//	private int loop=0;
//	private int counter=0;//���� �浹 Ƚ��
//	public Bubble(Context context, int _x,int _y,int _width,int _height){
//		width=_width; height=_height;
//		x=_x; y=_y;
//		imgBall=BitmapFactory.decodeResource(context.getResources(),R.drawable.bubble_1);
//		Random rnd=new Random();
//		_rad=rnd.nextInt(11)+20;//20~10���� ������
//		rad=_rad;
//		for(int i=0;i<=3;i++){ //�������� 2�������� Ŀ���� ��6�� ����
//			bubbles[i]=Bitmap.createScaledBitmap(imgBall, _rad*2+i*2,_rad*2+i*2,false);
//		}//for
//		bubbles[4]=bubbles[2]; bubbles[5]=bubbles[1];
//		imgBall=bubbles[0]; //�������� ���̹���
//		sx=rnd.nextInt(2);
//		if(sx==0) sx=-1;
//		else sx=1;
//		sy=rnd.nextInt(2);
//		if(sy==0)sy=-2;
//		else sy=2;
//		moveBubble();
//	}
//	public void moveBubble(){
//		loop++;
//		if(loop%3==0){
//			imgNum++;
//			if(imgNum>5)imgNum=0;
//			imgBall=bubbles[imgNum];
//			int k=imgNum;
//			if(k>3)imgNum=6-imgNum;
//			rad=_rad+k*2;
//		}
//		x=x+sx; y=y+sy;
//		if(x>=width-rad){
//			sx=-sx;x=x+sx;
////			counter++;
//		}
//		if(y<=rad||y>=height-rad){
//			sy=-sy;y=y+sy;
////			counter++;
//		}
////		if(counter>=3)dead =true;// 3ȸ �浹�ϸ� ǳ�� ������
//	}
//}
