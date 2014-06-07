package com.ijob.activity;

import com.example.ijob.R;

import android.R.interpolator;
import android.R.string;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MBTIchcek extends Activity {
	TextView questionTextView;
	RadioButton radioButton1;
	RadioButton radioButton2;
	Button button;
	int finishcount;
	int buttonState;// 1:start 2:nextQuestion 3:finish
	private int answer[] = new int[31];// A:1 B:2

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mbtichcek);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		questionTextView = (TextView) findViewById(R.id.MBTI_question);
		radioButton1 = (RadioButton) findViewById(R.id.MBTI_radioButton1);
		radioButton2 = (RadioButton) findViewById(R.id.MBTI_radioButton2);
		button = (Button) findViewById(R.id.MBTI_next);
		finishcount = 0;
		buttonState = 1;
		for (int i = 0; i < answer.length; i++) {
			answer[i] = 0;

		}
		final String question[] = getResources().getStringArray(
				R.array.MBTIquestion);
		final String chooseA[] = getResources().getStringArray(R.array.chooseA);
		final String chooseB[] = getResources().getStringArray(R.array.chooseB);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (finishcount - 1 >= 0 && answer[finishcount - 1] == 0) {
					Toast.makeText(v.getContext(), "You haven't choose",
							Toast.LENGTH_SHORT).show();
				} else {
					radioButton1.setChecked(false);
					radioButton2.setChecked(false);
					switch (buttonState) {
					case 1:
						buttonState = 2;
						button.setText(getResources().getString(
								R.string.button_state2));
						questionTextView.setVisibility(View.VISIBLE);
						questionTextView.setText(question[finishcount]);
						radioButton1.setVisibility(View.VISIBLE);
						radioButton2.setVisibility(View.VISIBLE);
						radioButton1.setText(chooseA[finishcount]);
						radioButton2.setText(chooseB[finishcount]);
						finishcount++;
						break;
					case 2:
						if (finishcount == question.length - 1) {
							buttonState = 3;
							button.setText(getResources().getString(
									R.string.button_state3));
						} else {
							buttonState = 2;
							button.setText(getResources().getString(
									R.string.button_state2));
						}
						questionTextView.setText(question[finishcount]);
						radioButton1.setText(chooseA[finishcount]);
						radioButton2.setText(chooseB[finishcount]);
						finishcount++;
						break;
					case 3:
						buttonState = 1;
						button.setText(getResources().getString(
								R.string.button_state1));
						questionTextView.setVisibility(View.INVISIBLE);
						questionTextView.setText("");
						radioButton1.setVisibility(View.INVISIBLE);
						radioButton2.setVisibility(View.INVISIBLE);
						radioButton1.setText("");
						radioButton2.setText("");
						finishcount = 0;
						break;
					default:
						break;
					}
				}

			}
		});
		radioButton1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				radioButton2.setChecked(false);
				radioButton1.setChecked(true);
				answer[finishcount - 1] = 1;
				Toast.makeText(v.getContext(), "Choose A", Toast.LENGTH_SHORT)
						.show();
			}
		});
		radioButton2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (answer[finishcount - 1] == 1) {
					radioButton1.setChecked(false);
				}
				radioButton2.setChecked(true);
				answer[finishcount - 1] = 2;
				Toast.makeText(v.getContext(), "Choose B", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
