package com.ijob.db;

public class Infor_item {
	private int _id;
	private String message_title;
	private String company;
	private String job_type;
	private String location;
	private String payment;
	private String release_time;
	private String deadline;

	/**
	 * Title: Infor_item Description: 默认构造函数
	 */
	public Infor_item() {
		super();
	}

	public Infor_item(int _id, String message_title, String company,
			String job_type, String location, String payment,
			String release_time, String deadline) {
		super();
		this._id = _id;
		this.message_title = message_title;
		this.company = company;
		this.job_type = job_type;
		this.location = location;
		this.payment = payment;
		this.release_time = release_time;
		this.deadline = deadline;
	}

	public int getid() {
		return this._id;
	}

	public String getmessage_title() {
		return this.message_title;
	}

	public String getcompany() {
		return this.company;
	}

	public String getjob_type() {
		return this.job_type;
	}

	public String getlocation() {
		return this.location;
	}

	public String getpayment() {
		return this.payment;
	}

	public String getrelease_time() {
		return this.release_time;
	}

	public String getdeadline() {
		return this.deadline;
	}
}
