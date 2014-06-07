package com.ijob.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * ClassName: resumeDataBase Description: TODO(������һ�仰��������������) Create date:
 * 2014��5��7�� ����9:51:24
 * 
 * @author ���ĺ�
 * @version V1.0
 * 
 */
public class resumeDataBase extends SQLiteOpenHelper {

	private static final int VERSION = 1; // �������ݿ�汾��
	private static final String DBNAME = "MyresumeDataBase"; // �������ݿ�����

	/**
	 * Title: resumeDataBase Description: TODO(������һ�仰�����������������)
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
	 * ���� Javadoc��
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table myresumeDataBase(" // ����
				+ "_id integer primary key," // �����������Ϣ��ID������Ϊ���֣�Ψһ��
				+ "name varchar(200)," // �������ַ�����
				+ "sex varchar(200)," // �Ա��ַ�����
				+ "age varchar(200)," // ���䣬�ַ�����
				+ "eduBackground varchar(200)," // ѧ�����ַ�����
				+ "school varchar(200)," // ��ҵѧУ���ַ�����
				+ "purpose varchar(200)," // ��ְ�����ַ�����
				+ "phoneNumber varchar(200)," // �绰���ַ�����
				+ "QQ varchar(200)," // QQ���ַ�����
				+ "E_mail varchar(200)," // ���䣬�ַ�����
				+ "address varchar(400)," // סַ���ַ�����
				+ "intro varchar(1000)," // ���ҽ��ܣ��ַ�����
				+ "exep varchar(2000)," // ʵϰ��������Ŀʵ�����ַ�����
				+ "skill varchar(1000)," // �ȼ������ܼ��ʸ�֤�飬�ַ�����
				+ "other varchar(2000) )"); // �������ַ�����

	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO �Զ����ɵķ������

	}

}
