package sc.my.kitchen.entity;

public class AlarmTime {

	private int hour;
	private int minute;
	
	public AlarmTime() {
		super();
	}

	public AlarmTime(int hour, int minute) {
		super();
		this.hour = hour;
		this.minute = minute;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	
	
}
