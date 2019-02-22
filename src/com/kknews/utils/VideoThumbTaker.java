package com.kknews.utils;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;


public class VideoThumbTaker
{
    protected String ffmpegApp;

    public VideoThumbTaker(String ffmpegApp)
    {
        this.ffmpegApp = ffmpegApp;
    }

    @SuppressWarnings("unused")
    /****
     * 获取指定时间内的图片
     * @param videoFilename:视频路径
     * @param thumbFilename:图片保存路径
     * @param width:图片长
     * @param height:图片宽
     * @param hour:指定时
     * @param min:指定分
     * @param sec:指定秒
     * @throws IOException
     * @throws InterruptedException
     */
    public void getThumb(String videoFilename, String thumbFilename, int width,
            int height, int hour, int min, float sec) throws IOException,
            InterruptedException
    {
        ProcessBuilder processBuilder = new ProcessBuilder(ffmpegApp, "-y",
                "-i", videoFilename, "-vframes", "1", "-ss", hour + ":" + min
                        + ":" + sec, "-f", "mjpeg", "-s", width + "*" + height,
                "-an", thumbFilename);

        Process process = processBuilder.start();

        InputStream stderr = process.getErrorStream();
        InputStreamReader isr = new InputStreamReader(stderr);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null)
            ;
        process.waitFor();
        
        if(br != null)
            br.close();
        if(isr != null)
            isr.close();
        if(stderr != null)
            stderr.close();
    }

    public static void main(String[] args)
    {
        VideoThumbTaker videoThumbTaker = new VideoThumbTaker("C:\\ffmpeg.exe");
        try
        {
            videoThumbTaker.getThumb("c:/chaorenaishangbianfuxia.mp4", "C:\\thumbTest.png",    276, 168, 0, 0, 9);
            System.out.println("over");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

/**
*@author zhuyq@zensvision.com
*@date   2018年6月20日-下午5:44:52
*/