/**********************************************************************
 **********************************************************************
 **    Project Name : UBAS-SERVICE
 **    Package Name : com.zens.ubasservice.entity								 
 **    Type    Name : TUser 							     	
 **    Create  Time : 2016年9月19日 下午2:19:54								
 ** 																
 **    (C) Copyright Zensvision Information Technology Co., Ltd.	 
 **            Corporation 2016 All Rights Reserved.				
 **********************************************************************
 **	     注意： 本内容仅限于上海仁视信息科技有限公司内部使用，禁止转发		 **
 **********************************************************************
 */
package com.kknews.enity;

import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * @author zyq
 * @e-mail zhuyq@zensvision.com
 * @date 2017年2月6日 下午4:23:15
 */
public class TContent extends Model<TContent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String videopath;
	String name;
	String duration;
	public String getVideopath() {
		return videopath;
	}
	public void setVideopath(String videopath) {
		this.videopath = videopath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}

}
