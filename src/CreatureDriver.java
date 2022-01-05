// -------------------------------------------------------
// Assignment 4
// Written by: Reuven Ostrofsky - 40188881
// For COMP 248 Section EB-X – Winter 2021
// Written on 03/31/2021
// --------------------------------------------------------

/*This program is to generate a creature-like game, competing and battling against each other until one remains as winner.*/


import java.util.Scanner; // Import Scanner

public class CreatureDriver {

	public static void main(String[] args) {

		//Variable declaration
		Scanner input = new Scanner(System.in);//Scanner name
		int amount; //Amount of creatures 
		String name; //Name of creature (prompted by user)
		int currentCreature; //Position of the creature's turn in array
		int choice; //Choice of option player would like to choose
		int creatureAttacked; //Position of the creature being attacked in array
		int rand; //random number variable 
		
		//Display welcome message
		System.out.println("[------------------------------------------------]\n\n[ Welcome to the Battle Game ]\n\n[------------------------------------------------]");
		
		//Ask user and prompt amount of creatures
		do {
			System.out.print("\nHow many creatures would you like to have (minimum 2, maximum 8)? ");
			amount = input.nextInt();
			//Display error message if illegal input
			if (amount < 2 || amount > 8 ) System.out.println("\n*** Illegal number of creatures requested ***"); 
				
			
		}while(amount < 2 || amount > 8); //Keep on prompting user as input is invalid
		
		//Create arrays of type Creature with length of creature amount
		Creature[] creatures = new Creature[amount];
		
		for (int i=0; i< amount; i++) {
			//Ask user to input creature's name
			System.out.print("\nWhat is the name of creature " + (i+1) + "? ");
			if (i == 0) //fixes input bug on first creature issue
			input.nextLine();
			name = input.nextLine(); //store name in variable
			creatures[i] = new Creature(name); //create new object of type creature in first open position in array
			System.out.println(creatures[i]); //Display creature's content
			
		}
		
		//Set random creature to start
		currentCreature = (int)(Math.random()*amount);
		
		do {
		do {
			//Display current creature's and choices of the battle game
			System.out.println("\nCreature #" + (currentCreature+1) + ": " + creatures[currentCreature].getName() + ", what do you want to do? ");
			System.out.println("\t1. How many creatures are alive?\n\t2. See my status\n\t3. See status of all players\n\t4. Change my name\n\t5. Work for some food\n\t6. Attack another creature (Warning! may turn against you)");
			//Prompt user for choice
			System.out.print("Your choice please > ");
			choice = input.nextInt();
			//Create switch statement for each choice 
			switch (choice) {
			
			//Display amount of creatures alive (User prompts 1)
			case 1: System.out.print("\tNumber of creatures alive: " + Creature.getNumStillAlive() + "\n");
			break;
			
			//Display creature's status (User prompts 2)
			case 2: System.out.println(creatures[currentCreature] + "\n");
			break;
			
			//Display all creatures' status (User prompts 3)
			case 3: for (int i = 0; i < amount; i++) {
				System.out.println(creatures[i]);
			}
			break;
			
			//Change creature's name (User prompts 4)
			case 4: System.out.println("your name is currently \""+ creatures[currentCreature].getName() + "\""); //Display current name
			input.nextLine(); //Skip line
			//Prompt user for new name
			System.out.print("What is the new name: ");
			name = input.nextLine(); //Store name in variable
			creatures[currentCreature].setName(name); //Set new name with method
			break;
			
			//Work for some food method (User prompts 5)
			case 5: 
				
			//Display status before earning food 
			System.out.print("\nYour status before working for food :" + creatures[currentCreature].showStatus() + "... ");
			//initiate earn food method and display ammount of food units earned
			System.out.println("You earned " + creatures[currentCreature].earnFood() + " food units.");

			//Display status after earning food
			System.out.println("\nYour status after working for food: " + creatures[currentCreature].showStatus() +"\n");
			
			//Give turn to next creature
			do {
				//Rotate if previous turn was the last creature
			if (currentCreature == amount -1)
				currentCreature -= amount -1;
			else currentCreature++; //Go to next creature
			}while(!creatures[currentCreature].isAlive()); //Skip dead creatures' turn
			break;
			
			//Attack method (User prompts 6)
			case 6: 
			do {
				//Prompt user to attack creature
				System.out.print("\nWho do you want to attack? (enter a number from 1 to " + amount + " other than yourself(" + (currentCreature + 1) + "): ");
				creatureAttacked = input.nextInt() -1;//store attacked creature in variable for array
				rand = (int)(Math.random()*3); // Generate random number between 0 and 2 to see who is getting attacked (1/3 chance for current creature being attacked)
				
				//Display error message if illegal input
				if (creatureAttacked < 0 || creatureAttacked > amount -1 )
					System.out.println("That creature does not exist. Try again ...");
				else if (creatureAttacked == currentCreature) 
					System.out.println("\nCant't attack yourself silly! Try again ...");
				else if (!creatures[creatureAttacked].isAlive())
					System.out.println("\n" + creatures[creatureAttacked].getName() + " is already dead! Try again ...");
					
				
			}while(creatureAttacked > amount -1 || creatureAttacked < 0 || creatureAttacked == currentCreature || !creatures[creatureAttacked].isAlive()); //Prompt user again if user's input is illegal(dead,itself or creature does not exist)
			
			//Generate cases where creature attacks, gets attack or neither
			if (rand != 0 && creatures[currentCreature].getFirePowerUnits() >= 2) {
				
				//Display attacked creature's name
				System.out.println("..... You are attacking " + creatures[creatureAttacked].getName() + "!");
				
				//Display creature's status before attacking
				System.out.println("Your status before attacking: " + creatures[currentCreature].showStatus());
				
				//Generate attack method
				creatures[currentCreature].attacking(creatures[creatureAttacked]);
				
				//Display creature's status after attacking
			System.out.println("Your status after attacking: " + creatures[currentCreature].showStatus());
			
			}else if(creatures[creatureAttacked].getFirePowerUnits() >= 2) {
				
				//Display message (Current creature getting attacked)
				System.out.print("\nThat was not a good idea ...");
				
				if (creatures[currentCreature].getFirePowerUnits() < 2)
					//Display message when creature does not have enough fire power
					System.out.println("you only had " + creatures[currentCreature].getFirePowerUnits() + " Fire Power Units!!!");
				
				//Display attacking creature's name
				System.out.println("....... Oh No!!! You are being attacked by " + creatures[creatureAttacked].getName() + "!");
				
				//Display status before attacking
				System.out.println("Your status before attacking: " + creatures[currentCreature].showStatus());
				
				//Generate attacking method
				creatures[creatureAttacked].attacking(creatures[currentCreature]);
				
				//Display status after attacking
				System.out.println("Your status after attacking: " + creatures[currentCreature].showStatus() + "\n");
			}else 
				//Display message where battle is a tie
				System.out.println("Lucky you, the odds were that the other player attacks you, but " + creatures[creatureAttacked].getName() + " doesn't have enough fire power to attack you! So is status quo!!");
			
			
			//Display dead creature message
			if (!creatures[creatureAttacked].isAlive())
				System.out.println("\n---->" + creatures[creatureAttacked].getName() + " is dead");
			else if (!creatures[currentCreature].isAlive())
				System.out.println("\n---->" + creatures[currentCreature].getName() + " is dead");
			
			
			do {
				//Rotate if previous turn was the last creature
				if (currentCreature == amount -1)
					currentCreature -= amount -1;
				else currentCreature++;
				}while(!creatures[currentCreature].isAlive()); //Skip dead creature's turn
			break;
			
			}
		}while ((choice < 1 || choice > 6) || (choice == 1 || choice == 2 || choice == 3 || choice == 4)); //keep prompting creature's choice as long as input is invalid or it is not the next creature's turn
		
		}while(Creature.getNumStillAlive() > 1); //Keep running game as long as there is more than one creature alive
		
		
		//Display end of game message
		System.out.println("\nGAME OVER!!!!!\n");
		
		//Display final status of each creature
		for (int i = 0; i < amount; i++) {
			System.out.println(creatures[i]);
		}
		
		//Display farewell message
		System.out.println("Thank you for playing BATTLE GAME!");
		
		//Close user's input
		input.close();

	}

}
