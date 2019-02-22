package com.kknews.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.optional.depend.constantpool.StringCPInfo;


public class Utils {

	
	public String formatTime(String time) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.parseLong(time));
		return formatter.format(calendar.getTime());
	}
	
	
	public String formatTimes(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	// 执行Linux图片渲染命令
	public void getImg(String url, String id, String video_path) {
		try {
			Properties props=System.getProperties(); //获得系统属性集    
			String osName = props.getProperty("os.name");
			System.out.println(osName);
			
			String commands = "webcasper get -url " + url + "  -to  " + video_path + id;
			if(osName.indexOf("Windows") != -1) {//Windows系统
				video_path = "D:/apache-tomcat-7.0.42/webapps/kkNews/";
				commands = "d:/services/opt/webCasper/bin/webCasper.bat get -url " + url.split("\\?")[0] + "  -to  " + video_path + id;	
			}
			
			System.out.println("##################shell:"+commands);
			Process process = Runtime.getRuntime().exec(commands);

			InputStreamReader ir = new InputStreamReader(process.getInputStream());

			BufferedReader input = new BufferedReader(ir);
			String line;

			while ((line = input.readLine()) != null) {
				// System.out.println("line&&&&&:"+line);
				line.equals(line);
			}

			ir.close();
			input.close();
			process.destroy();
			if(osName.indexOf("Windows") != -1) {//Windows系统
				UploadFile("10.27.155.5", "zftp", "zftp", "D:/apache-tomcat-7.0.42/webapps/kkNews/", "/media/imgcache/kknews", id+".png");	
			}
		} catch (java.io.IOException e) {
			System.err.println("IOException " + e.getMessage());
		}
	}
	
	//获取视频帧
	public void getVideoImg(String src, String Name) {
		Properties props=System.getProperties(); //获得系统属性集    
		String osName = props.getProperty("os.name");
		System.out.println(osName);
		if(osName.indexOf("Windows") != -1) {//Windows系统
			src = src.replace("/media/imgcache/kknews/", "D:/apache-tomcat-7.0.42/webapps/kkNews/");
			VideoThumbTaker videoThumbTaker = new VideoThumbTaker("C:\\ffmpeg.exe");
	        try
	        {
	        		System.out.println(src);
	            videoThumbTaker.getThumb(src, src+".jpg",    276, 168, 0, 0, 9);
	            System.out.println("over");
	            UploadFile("10.27.155.5", "zftp", "zftp", "D:/apache-tomcat-7.0.42/webapps/kkNews/", "/media/imgcache/kknews", Name+".jpg");
	        } catch (Exception e)
	        {
	            e.printStackTrace();
	        }
		}else {//Linux系统
			try {
				String commands = "sparkavc -ss 00:00:02 -i "+src+" -f image2 -vframes 1 -s 276x168 -y "+src+".jpg" ;
				
				//sparkavc -ss 00:00:02 -i ombRaider2-MPEG2-TS.mp4 -f image2 -vframes 1 -s 276x168 -y TombRaider2-MPEG2-TS.mp4.jpg 
				System.out.println("##################shell:"+commands);
				Process process = Runtime.getRuntime().exec(commands);

				InputStreamReader ir = new InputStreamReader(process.getInputStream());

				BufferedReader input = new BufferedReader(ir);
				String line;

				while ((line = input.readLine()) != null) {
					// System.out.println("line&&&&&:"+line);
					line.equals(line);
				}

				ir.close();
				input.close();
				process.destroy();
			} catch (java.io.IOException e) {
				System.err.println("IOException " + e.getMessage());
			}
		}
	}
	
	//删除文件
		public void delete(String src) {
			try {
				String commands = "rm -rf "+src;
				
				//"sparkavc -ss 00:00:02 -i "+TombRaider2-MPEG2-TS.mp4+" -f image2 -vframes 1 -s 276x168 -y "+TombRaider2-MPEG2-TS.mp4.jpg 
				System.out.println("##################shell:"+commands);
				Process process = Runtime.getRuntime().exec(commands);

				InputStreamReader ir = new InputStreamReader(process.getInputStream());

				BufferedReader input = new BufferedReader(ir);
				String line;

				while ((line = input.readLine()) != null) {
					// System.out.println("line&&&&&:"+line);
					line.equals(line);
				}

				ir.close();
				input.close();
				process.destroy();
			} catch (java.io.IOException e) {

				System.err.println("IOException " + e.getMessage());

			}
		}

	// 匹配指定标签的指定属性值
	public List<String> match(String source, String element, String attr) {
		List<String> result = new ArrayList<String>();
		String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>";
		Matcher m = Pattern.compile(reg).matcher(source);
		while (m.find()) {
			String r = m.group(1);
			result.add(r);
		}
		return result;
	}
	
	public static boolean UploadFile(String ftpPath, String ftpUser, String ftpPwd, String collectCacheFilePath, String filePath, String fileName) {
        FTPClient ftpClient = new FTPClient(); 
        FileInputStream fis = null; 
        boolean b = true;

        try { 
            ftpClient.connect(ftpPath); 
            ftpClient.login(ftpUser, ftpPwd);
            //ftpClient.enterLocalActiveMode();    //主动模式
            ftpClient.enterLocalPassiveMode(); //被动模式
            System.out.println("FTP:"+ftpPath+"|FTPUSER:"+ftpUser+"|FTPPWD:"+ftpPwd); 
            System.out.println("fileName:"+collectCacheFilePath+fileName+"|filePath:"+filePath);
            File srcFile = new File(collectCacheFilePath+fileName); 
            fis = new FileInputStream(srcFile); 
            //设置上传目录 
            String[] dirs = filePath.split("/");  
            ftpClient.changeWorkingDirectory("/");  
              
            //按顺序检查目录是否存在，不存在则创建目录  
            for(int i=1; dirs!=null&&i<dirs.length; i++) {  
                if(!ftpClient.changeWorkingDirectory(dirs[i])) {  
                    if(ftpClient.makeDirectory(dirs[i])) {  
                        if(!ftpClient.changeWorkingDirectory(dirs[i])) {  
                        }  
                    }else {  
                    }  
                }  
            }  
            
            ftpClient.setBufferSize(1024*1024*100); 
            ftpClient.setControlEncoding("UTF-8"); 
            //设置文件类型（二进制） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            b = ftpClient.storeFile(fileName, fis); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            b = false;
            System.out.println("FTP client error！");
            throw new RuntimeException("FTP客户端出错！", e); 
        } finally { 
            IOUtils.closeQuietly(fis); 
            try { 
                ftpClient.disconnect(); 
                if(b){
                	System.out.println("media file upload success!");
                }else{
                	System.out.println("media file upload failed!");
                }
            } catch (IOException e) { 
                e.printStackTrace(); 
                System.out.println("close FTP client error！");
                b = false;
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            } 
        } 
        return b;
    } 

	public static void main(String[] args) {// 12471346813
		Properties props=System.getProperties(); //获得系统属性集    
		String osName = props.getProperty("os.name");
		System.out.println(osName);
	}
}
