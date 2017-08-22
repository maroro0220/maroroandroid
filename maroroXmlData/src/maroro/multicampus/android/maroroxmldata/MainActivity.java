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
					XmlPullParser parser= getResources().getXml(R.xml.sales);//XmlPullParser�� XML�ؼ�/getResource�� ������ �ڿ��� ã���ִ� ��ü /getXml�� Xml�� ã���ִ�
					StringBuilder sBuilder =new StringBuilder(); //String���� �ӵ��� �帲 StringBuilder�� �������� ���ڿ�
					try{
						while(parser.next() !=XmlPullParser.END_DOCUMENT){ //.next�� �� �̺�Ʈ<> �� �о���°� 
							//XML�� ���� �ƴϸ� �ݺ�ó��
							String name=parser.getName();  //������Ʈ�� �̸��� ����
							String position=null;
							String brand=null;
							if(name!=null && name.equals("car")){  //<�̸� �Ӽ�1="��" �Ӽ�2="��">  ���� String
								int size =parser.getAttributeCount();	//�Ӽ��� ������ ����
								for(int i=0; i<size;i++){
									String attr=parser.getAttributeName(i);//�Ӽ� �̸� ����
									String attrValue=parser.getAttributeValue(i);//�Ӽ� �� ����
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
