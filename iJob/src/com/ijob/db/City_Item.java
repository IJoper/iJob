package com.ijob.db;

import android.R.integer;

public class City_Item {
	private int _id;// 具体城市对应的ID
	private int city_choose;// 1:选中 0：没选
	private String city_name;// 具体城市名称

	public City_Item() {

	}

	public City_Item(int id, int ischoose, String cityname) {
		this._id = id;
		this.city_choose = ischoose;
		this.city_name = cityname;
	}

	public int getCityId() {
		return this._id;
	}

	public int getCityChoose() {
		return this.city_choose;
	}

	public String getCityName() {
		return this.city_name;
	}

	public void setCityId(int id) {
		this._id = id;
	}

	public void setCityChoose(int ischoose) {
		this.city_choose = ischoose;
	}

	public void setCityName(String cityname) {
		this.city_name = cityname;
	}
}
