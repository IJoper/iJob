package com.ijob.fragment;

import org.w3c.dom.Text;

import com.example.ijob.MainActivity;
import com.example.ijob.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;

/**
 * menu fragment
 * @author <a href="mailto:kris@krislq.com">Kris.lee</a>
 * @since Mar 12, 2013
 * @version 1.0.0
 */
public class MenuFragment extends PreferenceFragment implements OnPreferenceClickListener{
	int index = -1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //set the preference xml to the content view
        addPreferencesFromResource(R.xml.menu);
        //add listener
        findPreference("a").setOnPreferenceClickListener(this);//main scene
        findPreference("b").setOnPreferenceClickListener(this);//personal area
        findPreference("c").setOnPreferenceClickListener(this);//help server
        findPreference("d").setOnPreferenceClickListener(this);//version update
        findPreference("e").setOnPreferenceClickListener(this);//request
        findPreference("f").setOnPreferenceClickListener(this);//exit
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if("a".equals(key)) {
            //if the content view is that we need to show . show directly
            if(index == 1) {
                ((MainActivity)getActivity()).getSlidingMenu().toggle();
                return true;
            }
            //otherwise , replace the content view via a new Content fragment
            index = 1;
            FragmentManager fragmentManager = ((MainActivity)getActivity()).getFragmentManager();
            MainFragment contentFragment = (MainFragment)fragmentManager.findFragmentByTag("A");
            fragmentManager.beginTransaction()
            .replace(R.id.content, contentFragment == null ?new MainFragment():contentFragment ,"A")
            .commit();
            
        }else if("b".equals(key)) {
            if(index == 2) {
                ((MainActivity)getActivity()).getSlidingMenu().toggle();
                return true;
            }
            index = 2;
            FragmentManager fragmentManager = ((MainActivity)getActivity()).getFragmentManager();
            Personal contentFragment = (Personal)fragmentManager.findFragmentByTag("B");
            fragmentManager.beginTransaction()
            .replace(R.id.content, contentFragment == null ? new Personal():contentFragment,"B")
            .commit();
        }else if ("c".equals(key)) {
			if (index == 4) {
				((MainActivity)getActivity()).getSlidingMenu().toggle();
                return true;
			}
			index = 4;
            FragmentManager fragmentManager = ((MainActivity)getActivity()).getFragmentManager();
            HelpServerFragment contentFragment = (HelpServerFragment)fragmentManager.findFragmentByTag("D");
            fragmentManager.beginTransaction()
            .replace(R.id.content, contentFragment == null ? new HelpServerFragment():contentFragment,"D")
            .commit();
		}else if ("d".equals(key)) {
			if (index == 5) {
				((MainActivity)getActivity()).getSlidingMenu().toggle();
                return true;
			}
			index = 5;
            FragmentManager fragmentManager = ((MainActivity)getActivity()).getFragmentManager();
            VersionFragment contentFragment = (VersionFragment)fragmentManager.findFragmentByTag("E");
            fragmentManager.beginTransaction()
            .replace(R.id.content, contentFragment == null ? new VersionFragment():contentFragment,"E")
            .commit();
		}else if ("e".equals(key)) {
			if (index == 6) {
				((MainActivity)getActivity()).getSlidingMenu().toggle();
                return true;
			}
			index = 6;
            FragmentManager fragmentManager = ((MainActivity)getActivity()).getFragmentManager();
            QuestFragment contentFragment = (QuestFragment)fragmentManager.findFragmentByTag("F");
            fragmentManager.beginTransaction()
            .replace(R.id.content, contentFragment == null ? new QuestFragment():contentFragment,"F")
            .commit();
		}else if ("f".equals(key)) {
			AlertDialog.Builder builder = new AlertDialog.Builder((MainActivity)getActivity());
			builder.setMessage(getString(R.string.exit_hint));
			builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO 自动生成的方法存根
					getActivity().finish();
				}
			});
			
			builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO 自动生成的方法存根
					//do nothing to return to the menu scene
				}
			});
			
			AlertDialog exitDialog = builder.create();
			exitDialog.show();
		}
        //anyway , show the sliding menu
        ((MainActivity)getActivity()).getSlidingMenu().toggle();
        return false;
    }
}
