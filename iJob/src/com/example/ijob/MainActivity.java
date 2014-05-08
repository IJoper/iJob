package com.example.ijob;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

import com.ijob.fragment.MainFragment;
import com.ijob.fragment.MenuFragment;
import com.ijob.messagealarm.BootBroadcastReceiver;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

public class MainActivity extends SlidingActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("iJob");
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.frame_content);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
        ActionBar actionBar = this.getActionBar();
        
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        BootBroadcastReceiver receiver = new BootBroadcastReceiver();
        registerReceiver(receiver, filter);
        actionBar = getActionBar();
        actionBar.show();
        
        // set the Behind View
        setBehindContentView(R.layout.frame_menu);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MenuFragment menuFragment = new MenuFragment();
        fragmentTransaction.replace(R.id.menu, menuFragment);
        fragmentTransaction.replace(R.id.content, new MainFragment(),"A");
        fragmentTransaction.commit();

        // customize the SlidingMenu
        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidth(40);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffset(150);
        sm.setFadeDegree(0.35f);
        
        //设置slding menu的几种手势模式 
        //TOUCHMODE_FULLSCREEN 全屏模式，在content页面中，滑动，可以打开sliding menu
        //TOUCHMODE_MARGIN 边缘模式，在content页面中，如果想打开slding ,你需要在屏幕边缘滑动才可以打开slding menu
        //TOUCHMODE_NONE 自然是不能通过手势打开啦
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

        //使用左上方icon可点，这样在onOptionsItemSelected里面才可以监听到R.id.home
//        menuView = (ImageView)findViewById(R.id.menuview);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        return true;
    } 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            //toggle就是程序自动判断是打开还是关闭
            toggle();
//          getSlidingMenu().showMenu();// show menu
//          getSlidingMenu().showContent();//show content
            
        case R.id.menu_search:
        	
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
