package combat;
import java.util.Scanner;


public class Loadout {
	
	Shield shield;
	Armor armor;
	Weapon weapon;
	boolean helm;
	boolean brgr;
	
	public static void main(String[] args) {

		Loadout load = Loadout.getLoadout();
		load.printLoadout();
		System.out.println("MAC: " + load.mac());
		System.out.println("RAC: " + load.rac());
	}
	
	Loadout() {
		this("none","none","none",false,false);
	}
	
	Loadout(String weapon, String shield, String armor, boolean helm, boolean brgr) {
		this.shield = Shield.get(shield);
		this.armor = Armor.get(armor);
		this.weapon = Weapon.get(weapon);
		this.helm = helm;
		this.brgr = brgr;
	}
	
	public String toString() {
		return "{" + weapon.name + ", "+ shield.name + ", " + armor.name + ", " + helm + ", " + brgr + "}";
	}
	
	public void printLoadout() {
		String output = "Weapon: " + weapon.name + '\n' +
		"Shield: " + shield.name + '\n' +
		"Armor: " + armor.name + '\n';
		if(helm) output += "Helm\n";
		if(brgr) output += "Bracers and Greaves\n";
		
		System.out.println(output);
	}
	
	public static Loadout getLoadout() {
		String weapon = Weapon.getWeapon();
		String shield = Shield.getShield();
		String armor = Armor.getArmor();
		
		boolean helm;
		boolean brgr;
		
		System.out.println("Are you wearing a helm? y/n");
		Scanner input = new Scanner(System.in);
		
		String helmIn = input.nextLine();
		helm = (helmIn.equals("y")||helmIn.equals("yes"));
		
		System.out.println("Are you wearing a bracers and greaves? y/n");
		String brgrIn = input.nextLine();
		brgr = (brgrIn.equals("y")||brgrIn.equals("yes"));
		
		return new Loadout(weapon,shield,armor,helm,brgr);
	}
	
	public int mac() {
		int parryB = weapon.parryBonus;
		int armorC= armor.armorClass;
		int shieldM = shield.melee;
		int h = 0;
		if(helm) h = 1;
		int bg = 0;
		if(brgr) bg = 1;
		return 8+parryB+armorC+shieldM+h+bg;
	}
	
	public int rac() {
		int armorC = armor.armorClass;
		int shieldR = shield.ranged;
		int h = 0;
		if(helm) h = 1;
		int bg = 0;
		if(brgr) bg = 1;
		return 8+armorC+shieldR+h+bg;
	}
}
