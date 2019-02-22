package com.kknews.db;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 
 * @author zyq
 * @e-mail zhuyq@zensvision.com
 * @date 2016年9月23日 下午5:37:02
 */

public class DBUtils {
	private static final String tbl_topic = "tbl_topic";
	private static final String tbl_utils = "tbl_utils";
	private static final String tbl_column = "tbl_column";
	DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public List<Record> getUtils() {
		String sql = "select * from " + tbl_utils + " order by id DESC limit 0,1";
		return Db.find(sql);
	}
	
	public List<Record> getColumns() {
		String sql = "select * from " + tbl_column ;
		return Db.find(sql);
	}
	
	public boolean insertTopic(Record record){
		record.set("createtime", format.format(new Date()));
		return Db.save(tbl_topic, record);
	}
	
	public List<Record> findTopic(String topicId){
		String sql = "select * from " + tbl_topic + " where topicId = '"+ topicId + "'";
		return Db.find(sql);
	}
	
	public void deleteTopicByTime(String time){
		String sql = "DELETE FROM " + tbl_topic + " where createtime < '"+ time +"'";
			Db.update(sql);
	}
	
	public List<Record> findTopicByTime(String time){
		String sql = "select * from " + tbl_topic + " where createtime < '"+ time +"'";
		return Db.find(sql);
	}
}
