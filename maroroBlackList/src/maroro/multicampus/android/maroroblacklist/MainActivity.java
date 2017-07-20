package maroro.multicampus.android.maroroblacklist;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity 
implements OnClickListener{
@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		LinearLayout ll=(LinearLayout)v;
		TextView num=(TextView)ll.findViewById(R.id._id);
		TextView name=(TextView)ll.findViewById(R.id.txt_name);
		TextView phone=(TextView)ll.findViewById(R.id.txt_phone);
		TextView addr=(TextView)ll.findViewById(R.id.txt_addr);
		txt_number.setText(num.getText());
		edt_name.setText(name.getText());
		edt_phone.setText(phone.getText());
		edt_addr.setText(addr.getText());
		
}
Button insert,delete,update,select,clear,exit;
EditText edt_name,edt_phone,edt_addr;
SQLiteDatabase myDB;
TextView txt_number;
static final String DB_NAME="myFriends.db";
static final String TBL_NAME="myFriends";
static final String CREATE_TABLE=
"create table "+TBL_NAME+" (_id integer "+
"primary key autoincrement, name text not null,"+
"phone text not null, address text);";
static final String INSERT_TABLE=
"insert into "+TBL_NAME+" (name,phone,address) "+
"values ";
static final String SELECT_TABLE=
"select * from "+TBL_NAME+" where 1=1 ";
static final String DELETE_TABLE=
"delete from "+TBL_NAME+" where 1=1 ";
static final String UPDATE_TABLE="update "+TBL_NAME;
public boolean updateTable(){
	String sql=UPDATE_TABLE;
	String name=edt_name.getText().toString();
	String phone=edt_phone.getText().toString();
	String addr=edt_addr.getText().toString();
	sql=sql+" set name='"+name+"' and ";
	sql=sql+" phone='"+phone+"' and ";
	sql=sql+" address='"+addr+"' ";
	sql=sql+" where 1=1 ";
	sql=sql+" and _id="+txt_number.getText()+";";
	boolean flag=false;
	try{
		myDB.execSQL(sql);
		flag=true;
	}catch(Exception e){}
	return flag;
}


