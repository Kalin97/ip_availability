package program.user;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Interval 
{
	private final String dateFormat = "yyyy­MM­dd'T'HH'_'mm'_'ss.SSSZ";
	
	private DateFormat dateFormater;
	private Date from;
	private Date to;
	
	public Interval(Date from)
	{
		dateFormater = new SimpleDateFormat(dateFormat);
		this.from = from;
	}
	
	public void SetFrom(Date from)
	{
		this.from = from;
	}
	
	public void SetTo(Date to)
	{
		this.to = to;
	}
	
	public Date GetFrom()
	{
		return from;
	}
	
	public Date GetTo()
	{
		return to;
	}
	
	public String FormatedFromTo()
	{
		String fromString = dateFormater.format(from);
		String toString = dateFormater.format(to);
		
		return fromString + ":" + toString;
	}
}
