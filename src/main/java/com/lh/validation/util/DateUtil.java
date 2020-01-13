package com.lh.validation.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    private static String yyyy_MM_dd = "yyyy-MM-dd";
    private static String yyyy_MM_dd_00_00_00 = "yyyy-MM-dd 00:00:00";
    private static String yyyy_MM_dd_23_59_59 = "yyyy-MM-dd 23:59:59";
    private static Long times4OneDay = 24L*60*60*1000;

    public static String getYyyymmddHHmmss(Date date){
        if (date==null){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        return simpleDateFormat.format(date);
    }

    /**
     *
     * @param dateTime 年月日时分秒 eg:yyyy-MM-dd HH:mm:ss
     * @return 获取 秒
     */
    public static long strToLong(String dateTime){
        if(!StringUtils.isEmpty(dateTime)){
            try {
                return new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss).parse(dateTime).getTime()/1000;
            } catch (ParseException e) {
                throw new RuntimeException("日期转换错误");
            }
        }
        throw new RuntimeException("日期字符串不能为空");
    }

    /**
     * 根据秒转yyyy-MM-dd HH:mm:ss
     * @param time
     * @return
     */
    public static String timeToDateStr(Long time){
        if (null==time){
            return null;
        }
        return  new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss).format(new Date(time*1000));
    }

    /**
     * 获取date的n天前的日期
     * @param date
     * @param n
     * @return
     */
    public static Date getDate4PreDays(Date date, Integer n) {
        if (null==n || n==0){
            return date;
        }
        date.setTime(date.getTime()-n*DateUtil.times4OneDay);
        return date;

    }

    public static String getDateStart(Date date) {
        if (null==date){
            return null;
        }
        SimpleDateFormat format1 = new SimpleDateFormat(yyyy_MM_dd_00_00_00);
        String startStr =format1.format(date);

        return startStr;
    }

    public static String getDateEnd(Date date) {
        if (null==date){
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(yyyy_MM_dd_23_59_59);
        String endStr =format.format(date);
        return endStr;
    }

    public static void main(String[] args) {
        String str = "2019-12-16 00:00:04";
        String end = "2019-12-16 10:08:28";
        System.out.println(strToLong(str));
        System.out.println(strToLong(end));
    }


}
