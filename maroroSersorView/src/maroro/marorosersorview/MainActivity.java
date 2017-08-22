package maroro.marorosersorview;

import android.app.Activity;
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
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	class maroroSersorView extends View implements SensorEventListener {
		int m_x = 0;
		int m_y = 0;
		String m_str = "";
		SensorManager sensorManager;

		public maroroSersorView(Context context) {
			super(context);
			sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
			sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
					SensorManager.SENSOR_DELAY_GAME);
			// SENSOR_DELAY_GAME:������� ������ ���� �ӵ�
			// SENSOR_DELAY_NORMAL:�⺻���� ������Ʈ �ӵ�
			// SENSOR_DELAY_UI:ȭ��UIó���� ������ ���� �ӵ� ���� ����
			// SENSOR_DELAY_FASTEST: ���� ���� ������Ʈ �ӵ�
		}// ������

		@Override
		protected void onDraw(Canvas canvas) {
			Paint paint = new Paint();
			paint.setTextSize(50);
			paint.setColor(Color.BLACK);
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

			canvas.drawBitmap(bitmap, m_x, m_y, null);
			canvas.drawText(m_str, 0, 80, paint);
		}

		@Override
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
					m_x=m_x-(int)roll; //-90~90
					m_y=m_y-(int)pitch;//-180~180
					if(m_x<=0) m_x=0;
					if(m_y<=0) m_y=0;
					if(m_x>=(getWidth()-50))
						m_x=getWidth()-50;
					if(m_y>=(getHeight()-50))
						m_y=getHeight()-50;
					
					break;
					
					
				}
			}invalidate();
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new maroroSersorView(this));
	}
}
