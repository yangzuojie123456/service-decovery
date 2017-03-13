package com.miapc.ipudong.utils;


import com.miapc.ipudong.constant.DatePatten;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>时间工具</p>
 */
public class DateUtil {
    /**
     * 获取SimpleDateFormat
     *
     * @param parttern 日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException 异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(String parttern) throws RuntimeException {
        return new SimpleDateFormat(parttern);
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date     日期
     * @param parttern 日期格式
     * @return 日期字符串 string
     */
    public static String dateToString(Date date, String parttern) {
        String dateString = "";
        if (date != null) {
            try {
                dateString = getDateFormat(parttern).format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dateString;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date       日期
     * @param datePatten 日期风格
     * @return 日期字符串 string
     */
    public static String dateToString(Date date, DatePatten datePatten) {
        String dateString = "";
        if (datePatten != null) {
            dateString = dateToString(date, datePatten.getValue());
        }
        return dateString;
    }

    /**
     * 获取日期。默认yyyy-MM-dd格式。失败返回null。
     *
     * @param date 日期
     * @return 日期 date
     */
    public static String getDate(Date date) {
        return dateToString(date, DatePatten.YYYY_MM_DD);
    }

    /**
     * 获取日期。 失败返回null。
     *
     * @param date   日期
     * @param patten the patten
     * @return String date
     */
    public static String getDate(Date date, DatePatten patten) {
        return dateToString(date, patten);
    }

    /**
     * Gets days begain.
     *
     * @param date the date
     * @return the days begain
     */
    public static Date getDaysBegain(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.set(Calendar.HOUR_OF_DAY, 0);
        cale.set(Calendar.MINUTE, 0);
        cale.set(Calendar.SECOND, 0);
        return cale.getTime();
    }

    /**
     * Gets days end.
     *
     * @param date the date
     * @return the days end
     */
    public static Date getDaysEnd(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.set(Calendar.HOUR_OF_DAY, 23);
        cale.set(Calendar.MINUTE, 59);
        cale.set(Calendar.SECOND, 59);
        return cale.getTime();
    }

    /**
     * Gets month begain.
     *
     * @return the month begain
     */
    public static Date getMonthBegain() {
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, cale.getActualMinimum(Calendar.DAY_OF_MONTH));
        cale.set(Calendar.HOUR_OF_DAY, 0);
        cale.set(Calendar.MINUTE, 0);
        cale.set(Calendar.SECOND, 0);
        return cale.getTime();
    }

    /**
     * Gets month end.
     *
     * @return the month end
     */
    public static Date getMonthEnd() {
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
        cale.set(Calendar.HOUR_OF_DAY, 23);
        cale.set(Calendar.MINUTE, 59);
        cale.set(Calendar.SECOND, 59);
        return cale.getTime();
    }

    /**
     * Gets date in days.
     *
     * @param days the days
     * @return the date in days
     */
    public static Date getDateInDays(int days) {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.DATE, days);
        return cale.getTime();
    }
}


