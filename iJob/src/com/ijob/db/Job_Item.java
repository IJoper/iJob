package com.ijob.db;

public class Job_Item {
	private int _id;//�����λ��Ӧ��ID
	private int job_choose;//1:ѡ��	0��ûѡ
	private String job_name;//�����λ����
	
	public Job_Item() {

	}
	
	public Job_Item(int id, int ischoose, String jobname){
		this._id = id;
		this.job_choose = ischoose;
		this.job_name = jobname;
	}
	
	public int getJobId(){
		return this._id;
	}
	public int getJobChoose(){
		return this.job_choose;
	}
	public String getJobName(){
		return this.job_name;
	}
	public void setJobId(int id){
		this._id = id;
	}
	public void setJobChoose(int ischoose){
		this.job_choose = ischoose;
	}
	public void setJobName(String jobname){
		this.job_name = jobname;
	}
}
