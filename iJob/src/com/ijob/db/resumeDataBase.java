package com.ijob.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * ClassName: resumeDataBase Description: TODO(这里用一句话描述这个类的作用) Create date:
 * 2014年5月7日 上午9:51:24
 * 
 * @author 钟文豪
 * @version V1.0
 * 
 */
public class resumeDataBase extends SQLiteOpenHelper {

	private static final int VERSION = 1; // 定义数据库版本号
	private static final String DBNAME = "MyresumeDataBase"; // 定义数据库名字

	/**
	 * Title: resumeDataBase Description: TODO(这里用一句话描述这个方法的作用)
	 * 
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public resumeDataBase(Context context) {
		super(context, DBNAME, null, VERSION);
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
		db.execSQL("create table myresumeDataBase(" // 表名
				+ "_id integer primary key," // 主键，简介信息的ID，类型为数字，唯一性
				+ "name varchar(200)," // 姓名，字符类型
				+ "sex varchar(200)," // 性别，字符类型
				+ "age varchar(200)," // 年龄，字符类型
				+ "eduBackground varchar(200)," // 学历，字符类型
				+ "school varchar(200)," // 毕业学校，字符类型
				+ "purpose varchar(200)," // 求职意向，字符类型
				+ "phoneNumber varchar(200)," // 电话，字符类型
				+ "QQ varchar(200)," // QQ，字符类型
				+ "E_mail varchar(200)," // 邮箱，字符类型
				+ "address varchar(400)," // 住址，字符类型
				+ "intro varchar(1000)," // 自我介绍，字符类型
				+ "exep varchar(2000)," // 实习经历或项目实践，字符类型
				+ "skill varchar(1000)," // 等级、技能及资格证书，字符类型
				+ "other varchar(2000) )"); // 其他，字符类型

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
		// TODO 自动生成的方法存根

	}

}
