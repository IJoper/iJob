package com.example.ijob;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import android.app.Application;

/**
 * 关注列表信息
 * @author ZipS
 *
 */
public class Follow extends Application {
	private static final long serialVersionUID = -7060210544600464481L;
	private ArrayList<String> followedCityList = new ArrayList<String>();
	private String[] followedCity = {"广州","北京","上海"};
	private String[] cityChoices = {"广州","北京","上海","杭州","南京","深圳","武汉","纽约","珠海","香港"};
	private ArrayList<String> cityChoicesList = new ArrayList<String>();
	private String[] followedPost = {"软件开发工程师"};
	private String[] postChoices = {"软件开发工程师","市场营销","人力资源","海外拓展","系统测试","CEO","安全专家"};
	public Follow(){
		for (int i = 0; i < followedCity.length; i++) {
			followedCityList.add(followedCity[i]);
		}
		for (int i = 0; i < cityChoices.length; i++) {
			cityChoicesList.add(cityChoices[i]);
		}
		
	}
	/**
	 * 选择关注城市
	 * @return
	 */
	public ArrayList<String> getCityChoices(){
		
		return cityChoicesList;
	}
	/**
	 * 取消关注
	 * @param s
	 */
	public void removeCityChoiceFromList(String s){
		cityChoicesList.remove(s);
	}
	public void removeFollowedCityFromList(String s){
		followedCityList.remove(s);
	}
	public void addCityToFollowedList(String s){
		if (!followedCityList.contains(s)) {
			followedCityList.add(s);
		}
		//followedCityList.add(s);
	}
	public String[] getPostChoices(){
		
		return postChoices;
	}
	public ArrayList<String> getFollowedCity(){
		return followedCityList;
	}
	public String[] getFollowedPost(){
		return followedPost;
	}
}
