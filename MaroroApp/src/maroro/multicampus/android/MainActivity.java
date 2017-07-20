package maroro.multicampus.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final CheckBox checkBox= (CheckBox)findViewById(R.id.Chk_1);
		//xml에 있는 체크박스 불러오기
		//xml에 있는 위젯을 불러오는 메서드
		//->findViewById();
		checkBox.setOnClickListener(
				new OnClickListener(){
			
			public void onClick(View v) {
				TextView tv=(TextView)findViewById(R.id.txt_title);
				if(checkBox.isChecked()){
					tv.setText("Checked");
				}
				else{
					tv.setText("unchecked");
				}
			}
			
		});
		RadioGroup rdGroup=(RadioGroup) findViewById(R.id.radioGroup1);
		rdGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			public void onCheckedChanged(RadioGroup group,int checkedId){
				TextView tv=(TextView)findViewById(R.id.txt_radio);
				RadioButton rd=(RadioButton)findViewById(checkedId);
				String str=rd.getText().toString();
				tv.setText(str);
				
			}
		});
		final ToggleButton tglBtn=(ToggleButton)findViewById(R.id.tgl_1);
		tglBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
			TextView tv=(TextView)findViewById(R.id.txt_tgl);
			if(tglBtn.isChecked()){
				tv.setText("on");}
			else{
				tv.setText("off");}
			}
		});

		Button exitBtn=(Button)findViewById(
				R.id.btn_exit);
		exitBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				finish();//시스템 종료
			}
		});

	}
}
