package helper;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateTimeHelper {
	
	public static String time2Date(long time){
    	Date date01 = new Date();
		date01.setTime(time);
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
		String dateFormatted = formatter.format(date01);
		return dateFormatted;
    }
	
	public static String timestamp2String(Time time, Date date) {
		String timeAgo = "hace ";
		long comment = time.getTime()+date.getTime()+3600000;
		comment/=1000;
		Calendar calendar = new GregorianCalendar();
		long current = calendar.getTime().getTime();
		current/=1000;
		long difference = current - comment;
		if (difference < 60) {
			timeAgo += difference;
			timeAgo += " segundo";
		} else if (difference < 3600) {
			difference/=60;
			timeAgo += difference;
			timeAgo += " minuto";
		} else if (difference < 86400) {
			difference/=3600;
			timeAgo += difference;
			timeAgo += " hora";
		}else  {
			difference/=86400;
			timeAgo += difference;
			timeAgo += " día";
		}
		if(difference!=1) timeAgo+="s";
		return timeAgo;
	}
}
