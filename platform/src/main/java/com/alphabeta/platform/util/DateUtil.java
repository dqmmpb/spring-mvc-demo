package com.alphabeta.platform.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {

    private static final SimpleDateFormat dt14 = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat dt14long = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dt10dot = new SimpleDateFormat("yyyy.MM.dd");
    private static final SimpleDateFormat dt18dot = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    private static final SimpleDateFormat dt8 = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat dt10 = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat dt10Ch = new SimpleDateFormat("yyyy年MM月dd日");
    private static final SimpleDateFormat time8 = new SimpleDateFormat("HHmmss");
    private static final SimpleDateFormat dt6 = new SimpleDateFormat("yyyyMM");
    private static final SimpleDateFormat dt4 = new SimpleDateFormat("yyyy");
    private static final SimpleDateFormat dt17 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static final SimpleDateFormat shortDay = new SimpleDateFormat("MMdd");
    private static final SimpleDateFormat day = new SimpleDateFormat("dd");
    private static final SimpleDateFormat dt12 = new SimpleDateFormat("yyyyMMddHHmm");
    private static final SimpleDateFormat dt12long = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat dt_Zh = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    private static final SimpleDateFormat dt14dot = new SimpleDateFormat("yyyy.MM.dd HH:mm");

    public static String d18dotFormat(Date date) {
        return dt18dot.format(date);
    }

    public static String d14dotFormat(Date date) {
        return dt14dot.format(date);
    }

    public static String dayFormat(Date date) {
        return day.format(date);
    }

    public static String shortDayFormat(Date date) {
        return shortDay.format(date);
    }

    public static String dt4Format(Date date) {
        return dt4.format(date);
    }

    public static String dt6Format(Date date) {
        return dt6.format(date);
    }

    public static String time8Format(Date date) {
        return time8.format(date);
    }

    public static String dt10dotFormat(Date date) {
        return dt10dot.format(date);
    }

    public static String dt10Format(Date date) {
        return dt10.format(date);
    }

    public static String dt10FormatCh(Date date) {
        return dt10Ch.format(date);
    }

    public static String dt14LongFormat(Date date) {
        return dt14long.format(date);
    }

    public static String dt14FromDate(Date date) {
        return dt14.format(date);
    }

    public static String dt8FormDate(Date date) {
        return dt8.format(date);
    }

    public static String dt17FromDate(Date date) {
        return dt17.format(date);
    }

    public static String dt12FromDate(Date date) {
        return dt12.format(date);
    }

    public static String d12longFormat(Date date) {
        return dt12long.format(date);
    }

    public static Date dt8FromStr(String date) {
        try {
            return dt8.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date dt10FromStr(String date) {
        try {
            return dt10.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date dt12LongFromStr(String date) {
        try {
            return dt12long.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date dt14LongFromStr(String date) {
        try {
            return dt14long.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String dtZhFormat(Date data) {
        return dt_Zh.format(data);
    }

    public static Date getBeginDateTime(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getEndDateTime(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    public static Date getWeekStart(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        return c.getTime();
    }

    public static Date getWeekEnd(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        return c.getTime();
    }

    public static Date getMonthStart(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    public static Date getMonthEnd(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        c.add(Calendar.DAY_OF_YEAR, -1);
        return c.getTime();
    }

    public static Date getNextYearDateFromStr(String date) {
        Calendar c = Calendar.getInstance();
        c.setTime(DateUtil.dt10FromStr(date));
        c.add(Calendar.YEAR, 1);
        c.add(Calendar.DAY_OF_YEAR, -1);
        return c.getTime();
    }

    public static Date addDay(Date date, int days) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }


    public static void main(String[] arg) throws ParseException {
        //System.out.println(calculateDateMonthGap(dt10.parse("2016-02-05"), dt10.parse("2017-02-04")));
    }

    public static int calculateDateMonthGap(Date startDate, Date endDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(startDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(endDate);
        cal2.add(Calendar.DAY_OF_YEAR, 1);
        int extMonth = 0;
        if (dayFormat(cal2.getTime()).compareTo(dayFormat(cal1.getTime())) < 0) {
            extMonth = -1;
        }
        int c = (cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR)) * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
        return c + extMonth;
    }

    public static long calculateDayGap(Date startDate, Date endDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(startDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(endDate);
        long rstFlag = (cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 3600 * 24) + 1;
        if (rstFlag <= 0) {
            return 0;
        }
        return rstFlag;
    }

    public static int calculateDateYearGap(String startDate, String endDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(dt10FromStr(startDate));
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(dt10FromStr(endDate));
        int extYear = 0;
        //如果开始日期的日月<=结束日期的日月则extyear=1
        if (shortDayFormat(cal2.getTime()).compareTo(shortDayFormat(cal1.getTime())) >= 0) {
            extYear = 1;
        }
        return cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) + extYear;
    }

    public static String extractDateStrFromStr(String date) {
        Pattern pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}");
        Matcher matcher = pattern.matcher(date);
        String dateStr = null;
        if (matcher.find()) {
            dateStr = matcher.group(0);
        }
        if (dateStr != null) {
            return dateStr;
        } else {
            pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{2}");
            matcher = pattern.matcher(date);
            if (matcher.find()) {
                dateStr = matcher.group(0);
            }
            if (dateStr != null) {
                return dateStr;
            } else {
                return null;
            }
        }
    }

    public static int[] getDateLength(String fromDate, String toDate) {
        Calendar c1 = getCal(fromDate);
        Calendar c2 = getCal(toDate);
        int[] p1 = {c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)};
        int[] p2 = {c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DAY_OF_MONTH)};
        return new int[]{p2[0] - p1[0], p2[0] * 12 + p2[1] - p1[0] * 12 - p1[1], (int) ((c2.getTimeInMillis() - c1.getTimeInMillis()) / (24 * 3600 * 1000))};
    }

    static Calendar getCal(String date) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8, 10)));
        return cal;
    }

    public static Date extractTimeFromStr(String date) {
        Pattern pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}");
        Matcher matcher = pattern.matcher(date);
        String dateStr = null;
        if (matcher.find()) {
            dateStr = matcher.group(0);
        }
        if (dateStr != null) {
            return dt14LongFromStr(dateStr);
        } else {
            pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{2}");
            matcher = pattern.matcher(date);
            if (matcher.find()) {
                dateStr = matcher.group(0);
            }
            if (dateStr != null) {
                return dt10FromStr(dateStr);
            } else {
                return null;
            }
        }
    }

    //比较2个时间差几天
    public static long getDistanceDays(String str1, String str2) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long days = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(days);
        return days;
    }
}
