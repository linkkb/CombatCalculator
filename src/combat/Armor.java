package combat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//Literally just Weapon with different stat fields. Should refactor to inherit from common class.
public class Armor {
	String name;
	int armorClass;
	int maxDex;
	public static Map<String,Armor> armorList = new HashMap<String,Armor>();
	static {
		initArmorList(armorList);
	}
	
	Armor(String inputLine) {
		String[] stats = inputLine.split(" ");
		name = stats[0];
		armorClass = Integer.parseInt(stats[1]);
		maxDex = Integer.parseInt(stats[2]);
	}
	
	public static Armor get(String armor) {
		return armorList.get(armor.replaceAll("\\s+","").toLowerCase());
	}
	
	public static boolean isValid(String input) {
		return armorList.containsKey(input.replaceAll("\\s+","").toLowerCase());
	}
	
	public static void listArmor() {
		for(String armor:armorList.keySet()){
			System.out.println(armor);
		}
	}
	
	public static String getArmor() {
		Scanner input = new Scanner(System.in);
		System.out.println("What armor are you using?");
		String armor = input.nextLine();
		while(!isValid(armor)) {
			if(armor.equals("armor")) listArmor();
			else System.out.println(armor + " is not a recognized armor type. Type \"armor\" for a list of armors.");
			System.out.println("What armor are you using?");
			armor = input.nextLine();
		}
		return armor;
	}
	
	public static HashMap makeArmorList() {
		HashMap armorList = new HashMap();
		initArmorList(armorList);
		return armorList;
	}
	
	public static void initArmorList(Map<String,Armor> armorList) {
		try {
			InputStream fstream = Weapon.class.getResourceAsStream("armorRaw.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
	
			String stats = br.readLine();
	
			//Read File Line By Line
			while ((stats = br.readLine()) != null)   {
				if(stats.length()>0 && stats.charAt(0)!='/') {
					Armor a = new Armor(stats);
					armorList.put(a.name, a);
				}
			}
			br.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
