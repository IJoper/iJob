package com.ijob.fragment;

import com.example.ijob.MainActivity;
import com.example.ijob.R;
import com.example.ijob.SetFollowActivity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;

/**
 * menu fragment ，主要是用于显示menu菜单
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
        findPreference("a").setOnPreferenceClickListener(this);
        findPreference("b").setOnPreferenceClickListener(this);
        findPreference("c").setOnPreferenceClickListener(this);
        findPreference("d").setOnPreferenceClickListener(this);
        findPreference("e").setOnPreferenceClickListener(this);
        findPreference("f").setOnPreferenceClickListener(this);
        findPreference("g").setOnPreferenceClickListener(this);
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
            MenuFragment contentFragment = (MenuFragment)fragmentManager.findFragmentByTag("B");
            fragmentManager.beginTransaction()
            .replace(R.id.content, contentFragment == null ? new MenuFragment():contentFragment,"B")
            .commit();
        }else if("c".equals(key)) {

            if(index == 3) {
                ((MainActivity)getActivity()).getSlidingMenu().toggle();
                return true;
            }
            index = 3;
//            FragmentManager fragmentManager = ((MainActivity)getActivity()).getFragmentManager();
//            FocusFragment contentFragment = (FocusFragment)fragmentManager.findFragmentByTag("C");
//            fragmentManager.beginTransaction()
//            .replace(R.id.content, contentFragment == null ? new SetFollowActivity():contentFragment,"C")
//            .commit();
            Intent intent = new Intent(getActivity(), SetFollowActivity.class);
            startActivity(intent);
        }else if ("d".equals(key)) {
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
		}else if ("e".equals(key)) {
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
		}else if ("f".equals(key)) {
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
		}else if ("g".equals(key)) {
			if (index == 7) {
				((MainActivity)getActivity()).getSlidingMenu().toggle();
                return true;
			}
			index = 7;
            FragmentManager fragmentManager = ((MainActivity)getActivity()).getFragmentManager();
            ExitFragment contentFragment = (ExitFragment)fragmentManager.findFragmentByTag("G");
            fragmentManager.beginTransaction()
            .replace(R.id.content, contentFragment == null ? new ExitFragment():contentFragment,"G")
            .commit();
		}
        //anyway , show the sliding menu
        ((MainActivity)getActivity()).getSlidingMenu().toggle();
        return false;
    }
}
