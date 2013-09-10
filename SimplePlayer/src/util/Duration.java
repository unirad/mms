package util;

public class Duration {
	
	int minutes;
	int seconds;
	String humanReadableForm;
	public Duration(long microseconds){
		long miliseconds = microseconds/1000;
		seconds = (int)Math.round(((double)miliseconds/1000));
	    minutes = seconds/60;
	    humanReadableForm = getMinutes() + ":" + getSeconds()%60;
	    
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	public String getHumanReadableForm() {
		return humanReadableForm;
	}

	
	


}
