package maroro.multicampus.android.marorobubblegame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case 1:  //exit
			BubbleView.stopGame();
			finish();
			break;
		case 2:  //pause
			BubbleView.pauseGame();
			break;
		case 3:  //on going
			BubbleView.resumeGame();
			break;
		case 4:  //reset
			BubbleView.restartGame();
			break;
			
		}
		return super.onOptionsItemSelected(item);
	}



	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		BubbleView.stopGame();
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		BubbleView.pauseGame();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		BubbleView.resumeGame();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,1,1,"exit");//그룹번호,식별자,순서,제목
		menu.add(0,2,2,"pause");
		menu.add(0,3,3,"on going");
		menu.add(0,4,4,"reset");
		return super.onCreateOptionsMenu(menu);
	}
	maroroBubbleView BubbleView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BubbleView=new maroroBubbleView(this);
		setContentView(BubbleView);
		
	}
}
