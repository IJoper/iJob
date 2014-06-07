package com.ijob.activity;

import java.util.List;

import com.example.ijob.R;
import com.ijob.db.City_Item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

public class CheckBoxAdapter extends BaseAdapter {

	class ViewHolder {
		CheckBox checkBox = null;
	}

	private List<City_Item> cityChoicesList;

	private Context context;
	// 用来导入布局
	private LayoutInflater inflater = null;

	public CheckBoxAdapter(List<City_Item> cityChoicesList, Context context) {
		this.context = context;
		this.cityChoicesList = cityChoicesList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return cityChoicesList.size();
	}

	@Override
	public Object getItem(int position) {
		return cityChoicesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			// 获得ViewHolder对象
			holder = new ViewHolder();
			// 导入布局并赋值给convertview
			convertView = inflater.inflate(R.layout.city_selection_item, null);
			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.citySelectionItem);
			// 为view设置标签
			convertView.setTag(holder);
		} else {
			// 取出holder
			holder = (ViewHolder) convertView.getTag();
		}

		// 设置checkbox的选中状态

		City_Item cityItem = (City_Item) cityChoicesList.get(position);
		holder.checkBox.setText(cityItem.getCityName());
		if (cityItem.getCityChoose() == 1) {
			holder.checkBox.setChecked(true);
		}
		return convertView;

	}

}
