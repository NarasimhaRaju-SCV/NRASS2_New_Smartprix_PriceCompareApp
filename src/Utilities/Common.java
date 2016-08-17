package Utilities;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
	/**
	 * 
	 * @return timestampString in the format yyyyMMddhhmmssSSS
	 */
	public static String getTimeStamp()
	{
		return new SimpleDateFormat ("yyyyMMddhhmmssSSS").format(new Date( ));
	}
    
}
