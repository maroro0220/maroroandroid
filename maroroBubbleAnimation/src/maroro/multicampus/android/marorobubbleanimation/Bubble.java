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
//class WaterBall{//총알 클래스
//	public int x,y,rad;//좌표,반지름
//	public boolean dead = false;//총알 제거 유무
//	public Bitmap imgBall;//총알 이미지용
//	private int width,height;//View의 크기
//	private int speed;//이동 속도
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
//		//풍선이 벽에 부딫히거나 생명주기가 0이면 소멸
//	}
//}
//public class Bubble {
//	public int x,y,rad;//좌표,반지름
//	public Bitmap imgBall;
//	public boolean dead=false;
//	private int _rad;//원래 반지름
//	private int sx,sy;//이동 방향 및 속도
//	private int width,height;//View의 가로 세로
//	private Bitmap[] bubbles=new Bitmap[6];
//	private int imgNum=0;//이미지번호
//	private int loop=0;
//	private int counter=0;//벽과 충돌 횟수
//	public Bubble(Context context, int _x,int _y,int _width,int _height){
//		width=_width; height=_height;
//		x=_x; y=_y;
//		imgBall=BitmapFactory.decodeResource(context.getResources(),R.drawable.bubble_1);
//		Random rnd=new Random();
//		_rad=rnd.nextInt(11)+20;//20~10까지 반지름
//		rad=_rad;
//		for(int i=0;i<=3;i++){ //반지름이 2간격으로 커지는 공6개 만듬
//			bubbles[i]=Bitmap.createScaledBitmap(imgBall, _rad*2+i*2,_rad*2+i*2,false);
//		}//for
//		bubbles[4]=bubbles[2]; bubbles[5]=bubbles[1];
//		imgBall=bubbles[0]; //가장작은 공이미지
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
////		if(counter>=3)dead =true;// 3회 충돌하면 풍선 없어짐
//	}
//}
