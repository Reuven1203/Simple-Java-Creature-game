// -------------------------------------------------------
// Assignment 4
// Written by: Reuven Ostrofsky - 40188881
// For COMP 248 Section EB-X – Winter 2021
// Written on 03/31/2021
// --------------------------------------------------------

//The purpose of this class is to store information about a player of the Battle Game into methods.

import java.util.Date; //import date class
public class Creature {
	
	//Attributes
	private static final int FOOD2HEALTH = 6; //Food converted to health constant
	private static final int HEALTH2POWER = 4; //Health converted to power constant
	private static int numStillAlive = 0; //Amount of creatures alive
	private String name; //Creature's name
	private int foodUnits; //Amount of food units
	private int healthUnits;//Amount of health units
	private int firePowerUnits; //Amount of fire power units
	private Date dateCreated; //Date creature was created 
	private Date dateDied; //Date creature died
	
	//Constructor
	public Creature(String name) {
		numStillAlive++; //increment number of alive creatures by 1 with every creature created
		this.name = name; //Name as the parameter
		
		do {
		foodUnits = (int)(Math.random()*13); //Store random amount of food units (1-12)
		}while (foodUnits == 0); //Keep generating random integer until integer is non-zero
		
		do {
		healthUnits = (int)(Math.random()*8); //Store random amount of heaht units (1-7)
		}while (healthUnits == 0);//Keep generating random integer until integer is non-zero
		
		
		firePowerUnits = (int)(Math.random()* 11); //Store random amount of fire power units (0-10)
		
		
		dateCreated = new Date(); //Create current date
		dateDied = null;
		normalizeUnits(); // normalize units in the end
	
	}
	
	
	//***** Mutator methods *****//
	
	//set Name method
	public void setName(String newName) {
		this.name = newName;
	}
	//set Health units method
	public void setHealthUnit(int n) {
		this.healthUnits = n;
	}
	//set Food Units method
	public void setFoodUnit(int n) {
		this.foodUnits = n;
	}
	
	
	//---------------------//
	
	

	//***** Accessor methods *****//

	//get name method
	public String getName() {
		return name;
	}
	//get food units method 
	public int getFoodUnits() {
		return foodUnits;
	}
	//get Health units method
	public int getHealthUnits() {
		return healthUnits;
	}
	//get fire power units method
	public int getFirePowerUnits() {
		return firePowerUnits;
	}
	// get date created method
	public Date getDateCreated() {
		return dateCreated;
	}
	//get date died method
	public Date getDateDied() {	
		return dateDied;
	}
	public static int getNumStillAlive() {
		return numStillAlive;
	}
	//--------------------//
	
	//**** Other methods ****//
	
	//Check if creature is alive
	public boolean isAlive() {
		return dateDied == null;
	}
	//Give creature amount fo food
	public int earnFood() {
		int earnedFood = (int)(Math.random()*16); //Generate random number of earned food units
		foodUnits += earnedFood; //Add random generated number of earned food units to food units
		normalizeUnits(); //Normalize units
		return earnedFood; //Return earned food to access amount of food units earned
		
	}

	//Creature attacking another creature method
	public void attacking(Creature player) {		
		foodUnits += (player.foodUnits+1)/2;//Add 50% of food units from attacked creature to attacking creature

		player.foodUnits -= (player.foodUnits+1)/2;//remove 50% food units of attacked creature

		healthUnits += (player.healthUnits+1)/2;//Add 50% of health units from attacked creature to attacking creature

		player.healthUnits -= (player.healthUnits+1)/2;//remove 50% health units of attacked creature

		reduceFirePowerUnits(2);; //Cost of attacking is 2 fire power units
		normalizeUnits(); //normalize units at the end

		if (player.healthFoodUnitsZero()) //Verify if creature has enough units
			player.died();//Call died method if creature is killed
	}
	//Checks whether creature still has units
	public boolean healthFoodUnitsZero() {
		return healthUnits == 0 && foodUnits == 0;
	}

	//Creature dies method
	private void died() {
		dateDied = new Date(); //Creates new current date (date died)
		numStillAlive--;//Decreases amount of creatures still alive by 1
	}

	//Default to string method
	public String toString() {
		if (dateDied == null) {
		return "\nFood units\tHealth units\tFire power units\tName\n" + 
				"----------\t------------\t----------------\t----\n" + 
				getFoodUnits() + "\t\t" + getHealthUnits() + "\t\t" + getFirePowerUnits() + "\t\t\t" + getName() + "\n"
				+ "Date created: " + getDateCreated() + "\nDate died: is still alive";
		}else //Case where creature dies
			return "Food units\tHealth units\tFire power units\tName\n" + 
			"----------\t------------\t----------------\t----\n" + 
			getFoodUnits() + "\t\t" + getHealthUnits() + "\t\t" + getFirePowerUnits() + "\t\t\t" + getName() + "\n"
			+ "Date created: " + getDateCreated() + "\nDate died: " + getDateDied();
				 
		
			
	}
	
	// reduce fire power unit method
	public void reduceFirePowerUnits(int n) {
		this.firePowerUnits -= n;
	}
	//Show basic status of creature method
	public String showStatus() {
		return getFoodUnits() + " food units," + getHealthUnits() + " health units," + getFirePowerUnits() + " fire power units.";
	}
	
	
	// Normalize units method
	void normalizeUnits() {
			
			healthUnits += foodUnits/FOOD2HEALTH;//Converts food units to health units
			foodUnits %= FOOD2HEALTH; //Remove food units converted
			if (foodUnits == 0) { // special case that Prevents health units and food units to be 0 simultaneously
			while (healthUnits > HEALTH2POWER ) { 
				healthUnits -= HEALTH2POWER;
				firePowerUnits++;
			}
			}else { //Main normalize method 
				firePowerUnits += healthUnits/HEALTH2POWER; //Converts health units to fire power units
				healthUnits %= HEALTH2POWER; //Remove health units converted
		}
			
	}
	
	
	
	
	
	
	
	
}
