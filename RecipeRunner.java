// Title: RecipeStorer
// Author: Mark Walker
// Date: December 20, 2012
// School: Valencia High School
// Computer: Dell with Windows 7
// IDE Used: BlueJ
// Purpose: This class runs the actual program, printing out menus, recording user options and handling all user interaction and fulfilling their needs. 

import java.util.*;

public class RecipeRunner
{
    private FileHandler handler; // creates a reference to the FileHandler to be used
    private Scanner scanMan; // creates a reference to a Scanner to be used

    public void runProgram()
    {
        handler = new FileHandler(); // initializes our FileHandler, which will be used to access the file
        scanMan = new Scanner(System.in); // initializes our Scanner, which will be used to read user input
        
        startMenu(); // brings the user to the program menu
    }
    
    private void startMenu()
    {
        boolean inputFailed = false; // will track whether the user's input caused an error
        
        System.out.println("Recipe Store v.1B: what would you like to do?"); // printing out of menu options
        System.out.println("1.) Create a recipe");
        System.out.println("2.) Search for a recipe");
        System.out.println("3.) Exit the program");
        
        int userSelection = -1; // initialized to -1 to ensure the following while loop runs at least one
        
        while (userSelection < 1 || userSelection > 3) // a loop that will run while the user's input is not a valid menu option
        {
            try{
               System.out.print("Please enter your menu selection: ");  // queues for the user's desire
               userSelection = scanMan.nextInt(); // records the user's desire
            }
            catch (Exception e) // creates a failsafe if the user enters a bad input
            {
                System.out.println("Error: " + e.getMessage() + ".  Please enter an int."); // advises user of their error
                scanMan.close(); // refreshes the Scanner to clear the bad input
                scanMan = new Scanner(System.in);
                inputFailed = true; // Changes to reflect that the user entered bad input
            }
            if ((userSelection < 1 || userSelection > 3) && inputFailed == false) // if they selected a non-menu item; only advises if they entered an int
            {
                System.out.println("Please enter a valid menu number.");
            }
            
            inputFailed = false; // refreshes in case they add a bad input next time
        }
        
        if (userSelection == 1) // next few ifs check for their input and call appropriate methods
        {
            createRecipe();
        }
        else if (userSelection == 2) 
        {
            searchForRecipe();
        }
        else if (userSelection == 3) 
        {
                endProgram();
        }
    }
    
    private void endProgram() // closes the program
    {
          handler.closeConnections(); // closes out any relation to the file; this is so the file can save properly
          System.exit(0); // ends our runtime 
    }
    
