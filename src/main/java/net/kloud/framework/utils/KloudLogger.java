package net.kloud.framework.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KloudLogger {
	
	private static boolean debug = false;
	private static final Logger logger = LogManager.getLogger("HexxitGear");
	private static final String newLine = "\n====================\n";
	
	public static void setDebug(boolean bug) {
		KloudLogger.debug = bug;
	}
	
	public static void log(final String msg, boolean overrideDebug) {
		if(debug | overrideDebug) {
			System.out.println(newLine);
			logger.info(msg);
			System.out.println(newLine);
		}
	}
	
	public static void log(final String msg) {
		log(msg, false);
	}

}
