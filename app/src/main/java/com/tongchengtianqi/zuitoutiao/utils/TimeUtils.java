package com.tongchengtianqi.zuitoutiao.utils;


import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    /**
     * 格式化创建时间
     * @param
     * @return string
     */

    public static String getDuration(int duration) {
        int min = (duration) / 60;
        int sec = (duration) % 60;
        return getType(min) + ":" + getType(sec);
    }
    public static String getType(int time) {
        return time<10 ? "0" + time : String.valueOf(time);
    }
    //怎么让以00:00这样的时间格式返回

    public static String createTimeToString(long millionsTime){
        long l = System.currentTimeMillis();
        long timeToNow = l - millionsTime;
        if(timeToNow < 0)timeToNow = 0;
        if(timeToNow < 3600000){
            long minutes = timeToNow / 60000;
            return minutes == 0 ? "刚刚": minutes+"分钟前";
        }else if(timeToNow < 86400000){
            long hours = timeToNow / 3600000;
            return hours+"小时前";
        }else if(timeToNow <= (86400000 * 3)){
            long days = timeToNow / 86400000;
            return days+"天前";

        }else {
            return "";
        }
    }

    /**
     * 将long是间值  转为  2017.5.14
     * @param millions
     * @return
     */
    public static String longTimeToString(long millions){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millions);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day  = calendar.get(Calendar.DAY_OF_MONTH);
        return year+"-"+((month+1) < 10 ? "0"+(month +1):(month +1))+"-"+(day < 10 ? "0"+day : day) ;
    }


    /**
     * 获取系统当前时间 格式 2017-04-01 17:30:30 
     */
    public static String getAll() {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        return localTime.format("%Y-%m-%d %H:%M:%S");
    }

    /**
     * 获取系统当前日期 格式 2017-04-01 
     */
    public static String getDate() {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        return localTime.format("%Y-%m-%d");
    }

    /**
     * 获取系统当前时间 格式 17:30:30 
     *
     * @return 
     * @param create_time
     */
    public static String getTime(long create_time) {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        return localTime.format("%H:%M:%S");
    }

    /**
     * 获取系统当前年份 格式 2017 
     *
     * @return 
     */
    public static String getYear() {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        return localTime.format("%Y");
    }

    /**
     * 获取系统当前月份 格式 03 
     */
    public static String getMouth() {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        return localTime.format("%m");
    }

    /**
     *  获取今天是什么日子 格式 02 
     */
    public static String getDay() {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        return localTime.format("%d");
    }

    /**
     * 获取系统当前小时 格式 17 
     */
    public static String getHour() {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        return localTime.format("%H");
    }

    /**
     * 获取系统当前分钟 格式 59 
     * @param duration
     */
    public static String getMinute(String duration) {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        return localTime.format("%M");
    }

    /**
     * 获取系统当前秒数 30 
     */
    public static String getSeconds() {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        return localTime.format("%S");
    }

    /**
     * 时间戳转时间 
     */
    public static String longToTime(String s) {
        long time = Long.parseLong(s);
        Date date = new Date(time * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
