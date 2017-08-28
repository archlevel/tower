package com.tower.service.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * 时间工具类
 * <p>
 * 只会包装<b>简单的，利用率高</b>的公共工具方法，复杂的自己去实现，勿将工具类搞的过于庞大<br/>
 * 定义新方法时，在方法签名上需要明确工具方法的用途，该类中支持的类型只应该有int、long、Date、String，勿引入过多类型
 * <p>
 * 该工具类中方法分为以下几类<br/>
 * 1.对时间戳的转换<br/>
 * 2.对date、long的format<br/>
 * 3.含有一些计算的方法<br/>
 * 
 * @author alex.zhu
 * 
 */
public class DateUtil {

    public static String yyyy_MM_dd = "yyyy-MM-dd";
    public static String MM_dd_yyyy = "MM/dd/yyyy";
    private static String MM_dd_yyyy_HH_mm = "MM/dd/yyyy HH:mm";
    private static String MM_dd_yyyy_HH_mm_ss = "MM/dd/yyyy HH:mm:ss";
    public static String yyyyMMdd = "yyyyMMdd", yyyyMMddHHmm = "yyyyMMddHHmm",
            yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static String yyyy_MM_dd_HH_mm_SS = "yyyy-MM-dd HH:mm:ss";
    
    private static final int[] dayArray =
            new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private int weeks = 0;
    private int MaxDate;// 一月最大天数
    private int MaxYear;// 一年最大天数

    /**
     * 判断当前时间是否在 开始日期跟结束日期之间
     * 
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public static boolean between(String startTime, String endTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_HH_mm_SS);
        Date start = sdf.parse(startTime);
        Date end = sdf.parse(endTime);
        Date nowDate = new Date();// test >= start && test <= end
        return ((nowDate.equals(start) || nowDate.after(start)) && (nowDate.equals(end) || nowDate
                .before(end)));
    }

    public static String transformDate(String date, String old_pattern, String new_pattern) {
        return getDate(getDate(date, old_pattern), new_pattern);
    }

    public static Date getDate(String date, String patten) {
        SimpleDateFormat sf = new SimpleDateFormat(patten);
        try {
            return sf.parse(date);
        } catch (ParseException e) {}
        return null;
    }

    public static String getDate(Date date, String patten) {
        SimpleDateFormat sf = new SimpleDateFormat(patten);
        return sf.format(date);
    }

    public static Calendar getCalendar() {
        return GregorianCalendar.getInstance();
    }

    /**
     * @return String
     */
    public static String getDateMilliFormat() {
        Calendar cal = Calendar.getInstance();
        return getDateMilliFormat(cal);
    }

    /**
     * @param cal
     * @return String
     */
    public static String getDateMilliFormat(Calendar cal) {
        String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
        return getDateFormat(cal, pattern);
    }

    /**
     * @param date
     * @return String
     */
    public static String yyyy_MM_dd$HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss,SSS";
    public static String getDateMilliFormat(Date date) {
        return getDateFormat(date, yyyy_MM_dd$HH_mm_ss_SSS);
    }

    /**
     * @param strDate
     * @return java.util.Calendar
     */
    public static Calendar parseCalendarMilliFormat(String strDate) {
        return parseCalendarFormat(strDate, yyyy_MM_dd$HH_mm_ss_SSS);
    }

    /**
     * @param strDate
     * @return java.util.Date
     */
    public static Date parseDateMilliFormat(String strDate) {
        return parseDateFormat(strDate, yyyy_MM_dd$HH_mm_ss_SSS);
    }

    /**
     * @return String
     */
    public static String getDateSecondFormat() {
        Calendar cal = Calendar.getInstance();
        return getDateSecondFormat(cal);
    }

    /**
     * @param cal
     * @return String
     */
    public static String getDateSecondFormat(Calendar cal) {
        String pattern = yyyy_MM_dd_HH_mm_SS;
        return getDateFormat(cal, pattern);
    }

    /**
     * @param date
     * @return String
     */
    public static String getDateSecondFormat(Date date) {
        String pattern = yyyy_MM_dd_HH_mm_SS;
        return getDateFormat(date, pattern);
    }

    /**
     * @param strDate
     * @return java.util.Calendar
     */
    public static Calendar parseCalendarSecondFormat(String strDate) {
        String pattern = yyyy_MM_dd_HH_mm_SS;
        return parseCalendarFormat(strDate, pattern);
    }

    /**
     * @param strDate
     * @return java.util.Date
     */
    public static Date parseDateSecondFormat(String strDate) {
        String pattern = yyyy_MM_dd_HH_mm_SS;
        return parseDateFormat(strDate, pattern);
    }

    /**
     * @return String
     */
    public static String getDateMinuteFormat() {
        Calendar cal = Calendar.getInstance();
        return getDateMinuteFormat(cal);
    }

    /**
     * @param cal
     * @return String
     */
    public static String yyyy_MM_dd$HH_mm = "yyyy-MM-dd HH:mm";
    public static String getDateMinuteFormat(Calendar cal) {
        return getDateFormat(cal, yyyy_MM_dd$HH_mm);
    }

    /**
     * @param date
     * @return String
     */
    public static String getDateMinuteFormat(Date date) {
        return getDateFormat(date, yyyy_MM_dd$HH_mm);
    }

