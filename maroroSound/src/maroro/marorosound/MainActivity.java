package maroro.marorosound;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	Button btn1,btn2;
	MediaPlayer sound_back;
	@Override
	protected void onPause() {
		sound_back.stop();
		sound_back.release();
		super.onPause();
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		TextView tv=(TextView)findViewById(R.id.result);
		if(sound_back.isPlaying()){
			tv.setText("now playing");
		}
		else{
			tv.setText("pause music");
		}
		switch(v.getId()){
		case R.id.button1:
			if(sound_back.isPlaying()){
				sound_back.pause();
				tv.setText("pause music");
			}
			else{
				sound_back.start();
				tv.setText("playing");
			}
			break;
		case R.id.button2:
			finish();
		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn1=(Button)findViewById(R.id.button1);
		btn2=(Button)findViewById(R.id.button2);
		sound_back=MediaPlayer.create(this,R.raw.background);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		
	}
}
