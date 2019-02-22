package com.kknews.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.chasonx.tools.HttpUtil;
import com.chasonx.tools.StringUtils;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.kknews.db.DBUtils;
import com.kknews.utils.Download;
import com.kknews.utils.HttpRequest;
import com.kknews.utils.Utils;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.zens.entity.Topic;
import com.zens.service.TopicService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author zyq
 * @e-mail zhuyq@zensvision.com
 * @date 2016年9月23日 下午5:36:42
 */

public class KKNewsController extends Controller {
	private static Logger log = Logger.getLogger(KKNewsController.class);
	DBUtils dbUtils = new DBUtils();
	Utils util = new Utils();
	HttpRequest httpRequest = new HttpRequest();
	Download downloadImg = new Download();
	
	List<Record> utils = dbUtils.getUtils();
	List<Record> columns = dbUtils.getColumns();
	
	String video_path = utils.get(0).getStr("video_path");
	String save_path = utils.get(0).getStr("save_path");
	String inject_url = utils.get(0).getStr("inject_url");//"http://localhost:8080/kankanNews/kknews/test";//
	String underFrame_url = utils.get(0).getStr("underFrame_url");
	String site_id = utils.get(0).getStr("site_id");
	int Maxsize = utils.get(0).getInt("size");

	@SuppressWarnings("deprecation")
	public void index() { // 获取内容并注入

		for (Object o : columns) {
			Record record = (Record) o;
			String rssUrl = record.getStr("FRssUrl");
			if (rssUrl != null) {
				System.out.println(new Date().toLocaleString()+"--"+ record.getStr("FColumnName") +"--"+rssUrl);
				log.info("       ");
				log.info(record.getStr("FColumnName") +"--"+rssUrl);
				String columnGuid = record.getStr("FColumnGUID");
 
				try {
					URL url = new URL(rssUrl);
					// 读取Rss源
					XmlReader reader = new XmlReader(url);
					// System.out.println("Rss源的编码格式为：" + reader.getEncoding());
					SyndFeedInput input = new SyndFeedInput();
					// 得到SyndFeed对象，即得到Rss源里的所有信息
					SyndFeed feed = input.build(reader);
					// System.out.println(feed);
					// 得到Rss新闻中子项列表
					List<?> entries = feed.getEntries();
					// 循环得到每个子项信息
					int total = entries.size();
					total = Maxsize == 0 ? total : Maxsize;
					//接口数据
					if(rssUrl.indexOf("kkvideo") == -1 ) {//精选、申事
						for (int i = 0; i < total; i++) {//默认设置为5个
						//for (int i = total-1; i >=0; i--) {//默认设置为5个
							SyndEntry entry = (SyndEntry) entries.get(i);
							// 标题、连接地址、标题简介、时间是一个Rss源项最基本的组成部分
							String title = entry.getTitle();
							String link = entry.getLink();
							SyndContent description = entry.getDescription();
							String tsummary = description.getValue();
							String newstime = util.formatTimes(entry.getPublishedDate());

							// System.out.println("标题简介：" + description.getValue());
							 System.out.println("发布时间：" +  util.formatTimes(entry.getPublishedDate()));

							getKKNews(link, title, tsummary, newstime, columnGuid);

							/*
							 * // 以下是Rss源可先的几个部分 System.out.println("标题的作者：" +
							 * entry.getAuthor()); // 此标题所属的范畴 List categoryList =
							 * entry.getCategories(); if (categoryList != null) {
							 * for (int m = 0; m < categoryList.size(); m++) {
							 * SyndCategory category = (SyndCategory)
							 * categoryList.get(m); System.out.println("此标题所属的范畴：" +
							 * category.getName()); } } // 得到流媒体播放文件的信息列表 List
							 * enclosureList = entry.getEnclosures(); if
							 * (enclosureList != null) { for (int n = 0; n <
							 * enclosureList.size(); n++) { SyndEnclosure enclosure
							 * = (SyndEnclosure) enclosureList.get(n);
							 * System.out.println("流媒体播放文件：" +
							 * entry.getEnclosures()); } }
							 */
						}
					}else {//松江、嘉定
						for (int i = total-1; i >=0; i--) {//默认设置为5个
							SyndEntry entry = (SyndEntry) entries.get(i);
							// 标题、连接地址、标题简介、时间是一个Rss源项最基本的组成部分
							String title = entry.getTitle();
							String link = entry.getLink();
							SyndContent description = entry.getDescription();
							String tsummary = description.getValue();
							String newstime = util.formatTimes(entry.getPublishedDate());

							// System.out.println("标题简介：" + description.getValue());
							 System.out.println("发布时间：" +  util.formatTimes(entry.getPublishedDate()));

							getKKNews(link, title, tsummary, newstime, columnGuid);

							/*
							 * // 以下是Rss源可先的几个部分 System.out.println("标题的作者：" +
							 * entry.getAuthor()); // 此标题所属的范畴 List categoryList =
							 * entry.getCategories(); if (categoryList != null) {
							 * for (int m = 0; m < categoryList.size(); m++) {
							 * SyndCategory category = (SyndCategory)
							 * categoryList.get(m); System.out.println("此标题所属的范畴：" +
							 * category.getName()); } } // 得到流媒体播放文件的信息列表 List
							 * enclosureList = entry.getEnclosures(); if
							 * (enclosureList != null) { for (int n = 0; n <
							 * enclosureList.size(); n++) { SyndEnclosure enclosure
							 * = (SyndEnclosure) enclosureList.get(n);
							 * System.out.println("流媒体播放文件：" +
							 * entry.getEnclosures()); } }
							 */
						}
						
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}  
			System.out.println();System.out.println();System.out.println();
		}
		log.info(" ZensvisionZensvision抓取看看新闻网数据完成！！！ZensvisionZensvision");
		log.info("       ");
		log.info("       ");
		renderText("注入UCGS成功！");
	}
	
	@SuppressWarnings("deprecation")
	public String getKKNews(String url, String title, String tsummary, String newstime, String columnGuid) throws Exception{
		
				String id = url.substring(url.lastIndexOf("/") + 1).split(".html")[0].split("_")[1];
				List<Record> topics = dbUtils.findTopic(id);

				log.info(id+":################################文章ID:"+topics.size());
				log.info("URL:"+url);
				String srcc = "";
				if(topics.size() != 0){
					return null;
				}else{
					JSONObject content = new JSONObject();
					JSONArray contents = new JSONArray();

					String result = httpRequest.sendGet(url, "");
					int tclass = 0;//文章类型 图文/视频
					int a = result.lastIndexOf("config={") + 7;
					int b = result.lastIndexOf("}	  </script>")  == -1 ? result.lastIndexOf("}  </script>") + 1 : result.lastIndexOf("}	  </script>") + 1;
					//int b = result.lastIndexOf("}  </script>") + 1 == 0 ? result.lastIndexOf("}</script>") + 1 : result.lastIndexOf("}  </script>") + 1;
					String config = result.substring(a, b);
					//log.info(config);
					//System.out.println("config:"+config);
					JSONObject object = new JSONObject();
					try {
						object = JSONObject.fromObject(
									config.replace("		title: document.title,", "").replace("  title: document.title,", ""));
						//String topicId = object.getString("id");
						String thumbnail = object.getString("titlepic");
						if(thumbnail.equals("")){
							//thumbnail = object.getString("thumbnail");
							//if(thumbnail.equals("")){
								thumbnail = "../assets/images/default_"+ random() + "_sh.png";
							//}
						}
						
						if (config.indexOf("mp4") != -1) {// 单视频文章
							log.info(id+":视频文章");
							tclass = 1;
							String videoUrl = object.getString("mp4");
							String src = video_path +  id + ".mp4";
							downloadImg.dowVideo(videoUrl, save_path ,  id + ".mp4");
	
							content.put("videopath", src);
							util.getVideoImg(src, id + ".mp4");
							srcc = src+".jpg";
							
							content.put("name", title);
							content.put("duration", object.getString("duration"));
							contents.add(content);
						} else {// 非单视频文章
							List<String> list = util.match(result, "iframe", "src");
							if(list.size() == 0){
								log.info(id+":图文文章");
								 content.put("videopath", video_path + id + ".png");
								 content.put("name", title);
								 contents.add(content);
								 tclass = 0;
	
								 if(thumbnail.equals("")){
									srcc = video_path + "KKNews-default-thumb.png";
								 }else{
									String imgName = thumbnail.substring(thumbnail.lastIndexOf("/") + 1);
									srcc = video_path + imgName;
									downloadImg.dowImg(thumbnail, save_path , imgName);
								 }
								//return null;//针对嘉定，图文文章调过################
	
								 util.getImg(url, id, video_path);//渲染图片
							}else{
								log.info(id+":视频文章");
								tclass = 1;
								//for(int i = 0; i < list.size(); i ++){
									int i = 0;
									String frameUrl = (String) list.get(i);
									String frameContent = httpRequest.sendGet(frameUrl, "");
									//System.out.println("frameContent:"+frameContent);
									 String videoUrl = frameContent.substring(frameContent.lastIndexOf("mp4 = \"") + 7 , frameContent.lastIndexOf(".mp4\",") +4);
									 String src = video_path + id + "_"+ (i + 1) +".mp4";
									 downloadImg.dowVideo(videoUrl, save_path , id + "_"+ (i + 1) +".mp4");
									 
									 content.put("videopath", src);
									 util.getVideoImg(src, id + "_"+ (i + 1) +".mp4");
									 srcc = src+".jpg";
									 content.put("name", title);
									 content.put("duration", "66");
									 contents.add(content);
								//}
							}
						}
						
						String tsource = StringUtils.enUnicode("互联网");
						String tinjecter= StringUtils.enUnicode("看看新闻");  
						String tcolmunId = columnGuid;
						String tsiteId = site_id;
						
						
						String param = "topicId=" + id + "&thumbnail=" + srcc + "&title=" + StringUtils.enUnicode(title) + "&tsource=" + tsource
								+ "&tclass=" + tclass + "&tcontent=" + StringUtils.enUnicode(contents.toString()) + "&tinjecter=" + tinjecter + "&tcolmunId=" + tcolmunId
								+ "&tsiteId=" + tsiteId + "&tsummary=" + StringUtils.enUnicode(tsummary) + "&tinjectTime=" + newstime+"&auditStatus=check";
						
						//注入到UCGS
						////String aa = httpRequest.sendPost(inject_url, StringUtils.enUnicode( param ));
					
						String UCGS_result =HttpUtil.UrlPostResponse(inject_url, null,   param );
						String code = JSONObject.fromObject(UCGS_result).getString("code");
						System.out.println(new Date().toLocaleString()+"----注入状态：" + UCGS_result);
						log.info("注入状态：" + UCGS_result);
						
						if(code.equals("200")){//注入成功
							//写入本地数据库
							Record record = new Record();
							record.set("topicId", id).set("tcolmunId", tcolmunId).set("tsiteId", tsiteId).set("title", title).set("tclass", tclass).set("tcontent", contents.toString()).set("thumbnail", srcc);
							dbUtils.insertTopic(record);
						}


					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						System.out.println("json格式错误！");
					}
				}
				
		return null;
	}
	
	public void underFrame() {// 下架接口
		String topicId = getPara("topicId");
		String result = HttpUtil.UrlGetResponse(underFrame_url + "?topicId=" + topicId);
		renderText(result);
	}

	public int random(){
		return (int)(10+Math.random()*10);
	}
	
	public void test(){//测试Interface-tools工具类-20170302
		TopicService topicService = new TopicService();
		List<Topic> topics = new ArrayList<>();
		Topic topic = new Topic();
		Topic topic1 = new Topic();

		topic.setTauthor("zyq");
		topic.setCreatetime("zyq");
		topic.setTclass(1);
		topic.setTcontent("zzzz");
		topic.setTfrom("a");
		topic.setThumbnail("nnn");
		topic.setTitle("111");
		topic.setTopicId("0999");
		topic.setTtime("567");

		topic1.setTauthor("zyq0000");
		topic1.setCreatetime("zyq");
		topic1.setTclass(1);
		topic1.setTcontent("zzzz");
		topic1.setTfrom("a");
		topic1.setThumbnail("nnn");
		topic1.setTitle("111");
		topic1.setTopicId("0999");
		topic1.setTtime("567");

		topics.add(topic);
		topics.add(topic1);
		
		renderText(topicService.insert(topics));

	}
public static void main(String[] args) throws IOException {
	URL url = new URL("http://api.kankanews.com/kkrss/ocn/kkapp/kkartical_511.xml");
    // 返回一个 URLConnection 对象，它表示到 URL 所引用的远程对象的连接。
    URLConnection uc = url.openConnection();
    Reader reader = new InputStreamReader(new BufferedInputStream(url.openStream()), "UTF-8");
    int c;
    while ((c = reader.read()) != -1) {
        System.out.println((char) c);
    }
    reader.close();
}
}
