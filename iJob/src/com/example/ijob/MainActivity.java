package com.example.ijob;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.ijob.fragment.MainFragment;
import com.ijob.fragment.MenuFragment;
import com.ijob.messagealarm.BootBroadcastReceiver;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

/**
 * 
 * @author <a href="mailto:kris@krislq.com">Kris.lee</a>
 * @since Mar 12, 2013
 * @version 1.0.0
 */
public class MainActivity extends SlidingActivity {
	private ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("iJob");
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.frame_content);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
        
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
        
        //����slding menu�ļ�������ģʽ
        //TOUCHMODE_FULLSCREEN ȫ��ģʽ����contentҳ���У����������Դ�sliding menu
        //TOUCHMODE_MARGIN ��Եģʽ����contentҳ���У�������slding ,����Ҫ����Ļ��Ե�����ſ��Դ�slding menu
        //TOUCHMODE_NONE ��Ȼ�ǲ���ͨ�����ƴ���
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

        //ʹ�����Ϸ�icon�ɵ㣬������onOptionsItemSelected����ſ��Լ�����R.id.home
//        menuView = (ImageView)findViewById(R.id.menuview);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
//    	MenuItem search = menu.add(0, 0, 0, );
//    	MenuItem add = menu.add(0, 0, 0, );
//    	
//    	search.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//    	add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            //toggle���ǳ����Զ��ж��Ǵ򿪻��ǹر�
            toggle();
//          getSlidingMenu().showMenu();// show menu
//          getSlidingMenu().showContent();//show content
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
