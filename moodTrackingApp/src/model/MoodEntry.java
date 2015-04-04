package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MoodEntry {

	private String date;
	private String mood;
	
	public MoodEntry(String mood){
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String date = dateFormat.format(new Date());
		this.setDate(date);
		this.setMood(mood);
	}
	
	public MoodEntry(String date, String mood){
		this.setDate(date);
		this.setMood(mood);
	}

	public String getDayOfWeek(){
		DateFormat dayFormat = new SimpleDateFormat("EE");
		String dayOfWeek = dayFormat.format(new Date(date));
		return dayOfWeek;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}
	
}
