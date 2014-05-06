package com.example.ijob;

import java.util.List;

import com.ijob.db.Job_Item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

public class PostCheckBoxAdapter extends BaseAdapter {
	class 
	
	PostViewHolder {
		CheckBox checkBox = null;
	}

	private LayoutInflater inflater = null;
	private List<Job_Item> jobChoicesList;
	private Context context;

	public PostCheckBoxAdapter(List<Job_Item> jobChoicesList, Context context) {
		this.context = context;
		this.jobChoicesList = jobChoicesList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return jobChoicesList.size();
	}

	@Override
	public Object getItem(int position) {
		return jobChoicesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PostViewHolder holder = null;

		if (convertView == null) {
			// ���ViewHolder����
			holder = new PostViewHolder();
			// ���벼�ֲ���ֵ��convertview
			convertView = inflater.inflate(R.layout.city_selection_item, null);
			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.citySelectionItem);
			// Ϊview���ñ�ǩ
			convertView.setTag(holder);
		} else {
			// ȡ��holder
			holder = (PostViewHolder) convertView.getTag();
		}

		// ����checkbox�е���ʾ״̬

		Job_Item jobItem = (Job_Item) jobChoicesList.get(position);
		holder.checkBox.setText(jobItem.getJobName());
		if (jobItem.getJobChoose() == 1) {
			holder.checkBox.setChecked(true);
		}

		return convertView;
	}

}
