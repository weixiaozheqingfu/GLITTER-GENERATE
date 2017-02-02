/**
 * 
 */
package jinhetech.oracle.generation.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zmm
 * 
 *         2010-12-14
 */
public class DateUtils {

	public static final String P_YYYYMM = "yyyyMM";
	
	public static final String P_MM_DD_HH_MM = "MM/dd HH:mm";
	
	public static final String P_YYYYMMDD = "yyyyMMdd";

	public static final String P_YYYY_MM_DD = "yyyy-MM-dd";

	public static final String P_YYYY_MM_DD_CN = "yyyy年MM月dd日";

	public static final String P_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	public static final String P_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String P_YYYY_MM_DD_HH_MM_CN = "yyyy年MM月dd日 HH时mm分";

	public static final String P_YYYY_MM_DD_HH_MM_SS_CN = "yyyy年MM月dd日 HH时mm分ss秒";

	public static final String P_YYYY_MM_DD_HH_MM_SS_NO_DELIMITER = "yyyyMMddHHmmss";

	public static final String DAY_BEGIN_TIME = " 00:00:00";

	public static final String DAY_END_TIME = " 23:59:59";

	private static final String FORMAT_NULL_DATE = "--";

	public static String format(Date date, String pattern) {
		String s = FORMAT_NULL_DATE;
		if (date != null) {
			return format(date, pattern, s);
		}
		return s;
	}
	
	public static Date parse(Date date,String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date newDate = null;
		try {
			newDate = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}
	
	public static String format(Date date, String pattern, String errorFormat) {
		String s = errorFormat;
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			try {
				s = sdf.format(date);
			} catch (Exception e) {
			}
		}
		return s;
	}

	public static Date parse(String dateStr, String pattern) {
		Date d = null;
		if (dateStr != null && dateStr.length() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			try {
				d = sdf.parse(dateStr);
			} catch (Exception e) {
			}
		}
		return d;
	}

	/**
	 * @return
	 */
	public static Date getCurrentDateTime() {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * @return
	 */
	public static Date getCurrentDate() {
		Date now = getCurrentDateTime();
		return strToDate(dateToStr(now));
	}

	public static Date strToDate(String s) {
		Date d = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			d = sdf.parse(s);
		} catch (Exception e) {

		}

		return d;
	}

	public static Date strToDateHMS(String s) {
		String ss = s;
		if (s.length() == 16) {
			ss += ":00";
		}
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			d = sdf.parse(ss);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;
	}

	public static String dateToStr(Date date) {
		String s = "";
		if (date == null) {
			return "";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			s = sdf.format(date);
		} catch (Exception e) {

		}

		return s;
	}

