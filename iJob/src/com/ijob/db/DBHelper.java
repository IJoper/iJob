package com.ijob.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ijob.R;

import android.R.bool;
import android.R.string;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private static final int VERSION = 1; // 定义数据库版本号
	private static final String DB_NAME = "MainListViewDB.db"; // 定义数据库名字
	private static final String LISTVIEW_TABLE = "listview_db";
	private static final String COLLECTIION_TABLE = "collection_db";
	private static final String CITY_TABLE = "focuscity_db";
	private static final String JOB_TABLE = "focusjob_db";
	private static final int JobTypeCount = 13;

	/**
	 * Title: MyDataBase Description: 构造函数，传入当前的上下文环境，如mainActivity.this
	 */
	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		// 第一个是当前环境，第二个是数据库名字，第三个是一个游标通常为空，第四个是版本号。
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建招聘信息简介表
		db.execSQL("create table " + LISTVIEW_TABLE + " (" // 表名
				+ "_id integer primary key autoincrement, " // 主键，简介信息的ID，类型为数字，唯一性
				+ "message_title varchar(100), "//
				+ "company varchar(50), " // 公司名称，字符类型
				+ "job_type varchar(50), " // 工作岗位，字符类型
				+ "location varchar(50), " // 工作城市，字符类型
				+ "payment varchar(50), " // 月薪，字符类型
				+ "release_time varchar(50), " // 发布时间，字符类型
				+ "deadline varchar(50))"); // 截止时间，字符类型

		db.execSQL("create table " + COLLECTIION_TABLE + " ("// 表名
				+ "_id integer primary key autoincrement, " // 主键，简介信息的ID，类型为数字，唯一性
				+ "message_title varchar(100), "//
				+ "company varchar(50), " // 公司名称，字符类型
				+ "job_type varchar(50), " // 工作岗位，字符类型
				+ "location varchar(50), " // 工作城市，字符类型
				+ "payment varchar(50), " // 月薪，字符类型
				+ "release_time varchar(50), " // 发布时间，字符类型
				+ "deadline varchar(50))"); // 截止时间，字符类型

		db.execSQL("create table " + CITY_TABLE + " ("// 表名
				+ "_id integer primary key autoincrement, " // 主键，简介信息的ID，类型为数字，唯一性
				+ "city_choose integer, " + "city_name varchar(100))");

		db.execSQL("create table " + JOB_TABLE + " ("// 表名
				+ "_id integer primary key autoincrement, " // 主键，简介信息的ID，类型为数字，唯一性
				+ "job_choose integer, " + "job_name varchar(100))");
	}

	public int getMainListViewItemNum() {
		return 0;
	}

	public int getCollectionListViewItemNum() {
		int Itemcount = 0;
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(COLLECTIION_TABLE, null, null, null, null,
				null, null);
		Itemcount = cursor.getCount();
		cursor.close();
		db.close();
		return Itemcount;
	}

	public int getCityNum() {
		int Itemcount = 0;
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db
				.query(CITY_TABLE, null, null, null, null, null, null);
		Itemcount = cursor.getCount();
		cursor.close();
		db.close();
		return Itemcount;
	}

	public int getJobNum() {
		int Itemcount = 0;
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(JOB_TABLE, null, null, null, null, null, null);
		Itemcount = cursor.getCount();
		cursor.close();
		db.close();
		return Itemcount;
	}

	public void initCityTable(int cityID[], String cityName[]) {
		for (int i = 0; i < cityID.length; i++) {
			City_Item city_Item = new City_Item(cityID[i], 0, cityName[i]);
			this.addCityByItem(city_Item);
		}
	}

	public void initJobTable(int jobTypeID[][], String jobTypeName[][]) {
		for (int i = 0; i < JobTypeCount; i++) {
			for (int j = 0; j < jobTypeID[i].length; j++) {
				Job_Item job_Item = new Job_Item(jobTypeID[i][j], 0,
						jobTypeName[i][j]);
				this.addJobByItem(job_Item);
			}
		}
	}

	public void addCityByItem(City_Item city_Item) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("insert into " + CITY_TABLE + " "
				+ "(_id,city_choose,city_name) " + "values (?,?,?)", // “添加”的数据库指令
				new Object[] { city_Item.getCityId(),
						city_Item.getCityChoose(), city_Item.getCityName() });
		db.close();
	}

	public void addJobByItem(Job_Item job_Item) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("insert into " + JOB_TABLE + " "
				+ "(_id,job_choose,job_name) " + "values (?,?,?)", // “添加”的数据库指令
				new Object[] { job_Item.getJobId(), job_Item.getJobChoose(),
						job_Item.getJobName() });
		db.close();
	}

	public void addMainListViewByItem(Infor_item infor_item) {
		SQLiteDatabase db = getWritableDatabase(); // 获取可写入的数据库对象。
		db.execSQL(
				"insert into "
						+ LISTVIEW_TABLE
						+ "(_id,message_title,company,job_type,location,payment,release_time,deadline) "
						+ "values (?,?,?,?,?,?,?,?)", // “添加”的数据库指令
				new Object[] { infor_item.getid(),
						infor_item.getmessage_title(), infor_item.getcompany(),
						infor_item.getjob_type(), infor_item.getlocation(),
						infor_item.getpayment(), infor_item.getrelease_time(),
						infor_item.getdeadline() });
		db.close();
	}

	public void addCollectionListViewByItem(Infor_item infor_item) {
		SQLiteDatabase db = getWritableDatabase(); // 获取可写入的数据库对象。
		db.execSQL(
				"insert into "
						+ COLLECTIION_TABLE
						+ "(_id,message_title,company,job_type,location,payment,release_time,deadline) "
						+ "values (?,?,?,?,?,?,?,?)", // “添加”的数据库指令
				new Object[] { infor_item.getid(),
						infor_item.getmessage_title(), infor_item.getcompany(),
						infor_item.getjob_type(), infor_item.getlocation(),
						infor_item.getpayment(), infor_item.getrelease_time(),
						infor_item.getdeadline() });
		db.close();
	}

	/**
	 * 
	 * Title: delete Description: 根据输入的id集合删除记录，不考虑id的合法性。
	 * 
	 * @param _id
	 *            void
	 */
	public void deleteMainListViewById(Integer id) {
		SQLiteDatabase db = getWritableDatabase();
		String whereClause = "_id = ?";
		String[] whereArgs = { id.toString() };
		db.delete(LISTVIEW_TABLE, whereClause, whereArgs);
		db.close();
	}

	public void deleteCollectionListViewById(Integer id) {
		SQLiteDatabase db = getWritableDatabase();
		String whereClause = "_id = ?";
		String[] whereArgs = { id.toString() };
		db.delete(COLLECTIION_TABLE, whereClause, whereArgs);
		db.close();
	}

	public void deleteMainListViewTable() {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from " + LISTVIEW_TABLE + " where _id > 0 ");
		db.close();
	}

	public void deleteCollectionListViewTable() {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from " + COLLECTIION_TABLE + " where _id > 0 ");
		db.close();
	}

	public void deleteCityTable() {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from " + CITY_TABLE + " where _id > 0 ");
		db.close();
	}

	public void deleteJobTable() {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from " + JOB_TABLE + " where _id > 0 ");
		db.close();
	}

	/**
	 * 
	 * Title: update Description: 根据传入信息的id，跟新数据库中对应的信息，不考虑id的合法性
	 * 
	 * @param recuritment_inf_intro
	 *            void
	 */
	public void updateMainListViewByItem(Infor_item Infor_item) {
		SQLiteDatabase db = getWritableDatabase(); // 获取可写入的数据库对象。
		db.execSQL(
				"update " + LISTVIEW_TABLE
						+ " set " // “更新”的数据库语句
						+ "message_title = ?,company = ?,job_type = ?,location = ?,payment = ?,release_time = ?,deadline = ? where id = ? ",
				new Object[] { Infor_item.getcompany(),
						Infor_item.getmessage_title(),
						Infor_item.getjob_type(), Infor_item.getlocation(),
						Infor_item.getpayment(), Infor_item.getrelease_time(),
						Infor_item.getdeadline(), Infor_item.getid() });
		db.close();
	}

	// 根据保存的信息更新
	// public void updateCityTableByList(List <City_Item> CityList){
	// this.deleteCityTable();
	// for (int i = 0; i < CityList.size(); i++) {
	// this.addCityByItem(CityList.get(i));
	// }
	// }
	// 根据保存的信息更新
	// public void updateJobTableByList(Map<String, List<Job_Item>> map){
	// this.deleteJobTable();
	// for (int i = 11; i < 24; i++) {
	// for (int j = 0; j < map.get(""+i).size(); j++) {
	// this.addJobByItem(map.get(""+i).get(j));
	// }
	// }
	// }

	/**
	 * 
	 * Title: find Description: 根据传入的id进行查找，不考虑id的合法性
	 * 
	 * @param _id
	 * @return Infor_item
	 */
	public boolean findMainListViewItemById(int _id) {
		SQLiteDatabase db = getReadableDatabase(); // 获取可写入的数据库对象。
		String selection = "_id = ?";
		String[] selectionArgs = { Integer.toString(_id) };
		Cursor cursor = db.query(LISTVIEW_TABLE, null, selection,
				selectionArgs, null, null, null);
		// Cursor cursor =
		// db.rawQuery("select message_title,company,job_type,location,payment,release_time,deadline "
		// + "from "+LISTVIEW_TABLE+" where _id = ?", new String[]
		// {String.valueOf(_id)} );
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
		SQLiteDatabase db = getReadableDatabase(); // 获取可写入的数据库对象。
		String selection = "_id = ?";
		String[] selectionArgs = { Integer.toString(_id) };
		Cursor cursor = db.query(COLLECTIION_TABLE, null, selection,
				selectionArgs, null, null, null);
		// Cursor cursor =
		// db.rawQuery("select message_title,company,job_type,location,payment,release_time,deadline "
		// + "from "+COLLECTIION_TABLE+" where _id = ?", new String[]
		// {String.valueOf(_id)} );
		if (cursor.moveToNext()) {
			cursor.close();
			db.close();
			return true;
		}
		cursor.close();
		db.close();
		return false;
	}

	// 根据唯一职业ID获取职位信息
	public Job_Item findJobItemById(int _id) {
		SQLiteDatabase db = getReadableDatabase();
		// Log.i("findJobItemById id = ", ""+_id);
		String selection = "_id = ?";
		String[] selectionArgs = { Integer.toString(_id) };
		Cursor cursor = db.query(JOB_TABLE, null, selection, selectionArgs,
				null, null, null);
		if (cursor.moveToNext()) {
			Job_Item job_Item = new Job_Item(cursor.getInt(cursor
					.getColumnIndex("_id")), cursor.getInt(cursor
					.getColumnIndex("job_choose")), cursor.getString(cursor
					.getColumnIndex("job_name")));
			cursor.close();
			db.close();
			return job_Item;
		}
		cursor.close();
		db.close();
		return null;
	}

	// 获取地区信息
	public List<City_Item> getCityList() {
		List<City_Item> CityList = new ArrayList<City_Item>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db
				.query(CITY_TABLE, null, null, null, null, null, null);
		if (cursor.getCount() < 1) {
			cursor.close();
			db.close();
			return null;
		}
		if (cursor.moveToFirst()) {
			do {
				CityList.add(new City_Item(cursor.getInt(cursor
						.getColumnIndex("_id")), cursor.getInt(cursor
						.getColumnIndex("city_choose")), cursor
						.getString(cursor.getColumnIndex("city_name"))));
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return CityList;
	}

	// 根据一组职业的ID获取工作职位信息
	public List<Job_Item> getJobListByIdArray(int jobTypeID[]) {
		List<Job_Item> JobList = new ArrayList<Job_Item>();
		if (this.getJobNum() < 1) {
			return null;
		}
		// Log.i("jobTypeID length = ", ""+jobTypeID.length);
		for (int i = 0; i < jobTypeID.length; i++) {
			JobList.add(findJobItemById(jobTypeID[i]));
		}
		return JobList;
	}

	// 返回首页中所有的项
	public List<Map<String, Object>> getMainListViewAllItem() {
		SQLiteDatabase db = getWritableDatabase(); // 获取可写入的数据库对象。
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Cursor cursor = db.query(LISTVIEW_TABLE, null, null, null, null, null,
				null);
		if (cursor.getCount() < 1) {
			cursor.close();
			db.close();
			return null;
		}
		if (cursor.moveToFirst()) {
			do {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", "" + cursor.getInt(cursor.getColumnIndex("_id")));
				map.put("message_title", cursor.getString(cursor
						.getColumnIndex("message_title")));
				map.put("company",
						cursor.getString(cursor.getColumnIndex("company")));
				map.put("job_type",
						cursor.getString(cursor.getColumnIndex("job_type")));
				map.put("location",
						cursor.getString(cursor.getColumnIndex("location")));
				dataList.add(0, map);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return dataList;
	}

	// 获取所有的收藏项
	public List<Map<String, Object>> getCollectionListViewAllItem() {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		SQLiteDatabase db = getReadableDatabase(); // 获取可写入的数据库对象。

		Cursor cursor = db.query(COLLECTIION_TABLE, null, null, null, null,
				null, null);
		if (cursor.getCount() < 1) {
			cursor.close();
			db.close();
			return null;
		}
		if (cursor.moveToFirst()) {
			do {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", "" + cursor.getInt(cursor.getColumnIndex("_id")));
				map.put("message_title", cursor.getString(cursor
						.getColumnIndex("message_title")));
				map.put("company",
						cursor.getString(cursor.getColumnIndex("company")));
				map.put("job_type",
						cursor.getString(cursor.getColumnIndex("job_type")));
				map.put("location",
						cursor.getString(cursor.getColumnIndex("location")));
				dataList.add(0, map);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return dataList;
	}

	// 获取设置关注的地区
	public List<City_Item> getFocusCityList() {
		List<City_Item> focusCityList = new ArrayList<City_Item>();
		SQLiteDatabase db = getReadableDatabase(); // 获取可写入的数据库对象。

		Cursor cursor = db
				.query(CITY_TABLE, null, null, null, null, null, null);
		if (cursor.getCount() < 1) {
			cursor.close();
			db.close();
			return null;
		}
		if (cursor.moveToFirst()) {
			do {
				if (cursor.getInt(cursor.getColumnIndex("city_choose")) == 1) {
					focusCityList.add(new City_Item(cursor.getInt(cursor
							.getColumnIndex("_id")), cursor.getInt(cursor
							.getColumnIndex("city_choose")), cursor
							.getString(cursor.getColumnIndex("city_name"))));
				}
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return focusCityList;
	}

	// 获取设置关注的工作职位
	public List<Job_Item> getFocusJobList() {
		List<Job_Item> focusJobList = new ArrayList<Job_Item>();
		SQLiteDatabase db = getReadableDatabase(); // 获取可写入的数据库对象。

		Cursor cursor = db.query(JOB_TABLE, null, null, null, null, null, null);
		if (cursor.getCount() < 1) {
			cursor.close();
			db.close();
			return null;
		}
		if (cursor.moveToFirst()) {
			do {
				if (cursor.getInt(cursor.getColumnIndex("job_choose")) == 1) {
					focusJobList.add(new Job_Item(cursor.getInt(cursor
							.getColumnIndex("_id")), cursor.getInt(cursor
							.getColumnIndex("job_choose")), cursor
							.getString(cursor.getColumnIndex("job_name"))));
				}
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return focusJobList;
	}

	public int getJobIdByName(String name) {
		SQLiteDatabase db = getReadableDatabase();
		int jobid = -1;
		String selection = "job_name = ?";
		String[] selectionArgs = { name };
		Cursor cursor = db.query(JOB_TABLE, null, selection, selectionArgs,
				null, null, null);
		if (cursor.moveToNext()) {
			jobid = cursor.getInt(cursor.getColumnIndex("_id"));
			cursor.close();
			db.close();
			return jobid;
		}
		cursor.close();
		db.close();
		return jobid;
	}

	public int getCityIdByName(String name) {
		SQLiteDatabase db = getReadableDatabase();
		int cityid = -1;
		String selection = "city_name = ?";
		String[] selectionArgs = { name };
		Cursor cursor = db.query(CITY_TABLE, null, selection, selectionArgs,
				null, null, null);
		if (cursor.moveToNext()) {
			cityid = cursor.getInt(cursor.getColumnIndex("_id"));
			cursor.close();
			db.close();
			return cityid;
		}
		cursor.close();
		db.close();
		return cityid;
	}

	public int getMaxMessageId() {
		SQLiteDatabase db = getReadableDatabase(); // 获取可写入的数据库对象。
		Cursor cursor = db.query(LISTVIEW_TABLE, null, null, null, null, null,
				null);
		if (cursor.moveToLast()) {
			return cursor.getInt(cursor.getColumnIndex("_id"));
		}

		return -1;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
