package combat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Weapon {
	String name;
	int parryBonus;
	int damageDice;
	int damageMod;
	
	//A map from each weapon's sanitized name to a Weapon object with the appropriate stats.
	//Currently draws from weaponsRaw.txt; consider modifying to accommodate spaces in name fields.
	//Maybe it should instead return a copy of the weapon, in case modifying weapons becomes a thing?
	public static final Map<String,Weapon> weaponList = new HashMap<String,Weapon>();
	static{
		initWeaponList(weaponList);
	}
	
	//returns the Weapon object with the same name as the sanitized input
	public static Weapon get(String weapon) {
		return weaponList.get(weapon.replaceAll("\\s+","").toLowerCase());
	}
	
	//Does this input, when sanitized, represent an existing weapon?
	public static boolean isValid(String input) {
		return weaponList.containsKey(input.replaceAll("\\s+","").toLowerCase());
	}
	
	//Print out the list of valid weapon names.
	//Currently unordered - would it be possible to create a list when initializing weaponList?
	public static void listWeapons() {
		for(String weapon:weaponList.keySet()){
			System.out.println(weapon);
		}
	}
	
	//Returns a valid weapon based on user input at the command line. Not useful for GUIs.
	public static String getWeapon() {
		Scanner input = new Scanner(System.in);
		System.out.println("What weapon are you using?");
		String weapon = input.nextLine();
		while(!isValid(weapon)) {
			if(weapon.equals("weapons")) listWeapons();
			else System.out.println(weapon + " is not a recognized weapon type. Type \"weapons\" for a list of weapons.");
			System.out.println("What weapon are you using?");
			weapon = input.nextLine();
		}
		return weapon;
	}
	
	//Creates a weapon with stats from a string, as lines from weaponsRaw.txt; format as follows:
	//name parrybonus damagedice damagemod
	//Consider modifying to accommodate spaces in name fields.
	Weapon(String inputLine) {
		String[] stats = inputLine.split(" ");
		name = stats[0];
		parryBonus = Integer.parseInt(stats[1]);
		damageDice = Integer.parseInt(stats[2]);
		damageMod = Integer.parseInt(stats[3]);
	}
	
	//Creates a weapon from direct data types.
	Weapon(String n, int parry, int dice, int mod) {
		name = n;
		parryBonus = parry;
		damageDice = dice;
		damageMod = mod;
	}
	
	//Prints out information about a weapon, writ longfrom.
	public void printWeapon() {
		System.out.println(name + ": parry +" + parryBonus + ", damage d" + damageDice + "+" + damageMod);
	}
	
	//For simple printing of Weapon types.
	public String toString() {
		return name;
	}
	
	//Creates a new weaponList, initializes it, and returns it.
	public static HashMap makeWeaponList() {
		HashMap weaponList = new HashMap();
		initWeaponList(weaponList);
		return weaponList;
	}
	
	//Reads lines from weaponsRaw.txt, attempts to parse as weapons if not commented out,
	//and adds them to the <String -> Weapon> map it was passed. (i.e. weaponList)
	public static void initWeaponList(Map<String,Weapon> weaponList) {
		try {
			InputStream fstream = Weapon.class.getResourceAsStream("weaponsRaw.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
	
			String stats = br.readLine();
	
			//Read File Line By Line
			while ((stats = br.readLine()) != null)   {
				if(stats.length()>0 && stats.charAt(0)!='/') {
					Weapon w = new Weapon(stats);
					weaponList.put(w.name, w);
				}
			}
			br.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}