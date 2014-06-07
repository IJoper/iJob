package com.ijob.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RDBmanager {

	private resumeDataBase mdb;
	private SQLiteDatabase db;

	/**
	 * Title: MyDBmanager Description: 初始化控制类，需要传入当前上下文环境，如mainActivity.this
	 */
	public RDBmanager(Context context) {
		mdb = new resumeDataBase(context);
	}

	/**
	 * 
	 * Title: add Description: 添加一条记录进入数据库。不会检查记录的合法性
	 * 
	 * @param recuritment_inf_intro
	 *            void
	 */
	public void add(Profile_Item profile_Item) {
		db = mdb.getWritableDatabase(); // 获取可写入的数据库对象。
		db.execSQL(
				"insert into myresumeDataBase "
						+ "(_id,name,sex,age,eduBackground,school,purpose,phoneNumber,"
						+ "QQ,E_mail,address,intro,exep,skill,other) "
						+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", // “添加”的数据库指令
				new Object[] { profile_Item.getid(), profile_Item.getName(),
						profile_Item.getSex(), profile_Item.getAge(),
						profile_Item.getEduBackground(),
						profile_Item.getSchool(), profile_Item.getPurpose(),
						profile_Item.getPhoneNumber(), profile_Item.getQQ(),
						profile_Item.getE_mail(), profile_Item.getAddress(),
						profile_Item.getIntro(), profile_Item.getExep(),
						profile_Item.getSkill(), profile_Item.getOther() });
		db.close();
	}

	/**
	 * 
	 * Title: delete Description: 根据输入的id集合删除记录，不考虑id的合法性。
	 * 
	 * @param _id
	 *            void
	 */
	public void delete(Integer[] _id) {
		if (_id.length > 0) {
			StringBuffer s = new StringBuffer();
			for (int i = 0; i < _id.length; i++)
				// 遍历id集合，要删除多少个id就要添加多少个问号
				s.append("?,");
			s.deleteCharAt(s.length() - 1); // 阐述·最后一个逗号

			db = mdb.getWritableDatabase();
			db.execSQL("delete from myresumeDataBase where_id in (" + s + ")",
					(Object[]) _id);
		}
		db.close();
	}

	/**
	 * 
	 * Title: update Description: 根据传入信息的id，跟新数据库中对应的信息，不考虑id的合法性
	 * 
	 * @param recuritment_inf_intro
	 *            void
	 */
	public void update(Profile_Item profile_Item) {
		db = mdb.getWritableDatabase(); // 获取可写入的数据库对象。
		db.execSQL(
				"update myresumeDataBase set " // “更新”的数据库语句
						+ "name = ? ,sex = ? ,age = ? ,eduBackground = ? ,school = ? ,purpose = ? ,phoneNumber = ? ,"
						+ "QQ = ? ,E_mail = ? ,address = ? ,intro = ? ,exep = ? ,skill = ? ,other = ?  where _id = ? ",
				new Object[] { profile_Item.getName(), profile_Item.getSex(),
						profile_Item.getAge(), profile_Item.getEduBackground(),
						profile_Item.getSchool(), profile_Item.getPurpose(),
						profile_Item.getPhoneNumber(), profile_Item.getQQ(),
						profile_Item.getE_mail(), profile_Item.getAddress(),
						profile_Item.getIntro(), profile_Item.getExep(),
						profile_Item.getSkill(), profile_Item.getOther(),
						profile_Item.getid() });
		db.close();
	}

	/**
	 * 
	 * Title: find Description: 根据传入的id进行查找，不考虑id的合法性
	 * 
	 * @param _id
	 * @return Infor_item
	 */
	public Profile_Item find(int _id) {
		db = mdb.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select _id,name,sex,age,eduBackground,school,purpose,phoneNumber,"
						+ "QQ,E_mail,address,intro,exep,skill,other "
						+ "from myresumeDataBase where _id = ?",
				new String[] { String.valueOf(_id) });

		if ((cursor.getCount() > 0) && (cursor.moveToNext())) {
			Profile_Item temp = new Profile_Item(cursor.getInt(cursor
					.getColumnIndex("_id")), cursor.getString(cursor
					.getColumnIndex("name")), cursor.getString(cursor
					.getColumnIndex("sex")), cursor.getString(cursor
					.getColumnIndex("age")), cursor.getString(cursor
					.getColumnIndex("eduBackground")), cursor.getString(cursor
					.getColumnIndex("school")), cursor.getString(cursor
					.getColumnIndex("purpose")), cursor.getString(cursor
					.getColumnIndex("phoneNumber")), cursor.getString(cursor
					.getColumnIndex("QQ")), cursor.getString(cursor
					.getColumnIndex("E_mail")), cursor.getString(cursor
					.getColumnIndex("address")), cursor.getString(cursor
					.getColumnIndex("intro")), cursor.getString(cursor
					.getColumnIndex("exep")), cursor.getString(cursor
					.getColumnIndex("skill")), cursor.getString(cursor
					.getColumnIndex("other")));
			cursor.close();
			db.close();
			return temp;
		}
		cursor.close();
		db.close();
		return new Profile_Item();
	}

	/**
	 * 
	 * Title: count Description: 获得当前数据库中数据项的数量
	 * 
	 * @return int
	 */
	public int count() {
		db = mdb.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from myresumeDataBase", null);
		int temp = cursor.getCount();
		cursor.close();
		db.close();
		return temp;
	}

}
