package com.xz.list.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xz on 2017/7/20 0020.
 */
public class DateTimeUtil {

    public static final String GROUP_BY_EACH_DAY = "yyyyMMdd";
    public static final String GROUP_BY_EACH_DAYSM = "yyyyMMddHHmmss";
    public static final String GROUP_BY_EACH_SECOND = "yyyyMMddHHmm";
    public static final String TIMEsubordinate = "0.000";
    public final static String TIME_PATTERN = "HH:mm:ss";// 定义标准时间格式
    public final static String DATE_PATTERN_1 = "yyyy/MM/dd";// 定义标准日期格式1
    public final static String DATE_PATTERN_2 = "yyyy-MM-dd";// 定义标准日期格式2
    public final static String DATE_PATTERN_3 = "yyyy/MM/dd HH:mm:ss";// 定义标准日期格式3，带有时间
    public final static String DATE_PATTERN_4 = "yyyy/MM/dd HH:mm:ss E";// 定义标准日期格式4，带有时间和星期
    public final static String DATE_PATTERN_5 = "yyyy年MM月dd日 HH:mm:ss E";// 定义标准日期格式5，带有时间和星期
    public final static String DATE_PATTERN_6 = "yyyy-MM-dd HH:mm:ss";// 定义标准日期格式6，带有时间
    public final static String DATE_PATTERN_7 = "yyyy年MM月dd日";// 定义标准日期格式7
    public final static String DATE_PATTERN_8 = "yyyy-MM-dd HH:mm";// 定义标准日期格式8，带有时间
    public final static String DATE_PATTERN_9 = "yy-MM-dd HH时";// 定义标准日期格式9，带有时间
    public final static String DATE_PATTERN_10 = "yy/MM/dd HH:mm";// 定义标准日期格式10，带有时间
    public final static String DATE_PATTERN_11 = "yy.MM.dd";// 定义标准日期格式11，带有时间
    public final static String DATE_PATTERN_12 = "yyyyMMdd";// 定义标准日期格式11，带有时间
    public final static String DATE_PATTERN_13 = "yyyy-MM-dd_HH:mm:ss";// 定义标准日期格式13，带有时间


    /**
     * 方法说明:把一个日期，按照某种格式 格式化输出
     * 方法名称:formatDate
     * return
     * 返回值:String
     */
    public static String formatDate(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        }
        return "";
    }
}
