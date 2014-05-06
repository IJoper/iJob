package com.ijob.messagealarm;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MessageActivity extends Service{
	private MyBinder mBinder = new MyBinder(); 
	private int count = 1;
	private String result;
	private String URL = "http://1.iters.sinaapp.com/messages/searchMessageByLocation.json";
	@Override  
    public void onCreate() {  
        super.onCreate(); 
        
    }  
	@Override  
	public int onStartCommand(Intent intent, int flags, int startId) { 
		MyBinder myBinder = new MyBinder();
		myBinder.startDownload();
		Bundle bundle = new Bundle();
        bundle.putString("message", "new message "+count+" !");
        Intent myIntent = new Intent(this, MyAlarm.class);
        myIntent.putExtras(bundle);
        count++;
        startService(myIntent);
        Log.i("message", "onCreate() executed");
	    return super.onStartCommand(intent, flags, startId);  
	}  
	class MyBinder extends Binder {  
		  
	    public void startDownload() {  
	        new Thread(new Runnable() {  
	            @Override  
	            public void run() {  
	                // 执行具体的下载任务  
	            	HttpPost httpPost = new HttpPost(URL);
	            	
	        		//按照网页的规定提交列表形式的访问需求数据
	        		List<NameValuePair> params = new ArrayList<NameValuePair>();
	        		params.add(new BasicNameValuePair("0", "广州"));
	        		params.add(new BasicNameValuePair("1", "深圳"));
	        		
	                try {
						httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
					} catch (UnsupportedEncodingException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
	                
	                HttpClient httpClient = new DefaultHttpClient();//定义一个客户端
	                result = "";
	                try {
	                	HttpResponse response = httpClient.execute(httpPost);//对预先定义的网站进行访问
	                	//如果访问连接成功，则获取该网站上的信息
	                	if(response.getStatusLine().getStatusCode() == 200){
	                		Log.i("connected", "has connected!");
	                        HttpEntity entity = response.getEntity();
	                        result = EntityUtils.toString(entity, HTTP.UTF_8);
	                    }
	        		} catch (Exception e) {
	        			// TODO: handle exception
	        			Log.i("error", e.toString());
	        		}
	                //将从网站上获取到的xml文件信息反馈给调用者
//	                return result;
	                Log.i("result post = ", result.toString());
	            }  
	        }).start();  
	    }
	}
	@Override  
    public void onDestroy() {  
        super.onDestroy();
        Log.d("ondestroy", "onDestroy() executed");  
    }
	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自动生成的方法存根
        Log.i("ddddd", "MessageActivity");
        
		return mBinder;
	}
}
