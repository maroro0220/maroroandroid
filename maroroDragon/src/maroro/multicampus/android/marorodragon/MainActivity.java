package maroro.multicampus.android.marorodragon;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

class DragonView extends SurfaceView implements Callback {
	DragonThread dThread;
	SurfaceHolder mHolder;

	class DragonThread extends Thread {
		SurfaceHolder mHolder;
		int width, height;
		int x, y, dw, dh;
		int sx, sy;
		int num;
		int x1, y1;
		Bitmap imgBack;// 배경이미지
		Bitmap[] dragons = new Bitmap[2];

		DragonThread(SurfaceHolder holder, Context ctx) {
			mHolder = holder;
			Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			width = display.getWidth();
			height = display.getHeight() - 50;
			imgBack = BitmapFactory.decodeResource(getResources(), R.drawable.back_1);
			imgBack = Bitmap.createScaledBitmap(imgBack, width, height, false);
			dragons[0] = BitmapFactory.decodeResource(getResources(), R.drawable.dragon);
			dw = dragons[0].getWidth() / 2;
			dh = dragons[0].getHeight() / 2;
			Matrix matrix = new Matrix();// 이미지 뒤집기 위해 매트릭스 사
			matrix.postScale(-1, 1);// 수평으로 뒤집기
			dragons[1] = Bitmap.createBitmap(dragons[0], 0, 0, dw * 2, dh * 2, matrix, false);
			num = 0;
			sx = sy = 3;
			x = y = 100;
		}

		void moveDragon() {
			x = x + sx;
			y = y + sy;
			if (x <= dw) {
				x = dw;
				sx = -sx;
				num = 1 - num;// dragons[0] dragons[1]
			}
			if (x >= width - dw) {
				x = width - dw;
				sx = -sx;
				num = 1 - num;
			}
			if (y <= dh) {
				y = dh;
				sy = -sy;
			}
			if (y >= height - dh) {
				y = height - dh;
				sy = -sy;
			}

		}

		@Override
		public void run() {
			Canvas canvas = null;
			while (true) {
				canvas = mHolder.lockCanvas();
				try {
					synchronized (mHolder) {
						moveDragon();
						canvas.drawBitmap(imgBack, 0, 0, null);
						canvas.drawBitmap(dragons[num], x - dw, y - dh, null);
					}
				} finally {
					if (canvas != null) {
						mHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}

	}

	public DragonView(Context context) {
		super(context);
		mHolder = getHolder();
		mHolder.addCallback(this);
		dThread = new DragonThread(mHolder, context);
		dThread.setDaemon(true);
		setFocusable(true);
		// TODO Auto-generated constructor stub
	}

	@Override // surfaceview가 생성될 떄 자동 호출
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		dThread.start();
	}

	@Override // surfaceview가 변경 ㄷ 자동으로 변경
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override // surfaceView가 제거될 때 자동 호출
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// surfaceview가 제거됨에도 불구하고 스레드가 동작하면 안되므로
		// 스레드가 종료될 때까지 기다렸다가 제거한다.

		boolean done = true;
		while (done) {
			try {
				dThread.join();// 스레드 종료
				done = false;
			} catch (Exception e) {
			}
		}
	}

}

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new DragonView(this));
	}
}
