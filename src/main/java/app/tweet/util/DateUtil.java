package app.tweet.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

/**
 * 日付の処理を行うユーティリティクラス
 * @author aoi
 *
 */
public class DateUtil {
	
	private static long MINUTE = 60;
	private static long HOUR = 60 * 60;
	private static long DAY = 60 * 60 * 24;
	private static long YEAR = 60 * 60 * 24 * 365;
	
	public static void test(Object a) {
		System.out.println(a);
	}
	
	public static String getPostTime(String dateFromString) throws ParseException {
		Date dateTo = new Date();
	
		//日付をlong値に変換
		long dateTimeTo = dateTo.getTime();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateFrom = format.parse(dateFromString);
		long dateTimeFrom = dateFrom.getTime();
	  
		//差分の秒を算出
		long diff = ( dateTimeTo - dateTimeFrom  ) / (1000 );
		
		LocalDate dt = dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (diff > YEAR) {
			String resultDate = dt.getYear() + "年 " + dt.getMonthValue() + "月" + dt.getDayOfMonth() + "日";
			return resultDate;
		}
		
		if (diff > DAY) {
			String resultDate = dt.getMonthValue() + "月" + dt.getDayOfMonth() + "日";
			return resultDate;
		}
		
		if (diff > HOUR) {
			return Integer.toString((int)Math.ceil(diff / HOUR)) + " h";
		}
		if (diff > MINUTE) {
			return Integer.toString((int)Math.ceil(diff / MINUTE)) + " m";
		}
		
		return (int)diff + " s";
	}
}
