package com.ijob.preference;

import com.example.ijob.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @description À©Õ¹preference
 * @version 1.0.0
 */
public class MenuPreference extends Preference {
	
	private Drawable mItemDrawable;

	public MenuPreference(Context context) {
		super(context);
	}

	public MenuPreference(Context context, AttributeSet attr) {
		super(context, attr);
		TypedArray a = context.obtainStyledAttributes(attr,
				R.styleable.preference);
		int icon = a.getResourceId(R.styleable.preference_icon, 0);
		mItemDrawable = context.getResources().getDrawable(icon);
		a.recycle();

	}

	public MenuPreference(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
		TypedArray a = context.obtainStyledAttributes(attr,
				R.styleable.preference, defStyle, 0);
		int icon = a.getResourceId(R.styleable.preference_icon, 0);
		mItemDrawable = context.getResources().getDrawable(icon);
		a.recycle();
	}

	protected View onCreateView(ViewGroup parent) {
		return LayoutInflater.from(getContext()).inflate(R.layout.icon_preference,
				parent, false);
	}

	protected void onBindView(View view) {
		super.onBindView(view);
		ImageView icon = (ImageView) view.findViewById(R.id.menu_icon);
		icon.setImageDrawable(mItemDrawable);
		TextView title = (TextView) view.findViewById(R.id.menu_title);
		title.setText(getTitle());
	}
}