	/**
	 * @return
	 */
	public static Date getThisWeekMonday() {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.MONDAY);
		return strToDate(dateToStr(calendar.getTime()));
	}

	/**
	 * 判断某个日期是否在开始和结束日期范围内，精确到天
	 * 
	 * @param now
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isBetweenTwoDate(Date now, Date startDate, Date endDate) {
		if (startDate != null) {
			startDate = strToDate(dateToStr(startDate) + DAY_BEGIN_TIME);
		}
		if (endDate != null) {
			endDate = strToDateHMS(dateToStr(endDate) + DAY_END_TIME);
		}
		return isBetweenTwoDateHMS(now, startDate, endDate);
	}

	/**
	 * 判断某个时间是否在开始和结束时间范围内，精确到毫秒
	 * 
	 * @param now
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isBetweenTwoDateHMS(Date now, Date startDate, Date endDate) {
		if (startDate == null && endDate == null) {
			return true;
		} else if (startDate != null && endDate != null) {
			return now.getTime() >= startDate.getTime() && now.getTime() <= endDate.getTime();
		} else if (startDate != null) {
			return now.getTime() >= startDate.getTime();
		} else if (endDate != null) {
			return now.getTime() <= endDate.getTime();
		}

		return false;
	}

	/**
	 * 根据年数，取得年份列表。
	 * 
	 * @param yearNum
	 *            要取得的年数。
	 * 
	 * @author YangZhenghua
	 * @date 2014-7-15
	 */
	public static List<String> getLastYearsByNum(int yearNum) {
		int currentYear;
		List<String> lstTenYears = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		currentYear = cal.get(Calendar.YEAR);

		for (int i = 0; i < yearNum; i++) {
			lstTenYears.add((currentYear - i) + "");
		}

		return lstTenYears;
	}

	/**
	 * 取得最近十年的年份。
	 * 
	 * @author YangZhenghua
	 * @date 2014-7-15
	 */
	public static List<String> getLastTenYears() {
		return getLastYearsByNum(10);
	}

	/**
	 * 判断是否是闰年
	 * 
	 * @author YangZhenghua
	 * @date 2014-7-21
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 计算给定的年，某个月 有多少天。
	 * 
	 * @author YangZhenghua
	 * @date 2014-7-21
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int calDaysInYearAndMonth(int year, int month) {

		if (month == 2 && isLeapYear(year)) {
			return 29;
		} else if (month == 2 && !isLeapYear(year)) {
			return 28;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		} else {
			return 31;
		}
	}

	/**
	 * 取出指定时间的月和日。
	 * 
	 * @author YangZhenghua
	 * @date 2014-7-28
	 * 
	 * @param dateTime
	 * @return
	 */
	public static String getMonthAndDay(java.sql.Date dateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);

		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);

		return month + "-" + day;
	}
	
	/**
	 * 取出指定时间的天。
	 * @author YangZhenghua  
	 * @date 2014-7-29
	 *
	 * @param dateTime
	 * @return
	 */
	public static int getDay(java.sql.Date dateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		int day = calendar.get(Calendar.DATE);

		return day;
	}
	
	/**
	 * 比较两个日期的月和日是否相同。
	 * 
	 * @author YangZhenghua
	 * @date 2014-7-28
	 * 
	 * @param dateFromTime
	 * @param dateToTime
	 * @return
	 */
	public static boolean compareSameMonthAndDay(java.sql.Date dateFromTime, java.sql.Date dateToTime) {
		return getMonthAndDay(dateFromTime).equals(getMonthAndDay(dateToTime));
	}

	public static void main(String[] args) throws ParseException {
		Date date = addMonth(new Date(), 3);
		String str = dateToStr(date);
		System.out.println(str);
	}

	/**
	 * 
	 * @author changkuan
	 * @param timeStr
	 * @return
	 */
	public static String handleDateStr2Per5Days(String timeStr) {
		if (timeStr != null && timeStr.length() > 2) {//只支持格式*DD
			int dateOfMonth = Integer.parseInt(timeStr.substring(timeStr.length()-2));
			if (dateOfMonth > 30) {
				dateOfMonth = 30;
			}
			dateOfMonth = (dateOfMonth-1)/5*5+1;
			if (dateOfMonth < 10) {
				timeStr = timeStr.substring(0, timeStr.length()-2) + '0' + dateOfMonth;
			} else {
				timeStr = timeStr.substring(0, timeStr.length()-2) + dateOfMonth;
			}
		}
		return timeStr;
	}


	/**
	 * 将列表中指定索引处的时间数据改为候报格式的时间，即归一化为X1和X6号
	 * @author changkuan
	 * @param rowData
	 * @param columnIndex
	 */
	public static void handleDateColumns2Per5Days(List<Object> rowData, int columnIndex) {
		String timeStr = (String) rowData.get(columnIndex);
		rowData.set(columnIndex, handleDateStr2Per5Days(timeStr));
	}

	/**
	 * 将月份转换为季度
	 * @param month
	 * @return
	 */
	public static String convertMonth2Quarter(String month) {
		if (month != null) {
			return "Q" + (Integer.parseInt(month) + 2) / 3;
		}
		return null;
	}
	/**
	 * 日期加减法,得到ii天后的日期
	 * @param date 原日期
	 * @param ii 向前或向后的天数
	 * @return 得到ii天后的日期
	 */
	public static Date addDay(Date date, int ii) {
		Calendar ins = Calendar.getInstance();
		ins.setTime(date);
		ins.set(Calendar.DAY_OF_YEAR, ins.get(Calendar.DAY_OF_YEAR)+ii);
		return ins.getTime();
	}
	
	/**
	 * 日期加减法,得到ii月后的日期
	 * @param date 原日期
	 * @param ii 向前或向后的月数
	 * @return 得到ii月后的日期
	 */
	public static Date addMonth(Date date, int ii) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, ii);
		return calendar.getTime();
	}
}
