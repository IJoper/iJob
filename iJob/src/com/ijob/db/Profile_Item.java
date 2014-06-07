package com.ijob.db;

public class Profile_Item {
	private int _id;
	private String name;
	private String sex;
	private String age;
	private String eduBackground;
	private String school;
	private String purpose;
	private String phoneNumber;
	private String QQ;
	private String E_mail;
	private String address;
	private String intro;
	private String exep;
	private String skill;
	private String other;

	/**
	 * Title: Infor_item Description: 默认构造函数
	 */
	public Profile_Item() {
		super();
	}

	public Profile_Item(int _id, String name, String sex, String age,
			String eduBackground, String school, String purpose,
			String phoneNumber, String QQ, String E_mail, String address,
			String intro, String exep, String skill, String other) {
		super();
		this._id = _id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.eduBackground = eduBackground;
		this.school = school;
		this.purpose = purpose;
		this.phoneNumber = phoneNumber;
		this.QQ = QQ;
		this.E_mail = E_mail;
		this.address = address;
		this.intro = intro;
		this.exep = exep;
		this.skill = skill;
		this.other = other;
	}

	public int getid() {
		return _id;
	}

	public void setid(int id) {
		this._id = id;
	}

	public String getName() {
		return name;
	}

	public void SetName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void SetSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void SetAge(String age) {
		this.age = age;
	}

	public String getEduBackground() {
		return eduBackground;
	}

	public void SetEduBackground(String eduBackground) {
		this.eduBackground = eduBackground;
	}

	public String getSchool() {
		return school;
	}

	public void SetSchool(String school) {
		this.school = school;
	}

	public String getPurpose() {
		return purpose;
	}

	public void SetPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void SetPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getQQ() {
		return QQ;
	}

	public void SetQQ(String QQ) {
		this.QQ = QQ;
	}

	public String getE_mail() {
		return E_mail;
	}

	public void SetE_mail(String E_mail) {
		this.E_mail = E_mail;
	}

	public String getAddress() {
		return address;
	}

	public void SetAddress(String address) {
		this.address = address;
	}

	public String getIntro() {
		return intro;
	}

	public void SetIntro(String intro) {
		this.intro = intro;
	}

	public String getExep() {
		return exep;
	}

	public void SetExep(String exep) {
		this.exep = exep;
	}

	public String getSkill() {
		return skill;
	}

	public void SetSkill(String skill) {
		this.skill = skill;
	}

	public String getOther() {
		return other;
	}

	public void SetOther(String other) {
		this.other = other;
	}
}
