package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import model.DailyMood;

public class Main {

	public static void main(String args[]) throws IOException{
		DailyMood m = new DailyMood();
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in), 1);
		boolean userOverridesEntry = false;
		if (m.doesEntryExistToday()){
			System.out.println("You have already entered your mood for today.");
			System.out.println("Would you like to re-enter your mood? Y/N \n");
			if (stdin.readLine().equalsIgnoreCase("y")){
				userOverridesEntry = true;
			}
		}
		String currentLine; 

		if (!m.doesEntryExistToday() || userOverridesEntry) {	
			System.out.println("How are you feelin' today? Please select your mood. \n");
			for (Map.Entry<Integer, String> entry : m.getMoodMap().entrySet()) {
			    Integer moodNumber = entry.getKey();
			    String mood = entry.getValue();
				System.out.println(moodNumber + " - " + mood);
			}
	
			while (true){
				currentLine = stdin.readLine();
			    int moodValue = 0;
			    try {
			    	moodValue = Integer.valueOf(currentLine);
			    } catch (NumberFormatException e) {
					System.out.println("Please choose your mood - 1, 2, 3, 4, 5");
			    }
				if (m.getMoodMap().containsKey(moodValue)){
						m.updateMoodList(m.getMoodList(),m.getMoodMap().get(moodValue), userOverridesEntry);
						break;
					}
				
				System.out.println("Please choose your mood - 1, 2, 3, 4, 5");
			}
		}
			
			ArrayList<String> averageMoods = m.getAverageMood(m.calculateAverage(m.getMoodList()));
			String printLine;
			if (!averageMoods.get(0).equals(averageMoods.get(1))){
				printLine = "between "+averageMoods.get(0)+ " and "+averageMoods.get(1);
			}
			else {
				printLine = averageMoods.get(0);
			}
			System.out.println("\nYour overall average mood for the past "
			+m.getMoodList().size()+" days is: "+printLine);


			System.out.println("\nYour average mood by day of the week: ");
			String printString;
			for (Map.Entry<String, ArrayList<String>> moods : m.getAverageMoodByDayOfWeek().entrySet()) {
				if (!moods.getValue().get(0).equals(moods.getValue().get(1))){
					printString = "between "+ moods.getValue().get(0)+" and "
							+ moods.getValue().get(1);	
				}
				else{
					printString = moods.getValue().get(0);
				}
				System.out.println(moods.getKey()+" : "+printString);
			}

			System.out.println("\nDo you want to see your moods for the past "+m.getMoodList().size()+" days? \nY/N?");
	
			while (true){
				currentLine = stdin.readLine();
				if (currentLine.equalsIgnoreCase("y") || currentLine.equalsIgnoreCase("n")){
					if(currentLine.equalsIgnoreCase("y")){
						m.printMoodList(m.getMoodList());
						
					}
					break;
				}
				System.out.println("\nPlease enter Y or N");
			}
		
		}
}
