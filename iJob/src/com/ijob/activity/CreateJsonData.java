package com.ijob.activity;

import java.util.List;

import org.json.JSONObject;

import android.util.Log;

import com.ijob.db.City_Item;
import com.ijob.db.Job_Item;

public class CreateJsonData {

	public String CreateJsonByCityList(List<City_Item> list) {
		String json = new String();
		if (list == null) {
			return json;
		}
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				json += list.get(i).getCityName();
			} else {
				json += "," + list.get(i).getCityName();
			}
		}
		return json;
	}

	public String CreateJsonByJobList(List<Job_Item> list) {
		String json = new String();
		if (list == null) {
			return json;
		}
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				json += list.get(i).getJobId();
			} else {
				json += "," + list.get(i).getJobId();
			}
		}
		return json;
	}
}