    private void createRecipe() // adding interface
    {
          String recipeTitle = ""; // creates reference to the user's title, to be used later in generating a new Recipe object
          String[] ingredients = new String[10]; // creates reference to various ingredients, to be used later in generating a Recipe object
          String[] procedure = new String[10]; // creates reference to various procedures, to be used later in generating a Recipe object
          
          
          System.out.print("Please enter the recipe title (do not include a '|' or a '[' or a ']' in the title): "); // queues for a title; characters limited due to their used in reading the file
          scanMan.nextLine(); // clears an extra "ENTER" present from the original  menu
          recipeTitle = scanMan.nextLine();
          while (recipeTitle.indexOf("|") !=-1 || recipeTitle.indexOf("[") != -1 || recipeTitle.indexOf("]") != -1) // ensures that the user did not use the blocked characters
          {
              System.out.println("You used an invalid character in your entry.  Try again."); 
              System.out.print("Please enter the recipe title (do not include a '|' or a '[' or a ']' in the title): ");
              recipeTitle = scanMan.nextLine();
          }
          
          String nextIng = ""; // will store their entered ingredient
          boolean hasNextIng = true; // tracks whether they have another ingredient
          int index = 0; // tracks our position in our Ingredient array
          
          while (hasNextIng) // will run until the user does not have another Ingredient to enter
          {
                System.out.print("Please enter an ingredient (do not include a '|' or a '[' or a ']' in the ingredient): "); // queues for Ingredient; same reasoning on blocked characters
                nextIng = scanMan.nextLine(); // records input
                while (nextIng.indexOf("|") !=-1 || nextIng.indexOf("[") != -1 || nextIng.indexOf("]") != -1) // ensures that blocked characters are not used
                {
                      System.out.println("You used an invalid character in your entry.  Try again.");
                      System.out.print("Please enter an ingredient (do not include a '|' or a '[' or a ']' in the ingredient): ");
                      nextIng = scanMan.nextLine();
                }
                ingredients[index] = nextIng; // adds the Ingredient to our Ingredient storing array
                index++; // moves us to the next index in the Ingredient storing array
                
                if (index > ingredients.length || index == ingredients.length) // checks if we are out of bounds in the array; makes a new, larger array if we are
                {
                    String[] newIng = new String[ingredients.length+10];
                    for (int i = 0; i < ingredients.length; i++)
                    {
                        newIng[i] = ingredients[i];
                    }
                    ingredients = newIng;
                }
                
                String answer = "w"; // will store user's response to our issue
                while(!answer.toUpperCase().equals("Y") && !answer.toUpperCase().equals("N")) // runs while they have not enetered a valid answer
                {
                    System.out.print("Do you have another ingredient to enter (Y/N)? "); // checks if they have another ingredient to enter
                    answer = scanMan.next();
                    
                    if(!answer.toUpperCase().equals("Y") && !answer.toUpperCase().equals("N")) // ensures and warns for improper entry
                    {
                        System.out.println("Please enter a valid input.");
                    }
                    scanMan.nextLine();
                }
                
                if(answer.toUpperCase().equals("Y")) {}
                else 
                {
                    hasNextIng = false; // will terminate our loop; indicates the user has nothing else to enter
                }
          }
          
          String nextProc = ""; // creates String with same intent as nextIng
          boolean hasNextProc = true; // creates boolean with same intent as hasNextIng
          index = 0; // resets index to now track in the Procedure array
          while (hasNextProc == true) /// will run until the user does not have another Procedure to enter
          {
                System.out.print("Please enter an procedure (do not include a '|' or a '[' or a ']'): "); // queues for the procedure entry
                nextProc = scanMan.nextLine();
                while (nextProc.indexOf("|") !=-1 || nextProc.indexOf("[") != -1 || nextProc.indexOf("]") != -1) // checks and fixes forbidden character issue
                {
                    System.out.println("You used an invalid character in your entry.  Try again.");
                    System.out.print("Please enter a procedure (do not include a '|' or a '[' or a ']' in the procedure): ");
                    nextProc = scanMan.nextLine();
                }
                procedure[index] = nextProc; // adds procedure to the array
                index++; // moves us along in the array
                
                if (index > procedure.length || index == procedure.length) // checks if we are out of bounds and lengthens the array as needed
                {
                    String[] newProc = new String[procedure.length+10];
                    for (int i = 0; i < procedure.length; i++)
                    {
                        newProc[i] = procedure[i];
                    }
                    procedure = newProc;
                }
                
                String answer = "w"; // creates another needed reference; ensures loop runs at least once
                while(!answer.toUpperCase().equals("Y") && !answer.toUpperCase().equals("N")) // creates an array for user selection, running until they enter something valid
                {
                    System.out.print("Do you have another procedure to enter (Y/N)? ");
                    answer = scanMan.next();
                    
                    if(!answer.toUpperCase().equals("Y") && !answer.toUpperCase().equals("N")) // warns as needed
                    {
                        System.out.println("Please enter a valid input.");
                        scanMan = new Scanner(System.in);
                    }
                }
                
                if(answer.toUpperCase().equals("Y")) {}
                else 
                {
                    hasNextProc = false; // if the user indicated they have no more procedures, fixes issue so no more queues
                }
                scanMan.nextLine(); // clears any extraneous "ENTER"s
          }
          
          int checker = 0; // will track in our arrays
          while(ingredients[checker] != null) // this and next identical code grouping concatenate arrays to prevent them from being full of "null"
          {
              checker++;
          }
          String[] newIngArr = new String[checker];
          for (int i = 0; i < newIngArr.length; i++)
          {
              newIngArr[i] = ingredients[i];
          }          
          
          checker = 0;
          while (procedure[checker] != null)
          {
              checker++;
          }
          String[] newProcArr = new String[checker];
          for (int i = 0; i < newProcArr.length; i++)
          {
              newProcArr[i] = procedure[i];    
          }
          
          Recipe toBeAdded = new Recipe(recipeTitle, newIngArr, newProcArr); // creates a Recipe based on user input
          handler.addToFile(toBeAdded.toString()); // adds the recipe to the file
          System.out.println("Returning to menu"); // informs user of what is going on
          handler.closeConnections(); // closes references to the file, which are no longer fully valid
          runProgram(); // restarts the program, creating new, correct file references
    }
    
