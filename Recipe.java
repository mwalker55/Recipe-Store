// Title: RecipeStorer
// Author: Mark Walker
// Date: January 23, 2013
// School: Valencia High School
// Computer: Dell with Windows 7
// IDE Used: BlueJ
// Purpose: This class facilitates data storage of our needed variables for the Recipe storing program.  Each instance of this class represents an individual
// recipe during the running of the program.

public class Recipe
{
    private String myTitle;  // represents the title of the recipe
    private String[] myIngredients;   // represents the ingredients of the recipe
    private String[] myProcedure;  // represents the procedure of the recipe
    private String oldRecipe; // represents the Recipe prior to any edits
    
    public Recipe(String title, String[] ingredients, String[] procedures)  // initializes our private variables
    {
        myTitle = title;
        myIngredients = ingredients;
        myProcedure = procedures;
        oldRecipe = "";
    }
    
    public String getTitle() // gives access to the Recipe title
    {
        return myTitle;
    }
    
    public String[] getIngredients() // gives access to the Recipe ingredients
    {
        return myIngredients;
    }
    
    public String[] getProcedure() // gives access to the Recipe procedures
    {
        return myProcedure;
    }
    
    public String getOldRecipe() // allows access to the oldRecipe; if it has not been made yet, an empty String will be returned
    {
        return oldRecipe;
    }
    
    public String toString() // used to compile a String to be written to the file, as well as method for representing the Recipe prior to edits
    {
        String compiledString = ""; // creating an empty String to add the Recipe parts to
        compiledString+= "[" + myTitle + "]"; // adds the title of the recipe to our String
        compiledString+= "["; // prepares the String to have Ingredients added
        for(int i = 0; i < myIngredients.length; i++)
        {
            compiledString+= myIngredients[i] + "|"; // adds the next Ingredient to the String and adds a partition for the next ingredient    
        }
        compiledString = compiledString.substring(0,compiledString.length()-1);
        compiledString+= "]["; // closes out our Ingredient adding and preapres the String to have procedures added
        for (int i = 0; i < myProcedure.length; i++)
        {
            compiledString+= myProcedure[i] + "|"; // adds the next Procedure to the String and adds a partition for the next procedure
        }
        compiledString = compiledString.substring(0,compiledString.length()-1);
        compiledString+= "]"; // closes out the final splitting for the Procedures
        return compiledString;
    }
    
    public void editTitle(String newTitle) // allows editing of the Title
    {
        if (oldRecipe.equals("")) 
            oldRecipe = this.toString();  // upon editing, it will be required to have reference to what the recipe was prior to editing; this will
                                          // create that copy only if it has not been already made
        myTitle = newTitle;
    }
    
    public void addIngredient(String newIngredient) // allows adding of an Ingredient
    {
        if (oldRecipe.equals(""))
            oldRecipe = this.toString(); //same reasoning as above
        String[] newIngredients = new String[myIngredients.length+1]; //creates a new array for the new Ingredient to be copied into
        for (int i = 0; i < myIngredients.length; i++)
        {
            newIngredients[i] = myIngredients[i]; // copying in the old array to the new one
        }
        newIngredients[myIngredients.length] = newIngredient;
        myIngredients = newIngredients; 
    }
    
    public void editIngredient(String change, int index)
    {
        if (oldRecipe.equals(""))
            oldRecipe = this.toString(); //same reasoning as above
        myIngredients[index] = change;  //making the desired change
    }
    
    public void addProcedure(String newProc)
    {
        if (oldRecipe.equals(""))
            oldRecipe = this.toString(); //same reasoning as above
        String[] newProcedure = new String[myProcedure.length + 1]; // creates a new array for the new Procedure to be added into
        for (int i = 0; i < myProcedure.length; i++)
        {
            newProcedure[i] = myProcedure[i]; // copying in the old array to the new one
        }
        newProcedure[myProcedure.length] = newProc;
        myProcedure = newProcedure;
    }
    
    public void editProcedure (String change, int index)
    {
        if (oldRecipe.equals(""))
            oldRecipe = this.toString(); //same reasoning as above
        myProcedure[index] = change;
    }
}