    /**
     * @param strDate
     * @return java.util.Calendar
     */
    public static Calendar parseCalendarMinuteFormat(String strDate) {
        return parseCalendarFormat(strDate, yyyy_MM_dd$HH_mm);
    }

    /**
     * @param strDate
     * @return java.util.Date
     */
    public static Date parseDateMinuteFormat(String strDate) {
        return parseDateFormat(strDate, yyyy_MM_dd$HH_mm);
    }

    /**
     * @param patten
     * @return java.lang.String
     */
    public static String getDate(String patten) {
        SimpleDateFormat sf = new SimpleDateFormat(patten, Locale.US);
        return sf.format(new Date());
    }

    /**
     * @return String
     */
    public static String getDateDayFormat() {
        Calendar cal = Calendar.getInstance();
        return getDateDayFormat(cal);
    }

    /**
     * @param cal
     * @return String
     */
    public static String getDateDayFormat(Calendar cal) {
        String pattern = yyyy_MM_dd;
        return getDateFormat(cal, pattern);
    }

    /**
     * @param date
     * @return String
     */
    public static String getDateDayFormat(Date date) {
        String pattern = yyyy_MM_dd;
        return getDateFormat(date, pattern);
    }

    public static String format(long ms) {// 将毫秒数换算成x天x时x分x秒x毫秒
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day;
        String strHour = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        return /* strDay + " " + */strHour + ":" + strMinute + ":" + strSecond/*
                                                                               * + " " +
                                                                               * strMilliSecond
                                                                               */;
    }

    public static String formatLongToTime(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;

        second = l.intValue() / 1000;

        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        return (hour + "小时" + minute + "分钟" + second + "秒");
    }

    public static long getUnixTimestamp() {
        return toSecond(Calendar.getInstance().getTime());
    }

    public static long toSecond(Date date) {
        return date.getTime() / 1000;
    }

    public static long toMilliSecond(Date date) {
        return date.getTime();
    }

    /**
     * @param strDate
     * @return java.util.Calendar
     */
    public static Calendar parseCalendarDayFormat(String strDate) {
        String pattern = yyyy_MM_dd;
        return parseCalendarFormat(strDate, pattern);
    }

    /**
     * @param strDate
     * @return java.util.Date
     */
    public static Date parseDateDayFormat(String strDate) {
        String pattern = yyyy_MM_dd;
        return parseDateFormat(strDate, pattern);
    }

    /**
     * @return String
     */
    public static String getDateFileFormat() {
        Calendar cal = Calendar.getInstance();
        return getDateFileFormat(cal);
    }