    private void searchForRecipe() // searching interface
    {
        scanMan.nextLine(); // clears the extra "ENTER" from the menu
        String keyword = "";
        
        System.out.print("Please enter your search keyword (can be any part of a recipe; make sure to not use '|', '[' and ']'): "); // queues for the word to search for; same char restrictions
        keyword = scanMan.nextLine();
        while (keyword.indexOf("|") !=-1 || keyword.indexOf("[") != -1 || keyword.indexOf("]") != -1) // ensures and fixes any problems with invalid characters
        {
                    System.out.println("You used an invalid character in your entry.  Try again.");
                    System.out.print("Please enter your search keyword (can be any part of a recipe; do not include a '|' or a '[' or a ']' in the keyword): ");
                    keyword = scanMan.nextLine();
        }
        
        Recipe[] returned = handler.convertStrings(handler.searchFile(keyword)); // converts the Strings returned by searching for the input keyword to usable recipes
        if (returned.length == 0) // informs the users if no recipes contained the entered keyword
        {
             System.out.println("Your search yielded no results.");
        }
        else // if something was returned 
        {
            boolean screwedUp = false;
            int userInput = -1;
            
            for (int i = 0; i < returned.length; i++)
            {
                System.out.println((i+1) + ".) " + returned[i].getTitle()); // prints out returned recipe titles
            }
            
            while (userInput <1 || userInput > returned.length)
            {
                try
                {
                    System.out.print("Please enter the number of the recipe you would like to view: "); // asks user for the recipe they want to view; same entry algorithm as above
                    userInput = scanMan.nextInt();
                }
                catch(Exception e)
                {
                    System.out.println("You must enter an int.  Please try again.");
                    screwedUp = true;
                    scanMan.close();
                    scanMan = new Scanner(System.in);
                }
                
                if ((userInput < 1 || userInput > returned.length) && screwedUp == false)
                {
                    System.out.println("Please enter a valid number, shown on the list above.");
                }
                
                screwedUp = false;
            }
            
            Recipe userChoice = returned[userInput-1]; // creates reference to selected recipe; -1 is accounting for printing differences
            System.out.println("Title: " + userChoice.getTitle()); // prints title
            System.out.println("Ingredients");
            String[] chosenIng = userChoice.getIngredients();
            for (int i = 0; i < chosenIng.length; i++) // prints ingredients
            {
                System.out.println((i+1) + ".) " + chosenIng[i]);
            }
            System.out.println("Procedure");
            String[] chosenProc = userChoice.getProcedure();
            for (int i = 0; i < chosenProc.length; i++) // prints procedures
            {
                System.out.println((i+1) + ".) " + chosenProc[i]);
            }
            
            System.out.println("What would you like to do now?"); // asks user for what to do next
            System.out.println("1.) Edit the recipe");
            System.out.println("2.) Delete the recipe");
            System.out.println("3.) Return to menu");
            int userEntry = -1;
            while (userEntry < 1 || userEntry > 3) // same user entry and wait algorithm as before
            {
                boolean entryFailed = false;
                try
                {
                    System.out.print("Choice: ");
                    userEntry = scanMan.nextInt();
                }
                catch (Exception e)
                {
                    System.out.println("You need to add an int. Try again.");
                    scanMan.close();
                    scanMan = new Scanner(System.in);
                    entryFailed = true;
                }
                
                if (entryFailed == false && (userEntry < 1 || userEntry > 3))
                {
                    System.out.println("You need to enter a valid  menu option.");
                    scanMan.nextLine();
                }
            }
            
            if (userEntry == 1) // calls proper methods based on input
            {
                recipeEditor(userChoice);
            }
            else if (userEntry == 2)
            {
                deleteRecipe(userChoice);
            }
            else {} // menu return will happen below anyways, no need to call anything
        }
        System.out.println("Returning to menu");
        handler.closeConnections(); // must be done for program reset
        runProgram(); // resets the program
    }
    
