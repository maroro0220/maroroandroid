package maroro.multicampus.android.maroroxmlanimatio;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends Activity {
	ImageView iv1,iv2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv1=(ImageView)findViewById(R.id.imageView1);
		iv1.setImageResource(R.drawable.photo);
		Animation an=AnimationUtils.loadAnimation(this,R.anim.spin);
		iv1.startAnimation(an);
		iv2=(ImageView)findViewById(R.id.imageView2);
		Animation ov = AnimationUtils.loadAnimation(
				this, R.anim.pulse);
			iv2.startAnimation(ov);
	}
}
