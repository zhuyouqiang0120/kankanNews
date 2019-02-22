package com.kknews.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


import net.sf.json.JSONObject;

/**
 * 
 * @author zyq
 * @e-mail zhuyq@zensvision.com
 * @date 2016年9月23日 下午5:37:19
 */

public class Download {
	private static Logger log = Logger.getLogger(Download.class);
	static HttpRequest httpRequest = new HttpRequest();
	private static Utils utils = new Utils();

	// 下载图片
	public String dowImg(String titlepic, String path, String Name) {
		String pathName = path+Name;
		System.out.println(titlepic+"---"+pathName);
		File file = new File(pathName);
		if (!file.exists()) {
			OutputStream os = null;
			String str = titlepic;
			InputStream is = null;
			HttpURLConnection connection = null;
			URL userver = null;

			try {
				userver = new URL(str);
				connection = (HttpURLConnection) userver.openConnection();
				connection.connect();
				is = connection.getInputStream();
				os = new FileOutputStream(file);
				int b = is.read();
				while (b != -1) {
					os.write(b);
					b = is.read();
				}
				is.close();
				os.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		Properties props=System.getProperties(); //获得系统属性集    
		String osName = props.getProperty("os.name");
		System.out.println(osName);
		if(osName.indexOf("Windows") != -1) {//Windows系统
			utils.UploadFile("10.27.155.5", "zftp", "zftp", path, "/media/imgcache/kknews", Name);
		}
		
		return pathName;
	}

	// 下载视频
	public String dowVideo(String url, String path, String Name) throws Exception {
		url = url.replace("https", "http");
		String pathName = path+Name;
		System.out.println(url+"---"+pathName);
		File file = new File(pathName);
		if (!file.exists()) {
			URL ul = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) ul.openConnection();
			BufferedInputStream bi = new BufferedInputStream(conn.getInputStream());
			FileOutputStream bs = new FileOutputStream(pathName);
			System.out.println("文件大约：" + (conn.getContentLength() / 1024) + "K");
			log.info("文件大约：" + (conn.getContentLength() / 1024) + "K");
			byte[] by = new byte[1024];
			int len = 0;
			while ((len = bi.read(by)) != -1) {
				bs.write(by, 0, len);
			}
			bs.close();
			bi.close();
		}

		Properties props=System.getProperties(); //获得系统属性集    
		String osName = props.getProperty("os.name");
		System.out.println(osName);
		if(osName.indexOf("Windows") != -1) {//Windows系统
			utils.UploadFile("10.27.155.5", "zftp", "zftp", path, "/media/imgcache/kknews", Name);
		}
		return pathName;
	}

	public static void main(String[] args) throws Exception {

		

	}

	// 匹配指定标签的指定属性值
	public static List<String> match(String source, String element, String attr) {
		List<String> result = new ArrayList<String>();
		String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>";
		Matcher m = Pattern.compile(reg).matcher(source);
		while (m.find()) {
			String r = m.group(1);
			result.add(r);
		}
		return result;
	}

}
