package combat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Gear {
	String name;
	int value;
	int weight;
	
	//A map from each gear's sanitized name to a Gear object with the appropriate stats.
	//Currently draws from gearRaw.txt; consider modifying to accommodate spaces in name fields.
	//Maybe it should instead return a copy of the gear, in case modifying gear becomes a thing?
	public static final Map<String,Gear> list = makeGearList();
	public static final ArrayList<String> names = new ArrayList<String>();
	static{
		initGearList();
	}
	
	//returns the Gear object with the same name as the sanitized input
	public static Gear get(String gear) {
		return list.get(gear.replaceAll("\\s+","").toLowerCase());
	}
	
	//Does this input, when sanitized, represent an existing gear?
	public static boolean isValid(String input) {
		return list.containsKey(input.replaceAll("\\s+","").toLowerCase());
	}
	
	//Print out the list of valid gear names.
	//Currently unordered - would it be possible to create a list when initializing list?
	public static void listGear() {
		for(String gear:list.keySet()){
			System.out.println(gear);
		}
	}
	
	//Returns a valid gear based on user input at the command line. Not useful for GUIs.
	public static String getGear() {
		Scanner input = new Scanner(System.in);
		System.out.println("What gear are you using?");
		String gear = input.nextLine();
		while(!isValid(gear)) {
			if(gear.equals("gear")) listGear();
			else System.out.println(gear + " is not a recognized gear type. Type \"gear\" for a list of gear.");
			System.out.println("What gear are you using?");
			gear = input.nextLine();
		}
		return gear;
	}
	
	//Creates a gear with stats from a string, as lines from gearRaw.txt; format as follows:
	//name parrybonus damagedice damagemod
	//Consider modifying to accommodate spaces in name fields.
	Gear(String inputLine) {
		String[] stats = inputLine.split(" ");
		name = stats[0];
	}
	
	//Creates a gear from direct data types.
	Gear(String n, int price, int stone) {
		name = n;
		value = price; 
		weight = stone;
	}
	
	//Prints out information about a gear, writ longfrom.
	public void printGear() {
		System.out.println(name + ": " + value + "sp, " + weight +" stones");
	}
	
	//For simple printing of Gear types.
	public String toString() {
		return name;
	}
	
	//Creates a new list, initializes it, and returns it.
	public static HashMap makeGearList() {
		HashMap<String,Gear> list = new HashMap<String,Gear>();
		initGearList();
		return list;
	}
	
	//Reads lines from gearRaw.txt, attempts to parse as gear if not commented out,
	//and adds them to the <String -> Gear> map it was passed. (i.e. list)
	public static void initGearList() {
		try {
			InputStream fstream = Gear.class.getResourceAsStream("gearRaw.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
	
			String stats = br.readLine();
	
			//Read File Line By Line
			while ((stats = br.readLine()) != null)   {
				if(stats.length()>0 && stats.charAt(0)!='/') {
					Gear w = new Gear(stats);
					list.put(w.name, w);
				}
			}
			br.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}