    private void recipeEditor(Recipe recipe)
    {
        boolean stillHaveAnEdit = true; // tracks whether the user still has something to edit
        int userInput; // will record menu inputs
        
        while (stillHaveAnEdit) // will run as long as the user has another input
        {
            userInput = -1; // used to force while loop running
            System.out.println("What would you like to edit?"); // prompting menu choices
            System.out.println("1.) Title");
            System.out.println("2.) Ingredient");
            System.out.println("3.) Procedure");
            while (userInput < 1 || userInput > 3) // same algorithm to ensure proper input as before
            {
                boolean failedEntry = false;
                System.out.print("Choice: ");
                try 
                {
                    userInput = scanMan.nextInt();
                }
                catch (Exception e)
                {
                    System.out.println("You need to enter an int.  Try again.");
                    scanMan.close();
                    scanMan = new Scanner(System.in);
                    failedEntry = true;
                }
                if (failedEntry == false && (userInput < 1 || userInput > 3))
                {
                    System.out.println("Please enter a valid menu option.");
                }
            }
            
            scanMan.nextLine(); // clears the last "ENTER" put in by the user
            
            if (userInput == 1) // gets a new title from the user and changes it in the passed in Recipe
            {
                String newTitle = "";
                System.out.print("Please enter your new title: ");
                newTitle = scanMan.nextLine();
                recipe.editTitle(newTitle);
            }
            else if (userInput == 2) // will allow for ingredient editing
            {
                String answer = "w";
                while(!answer.toUpperCase().equals("Y") && !answer.toUpperCase().equals("N"))
                {
                    System.out.print("Do you want to add an ingredient or edit one? (Y = add; N = edit one) "); // records user's choice
                    answer = scanMan.next();
                    
                    if(!answer.toUpperCase().equals("Y") && !answer.toUpperCase().equals("N"))
                    {
                        System.out.println("Please enter a valid input by the options.");
                        scanMan = new Scanner(System.in);
                    }
                }
                
                scanMan.nextLine(); // flushes previous "ENTER" put in by user
                String[] ingArr = recipe.getIngredients(); // gives us reference to current ingrdients
                
                if (answer.toUpperCase().equals("Y")) // if they want to add a new ingredient, runs proper code
                {
                    String newIng;
                    System.out.print("Please enter your new ingredient: ");
                    newIng = scanMan.nextLine();
                    recipe.addIngredient(newIng);
                }
                else // for editing a current ingredient
                {
                    int whichIng = -1; // for tracking the desired ingredient to enter
                    String newIng;
                    while (whichIng < 1 || whichIng > ingArr.length) // gives for the user's desired ingredient to edit; same algorithm to ensure proper entry
                    {
                        System.out.print("Please enter the number of the ingredient you would like to edit (refer to previous printout): ");
                        boolean failedEntry = false; 
                        try
                        {
                            whichIng = scanMan.nextInt();
                        }
                        catch (Exception e)
                        {
                            System.out.println("You need to enter an int.  Try again.");
                            scanMan.close();
                            scanMan = new Scanner(System.in);
                            failedEntry = true;
                        }
                        
                        if (failedEntry == false && (whichIng < 0 || whichIng > ingArr.length))
                        {
                            System.out.println("You need to enter a valid ingredient number from above.");
                            scanMan.nextLine(); // flushes out extra "ENTER" from previous entry
                        }
                    }
                    scanMan.nextLine(); // flushes out extra "ENTER" from previous entry
                    System.out.print("Enter your new ingredient for that line: "); // queues for the new, edited ingredient
                    newIng = scanMan.nextLine(); // records it
                    recipe.editIngredient(newIng, whichIng-1); // edits the ingredient in the recipe instance
                }
            }
            else // used to edit procedures; same algorithm as that for ingredients
            {
                String answer = "w";
                while(!answer.toUpperCase().equals("Y") && !answer.toUpperCase().equals("N"))
                {
                    System.out.print("Do you want to add a procedure to the end or edit one? (Y = add; N = edit one) "); // records user entry
                    answer = scanMan.next();
                    
                    if(!answer.toUpperCase().equals("Y") && !answer.toUpperCase().equals("N"))
                    {
                        System.out.println("Please enter a valid input by the options.");
                        scanMan = new Scanner(System.in);
                    }
                }
                
                scanMan.nextLine(); // flushes previous "ENTER" put in by user
                String[] procArr = recipe.getProcedure(); // creates a local reference to the procedure array
                
                if (answer.toUpperCase().equals("Y")) // records new procedure for the end and adds it
                {
                    String newProc;
                    System.out.print("Please enter your new procedure: ");
                    newProc = scanMan.nextLine();
                    recipe.addProcedure(newProc);
                }
                else // edits an already existing Procedure; same algorithm
                {
                    int whichProc = -1;
                    String newProc;
                    while (whichProc < 1 || whichProc > procArr.length) // ensures a proper entry
                    {
                        System.out.print("Please enter the number of the procedure you would like to edit (refer to previous printout): ");
                        boolean failedEntry = false;
                        try
                        {
                            whichProc = scanMan.nextInt();
                        }
                        catch (Exception e)
                        {
                            System.out.println("You need to enter an int.  Try again.");
                            scanMan.close();
                            scanMan = new Scanner(System.in);
                            failedEntry = true;
                        }
                        
                        if (failedEntry == false && (whichProc < 0 || whichProc > procArr.length))
                        {
                            System.out.println("You need to enter a valid procedure number from above.");
                            scanMan.nextLine();
                        }
                    }
                    scanMan.nextLine(); // clears the "ENTER" from after the user entered the #
                    System.out.print("Enter your new, edited procedure for that line: "); // queues for user's new procedure
                    newProc = scanMan.nextLine(); // records that new procedure
                    recipe.editProcedure(newProc, whichProc-1); // edits it in our Recipe record
                 }
            }
            
            String answer = "w";
            while(!answer.toUpperCase().equals("Y") && !answer.toUpperCase().equals("N"))
            {
                System.out.print("Do you have another edit to make (Y/N)? "); // recording whether or not they have more to edit in the Recipe
                answer = scanMan.next();
                
                if(!answer.toUpperCase().equals("Y") && !answer.toUpperCase().equals("N"))
                {
                    System.out.println("Please enter a valid input by the options.");
                    scanMan = new Scanner(System.in);
                }
            }
            
            if (answer.toUpperCase().equals("N"))
            {
                stillHaveAnEdit = false; // will provide for termination of the while loop
            }
        }
        scanMan.close();
        handler.editRecipeInFile(recipe.getOldRecipe(), recipe.toString()); // calls a proper method to edit the Recipe record in the file; will then return back to Search method and return to menu
    }
    
    private void deleteRecipe(Recipe recipe) // deletes a recipe from the file
    {
        handler.deleteRecipeInFile(recipe.toString()); // calls proper method to delete the Recipe record in the file
    }
    
}