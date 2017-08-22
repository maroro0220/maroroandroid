package maroro.multicampus.android.maroroxmldata;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
TextView result; Button btn;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		result=(TextView)findViewById(R.id.result);
		btn=(Button)findViewById(R.id.button1);
			btn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					XmlPullParser parser= getResources().getXml(R.xml.sales);//XmlPullParser가 XML해석/getResource는 어플의 자원을 찾아주는 객체 /getXml은 Xml을 찾아주는
					StringBuilder sBuilder =new StringBuilder(); //String쓰면 속도가 드림 StringBuilder는 가변길이 문자열
					try{
						while(parser.next() !=XmlPullParser.END_DOCUMENT){ //.next는 한 이벤트<> 씩 읽어오는거 
							//XML의 끝이 아니면 반복처리
							String name=parser.getName();  //엘리먼트의 이름을 추출
							String position=null;
							String brand=null;
							if(name!=null && name.equals("car")){  //<이름 속성1="값" 속성2="값">  값은 String
								int size =parser.getAttributeCount();	//속성의 갯수를 추출
								for(int i=0; i<size;i++){
									String attr=parser.getAttributeName(i);//속성 이름 추출
									String attrValue=parser.getAttributeValue(i);//속성 값 추출
									if((attr!=null)&&(attr.equals("position"))){
										position = attrValue;
									}else if((attr!=null)&&(attr.equals("brand"))){
										brand=attrValue;
									}
								}//for
								if(position !=null && brand!=null){
									sBuilder.append(position+","+brand);
								}
							}//if
						}//while
						result.setText(sBuilder.toString());
	
					}//try
					catch(Exception e){
						
					}
				}
			});
	}
}
