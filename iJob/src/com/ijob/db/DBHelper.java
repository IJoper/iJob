
package com.ijob.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.bool;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHelper extends SQLiteOpenHelper {

	private static final int VERSION = 1; //定义数据库版本号
	private static final String DB_NAME = "MainListViewDB.db"; //定义数据库名字
	private static final String LISTVIEW_TABLE = "listview_db";
	private static final String COLLECTIION_TABLE = "collection_db";
	
	/**
	 * Title:        MyDataBase
	 * Description:    构造函数，传入当前的上下文环境，如mainActivity.this
	 */
	public DBHelper(Context context) {
		super(context,DB_NAME,null,VERSION); 
		//第一个是当前环境，第二个是数据库名字，第三个是一个游标通常为空，第四个是版本号。
	}

	/* （非 Javadoc）
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
//		创建招聘信息简介表
		db.execSQL("create table "+LISTVIEW_TABLE+" (" //表名
				+ "_id integer primary key autoincrement, "			//主键，简介信息的ID，类型为数字，唯一性
				+ "message_title varchar(100), "//
				+ "company varchar(50), "			//公司名称，字符类型
				+ "job_type varchar(50), "					//工作岗位，字符类型
				+ "location varchar(50), "					//工作城市，字符类型
				+ "payment varchar(50), "				//月薪，字符类型
				+ "release_time varchar(50), "			//发布时间，字符类型
				+ "deadline varchar(50))");			//截止时间，字符类型
		
		db.execSQL("create table "+COLLECTIION_TABLE+" ("//表名
				+ "_id integer primary key autoincrement, "			//主键，简介信息的ID，类型为数字，唯一性
				+ "message_title varchar(100), "//
				+ "company varchar(50), "			//公司名称，字符类型
				+ "job_type varchar(50), "					//工作岗位，字符类型
				+ "location varchar(50), "					//工作城市，字符类型
				+ "payment varchar(50), "				//月薪，字符类型
				+ "release_time varchar(50), "			//发布时间，字符类型
				+ "deadline varchar(50))");			//截止时间，字符类型
	}
	public int getMainListViewItemNum(){
		return 0;
	}
	public int getCollectionListViewItemNum(){
		int Itemcount = 0;
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(COLLECTIION_TABLE, null, null, null, null, null, null);
		Itemcount = cursor.getCount();
		cursor.close();
		db.close();
		return Itemcount;
	}
	public void addMainListViewByItem(Infor_item infor_item) {
		SQLiteDatabase db = getWritableDatabase(); //获取可写入的数据库对象。
		db.execSQL("insert into "+LISTVIEW_TABLE+" "
				+ "(_id,message_title,company,job_type,location,payment,release_time,deadline) "
				+ "values (?,?,?,?,?,?,?,?)",     //“添加”的数据库指令
				new Object[] {infor_item.getid(), infor_item.getmessage_title(),infor_item.getcompany(),
						infor_item.getjob_type(), infor_item.getlocation(),
						infor_item.getpayment(), infor_item.getrelease_time(),
						infor_item.getdeadline()});
		db.close();
	}
	
	public void addCollectionListViewByItem(Infor_item infor_item) {
		SQLiteDatabase db = getWritableDatabase(); //获取可写入的数据库对象。
		db.execSQL("insert into "+COLLECTIION_TABLE+" "
				+ "(_id,message_title,company,job_type,location,payment,release_time,deadline) "
				+ "values (?,?,?,?,?,?,?,?)",     //“添加”的数据库指令
				new Object[] {infor_item.getid(), infor_item.getmessage_title(),infor_item.getcompany(),
						infor_item.getjob_type(), infor_item.getlocation(),
						infor_item.getpayment(), infor_item.getrelease_time(),
						infor_item.getdeadline()});
		db.close();
	}
	
	/**
	 * 
	 * Title: delete
	 * Description: 根据输入的id集合删除记录，不考虑id的合法性。
	 * @param _id   
	 * void
	 */
	public void deleteMainListViewById(Integer id){
		SQLiteDatabase db = getWritableDatabase();
		String whereClause = "_id = ?";
		String[] whereArgs = { id.toString() };
		db.delete(LISTVIEW_TABLE, whereClause, whereArgs);
		db.close();
	}
	
	public void deleteCollectionListViewById(Integer id){
		SQLiteDatabase db = getWritableDatabase();
		String whereClause = "_id = ?";
		String[] whereArgs = { id.toString() };
		db.delete(COLLECTIION_TABLE, whereClause, whereArgs);
		db.close();
	}
	public void deleteMainListViewTable(){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from "+LISTVIEW_TABLE+" where_id > 0 ");
		db.close();
	}
	public void deleteCollectionListViewTable(){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from "+COLLECTIION_TABLE+" where_id > 0 ");
		db.close();
	}
	
	/**
	 * 
	 * Title: update
	 * Description: 根据传入信息的id，跟新数据库中对应的信息，不考虑id的合法性
	 * @param recuritment_inf_intro   
	 * void
	 */
	public void updateMainListViewByItem(Infor_item Infor_item) {
		SQLiteDatabase	db = getWritableDatabase(); //获取可写入的数据库对象。
		db.execSQL("update "+LISTVIEW_TABLE+" set " //“更新”的数据库语句
				+ "message_title = ?,company = ?,job_type = ?,location = ?,payment = ?,release_time = ?,deadline = ? where id = ? ",
				new Object[] {Infor_item.getcompany(),Infor_item.getmessage_title(),
						Infor_item.getjob_type(),Infor_item.getlocation(),
						Infor_item.getpayment(),Infor_item.getrelease_time(),
						Infor_item.getdeadline(), Infor_item.getid() 
							 } );
		db.close();
	}
	
	/**
	 * 
	 * Title: find
	 * Description: 根据传入的id进行查找，不考虑id的合法性
	 * @param _id
	 * @return   
	 * Infor_item
	 */
	public boolean findMainListViewItemById(int _id) {
		SQLiteDatabase db = getReadableDatabase(); //获取可写入的数据库对象。
		String selection = "_id = ?";
		String[] selectionArgs = { Integer.toString(_id) };
		Cursor cursor = db.query(LISTVIEW_TABLE, null, selection, selectionArgs, null, null, null);
//		Cursor cursor = db.rawQuery("select message_title,company,job_type,location,payment,release_time,deadline "
//				+ "from "+LISTVIEW_TABLE+" where _id = ?", new String[] {String.valueOf(_id)} );
		if (cursor.moveToNext()) {
			cursor.close();
			db.close();
			return true;
		}
		cursor.close();
		db.close();
		return false;
	}
	
	public boolean findCollectionListViewItemById(int _id) {
		SQLiteDatabase db = getReadableDatabase(); //获取可写入的数据库对象。
		String selection = "_id = ?";
		String[] selectionArgs = { Integer.toString(_id) };
		Cursor cursor = db.query(COLLECTIION_TABLE, null, selection, selectionArgs, null, null, null);
//		Cursor cursor = db.rawQuery("select message_title,company,job_type,location,payment,release_time,deadline "
//				+ "from "+COLLECTIION_TABLE+" where _id = ?", new String[] {String.valueOf(_id)} );
		if (cursor.moveToNext()) {
			cursor.close();
			db.close();
			return true;
		}
		cursor.close();
		db.close();
		return false;
	}
	
	//返回数据库中所有的项
	public List<Map<String, Object>> getMainListViewAllItem(){
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		SQLiteDatabase db = getReadableDatabase(); //获取可写入的数据库对象。
		
		Cursor cursor = db.query(LISTVIEW_TABLE, null, null, null, null, null, null);
		if (cursor.getCount() < 1) {
			cursor.close();
			db.close();
			return null;
		}
		if (cursor.moveToFirst()) {
			 do{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", ""+cursor.getInt(cursor.getColumnIndex("_id")));
				map.put("message_title", cursor.getString(cursor.getColumnIndex("message_title")));
				map.put("company", cursor.getString(cursor.getColumnIndex("company")));
				map.put("job_type", cursor.getString(cursor.getColumnIndex("job_type")));
				map.put("location", cursor.getString(cursor.getColumnIndex("location")));
				dataList.add(0,map);
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return dataList;
	}
	
	public List<Map<String, Object>> getCollectionListViewAllItem(){
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		SQLiteDatabase db = getReadableDatabase(); //获取可写入的数据库对象。
		
		Cursor cursor = db.query(COLLECTIION_TABLE, null, null, null, null, null, null);
		if (cursor.getCount() < 1) {
			cursor.close();
			db.close();
			return null;
		}
		if (cursor.moveToFirst()) {
			 do{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", ""+cursor.getInt(cursor.getColumnIndex("_id")));
				map.put("message_title", cursor.getString(cursor.getColumnIndex("message_title")));
				map.put("company", cursor.getString(cursor.getColumnIndex("company")));
				map.put("job_type", cursor.getString(cursor.getColumnIndex("job_type")));
				map.put("location", cursor.getString(cursor.getColumnIndex("location")));
				dataList.add(0,map);
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return dataList;
	}
	/* （非 Javadoc）
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {		
	}

}
