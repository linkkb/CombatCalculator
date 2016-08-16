package combat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//Literally just Weapon with different stat fields. Should refactor to inherit from common class.
public class Shield {
	String name;
	int melee;
	int ranged;
	int maxDex;
	public static Map<String,Shield> shieldList = new HashMap<String,Shield>();
	static {
		initShieldList(shieldList);
	}
	
	Shield(String inputLine) {
		String[] stats = inputLine.split(" ");
		name = stats[0];
		melee = Integer.parseInt(stats[1]);
		ranged = Integer.parseInt(stats[2]);
		maxDex = Integer.parseInt(stats[3]);
	}
	
	public static Shield get(String shield) {
		return shieldList.get(shield.replaceAll("\\s+","").toLowerCase());
	}
	
	public static boolean isValid(String input) {
		return shieldList.containsKey(input.replaceAll("\\s+","").toLowerCase());
	}
	
	public static void listShields() {
		for(String shield:shieldList.keySet()){
			System.out.println(shield);
		}
	}
	
	public static String getShield() {
		Scanner input = new Scanner(System.in);
		System.out.println("What shield are you using?");
		String shield = input.nextLine();
		while(!isValid(shield)) {
			if(shield.equals("shields")) listShields();
			else System.out.println(shield + " is not a recognized shield type. Type \"shields\" for a list of shields.");
			System.out.println("What shield are you using?");
			shield = input.nextLine();
		}
		return shield;
	}
	
	public static HashMap makeShieldList() {
		HashMap shieldList = new HashMap();
		initShieldList(shieldList);
		return shieldList;
	}
	
	public static void initShieldList(Map<String,Shield> shieldList) {
		try {
			InputStream fstream = Weapon.class.getResourceAsStream("shieldsRaw.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
	
			String stats = br.readLine();
	
			//Read File Line By Line
			while ((stats = br.readLine()) != null)   {
				if(stats.length()>0 && stats.charAt(0)!='/') {
					Shield s = new Shield(stats);
					shieldList.put(s.name, s);
				}
			}
			br.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
