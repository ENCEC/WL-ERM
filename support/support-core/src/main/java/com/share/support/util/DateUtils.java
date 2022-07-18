/**
 *
 */
package com.share.support.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author 马金丰
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYYMM = "yyyyMM";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String DD = "dd";

    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String DATE_TIME_FORMAT_2 = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_NAME_FORMAT = "yyyyMMddHHmmss";
    public static final String DATE_TIMES_NAME_FORMAT = "yyyyMMddHHmmssSSS";
    public static final String YYYY_MM_DDHHMMSS = "yyyy-MM-ddHHmmss";
    public static final String YYYY_MM_DDHH_MM_SS = "yyyy_MM_dd HH_mm_ss";
    private static final String UTC = "UTC";
    public static final String YYYY_MM_DD_T_HH_MM_SS_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_EXCEL = "yyyy/MM/dd";
    private static final String DATE_FORMAT_T = "yyMMdd";
    public static final String DATE_TIME_MS_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String DATE_TIME_NOS_FORMAT = "yyyy-MM-dd HH:mm";
    private static final String TIME_FORMAT = "HHmmss";
    private static final String TIME_SEMICOLON_FORMAT = "HH:mm:ss";
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    private static final String TIME_EXCEPTION = "时间转换异常";
    private static final String TIME_1 = "-";
    private static final String TIME_2 = ":";
    private static final String TIME_3 = "T";
    private static final String TIME_4 = "/";

    private static final int theFirstDayOfMonthShould = 4;

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
    private static ThreadLocal<DateFormat> dateTimeThreadLocal = new ThreadLocal<DateFormat>();
    private static ThreadLocal<DateFormat> dateTimeMsThreadLocal = new ThreadLocal<DateFormat>();
    private static ThreadLocal<DateFormat> dateTimeNosThreadLocal = new ThreadLocal<DateFormat>();
    private static ThreadLocal<DateFormat> dateThreadLocal = new ThreadLocal<DateFormat>();
    private static ThreadLocal<DateFormat> timeThreadLocal = new ThreadLocal<DateFormat>();
    private static ThreadLocal<DateFormat> timeSemicolonThreadLocal = new ThreadLocal<DateFormat>();

    /**
     * 将UTC格式的字符串转换成日期格式
     *
     * @param dateStr UTC格式日期字符串
     */
    public static Date parseUTCDate(String dateStr) {
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(UTC));
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            String infoMessage = "Parse date is error, try parse via other pattern[yyyy-MM-dd HH:mm:ss, yyyy-MM-dd, yyyyMMddHHmmss, yyyyMMdd, yyyyMMddHHmm]! dateStr#{}, datePattern#{}";
            logger.info(infoMessage, dateStr, pattern);
            String[] parsePatterns = new String[]{DATE_TIME_FORMAT, DATE_FORMAT, DATE_TIME_NAME_FORMAT, DATE_TIME_NAME_FORMAT,
                    YYYYMMDD};
            try {
                date = parseDate(dateStr, parsePatterns);
            } catch (ParseException e1) {
                infoMessage = "Parse date is error, get system current time! dateStr#{}, datePattern#{}";
                logger.info(infoMessage, dateStr, "yyyy-MM-dd HH:mm:ss|yyyy-MM-dd");
                date = new Date();
            }
        }
        return date;
    }

    /**
     * 将日期格式化成指定的日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date parseFormatDate(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        try {
            String dateStr = DateUtils.formatDate(date, pattern);
            return DateUtils.parseDate(dateStr, pattern);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 计算近期失效合同
     *
     * @param date
     * @param day
     * @return
     * @throws ParseException
     */
    public static Date addDate(Date date, long day) {

        long time = date.getTime();// 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }

    /**
     * @param datdString Thu May 18 2017 00:00:00 GMT+0800 (中国标准时间)
     * @return 年月日;
     */
    public static String parseTime(String datdString) {
        datdString = datdString.replaceAll("\\(.*\\)", "").replace("GMT 0800", "GMT+08:00");
        // 将字符串转化为date类型，格式2016-10-12
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
        Date dateTrans = null;
        try {
            dateTrans = format.parse(datdString);
            return new SimpleDateFormat(DATE_TIME_NAME_FORMAT).format(dateTrans);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return datdString;

    }

    /**
     * 根据给定的格式格式化成字符串
     *
     * @param date 日期
     * @param pattern 格式
     */
    public static String formatDate(Date date, String pattern) {
        if(date == null){
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern(pattern);
        return dateFormat.format(date);
    }

    /**
     * 根据给定的格式格式化成字符串
     *
     * @param date 日期
     * @param pattern 格式
     */
    public static String formatUTCDate(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern(YYYY_MM_DD_T_HH_MM_SS_Z);
        String formatUTCDate = dateFormat.format(date);
        dateFormat.setTimeZone(TimeZone.getTimeZone(UTC));
        Date localDate = null;
        try {
            localDate = dateFormat.parse(formatUTCDate);
        } catch (ParseException e) {
            localDate = date;
        }
        dateFormat.applyPattern(pattern);
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(localDate);
    }

    /**
     * 根据给定的格式解析字符串成日期
     *
     * @param str 字符串
     * @param pattern 格式
     */
    public static Date parseDate(String str, String pattern) {
        try {
            return parseDate(str, new String[]{pattern});
        } catch (ParseException e) {
            logger.info(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getNowTime() {
        return new Date();
    }

    /**
     * 获取当前时间字符串 格式:yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTime() {
        Date dt = new Date();
        DateFormat df = getDateTimeFormat();
        // df.setTimeZone(TimeZone.getTimeZone("GMT-0:00"));
        return df.format(dt);
    }

    /**
     * 获取指定时间字符串 格式:yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getDateTimeStr(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat sdf = getDateTimeFormat();
        return sdf.format(date);
    }

    public static String getCurrentDateTimsMs() {
        return getDateTimsMsStr(getNowTime());
    }

    public static String getDateTimsMsStr(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat sdf = getDateTimeMsFormat();
        return sdf.format(date);

    }

    /**
     * 转换获取时间，指定时间字符串 格式:yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr
     * @return
     */
    public static Date getDateTime(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        DateFormat sdf = getDateTimeFormat();
        ParsePosition pos = new ParsePosition(0);
        return sdf.parse(dateStr, pos);
    }

    public static Date getDateForExcel(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        DateFormat sdf = getDateFormatExcel();
        ParsePosition pos = new ParsePosition(0);
        return sdf.parse(dateStr, pos);
    }

    /**
     * 获取指定日期字符串 格式:yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getDateStr(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat df = getDateFormat();
        return df.format(date);
    }

    public static Date getNowDate() {
        return getDate(getCurrentTime());
    }

    public static Date getDate(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        DateFormat df = getDateFormat();
        try {
            return df.parse(str);
        } catch (ParseException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * @param date
     * @return
     */
    public static String getDateStrYYMMDD(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat df = getDateFormatT();
        return df.format(date);
    }

    public static String getDateTimeForName() {
        return getDateTimeForName(new Date());
    }

    public static String getDateTimeForName(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat df = getDateTimeNameFormat();
        return df.format(date);
    }

    /**
     * 获取指定时间字符串 格式:HHmmss
     *
     * @param date
     * @return
     */
    public static String getTimeStr(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat df = getTimeFormat();
        return df.format(date);
    }

    /**

     - TODd 将时间转为YYYYMMDD
     - @Author 黄灵波
     - @Date
     - @Param
     - @return

      **/

    public static String getDateFormatYYYYMMDD(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat df = getDateFormatYYYYMMDD();
        return df.format(date);
    }

    /**
     * 获取指定时间字符串 格式:HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getTimeSemicolonStr(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat df = getTimeSemicolonFormat();
        return df.format(date);
    }

    public static DateFormat getDateTimeFormat() {
        DateFormat df = (DateFormat) dateTimeThreadLocal.get();
        if (df == null) {
            df = new SimpleDateFormat(DATE_TIME_FORMAT);
            dateTimeThreadLocal.set(df);
        }
        return df;
    }

    public static DateFormat getDateTimeMsFormat() {
        DateFormat df = (DateFormat) dateTimeMsThreadLocal.get();
        if (df == null) {
            df = new SimpleDateFormat(DATE_TIME_MS_FORMAT);
            dateTimeMsThreadLocal.set(df);
        }
        return df;
    }

    public static DateFormat getDateTimeNosFormat() {
        DateFormat df = (DateFormat) dateTimeNosThreadLocal.get();
        if (df == null) {
            df = new SimpleDateFormat(DATE_TIME_NOS_FORMAT);
            dateTimeNosThreadLocal.set(df);
        }
        return df;
    }

    public static DateFormat getDateFormat() {
        DateFormat df = (DateFormat) dateThreadLocal.get();
        if (df == null) {
            df = new SimpleDateFormat(DATE_FORMAT);
            dateThreadLocal.set(df);
        }
        return df;
    }

    public static DateFormat getDateFormatExcel() {
        DateFormat df = (DateFormat) dateThreadLocal.get();
        if (df == null) {
            df = new SimpleDateFormat(DATE_FORMAT_EXCEL);
            dateThreadLocal.set(df);
        }
        return df;
    }

    public static DateFormat getDateFormatT() {
        DateFormat df = (DateFormat) dateThreadLocal.get();
        if (df == null) {
            df = new SimpleDateFormat(DATE_FORMAT_T);
            dateThreadLocal.set(df);
        }
        return df;
    }

    public static DateFormat getDateFormatYYYYMMDD() {
        DateFormat df = (DateFormat) dateThreadLocal.get();
        if (df == null) {
            df = new SimpleDateFormat(YYYYMMDD);
            dateThreadLocal.set(df);
        }
        return df;
    }

    public static DateFormat getTimeFormat() {
        DateFormat df = (DateFormat) timeThreadLocal.get();
        if (df == null) {
            df = new SimpleDateFormat(TIME_FORMAT);
            timeThreadLocal.set(df);
        }
        return df;
    }

    public static DateFormat getDateTimeNameFormat() {
        DateFormat df = (DateFormat) timeThreadLocal.get();
        if (df == null) {
            df = new SimpleDateFormat(DATE_TIME_NAME_FORMAT);
            timeThreadLocal.set(df);
        }
        return df;
    }

    public static DateFormat getTimeSemicolonFormat() {
        DateFormat df = (DateFormat) timeSemicolonThreadLocal.get();
        if (df == null) {
            df = new SimpleDateFormat(TIME_SEMICOLON_FORMAT);
            timeSemicolonThreadLocal.set(df);
        }
        return df;
    }

    /**
     * 获取时间差 秒级
     *
     * @param begin
     * @param end
     * @return
     */
    public static String getTimeSpace(Date begin, Date end) {
        try {
            long between = (end.getTime() - begin.getTime());// 除以1000是为了转换成秒
            long day1 = between / 1000 / (24 * 3600);
            long hour1 = between / 1000 % (24 * 3600) / 3600;
            long minute1 = between / 1000 % 3600 / 60;
            long second1 = between / 1000 % 60;
            long ms = between % 60 % 1000;
            return "" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒" + ms + "毫秒";
        } catch (Exception pe) {
            return null;
        }
    }

    /**
     * 日期比较 yyyy-MM-dd
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean dateEquals(Date d1, Date d2) {
        if (d1 == null && d2 == null)
            return true;
        else if ((d1 == null && d2 != null) || (d1 != null && d1 == null))
            return false;
        else
            return getDateStr(d1).equals(getDateStr(d2));
    }

    /**
     * 根据时间得到星期
     *
     * @param date
     * @return
     */
    public static String getWeekCurrent(Date date) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        if (date == null) {
            date = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * @param @param date
     * @param @return
     * @return Date
     * @description 当前时间设置为当天结束时间
     * @author liudejin liudej@haier.com
     * @date 2016年12月26日 下午6:31:52
     * @version 1.0
     */
    public static Date setTimeForDayEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 39);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * @Author huangsl
     * @Description // 返回当天开始字符串
     * @Date 16:03 2019/1/16
     * @Param [date]
     * @return java.lang.String
     **/
    public static String setTimeForDayBegin(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        return sdf.format(c.getTime());
    }

    /**
     * @Author 黄灵波
     * @Description // 返回当天开始时间
     * @return java.lang.String
     **/
    public static Date setTimeForDay(Date date) {
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
        } else {
            c.setTime(getNowDate());
        }

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }


    /**
     * @param @param date
     * @param @return
     * @return Date
     * @description 获得指定周日的日期
     * @author liudejin liudej@haier.com
     * @date 2017年1月1日 上午11:30:06
     * @version 1.0
     */
    public static Date getSundayOFWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, 6);
        return cal.getTime();
    }

    /**
     * @param @param d1
     * @param @param d2
     * @param @return
     * @return Boolean
     * @description 判断两个date 是否同一天
     * @author liudejin liudej@haier.com
     * @date 2016年12月27日 上午9:20:02
     * @version 1.0
     */
    public static boolean isSameDay(Date d1, Date d2) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d1);
        int id1 = cal.get(Calendar.DAY_OF_YEAR);
        int iy1 = cal.get(Calendar.YEAR);
        cal.setTime(d2);
        int id2 = cal.get(Calendar.DAY_OF_YEAR);
        int iy2 = cal.get(Calendar.YEAR);

        return (id1 == id2 && iy1 == iy2);
    }

    /**
     * @param @param date
     * @param @param day
     * @param @return
     * @return Date
     * @description 时间增加多少小时
     * @author liudejin liudej@haier.com
     * @date 2017年1月1日 上午11:05:22
     * @version 1.0
     */
    public static Date addHourToDate(Date date, double hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, (int) Math.ceil(hour * 60));
        return c.getTime();
    }

    public static Date addSecondToDate(Integer second) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.SECOND, second);
        return c.getTime();
    }

    public static Date addSecondToDate(Date date, Integer second) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.SECOND, second);
        return c.getTime();
    }

    public static Date addDateToDate(Date date, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * @Author huangsl
     * @Description // 计算两个日期的时间间隔
     * @Date 19:36 2019/1/8
     * @Param [beginDate, endDate]
     * @return int
     **/
    public static int intervalOfTime(Date beginDate, Date endDate) {
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(beginDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        beginCalendar.set(Calendar.HOUR, 0);
        beginCalendar.set(Calendar.MINUTE, 0);
        beginCalendar.set(Calendar.SECOND, 0);
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        long beginTime = beginCalendar.getTime().getTime();
        endCalendar.add(Calendar.SECOND, 1);
        long endTime = endCalendar.getTime().getTime();
        int wareAge = (int) ((endTime - beginTime) / (1000 * 60 * 60 * 24));
        return wareAge;
    }

    /**
     * 计算两个日期相差的天数，忽略时分秒
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int intervalOfDay(Date beginDate, Date endDate) {
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(beginDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        beginCalendar.set(Calendar.HOUR_OF_DAY, 0);
        beginCalendar.set(Calendar.MINUTE, 0);
        beginCalendar.set(Calendar.SECOND, 0);
        beginCalendar.set(Calendar.MILLISECOND, 0);
        endCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
        endCalendar.set(Calendar.MILLISECOND, 0);
        return (int) ((endCalendar.getTime().getTime() - beginCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * @Description // public static
     * @Date 17:09 2019/4/17
     * @Param [cargoName, detailName]
     * @Author 郑族彬
     * @return
     */
    public static boolean compareDate(Date beginDate, Date endDate) {
        if (beginDate == null) {
            if (endDate != null) {
                return true;
            }
        } else {
            if (endDate == null) {
                return true;
            }
            if (beginDate.before(endDate)) {
                return true;
            } else if (beginDate.after(endDate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Description //compare two dates,return
     *      1 if beginDate after endDate or endDate is null,
     *      0 if beginDate equals with endDate,
     *     -1 if beginDate before endDate or beginDate is null,
     * @Date 2019/07/25
     * @Param [date1, date2]
     * @Author suggestionLiao
     * @return
     */
    public static int compareDates(Date beginDate, Date endDate) {
        if (Objects.isNull(beginDate)) {
            return Objects.isNull(endDate) ? 0 : -1;
        }
        if (Objects.isNull(endDate)) {
            return 1;
        }
        return beginDate.compareTo(endDate);
    }

    /**
     * @Author lianjn
     * @Description // 切割时间段
     * @Date 2019/5/29
     * @Param [dateType, start, end] 类型 M/D/H/N --> 月/天/小时/分钟 | 格式 yyyy-MM-dd HH:mm:ss
     * @return java.util.List<java.lang.String>
     **/
    public static List<String> cutDate(String dateType, String start, String end, String returnFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(returnFormat);
            Date dBegin = sdf.parse(start);
            Date dEnd = sdf.parse(end);
            return findDates(dateType, dBegin, dEnd, returnFormat);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static List<String> getDatesBetweenTwoDates(Date beginDate, Date endDate, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        LocalDate start = LocalDate.parse(simpleDateFormat.format(beginDate));
        LocalDate end = LocalDate.parse(simpleDateFormat.format(endDate));
        List<String> totalDates = new ArrayList<>();
        while (!start.isAfter(end)) {
            totalDates.add(start.format(dateFormatter));
            start = start.plusDays(1);
        }
        return totalDates;
    }

    public static List<String> findDates(String dateType, Date dBegin, Date dEnd, String returnFormat) throws Exception {
        List<String> listDate = new ArrayList<>();
        listDate.add(new SimpleDateFormat(returnFormat).format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        while (calEnd.after(calBegin)) {
            switch (dateType) {
                case "M":
                    calBegin.add(Calendar.MONTH, 1);
                    break;
                case "D":
                    calBegin.add(Calendar.DAY_OF_YEAR, 1);
                    break;
                case "H":
                    calBegin.add(Calendar.HOUR, 1);
                    break;
                case "N":
                    calBegin.add(Calendar.SECOND, 1);
                    break;
            }
            if (calEnd.after(calBegin)) {
                listDate.add(new SimpleDateFormat(returnFormat).format(calBegin.getTime()));
            } else {
                listDate.add(new SimpleDateFormat(returnFormat).format(calEnd.getTime()));
            }
        }
        return listDate;
    }



    /**
     * @Author 赖辰辉
     *  比较两个日期,返回较大的日期
     * @date 17:15 2019/7/23
     * @param beginDate
     * @param endDate
     * @return java.util.Date
     **/
    public static Date getmaxDate(Date beginDate, Date endDate) {
        if (beginDate == null) {
            if (endDate != null) {
                return endDate;
            }
            return null;
        } else {
            if (endDate == null) {
                return beginDate;
            }
            if (beginDate.before(endDate)) {
                return endDate;
            } else if (beginDate.after(endDate)) {
                return beginDate;
            }
        }
        return null;
    }

    /**
     * @Author 赖辰辉
     *  比较两个日期,返回较大的日期
     * @date 17:15 2019/7/23
     * @param beginDate
     * @param endDate
     * @return java.util.Date
     **/
    public static Date getMinDate(Date beginDate, Date endDate) {
        if (beginDate == null) {
            if (endDate != null) {
                return endDate;
            }
            return null;
        } else {
            if (endDate == null) {
                return beginDate;
            }
            if (beginDate.before(endDate)) {
                return beginDate;
            } else if (beginDate.after(endDate)) {
                return endDate;
            } else if (beginDate.compareTo(endDate) == 0) {
                return endDate;
            }
        }
        return null;
    }

    /**
     * @Author 赖辰辉
     *  获取海关申报汇率基础日期
     * @date 9:38 2019/8/2
     * @param orderDate
     * @return java.util.Date
     **/
    public static Date getDateForCustomsDeclarationExchangeRat(Date orderDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(orderDate);
        //上个月 一号 为周几
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int dayOfWeekForfirstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        //上个月一号 要 小于 周四 时,直接取 第三个周三
        if (dayOfWeekForfirstDayOfMonth < theFirstDayOfMonthShould) {
            //加两周,且取周三
            calendar.add(Calendar.DAY_OF_MONTH, 14);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            return calendar.getTime();
        }
        //否则,取第四个周三
        else {
            //加三周,且取周三
            calendar.add(Calendar.DAY_OF_MONTH, 21);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            return calendar.getTime();
        }
    }

    /**
     * @Author 林安平
     * @Description //计算几天（前后）的日期
     * @Date 16:51 2019/11/7
     * @Param [date, num]
     * @return java.util.Date
     */
    public static Date calculateDate(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + num);
        return calendar.getTime();
    }

    /**
     * @Author 黄德芳
     * @Description //判断是否当天
     * @Date 16:51 2019/11/7
     * @Param [date, num]
     * @return java.util.Date
     */
    public static boolean isToday(Date inputJudgeDate) {
        boolean flag = false;
        // 获取当前系统时间
        long longDate = System.currentTimeMillis();
        Date nowDate = new Date(longDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(nowDate);
        String subDate = format.substring(0, 10);
        // 定义每天的24h时间范围
        String beginTime = subDate + " 00:00:00";
        String endTime = subDate + " 23:59:59";
        Date paseBeginTime = null;
        Date paseEndTime = null;
        try {
            paseBeginTime = dateFormat.parse(beginTime);
            paseEndTime = dateFormat.parse(endTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (inputJudgeDate.after(paseBeginTime) && inputJudgeDate.before(paseEndTime)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 获取curreTime+amount日期所在月份的第一天和最后一天
     * @param currTime
     * @param amount
     * @param max true:获取最后一天，false：获取第一天
     * @return
     */
    public static Date getFirstOrLastOfMouth(Date currTime, int amount, boolean max) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currTime);
        cal.add(Calendar.DAY_OF_MONTH, amount);
        cal.set(Calendar.DAY_OF_MONTH, max ? cal.getActualMaximum(Calendar.DATE) : cal.getMinimum(Calendar.DATE));
        return cal.getTime();
    }

    public static Date addMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static boolean isCurrentDay(Date date) {
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        String day = sf.format(date);
        return day.equals(nowDay);
    }

    /**
     * 获得上个月月份
     * @param dateFormat
     * @return
     */
    public static String getLastMonth(String dateFormat) {
        LocalDate today = LocalDate.now();
        today = today.minusMonths(1);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern(dateFormat);
        return formatters.format(today);
    }

    /**
     * 获得上N个月月份
     * @param dateFormat
     * @return
     */
    public static String getLastMonth(int i) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - i); // 设置为上一个月
        date = calendar.getTime();
        String accDate = format.format(date);
        return accDate;
    }

    /**
     * 字符串转日期格式
     * @param time
     * @param pattern
     * @return
     */
    public static Date getDateYYYYMM(String time, String pattern) {
        if (StringUtils.isEmpty(time)) {
            return null;
        }
        if (StringUtils.isEmpty(pattern)) {
            pattern = DateUtils.YYYYMM;
        }
        Date date = null;
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取 当前月份的前几个月份月份的第一天和最后一天
     * @param amount:当前月份的前几个月份
     * @param max true:获取最后一天，false：获取第一天
     * @return
     */
    public static String getFirstOrLastOfMouth(int amount, boolean max) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();//获取当前日期
        cal.add(Calendar.MONTH, amount);
        cal.set(Calendar.DAY_OF_MONTH, max ? cal.getActualMaximum(Calendar.DATE) : cal.getMinimum(Calendar.DATE));
        String day = format.format(cal.getTime());
        return day;
    }

    /**
     * zhaoym
     * 两个个日期的月份差
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getDifMonth(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);
        int result = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
        int month = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12;
        int diff = month + result;
        return diff;
    }

    /**
     - 获取前n个月所在月份的最后一天的开始时间
     - @Author jxchen
     - @Date 11:38 2020/9/7
     - @Params n - 前n个月，自然数
     - @return
     **/
    public static Date getPreviousMonthLastDayStartTime(int n) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, n * (-1));
        int MaxDay = cal.getMaximum(Calendar.DAY_OF_MONTH);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), MaxDay, 0, 0, 0);
        return cal.getTime();
    }

    /**
     * 获取某年最后一天日期最后时刻
     * @param year 年份
     * @return Date
     */
    public static Date getYearLastDay(int year){
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.roll(Calendar.DAY_OF_YEAR, -1);
        Date yearLastDay = cal.getTime();
        return getFinallyDate(yearLastDay);
    }

    /**
     * 得到指定日期的一天的的最后时刻23:59:59
     *
     * @param date
     * @return
     */
    public static Date getFinallyDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String temp = format.format(date);
        temp += " 23:59:59";
        try {
            return format1.parse(temp);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取某年第一天日期开始时刻
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirstDay(int year){
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        Date yearFirstDay = cal.getTime();
        return getStartDate(yearFirstDay);
    }

    /**
     * 得到指定日期的一天的开始时刻00:00:00
     *
     * @param date
     * @return
     */
    public static Date getStartDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String temp = format.format(date);
        temp += " 00:00:00";

        try {
            return format1.parse(temp);
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * @Author kzb
     * @Description //指定月份的最后一天
     * @Date  2021/11/24 11:02
     * @Param [yearMonth] YYYY-MM
     * @return java.lang.String
     **/
    public static Date getLastDayOfMonth(String yearMonth) {
        int year = Integer.parseInt(yearMonth.split("-")[0]);  //年
        int month = Integer.parseInt(yearMonth.split("-")[1]); //月
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month); //设置当前月的上一个月
        // 获取某月最大天数
        int lastDay = cal.getMinimum(Calendar.DATE); //获取月份中的最小值，即第一天
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay - 1); //上月的第一天减去1就是当月的最后一天
        // 格式化日期
//        formatDate(getFinallyDate(cal.getTime()),"yyyy-MM-dd HH:mm:ss");
        return getFinallyDate(cal.getTime());
    }

}
