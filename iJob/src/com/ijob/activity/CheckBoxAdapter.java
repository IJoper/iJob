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
	// �������벼��
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
			// ���ViewHolder����
			holder = new ViewHolder();
			// ���벼�ֲ���ֵ��convertview
			convertView = inflater.inflate(R.layout.city_selection_item, null);
			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.citySelectionItem);
			// Ϊview���ñ�ǩ
			convertView.setTag(holder);
		} else {
			// ȡ��holder
			holder = (ViewHolder) convertView.getTag();
		}

		// ����checkbox��ѡ��״̬

		City_Item cityItem = (City_Item) cityChoicesList.get(position);
		holder.checkBox.setText(cityItem.getCityName());
		if (cityItem.getCityChoose() == 1) {
			holder.checkBox.setChecked(true);
		}
		return convertView;

	}

}
