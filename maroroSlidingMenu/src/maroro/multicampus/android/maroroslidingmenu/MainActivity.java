package maroro.multicampus.android.maroroslidingmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements Animation.AnimationListener{
	public void onAnimationStart(Animation animation) {	}
	public void onAnimationEnd(Animation animation) {
		if(isPageOpen){
			layout.setVisibility(View.INVISIBLE);
			btn.setText("¿­¸²¹öÆ°");
			isPageOpen = false;
		}else{
			btn.setText("´ÝÈû¹öÆ°");
			isPageOpen = true;
		}
	}
	public void onAnimationRepeat(Animation animation) {}
	boolean isPageOpen = false;
	Animation toLeft, toRight;
	Button btn; LinearLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn=(Button)findViewById(R.id.button3);
		layout=(LinearLayout)findViewById(
				R.id.my_layout);
		btn.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(isPageOpen){
					layout.startAnimation(toRight);
				}else{
					layout.setVisibility(View.VISIBLE);
					layout.startAnimation(toLeft);
				}
			}
		});
		toLeft=AnimationUtils.loadAnimation(this, 
				R.anim.move_to_left);
		toRight=AnimationUtils.loadAnimation(this, 
				R.anim.move_to_right);
		toLeft.setAnimationListener(this);
		toRight.setAnimationListener(this);
	}
}