void createTable(){//테이블 생성
	try{
		myDB.execSQL(CREATE_TABLE);
		Toast.makeText(this,"Table OK", 
			Toast.LENGTH_SHORT).show();
	}catch(Exception e){
	}
}
boolean openDatabase(){//DB 생성 및 open
	myDB=openOrCreateDatabase(DB_NAME,
		Context.MODE_PRIVATE, null);
	boolean flag = false;
	if(myDB != null){
		Toast.makeText(this,"DB OK",
			Toast.LENGTH_SHORT).show();
		flag = true;
	}else {
		Toast.makeText(this,"DB 실패",
			Toast.LENGTH_SHORT).show();
	}
	return flag;
}

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	txt_number=(TextView)findViewById(R.id.txt_num);
	insert=(Button)findViewById(R.id.btn_insert);
	delete=(Button)findViewById(R.id.btn_delete);
	update=(Button)findViewById(R.id.btn_update);
	select=(Button)findViewById(R.id.btn_select);
	clear=(Button)findViewById(R.id.btn_clear);
	exit=(Button)findViewById(R.id.btn_exit);
	edt_name=(EditText)findViewById(R.id.edit_name);
	edt_phone=(EditText)findViewById(R.id.edit_phone);
	edt_addr=(EditText)findViewById(R.id.edit_addr);
	insert.setOnClickListener(this);
	delete.setOnClickListener(this);
	update.setOnClickListener(this);
	select.setOnClickListener(this);
	clear.setOnClickListener(this);
	openDatabase();//DB 생성 및 open
	createTable();//Table 생성
}public boolean deleteTable(){
	String sql=DELETE_TABLE;
	String name=edt_name.getText().toString();
	if(!name.equals("")){
		sql=sql+" and name='"+name+"'";
	}
	String phone=edt_phone.getText().toString();
	if(!phone.equals("")){
		sql=sql+" and phone='"+phone+"'";
	}
	String addr=edt_addr.getText().toString();
	if(!addr.equals("")){
		sql=sql+" and address='"+addr+"'";
	}
	sql=sql+";";
	boolean flag =false;
	try{
		myDB.execSQL(sql);
		flag=true;//삭제 실행
	}catch(Exception e){
		
	}
	return flag;
	
}
public void selectTable(){
	String sql=SELECT_TABLE;
	String name=edt_name.getText().toString();
	if(!name.equals("")){
		sql=sql+" and name='"+name+"'";
	}
	String phone=edt_phone.getText().toString();
	if(!phone.equals("")){
		sql=sql+" and phone='"+phone+"'";
	}
	String addr=edt_addr.getText().toString();
	if(!addr.equals("")){
		sql=sql+" and address='"+addr+"'";
	}
	sql=sql+";";
	Cursor c = myDB.rawQuery(sql,null);
	startManagingCursor(c);
	String[] from=new String[]{
			"_id","name","phone","address"
	};
	int[] to =new int[]{
			R.id._id,R.id.txt_name,R.id.txt_phone,R.id.txt_addr//위젯의 이름
	};
	ListAdapter adapter =new SimpleCursorAdapter(this, R.layout.maroroblack_row,c,from,to);
	setListAdapter(adapter);
	
}
public void selectAll(){
	//삽입,삭제,변경 후 호출되는 조회(무조건 조회)
		String sql=SELECT_TABLE;
		sql=sql+";";
		Cursor c = myDB.rawQuery(sql, null);
		c.moveToFirst();
		startManagingCursor(c);
		String[] from = new String[]{
			"_id","name","phone","address"
		};
		int[] to = new int[]{
			R.id._id,R.id.txt_name,R.id.txt_phone,
			R.id.txt_addr
		};
		ListAdapter adapter=new SimpleCursorAdapter(
			this,R.layout.maroroblack_row,c,from,to);
		setListAdapter(adapter);
	}
public void clear(){
	edt_name.setText("");
	edt_phone.setText("");
	edt_addr.setText("");
}
public boolean insertTable(){
	String sql=INSERT_TABLE; boolean flag=false;
	String name=edt_name.getText().toString();
	String phone=edt_phone.getText().toString();
	String addr=edt_addr.getText().toString();
	sql=sql+"('"+name+"','"+phone+"','"+
			addr+"');";
	try{
		myDB.execSQL(sql);
		flag=true;
	}catch(Exception e){
		Toast.makeText(MainActivity.this, 
		e.getMessage(),Toast.LENGTH_SHORT).show();
	}
	return flag;
}
@Override
public void onClick(View v) {
	int id = v.getId();
	if(id == R.id.btn_insert){//삽입버튼
		AlertDialog.Builder builder = 
			new AlertDialog.Builder(this);
		builder.setMessage("정말로 삽입하시겠습니까?")
		.setCancelable(false).setPositiveButton(
		"아니오",null).setNegativeButton("예",
		new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				boolean flag = insertTable();
				selectAll();
				if(flag==true){
					Toast.makeText(MainActivity.this, 
				"삽입되었습니다.",Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(MainActivity.this,
				"삽입에 실패했습니다.",Toast.LENGTH_SHORT).show();
				}
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}else if(id==R.id.btn_delete){
		boolean result=deleteTable();
		selectAll();
		if(result){
		Toast.makeText(this,"삭제 되었습니다",Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(this,"삭제 되었습니다",Toast.LENGTH_SHORT).show();
		}
	}else if(id==R.id.btn_update){
		boolean result = updateTable();
		selectAll();
		if(result){
			Toast.makeText(this,"수정 되었습니다",Toast.LENGTH_SHORT).show();
			}
			else{
				Toast.makeText(this,"수정 되었습니다",Toast.LENGTH_SHORT).show();
			}
		
	}else if(id==R.id.btn_select){
		//검색 메서드 호출
		selectTable();
	}else if(id==R.id.btn_clear){
		clear();
	}else if(id==R.id.btn_exit){
		
	}
}
}


