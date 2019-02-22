package com.kknews.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.kknews.db.DBUtils;
import com.kknews.enity.TContent;
import com.kknews.utils.Utils;


/**
 * 
 * @author zyq
 * @e-mail zhuyq@zensvision.com
 * @date 2017年2月6日 上午10:10:58
 */

public class DeleteController extends Controller {
	private static Logger log = Logger.getLogger(DeleteController.class);
	DBUtils dbUtils = new DBUtils();
	Utils utils = new Utils();
	DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void index() { // 获取内容并注入
		int preIndex = -getParaToInt("preIndex");
		
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, preIndex);
		date = preIndex == 0 ? date : calendar.getTime();
		System.out.println(format.format(date));	
		
		List<Record> topics = dbUtils.findTopicByTime(format.format(date));
		if(topics.size() != 0){
			for (int j = 0; j < topics.size(); j++) {
				Record topic = topics.get(j);
				String thumbnail = topic.getStr("thumbnail");
				utils.delete(thumbnail);
				String tcontent = topic.getStr("tcontent");
				TContent content = new Gson().fromJson(tcontent.replace("[", "").replace("]", ""), TContent.class);
				utils.delete(content.getVideopath());
			}
		}
		dbUtils.deleteTopicByTime(format.format(date));

		log.info("DELETE | 删除历史数据成功，共删除"+topics.size()+"条数据！");
		renderText("清理缓存成功！共清理"+topics.size()+"条记录  "+format.format(new Date()));
	}
	
}