    /**
     * @param cal
     * @return String
     */
    public static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd_HH-mm-ss";
    public static String getDateFileFormat(Calendar cal) {
        return getDateFormat(cal, yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * @param date
     * @return String
     */
    public static String getDateFileFormat(Date date) {
        return getDateFormat(date, yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * @param strDate
     * @return java.util.Calendar
     */
    public static Calendar parseCalendarFileFormat(String strDate) {
        return parseCalendarFormat(strDate, yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * @param strDate
     * @return java.util.Date
     */
    public static Date parseDateFileFormat(String strDate) {
        return parseDateFormat(strDate, yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * @return String
     */
    public static String getDateW3CFormat() {
        Calendar cal = Calendar.getInstance();
        return getDateW3CFormat(cal);
    }

    /**
     * @param cal
     * @return String
     */
    public static String getDateW3CFormat(Calendar cal) {
        String pattern = yyyy_MM_dd_HH_mm_SS;
        return getDateFormat(cal, pattern);
    }

    /**
     * @param date
     * @return String
     */
    public static String getDateW3CFormat(Date date) {
        String pattern = yyyy_MM_dd_HH_mm_SS;
        return getDateFormat(date, pattern);
    }

    /**
     * @param strDate
     * @return java.util.Calendar
     */
    public static Calendar parseCalendarW3CFormat(String strDate) {
        String pattern = yyyy_MM_dd_HH_mm_SS;
        return parseCalendarFormat(strDate, pattern);
    }

    /**
     * @param strDate
     * @return java.util.Date
     */
    public static Date parseDateW3CFormat(String strDate) {
        String pattern = yyyy_MM_dd_HH_mm_SS;
        return parseDateFormat(strDate, pattern);
    }

    /**
     * @param cal
     * @return String
     */
    public static String getDateFormat(Calendar cal) {
        String pattern = yyyy_MM_dd_HH_mm_SS;
        return getDateFormat(cal, pattern);
    }

    /**
     * @param date
     * @return String
     */
    public static String getDateFormat(Date date) {
        String pattern = yyyy_MM_dd_HH_mm_SS;
        return getDateFormat(date, pattern);
    }

    /**
     * @param strDate
     * @return java.util.Calendar
     */
    public static Calendar parseCalendarFormat(String strDate) {
        String pattern = yyyy_MM_dd_HH_mm_SS;
        return parseCalendarFormat(strDate, pattern);
    }

    /**
     * @param strDate
     * @return java.util.Date
     */
    public static Date parseDateFormat(String strDate) {
        String pattern = yyyy_MM_dd_HH_mm_SS;
        return parseDateFormat(strDate, pattern);
    }

    /**
     * @param cal
     * @param pattern
     * @return String
     */
    public static String getDateFormat(Calendar cal, String pattern) {
        return getDateFormat(cal.getTime(), pattern);
    }

    /**
     * @param date
     * @param pattern
     * @return String
     */
    public static String getDateFormat(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        String str = null;
        sdf.applyPattern(pattern);
        str = sdf.format(date);
        return str;
    }

    /**
     * @param strDate
     * @param pattern
     * @return java.util.Calendar
     */
    public static Calendar parseCalendarFormat(String strDate, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        Calendar cal = null;
        sdf.applyPattern(pattern);
        try {
            sdf.parse(strDate);
            cal = sdf.getCalendar();
        } catch (Exception e) {}
        return cal;

    }

    /**
     * @param strDate
     * @param pattern
     * @return java.util.Date
     */
    public static Date parseDateFormat(String strDate, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        Date date = null;
        sdf.applyPattern(pattern);
        try {
            date = sdf.parse(strDate);
        } catch (Exception e) {}
        return date;

    }

    public static int getLastDayOfMonth(int month) {
        if (month < 1 || month > 12) {
            return -1;
        }
        int retn = 0;
        if (month == 2) {
            if (isLeapYear()) {
                retn = 29;
            } else {
                retn = dayArray[month - 1];
            }
        } else {
            retn = dayArray[month - 1];
        }
        return retn;
    }

    public static boolean isLeapYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    public static boolean isLeapYear(int year) {
        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年 3.能被4整除同时能被100整除则不是闰年
         */
        if ((year % 400) == 0)
            return true;
        else if ((year % 4) == 0) {
            if ((year % 100) == 0)
                return false;
            else
                return true;
        } else
            return false;
    }

    /**
     * 判断指定日期的年份是否是闰年
     * 
     * @param date 指定日期。
     * @return 是否闰年
     */
    public static boolean isLeapYear(Date date) {
        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年 3.能被4整除同时能被100整除则不是闰年
         */
        // int year = date.getYear();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        int year = gc.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    public static boolean isLeapYear(Calendar gc) {
        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年 3.能被4整除同时能被100整除则不是闰年
         */
        int year = gc.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    /**
     * 得到指定日期的前一个工作日
     * 
     * @param date 指定日期。
     * @return 指定日期的前一个工作日
     */
    public static Date getPreviousWeekDay(Date date) {
        /**
         * 详细设计： 1.如果date是星期日，则减3天 2.如果date是星期六，则减2天 3.否则减1天
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return getPreviousWeekDay(gc);
    }

    public static Date getPreviousWeekDay(java.util.Calendar gc) {
        {
            /**
             * 详细设计： 1.如果date是星期日，则减3天 2.如果date是星期六，则减2天 3.否则减1天
             */
            switch (gc.get(Calendar.DAY_OF_WEEK)) {
                case (Calendar.MONDAY):
                    gc.add(Calendar.DATE, -3);
                    break;
                case (Calendar.SUNDAY):
                    gc.add(Calendar.DATE, -2);
                    break;
                default:
                    gc.add(Calendar.DATE, -1);
                    break;
            }
            return gc.getTime();
        }
    }

    /**
     * 得到指定日期的后一个工作日
     * 
     * @param date 指定日期。
     * @return 指定日期的后一个工作日
     */
    public static Date getNextWeekDay(Date date) {
        /**
         * 详细设计： 1.如果date是星期五，则加3天 2.如果date是星期六，则加2天 3.否则加1天
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        switch (gc.get(Calendar.DAY_OF_WEEK)) {
            case (Calendar.FRIDAY):
                gc.add(Calendar.DATE, 3);
                break;
            case (Calendar.SATURDAY):
                gc.add(Calendar.DATE, 2);
                break;
            default:
                gc.add(Calendar.DATE, 1);
                break;
        }
        return gc.getTime();
    }

    public static Calendar getNextWeekDay(Calendar gc) {
        /**
         * 详细设计： 1.如果date是星期五，则加3天 2.如果date是星期六，则加2天 3.否则加1天
         */
        switch (gc.get(Calendar.DAY_OF_WEEK)) {
            case (Calendar.FRIDAY):
                gc.add(Calendar.DATE, 3);
                break;
            case (Calendar.SATURDAY):
                gc.add(Calendar.DATE, 2);
                break;
            default:
                gc.add(Calendar.DATE, 1);
                break;
        }
        return gc;
    }

    /**
     * 取得指定日期的下一个月的最后一天
     * 
     * @param date 指定日期。
     * @return 指定日期的下一个月的最后一天
     */
    public static Date getLastDayOfNextMonth(Date date) {
        /**
         * 详细设计： 1.调用getNextMonth设置当前时间 2.以1为基础，调用getLastDayOfMonth
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.setTime(DateUtil.getNextMonth(gc.getTime()));
        gc.setTime(DateUtil.getLastDayOfMonth(gc.getTime()));
        return gc.getTime();
    }

    /**
     * 取得指定日期的下一个星期的最后一天
     * 
     * @param date 指定日期。
     * @return 指定日期的下一个星期的最后一天
     */
    public static Date getLastDayOfNextWeek(Date date) {
        /**
         * 详细设计： 1.调用getNextWeek设置当前时间 2.以1为基础，调用getLastDayOfWeek
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.setTime(DateUtil.getNextWeek(gc.getTime()));
        gc.setTime(DateUtil.getLastDayOfWeek(gc.getTime()));
        return gc.getTime();
    }

    /**
     * 取得指定日期的下一个月的第一天
     * 
     * @param date 指定日期。
     * @return 指定日期的下一个月的第一天
     */
    public static Date getFirstDayOfNextMonth(Date date) {
        /**
         * 详细设计： 1.调用getNextMonth设置当前时间 2.以1为基础，调用getFirstDayOfMonth
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.setTime(DateUtil.getNextMonth(gc.getTime()));
        gc.setTime(DateUtil.getFirstDayOfMonth(gc.getTime()));
        return gc.getTime();
    }

    public static Calendar getFirstDayOfNextMonth(Calendar gc) {
        /**
         * 详细设计： 1.调用getNextMonth设置当前时间 2.以1为基础，调用getFirstDayOfMonth
         */
        gc.setTime(DateUtil.getNextMonth(gc.getTime()));
        gc.setTime(DateUtil.getFirstDayOfMonth(gc.getTime()));
        return gc;
    }

    /**
     * 取得指定日期的下一个星期的第一天
     * 
     * @param date 指定日期。
     * @return 指定日期的下一个星期的第一天
     */
    public static Date getFirstDayOfNextWeek(Date date) {
        /**
         * 详细设计： 1.调用getNextWeek设置当前时间 2.以1为基础，调用getFirstDayOfWeek
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.setTime(DateUtil.getNextWeek(gc.getTime()));
        gc.setTime(DateUtil.getFirstDayOfWeek(gc.getTime()));
        return gc.getTime();
    }

    public static Calendar getFirstDayOfNextWeek(Calendar gc) {
        /**
         * 详细设计： 1.调用getNextWeek设置当前时间 2.以1为基础，调用getFirstDayOfWeek
         */
        gc.setTime(DateUtil.getNextWeek(gc.getTime()));
        gc.setTime(DateUtil.getFirstDayOfWeek(gc.getTime()));
        return gc;
    }

    /**
     * 取得指定日期的下一个月
     * 
     * @param date 指定日期。
     * @return 指定日期的下一个月
     */
    public static Date getNextMonth(Date date) {
        /**
         * 详细设计： 1.指定日期的月份加1
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.add(Calendar.MONTH, 1);
        return gc.getTime();
    }

    public static Calendar getNextMonth(Calendar gc) {
        /**
         * 详细设计： 1.指定日期的月份加1
         */
        gc.add(Calendar.MONTH, 1);
        return gc;
    }

    /**
     * 取得指定日期的下一天
     * 
     * @param date 指定日期。
     * @return 指定日期的下一天
     */
    public static Date getNextDay(Date date) {
        /**
         * 详细设计： 1.指定日期加1天
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.add(Calendar.DATE, 1);
        return gc.getTime();
    }

    public static Calendar getNextDay(Calendar gc) {
        /**
         * 详细设计： 1.指定日期加1天
         */
        gc.add(Calendar.DATE, 1);
        return gc;
    }

    /**
     * 取得指定日期的下一个星期
     * 
     * @param date 指定日期。
     * @return 指定日期的下一个星期
     */
    public static Date getNextWeek(Date date) {
        /**
         * 详细设计： 1.指定日期加7天
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.add(Calendar.DATE, 7);
        return gc.getTime();
    }

    public static Calendar getNextWeek(Calendar gc) {
        /**
         * 详细设计： 1.指定日期加7天
         */
        gc.add(Calendar.DATE, 7);
        return gc;
    }

    /**
     * 取得指定日期的所处星期的最后一天
     * 
     * @param date 指定日期。
     * @return 指定日期的所处星期的最后一天
     */
    public static Date getLastDayOfWeek(Date date) {
        /**
         * 详细设计： 1.如果date是星期日，则加6天 2.如果date是星期一，则加5天 3.如果date是星期二，则加4天 4.如果date是星期三，则加3天
         * 5.如果date是星期四，则加2天 6.如果date是星期五，则加1天 7.如果date是星期六，则加0天
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        switch (gc.get(Calendar.DAY_OF_WEEK)) {
            case (Calendar.SUNDAY):
                gc.add(Calendar.DATE, 6);
                break;
            case (Calendar.MONDAY):
                gc.add(Calendar.DATE, 5);
                break;
            case (Calendar.TUESDAY):
                gc.add(Calendar.DATE, 4);
                break;
            case (Calendar.WEDNESDAY):
                gc.add(Calendar.DATE, 3);
                break;
            case (Calendar.THURSDAY):
                gc.add(Calendar.DATE, 2);
                break;
            case (Calendar.FRIDAY):
                gc.add(Calendar.DATE, 1);
                break;
            case (Calendar.SATURDAY):
                gc.add(Calendar.DATE, 0);
                break;
        }
        return gc.getTime();
    }

    /**
     * 取得指定日期的所处星期的第一天
     * 
     * @param date 指定日期。
     * @return 指定日期的所处星期的第一天
     */
    public static Date getFirstDayOfWeek(Date date) {
        /**
         * 详细设计： 1.如果date是星期日，则减0天 2.如果date是星期一，则减1天 3.如果date是星期二，则减2天 4.如果date是星期三，则减3天
         * 5.如果date是星期四，则减4天 6.如果date是星期五，则减5天 7.如果date是星期六，则减6天
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        switch (gc.get(Calendar.DAY_OF_WEEK)) {
            case (Calendar.SUNDAY):
                gc.add(Calendar.DATE, 0);
                break;
            case (Calendar.MONDAY):
                gc.add(Calendar.DATE, -1);
                break;
            case (Calendar.TUESDAY):
                gc.add(Calendar.DATE, -2);
                break;
            case (Calendar.WEDNESDAY):
                gc.add(Calendar.DATE, -3);
                break;
            case (Calendar.THURSDAY):
                gc.add(Calendar.DATE, -4);
                break;
            case (Calendar.FRIDAY):
                gc.add(Calendar.DATE, -5);
                break;
            case (Calendar.SATURDAY):
                gc.add(Calendar.DATE, -6);
                break;
        }
        return gc.getTime();
    }

    public static Calendar getFirstDayOfWeek(Calendar gc) {
        /**
         * 详细设计： 1.如果date是星期日，则减0天 2.如果date是星期一，则减1天 3.如果date是星期二，则减2天 4.如果date是星期三，则减3天
         * 5.如果date是星期四，则减4天 6.如果date是星期五，则减5天 7.如果date是星期六，则减6天
         */
        switch (gc.get(Calendar.DAY_OF_WEEK)) {
            case (Calendar.SUNDAY):
                gc.add(Calendar.DATE, 0);
                break;
            case (Calendar.MONDAY):
                gc.add(Calendar.DATE, -1);
                break;
            case (Calendar.TUESDAY):
                gc.add(Calendar.DATE, -2);
                break;
            case (Calendar.WEDNESDAY):
                gc.add(Calendar.DATE, -3);
                break;
            case (Calendar.THURSDAY):
                gc.add(Calendar.DATE, -4);
                break;
            case (Calendar.FRIDAY):
                gc.add(Calendar.DATE, -5);
                break;
            case (Calendar.SATURDAY):
                gc.add(Calendar.DATE, -6);
                break;
        }
        return gc;
    }

    public String del_fh(String re_time) {
        String back_time = "";
        back_time =
                re_time.toString().replaceAll("/", "").replaceAll("-", "").replaceAll(" ", "")
                        .replaceAll(":", "");
        return back_time;
    }

    public String getMondayOFWeek(String bs) {
        int weeks = 0;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);

        String[] arr_time = preMonday.toString().split("-");
        if (arr_time[1].length() == 1) {
            arr_time[1] = "0" + arr_time[1];
        }
        if (arr_time[2].length() == 1) {
            arr_time[2] = "0" + arr_time[2];
        }
        // ls add
        if (bs.equals("1")) {
            preMonday = arr_time[0] + "-" + arr_time[1] + "-" + arr_time[2];
            preMonday = del_fh(preMonday) + "000000";

        } else if (bs.equals("0")) {
            preMonday = arr_time[0] + "/" + arr_time[1] + "/" + arr_time[2];
            preMonday = preMonday + " 00:00:00";

        }

        System.out.println("本周第一天====" + preMonday);
        return preMonday;
    }

    /**
     * 取得指定日期的所处月份的最后一天
     * 
     * @param date 指定日期。
     * @return 指定日期的所处月份的最后一天
     */
    public static Date getLastDayOfMonth(Date date) {
        /**
         * 详细设计： 1.如果date在1月，则为31日 2.如果date在2月，则为28日 3.如果date在3月，则为31日 4.如果date在4月，则为30日
         * 5.如果date在5月，则为31日 6.如果date在6月，则为30日 7.如果date在7月，则为31日 8.如果date在8月，则为31日 9.如果date在9月，则为30日
         * 10.如果date在10月，则为31日 11.如果date在11月，则为30日 12.如果date在12月，则为31日 1.如果date在闰年的2月，则为29日
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        switch (gc.get(Calendar.MONTH)) {
            case 0:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 1:
                gc.set(Calendar.DAY_OF_MONTH, 28);
                break;
            case 2:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 3:
                gc.set(Calendar.DAY_OF_MONTH, 30);
                break;
            case 4:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 5:
                gc.set(Calendar.DAY_OF_MONTH, 30);
                break;
            case 6:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 7:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 8:
                gc.set(Calendar.DAY_OF_MONTH, 30);
                break;
            case 9:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 10:
                gc.set(Calendar.DAY_OF_MONTH, 30);
                break;
            case 11:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
        }
        // 检查闰年
        if ((gc.get(Calendar.MONTH) == Calendar.FEBRUARY) && (isLeapYear(gc.get(Calendar.YEAR)))) {
            gc.set(Calendar.DAY_OF_MONTH, 29);
        }
        return gc.getTime();
    }

    public static Calendar getLastDayOfMonth(Calendar gc) {
        /**
         * 详细设计： 1.如果date在1月，则为31日 2.如果date在2月，则为28日 3.如果date在3月，则为31日 4.如果date在4月，则为30日
         * 5.如果date在5月，则为31日 6.如果date在6月，则为30日 7.如果date在7月，则为31日 8.如果date在8月，则为31日 9.如果date在9月，则为30日
         * 10.如果date在10月，则为31日 11.如果date在11月，则为30日 12.如果date在12月，则为31日 1.如果date在闰年的2月，则为29日
         */
        switch (gc.get(Calendar.MONTH)) {
            case 0:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 1:
                gc.set(Calendar.DAY_OF_MONTH, 28);
                break;
            case 2:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 3:
                gc.set(Calendar.DAY_OF_MONTH, 30);
                break;
            case 4:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 5:
                gc.set(Calendar.DAY_OF_MONTH, 30);
                break;
            case 6:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 7:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 8:
                gc.set(Calendar.DAY_OF_MONTH, 30);
                break;
            case 9:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 10:
                gc.set(Calendar.DAY_OF_MONTH, 30);
                break;
            case 11:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
        }
        // 检查闰年
        if ((gc.get(Calendar.MONTH) == Calendar.FEBRUARY) && (isLeapYear(gc.get(Calendar.YEAR)))) {
            gc.set(Calendar.DAY_OF_MONTH, 29);
        }
        return gc;
    }

    /**
     * 取得指定日期的所处月份的第一天
     * 
     * @param date 指定日期。
     * @return 指定日期的所处月份的第一天
     */
    public static Date getFirstDayOfMonth(Date date) {
        /**
         * 详细设计： 1.设置为1号
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        return gc.getTime();
    }

    public static Calendar getFirstDayOfMonth(Calendar gc) {
        /**
         * 详细设计： 1.设置为1号
         */
        gc.set(Calendar.DAY_OF_MONTH, 1);
        return gc;
    }

    /**
     * 将日期对象转换成为指定ORA日期、时间格式的字符串形式。如果日期对象为空，返回 一个空字符串对象，而不是一个空对象。
     * 
     * @param theDate 将要转换为字符串的日期对象。
     * @param hasTime 如果返回的字符串带时间则为true
     * @return 转换的结果
     */
    public static String toOraString(Date theDate, boolean hasTime) {
        /**
         * 详细设计： 1.如果有时间，则设置格式为getOraDateTimeFormat()的返回值 2.否则设置格式为getOraDateFormat()的返回值
         * 3.调用toString(Date theDate, DateFormat theDateFormat)
         */
        DateFormat theFormat;
        if (hasTime) {
            theFormat = getOraDateTimeFormat();
        } else {
            theFormat = getOraDateFormat();
        }
        return toString(theDate, theFormat);
    }

    /**
     * 将日期对象转换成为指定日期、时间格式的字符串形式。如果日期对象为空，返回 一个空字符串对象，而不是一个空对象。
     * 
     * @param theDate 将要转换为字符串的日期对象。
     * @param hasTime 如果返回的字符串带时间则为true
     * @return 转换的结果
     */
    public static String toString(Date theDate, boolean hasTime) {
        /**
         * 详细设计： 1.如果有时间，则设置格式为getDateTimeFormat的返回值 2.否则设置格式为getDateFormat的返回值 3.调用toString(Date
         * theDate, DateFormat theDateFormat)
         */
        DateFormat theFormat;
        if (hasTime) {
            theFormat = getDateTimeFormat();
        } else {
            theFormat = getDateFormat();
        }
        return toString(theDate, theFormat);
    }

    /**
     * 标准日期格式
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(MM_dd_yyyy);
    /**
     * 标准时间格式
     */
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(MM_dd_yyyy_HH_mm);
    /**
     * 带时分秒的标准时间格式
     */
    private static final SimpleDateFormat DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat(
            MM_dd_yyyy_HH_mm_ss);

    /**
     * ORA标准日期格式
     */
    private static final SimpleDateFormat ORA_DATE_FORMAT = new SimpleDateFormat(yyyyMMdd);
    /**
     * ORA标准时间格式
     */
    private static final SimpleDateFormat ORA_DATE_TIME_FORMAT = new SimpleDateFormat(yyyyMMddHHmm);
    /**
     * 带时分秒的ORA标准时间格式
     */
    private static final SimpleDateFormat ORA_DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat(
            yyyyMMddHHmmss);

    /**
     * 创建一个标准日期格式的克隆
     * 
     * @return 标准日期格式的克隆
     */
    public static DateFormat getDateFormat() {
        /**
         * 详细设计： 1.返回DATE_FORMAT
         */
        SimpleDateFormat theDateFormat = (SimpleDateFormat) DATE_FORMAT.clone();
        theDateFormat.setLenient(false);
        return theDateFormat;
    }

    /**
     * 创建一个标准时间格式的克隆
     * 
     * @return 标准时间格式的克隆
     */
    public static DateFormat getDateTimeFormat() {
        /**
         * 详细设计： 1.返回DATE_TIME_FORMAT
         */
        SimpleDateFormat theDateTimeFormat = (SimpleDateFormat) DATE_TIME_FORMAT.clone();
        theDateTimeFormat.setLenient(false);
        return theDateTimeFormat;
    }

    /**
     * 创建一个标准ORA日期格式的克隆
     * 
     * @return 标准ORA日期格式的克隆
     */
    public static DateFormat getOraDateFormat() {
        /**
         * 详细设计： 1.返回ORA_DATE_FORMAT
         */
        SimpleDateFormat theDateFormat = (SimpleDateFormat) ORA_DATE_FORMAT.clone();
        theDateFormat.setLenient(false);
        return theDateFormat;
    }

    /**
     * 创建一个标准ORA时间格式的克隆
     * 
     * @return 标准ORA时间格式的克隆
     */
    public static DateFormat getOraDateTimeFormat() {
        /**
         * 详细设计： 1.返回ORA_DATE_TIME_FORMAT
         */
        SimpleDateFormat theDateTimeFormat = (SimpleDateFormat) ORA_DATE_TIME_FORMAT.clone();
        theDateTimeFormat.setLenient(false);
        return theDateTimeFormat;
    }

    /**
     * 将一个日期对象转换成为指定日期、时间格式的字符串。 如果日期对象为空，返回一个空字符串，而不是一个空对象。
     * 
     * @param theDate 要转换的日期对象
     * @param theDateFormat 返回的日期字符串的格式
     * @return 转换结果
     */
    public static String toString(Date theDate, DateFormat theDateFormat) {
        /**
         * 详细设计： 1.theDate为空，则返回"" 2.否则使用theDateFormat格式化
         */
        if (theDate == null) {
            return "";
        }
        return theDateFormat.format(theDate);
    }

    /**
     * 判断二个时间是否在同一个周
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) return true;
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) return true;
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) return true;
        }
        return false;
    }

    /**
     * 产生周序列,即得到当前时间所在的年度是第几周
     * 
     * @return
     */
    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1) week = "0" + week;
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + week;
    }

    public static boolean isDate(String date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = df.parse(date);
        } catch (Exception e) {
            // 如果不能转换,肯定是错误格式
            return false;
        }
        String str = df.format(d);
        // 转换后的日期再转换回String,如果不等,逻辑错误.如format为yyyy_MM_dd,date为
        // "2006-02-31",转换为日期后再转换回字符串为"2006-03-03",说明格式虽然对,但日期
        // 逻辑上不对.
        return date.equals(str);
    }

    /**
     * 将Date类型转换为字符串
     * 
     * @param date 日期类型
     * @return 日期字符串
     */
    public static String format(Date date) {
        return format(date, yyyy_MM_dd_HH_mm_SS);
    }

    /**
     * 将Date类型转换为字符串
     * 
     * @param date 日期类型
     * @param pattern 字符串格式
     * @return 日期字符串
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return "null";
        }
        if (pattern == null || pattern.equals("") || pattern.equals("null")) {
            pattern = yyyy_MM_dd_HH_mm_SS;
        }
        return new java.text.SimpleDateFormat(pattern).format(date);
    }

    /**
     * 将字符串转换为Date类型
     * 
     * @param date 字符串类型
     * @return 日期类型
     */
    public static Date format(String date) {
        return format(date, null);
    }

    /**
     * 将字符串转换为Date类型
     * 
     * @param date 字符串类型
     * @param pattern 格式
     * @return 日期类型
     */
    public static Date format(String date, String pattern) {
        if (pattern == null || pattern.equals("") || pattern.equals("null")) {
            pattern = yyyy_MM_dd_HH_mm_SS;
        }
        if (date == null || date.equals("") || date.equals("null")) {
            return new Date();
        }
        Date d = null;
        try {
            d = new java.text.SimpleDateFormat(pattern).parse(date);
        } catch (ParseException pe) {}
        return d;
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat(yyyy_MM_dd);
        long day = 0;
        try {
            java.util.Date date = myFormatter.parse(sj1);
            java.util.Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     * 
     * @param sdate
     * @return
     */
    public static String getWeek(String sdate) {
        // 再转换为时间
        Date date = DateUtil.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     * 
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(yyyy_MM_dd);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 两个时间之间的天数
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals("")) return 0;
        if (date2 == null || date2.equals("")) return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat(yyyy_MM_dd);
        java.util.Date date = null;
        java.util.Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {}
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    // 计算当月最后一天,返回字符串
    public String getDefaultDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 上月第一天
    public String getPreviousMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
        // lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获取当月第一天
    public String getFirstDayOfMonth() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获得本周星期日的日期
    public String getCurrentWeekday() {
        weeks = 0;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获取当天时间
    public String getNowTime() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(yyyy_MM_dd);// 可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        return hehe;
    }

    // 获得当前日期与本周日相差的天数
    private int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    // 获得本周一的日期
    public String getMondayOFWeek() {
        weeks = 0;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得相应周的周六的日期
    public String getSaturday() {
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得上周星期日的日期
    public String getPreviousWeekSunday() {
        weeks = 0;
        weeks--;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得上周星期一的日期
    public String getPreviousWeekday() {
        weeks--;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得下周星期一的日期
    public String getNextMonday() {
        weeks++;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得下周星期日的日期
    public String getNextSunday() {

        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    private int getMonthPlus() {
        Calendar cd = Calendar.getInstance();
        int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
        cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        MaxDate = cd.get(Calendar.DATE);
        if (monthOfNumber == 1) {
            return -MaxDate;
        } else {
            return 1 - monthOfNumber;
        }
    }

    // 获得上月最后一天的日期
    public String getPreviousMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, -1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获得下个月第一天的日期
    public String getNextMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获得下个月最后一天的日期
    public String getNextMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 加一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获得明年最后一天的日期
    public String getNextYearEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);// 加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        lastDate.roll(Calendar.DAY_OF_YEAR, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获得明年第一天的日期
    public String getNextYearFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);// 加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        str = sdf.format(lastDate.getTime());
        return str;

    }
    
    public static String getNextDay(String date){
        Calendar end = DateUtil.parseCalendarFormat(date, "yyyy-MM-dd");
        end.add(Calendar.DATE, 1);
        Date endTime = end.getTime();
        date = DateUtil.format(endTime, "yyyy-MM-dd");
        return date;
    }


    // 获得本年有多少天
    private int getMaxYear() {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        return MaxYear;
    }

    private int getYearPlus() {
        Calendar cd = Calendar.getInstance();
        int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
        cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        if (yearOfNumber == 1) {
            return -MaxYear;
        } else {
            return 1 - yearOfNumber;
        }
    }

    // 获得本年第一天的日期
    public String getCurrentYearFirst() {
        int yearPlus = this.getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus);
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        return preYearDay;
    }

    // 获得本年最后一天的日期 *
    public String getCurrentYearEnd() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        return years + "-12-31";
    }

    // 获得上年第一天的日期 *
    public String getPreviousYearFirst() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);
        years_value--;
        return years_value + "-1-1";
    }

    // 获得上年最后一天的日期
    public String getPreviousYearEnd() {
        weeks--;
        int yearPlus = this.getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks + (MaxYear - 1));
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        getThisSeasonTime(11);
        return preYearDay;
    }

    // 获得本季度
    public String getThisSeasonTime(int month) {
        int array[][] = { {1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int start_month = array[season - 1][0];
        int end_month = array[season - 1][2];

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);

        int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
        int end_days = getLastDayOfMonth(years_value, end_month);
        String seasonDate =
                years_value + "-" + start_month + "-" + start_days + ";" + years_value + "-"
                        + end_month + "-" + end_days;
        return seasonDate;

    }

    /**
     * 获取某年某月的最后一天
     * 
     * @param year 年
     * @param month 月
     * @return 最后一天
     */
    private int getLastDayOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10
                || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }

    public static void dateDiff(String startTime, String endTime, String format) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            long day = diff / nd;// 计算差多少天
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            System.out.println("时间相差：" + day + "天" + hour + "小时" + min + "分钟" + sec + "秒。");
        } catch (Exception e) {}
    }

    public static boolean between(String startTime, String endTime, String compareTime,
            String format) {
        Date start = createDateWith(startTime, format);
        Date end = createDateWith(endTime, format);
        Date nowDate;// test >= start && test <= end
        if (StringUtils.isEmpty(compareTime)) {
            nowDate = new Date();
        } else {
            nowDate = createDateWith(compareTime, format);;
        }
        return ((nowDate.equals(start) || nowDate.after(start)) && (nowDate.equals(end) || nowDate
                .before(end)));
    }

    public static boolean betweenByDay(String startTime, String endTime, String compareTime)
            throws ParseException {
        return between(startTime, endTime, compareTime, yyyy_MM_dd);
    }

    public static boolean betweenByDay(String startTime, String endTime) throws ParseException {
        return between(startTime, endTime, null, yyyy_MM_dd);
    }

    public static String addDate(String now, int day) {
        Calendar fromCal = Calendar.getInstance();
        String format = yyyy_MM_dd;
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = createDateWith(now, format);
        fromCal.setTime(date);
        fromCal.add(Calendar.DATE, day);
        return dateFormat.format(fromCal.getTime());
    }

    public static String addMinute(String now, int minute) {
        Calendar fromCal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(yyyy_MM_dd_HH_mm_SS);
        Date date = createDateWith(now, yyyy_MM_dd_HH_mm_SS);
        fromCal.setTime(date);
        fromCal.add(Calendar.MINUTE, minute);
        return dateFormat.format(fromCal.getTime());
    }

    public static boolean gt(String startTime, String endTime, String format) {
        Date start = createDateWith(startTime, format);
        Date end = createDateWith(endTime, format);
        if (start.compareTo(end) > 0) {
            return true;
        }
        return false;
    }

    private static Date createDateWith(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date start;
        try {
            start = sdf.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
        return start;
    }

    public static boolean gtByDay(String startTime, String endTime) {
        return gt(startTime, endTime, yyyy_MM_dd);
    }
    /**
     * 从当天零点零分零秒到当前过去了多少秒
     * @return
     */
    public static Long secondsDay(){
    	Calendar current = Calendar.getInstance();
    	Calendar start = Calendar.getInstance();
    	start.set(Calendar.HOUR, 0);
    	start.set(Calendar.MINUTE, 0);
    	start.set(Calendar.SECOND, 0);
    	Long time = DateUtil.toSecond(current.getTime())-DateUtil.toSecond(start.getTime());
    	return time;
    }
}
