package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class DailyMood { 
	
	private MoodEntry moodEntry;
	private ArrayList<MoodEntry> moodList;
	private HashMap<Integer, String> moodMap;
	private static File textFile = new File("mood.txt");
	
	public DailyMood() throws IOException{
		moodList = new ArrayList<MoodEntry>();
		moodMap = new HashMap<Integer, String>();
		this.mapMoods(moodMap);
		this.setMoodList(textFile.getName());
	}
	
	public void mapMoods(HashMap<Integer, String> moodMap){
		moodMap.put(1, "Depressed");
		moodMap.put(2, "Crappy");
		moodMap.put(3, "Meh");
		moodMap.put(4, "Happy");
		moodMap.put(5, "Thrilled");
	}

	public boolean doesEntryExistToday(){
		boolean doesEntryExistToday = false;
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String currentDate = dateFormat.format(new Date());
		if (!moodList.isEmpty() && currentDate.equals(moodList.get(moodList.size()-1).getDate())){
			doesEntryExistToday = true;		
		}
		return doesEntryExistToday;
	}

	public void updateMoodList(ArrayList<MoodEntry> moodList, String mood, boolean userOverridesEntry) throws IOException {
		MoodEntry entry = new MoodEntry(mood);
		if (userOverridesEntry){
			moodList.set(moodList.size()-1, entry);
		} else {
			moodList.add(entry);
		}
		this.writeMoodToTextFile(moodList);
	}

	public void writeMoodToTextFile(ArrayList<MoodEntry> moodList) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(textFile));
		for (MoodEntry entry: moodList){
			writer.write(entry.getDate()+"		"+entry.getMood()+"\n");
		}
		writer.close();
	}
	
	public ArrayList<MoodEntry> getMoodList() {
		return moodList;
	}

	public void setMoodList(String filename) throws IOException {
		try {
			if (textFile.exists()){
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String inputLine;
			while ((inputLine = br.readLine())!= null){
				String[] entryArray= inputLine.split("		");
				MoodEntry entry = new MoodEntry(entryArray[0], entryArray[1]); 
				moodList.add(entry);
			}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
	public Integer convertMoodToValue(String mood){
		Integer moodValue = 0;
		for (Map.Entry<Integer, String> moods : moodMap.entrySet()) {
			if (moods.getValue().equals(mood)){
				moodValue = moods.getKey();
			
			}
		}
		return moodValue;
	}
	
	public double calculateAverage(ArrayList<MoodEntry> moodList){
		Integer moodValue = 0;
		Integer sum = 0;
		Double average = null;
		for (MoodEntry entry: moodList){
			moodValue =convertMoodToValue(entry.getMood());
			sum = sum+ moodValue;
			average = sum/(double)moodList.size();
		}			
	    return average;
	}
	
	
	public ArrayList<String> getAverageMood(double averageMoodValue){
		ArrayList<String> averageMoods = new ArrayList<String>();
		Integer lowerBound = (int)Math.floor(averageMoodValue);
		Integer upperBound = (int)Math.ceil(averageMoodValue);
		averageMoods.add(moodMap.get(lowerBound));
		averageMoods.add(moodMap.get(upperBound));
		
		return averageMoods;
	}

	public HashMap<String, ArrayList<String>> getAverageMoodByDayOfWeek(){
		HashMap<String, ArrayList<String>> averageMoodsByDayMap = new HashMap<String, ArrayList<String>>();
		for (DayOfWeek day: DayOfWeek.values()){
			ArrayList<MoodEntry> moodListDayOfWeek = new ArrayList<MoodEntry>();
			for (MoodEntry moodEntry: moodList){
				if(moodEntry.getDayOfWeek().equals(day.toString())){
					moodListDayOfWeek.add(moodEntry);
					averageMoodsByDayMap.put(day.toString(), getAverageMood(calculateAverage(moodListDayOfWeek)));
				}
			}
		}
		return averageMoodsByDayMap;
		
	}
	
	public HashMap<Integer, String> getMoodMap() {
		return moodMap;
	}

	public void setMoodMap(HashMap<Integer, String> moodMap) {
		this.moodMap = moodMap;
	}

	public File getTextFile() {
		return textFile;
	}

	public void setTextFile(File textFile) {
		DailyMood.textFile = textFile;
	}

	public void printMoodList(ArrayList<MoodEntry> moodList){
		for (MoodEntry entry: moodList){
			System.out.println(entry.getDate()+"	"+entry.getMood()+"\n");
		}
	}

	public MoodEntry getMoodEntry() {
		return moodEntry;
	}

	public void setMoodEntry(MoodEntry moodEntry) {
		this.moodEntry = moodEntry;
	}
	
}
