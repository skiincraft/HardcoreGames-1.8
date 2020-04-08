package me.skincraft.hardcoregames.api;

public class LongAPI {

	private long days;
	private long hours;
	private long minutes;
	private long seconds;
	private long totaltime;
	
	public LongAPI(long days, long hours, long minutes, long seconds) {
		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}
	
	public long convert() {
		long x = 0L;
		long minute = minutes * 60L;
		long hour = hours * 3600L;
		long day = days * 86400L;
		x = minute + hour + day + seconds;
		long time = System.currentTimeMillis() + x * 1000L;
		totaltime = time;
		return totaltime;
	}
	
	public long fromString(String str) {
		String[] arrayOfString;
		Integer templateInteger;
		Integer integer;
		String[] timeString = str.split(",");
		Integer day = 0, hour = 0, minute = 0, second = 0;

		templateInteger = (arrayOfString = timeString).length;
		for (integer = 0; integer < templateInteger; integer++) {
			String string = arrayOfString[integer];
			if (string.contains("d") || string.contains("D")) {
				day = Integer.valueOf(string.replace("d", "").replace("D", "")).intValue();
			}
			if (string.contains("h") || string.contains("H")) {
				hour = Integer.valueOf(string.replace("h", "").replace("H", "")).intValue();
			}
			if (string.contains("m") || string.contains("M")) {
				minute = Integer.valueOf(string.replace("m", "").replace("M", "")).intValue();
			}
			if (string.contains("s") || string.contains("S")) {
				second = Integer.valueOf(string.replace("s", "").replace("S", "")).intValue();
			}
		}
		this.days = day; this.hours = hour; this.minutes = minute; this.seconds = second;
		
		Long time = convert();
		return time;
	}
	
	
	
	
}
