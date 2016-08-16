package com.sf.sfpp.web.common.utils;

import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @描述：时间辅助类
 * @日期：2015年2月6日 下午6:33:21
 * @开发人员： MR.X
 */
public final class DateUtils {
    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATEONLYNOSP = "yyyyMMdd"; // 年/月/日
    public static final String DATE_FORMAT_DATEONLY = "yyyy-MM-dd";
    public static final long DAY_MILLI = 24 * 60 * 60 * 1000; // 一天的MilliSecond

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String START_TIME = " 00:00:00";
    public static final String END_TIME = " 23:59:59";

    public static String DateToStr(Date date) {
        if (date == null) {
            return null;
        }
        return dateToString(date, FORMAT);
    }

    /**
     * 日期转指定格式字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date, String format) {
        if (date == null) {
            return null;
        }
        if (StringUtils.isEmpty(format)) {
            return null;
        }
        String sRet = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            sRet = formatter.format(date).toString();
        } catch (Exception ex) {
            sRet = null;
        }
        return sRet;
    }

    /**
     * 接收形如2000-02-01 01:02:03的时间两个参数转化为long型值后进行比较
     *
     * @param smallTamp
     * @param bigTamp
     * @return
     */
    public static long compareTimestamp(String smallTamp, String bigTamp) {
        if (!StringUtils.isEmpty(smallTamp) && !StringUtils.isEmpty(bigTamp)) {
            try {
                Timestamp t = parseTimestamp(smallTamp, true);
                Timestamp t2 = parseTimestamp(bigTamp, true);
                if (t != null && t2 != null) {
                    return t2.getTime() - t.getTime();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            return 0;
        }
        return 0;
    }

    /**
     * 获取Tiestamp<br/>
     * 输入：2002-02-02 或者 2002-02-02 00:00:00
     *
     * @param timestamp
     *            时间格式或者日期格式
     */
    public static Timestamp parseTimestamp(String timestamp, boolean bStart) {
        if (bStart) {
            return parseTimestamp(timestamp);
        }
        try {
            return Timestamp.valueOf(timestamp);
        } catch (Exception e) {
            try {
                return Timestamp.valueOf(timestamp.trim() + " 23:59:59");
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 根据时间戳字符串获取Tiestamp对象<br/>
     * 输入：2002-02-02 或者 2002-02-02 00:00:00
     *
     * @param timestamp
     *            时间格式或者日期格式
     * @return Timestamp对象
     */
    public static Timestamp parseTimestamp(String timestamp) {
        try {
            return Timestamp.valueOf(timestamp);
        } catch (Exception e) {
            try {
                return Timestamp.valueOf(timestamp.trim() + " 00:00:00");
            } catch (Exception ee) {
                try {
                    return Timestamp.valueOf(timestamp.trim() + ":00");
                } catch (Exception eee) {
                    try {
                        return Timestamp.valueOf(timestamp.trim() + ":00:00");
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    public static long daysBetween(Timestamp t1, Timestamp t2) {
        return (t2.getTime() - t1.getTime()) / DAY_MILLI;
    }

    public static String getNoSpSysDateString() {
        return dateToString(new Date(System.currentTimeMillis()), FORMAT_DATEONLYNOSP);
    }

    public static String getSysDateString() {
        return dateToString(new Date(System.currentTimeMillis()), DATE_FORMAT_DATEONLY);
    }

    public static Timestamp toSqlTimestamp(String sDate) {
        if (sDate == null) {
            return null;
        }
        if (sDate.length() != DateUtils.DATE_FORMAT_DATEONLY.length()) {
            return null;
        }
        return toSqlTimestamp(sDate, DateUtils.DATE_FORMAT_DATEONLY);
    }

    public static Timestamp toSqlTimestamp(String sDate, String sFmt) {
        String temp = null;
        if (sDate == null || sFmt == null) {
            return null;
        }
        if (sDate.length() != sFmt.length()) {
            return null;
        }
        if (sFmt.equals(DateUtils.FORMAT)) {
            temp = sDate.replace('/', '-');
            temp = temp + ".000000000";
        } else if (sFmt.equals(DateUtils.DATE_FORMAT_DATEONLY)) {
            temp = sDate.replace('/', '-');
            temp = temp + " 00:00:00.000000000";
        } else {
            return null;
        }
        // java.sql.Timestamp.value() 要求的格式必须为yyyy-mm-dd hh:mm:ss.fffffffff
        return Timestamp.valueOf(temp);
    }

    public static Timestamp getSysDateTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 将Long型的时间转换为相应的Str类型
     * @param time
     * @return
     */
    public static String timestampToStr(Long time){
        SimpleDateFormat df = new SimpleDateFormat(FORMAT);
        Date date = new Date(time);
        return DateToStr(date);

    }

    public static long getTime(Date date){
        if(date == null){
            return 0;
        }
        return date.getTime();
    }

    /**
     * 计算date时间的前后多少分钟,并转为long型
     * @param date 输入时间
     * @param min 分钟数，负数往前，正数往后
     * @return
     */
    public static long getDiffDate(Date date,int min)throws Exception{
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, min);
        return cal.getTime().getTime();
    }


    /**
     * 对开始时间与结束时间进行检查是否规范
     * @param time
     * @return
     */
    public static Date checkTime(String time) {
        return checkTime(time,new Date());
    }


    /**
     * 时间出错时，会默认使用 date 作为查询的时间
     * @param time
     * @param defaultDate
     * @return
     */
    public static Date checkTime(String time, Date defaultDate) {
        Date result = new Date();
        if (!StringUtils.isEmpty(time)) {
            try {
                result = DateUtils.parseTimestamp(time);
                /*System.out.println("checkTime:======>" + time);*/
            } catch (Exception e) {
                result = defaultDate;
            }
        } else {
            result = defaultDate;
        }
        return result;
    }


    /**
     * 字符串日期转换为查询的截止日期
     *
     * @param dateStr
     * @param endOrStart yyyy-MM-dd
     * @return
     */
    public static Date str2time(String dateStr) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            return dateTimeFormat.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 得到n分钟前时间
     *
     * @param minute 分钟数
     * @return Date
     */
    public static Date getTimeBeforeMinutes(int minute) {
        return getBeforeTime(minute,Calendar.MINUTE);
    }

    private static Date getBeforeTime(int count,int timeUnit) {
        Calendar cl = Calendar.getInstance();
        cl.add(timeUnit,0-count);
        return cl.getTime();
    }

    
    /**
     * 得到n小时前时间
     *
     * @param hour 小时数
     * @return Date
     */
    public static Date getTimeBeforeHours(int hour) {
        return getBeforeTime(hour,Calendar.HOUR);
    }


    /**
     * 得到n天前时间
     * @param Day
     * @return
     */
    public static Date getTimeBeforeDays(int day) {
        return getBeforeTime(day ,Calendar.DATE);
    }



    /**
     * 获得从今天开始的N天前的日期时间列表
     * @param day
     * @param sFmt
     * @return
     */
    public static List<String> getDateListBeforeDay(int day,String sFmt) {

        List<String> dateList = new ArrayList<String>();

        for(int i=day;i>=0;i--){
            Date dates = getTimeBeforeDays(i);

            String dateString = dateToString(dates,sFmt);

            dateList.add(dateString);
        }

        return dateList;
    }


    /**
     * 将字符串转换成Date
     * @author 591791
     * @param dateStr
     * @param patten
     * @return
     */
    public static Date praseDate(String dateStr, String patten) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(patten);
        try {
            return dateTimeFormat.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 按照指定格式将string转Date
     * @param dateString
     * @param sFmt
     * @return
     */
    public static Date strToDate(String dateString,String sFmt){

        SimpleDateFormat sdf = new SimpleDateFormat(sFmt);

        try {
            Date date = sdf.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }


    /**
     * str 转Calendar
     * @param dateString
     * @param sFmt
     * @return
     */
    public static Calendar strToCalendar(String dateString,String sFmt){
        SimpleDateFormat sdf= new SimpleDateFormat(sFmt);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 将日期String数据转换为另一种string
     * @param dateString
     * @param sFmt
     * @return
     */
    public static String strToStr(String dateString,String sFmt){
        Date beginD = DateUtils.strToDate(dateString,sFmt);
        String dString = DateUtils.dateToString(beginD,sFmt);

        return dString;

    }

    /**
     * 用新的format 进行date to date 的转化
     * @param format
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date dateToDate(String format,Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String target = sdf.format(date);
        try {
            return sdf.parse(target);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
        
    }
    
    /**
     * 对时间进行 yyyy-MM-dd 00:00:00 format
     * @param date
     * @return
     */
    public static Date getTimeZero(Date date){
        return dateToDate(DATE_FORMAT_DATEONLY + START_TIME , date);
    }
}