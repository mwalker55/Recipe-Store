// Title: RecipeStorer
// Author: Mark Walker
// Date: December 20, 2012
// School: Valencia High School
// Computer: Dell with Windows 7
// IDE Used: BlueJ
// Purpose: This class handles all of the program's interactions with the file, whether it be reading from the file or writing to it.

import java.io.*;
import java.util.*;

public class FileHandler
{
    private Scanner scanTheFile; // to read from the file
    private FileOutputStream streamer; // to allow writing to the file
    private PrintStream printer; // to write to the file
    public FileHandler() // initializes the class
    {
        try {
            scanTheFile = new Scanner(new File("recipe.txt"));  // creates a Scanner to read from the file
        }
        catch (Exception e) // in case the file does not exist
        {
            try
            {
                FileWriter man = new FileWriter(new File("recipe.txt"));  // creates the file if it is not already there
                man.close(); 
                scanTheFile = new Scanner(new File("recipe.txt")); // makes sure our scanner exists
            } catch(Exception f) {}}

        try{
            streamer = new FileOutputStream(new File("recipe.txt"), true); // creates a new FileOutputStream for our file that writes to the end of the file
            printer = new PrintStream(streamer); // allows us to write to the file
        }
        catch (Exception e) {System.out.println(e.getMessage());}
    }

    public String[] searchFile(String keyword) // searches the file for any Recipe containing the passed in keyword
    {
        String[] terms = new String[500]; // array to hold Search results; made purposefully big to allow for a lot of search results
        int index = 0; // tracks what index I am at in the terms array; used to transfer to a smaller array later on
        
        while(scanTheFile.hasNextLine()) // keeps running while there are still lines in the file
        {
            String term = scanTheFile.nextLine(); // sees if the line contains our search term
            String temp = term.toLowerCase();
            if (temp.contains(keyword.toLowerCase()))
            {
                terms[index] = term; // adds it to our String
                index++; // changes the index so we don't overwrite a previous value
                if (index == terms.length) // checks if we are out of room in our array
                {
                    String[] replacer = new String[index+100]; // creates a bigger array if we are out fo room
                    for (int i = 0; i < terms.length; i++)
                    {
                        replacer[i] = terms[i]; // adds the older values to the new array
                    }
                    terms = replacer; // changes it back
                }
            }
        }
        
        String[] smallerArray = new String[index]; // index was tracking where we were in the array; this array represents exactly how many 
                                                     // values were being returned by the Search so we can shorten the returned array
        for (int i = 0; i < index; i++)
        {
            smallerArray[i] = terms[i]; // copies over existant values
        }
        
        return smallerArray; 
    }
    
    public void addToFile(String term) // writes term to the file
    {
        printer.println(term); // adds a line to the file
    }
    
    public void closeConnections() // closes our programs connections to the file
    {
        try {
        streamer.close(); // closes our FileOutputStream
        streamer.flush();
        printer.close(); // closes our PrintStream
        printer.flush();
        scanTheFile.close(); // closes the Scanner
        System.gc();
       } catch (Exception e)
       {
           System.out.println("close failed");
       } 
    }
    
    public void editRecipeInFile(String oldRecipe, String newRecipe) // allows us to edit a Recipe within the given file
    {
        FileWriter creater = null; //creates our new File
        File file = new File("newRecipes.txt"); // will represent our new file , newRecipes
        FileOutputStream coolGuy = null; // gives us access to the new File
        PrintStream writer =  null; // will let us write to the new File
        try // creates our new file for us and sets up our new writing interface
        {
            creater = new FileWriter(file);
            creater.close();
            coolGuy = new FileOutputStream(file);
            writer = new PrintStream(coolGuy);
            scanTheFile = new Scanner(new File("recipe.txt"));
        }
        catch (Exception e) {}
        
        while (scanTheFile.hasNextLine()) // while the original scanner has more lines
        {
            String phrase = scanTheFile.nextLine(); // gives us the next line in the File
            if (phrase.equals(oldRecipe))
            {
                writer.println(newRecipe);
            }
            else
            {
                writer.println(phrase);
            }
        }
        try // will close out our current data types accessing the new file
        {
            writer.close();
            coolGuy.close();
        }
        catch (Exception e) 
        {
            System.out.println("n");
        }
        
        File oldFile = new File("recipe.txt"); // creates a reference to the old, incorrect recipe.txt so we can delete it
        closeConnections(); // closes our connections to the old file - allows us to delete it
        oldFile.setWritable(true);
        while(!oldFile.delete()){ // this block was necessary as File would not always want to delete
            synchronized(this){ // allocates full resources to deletion of File
                try {
                    this.wait(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        file.renameTo(oldFile); // renames our new, correct file to 'recipe.txt'
        file.delete(); // deletes the temporary file
    }
    
    public void deleteRecipeInFile(String oldRecipe)
    {
        FileWriter creater = null; // creates our new file to copy into
        File file = new File("newRecipes.txt"); // will represent our new file, newRecipes
        FileOutputStream wowWriter = null; // gives us access to the new File
        PrintStream writer = null; // allows us to write to the new File
        try // creates our new file and sets up our necessary writers
        {
            creater = new FileWriter(file);
            creater.close();
            wowWriter = new FileOutputStream(file);
            writer = new PrintStream(wowWriter);
            scanTheFile = new Scanner(new File("recipe.txt"));
        }
        catch (Exception e) {}
        
        while (scanTheFile.hasNextLine()) // will keep running while more lines exist in the file
        {
            String phrase = scanTheFile.nextLine();
            if (phrase.equals(oldRecipe)) {} // won't copy the recipe we want to delete into the new file
            else
            {
                writer.println(phrase);
            }
        }
        
        try // will close out our current data types accessing the file
        {
            writer.close();
            wowWriter.close();
        }
        catch (Exception e) {}        
        
        File oldFile = new File("recipe.txt"); // creates a reference to the old, incorrect recipe.txt so we can delete it
        closeConnections(); // closes our connections to the old file - allows us to delete it
        while(!oldFile.delete()){ // this block was necessary as File would not always want to delete
            synchronized(this){ // allocates full resources to deletion of File
                try {
                    this.wait(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        file.renameTo(oldFile); // renames our new, correct file to 'recipe.txt'
        file.delete(); // deletes the temporary file
    }
    
    public Recipe[] convertStrings(String[] arr) // converts our search results to Recipes for easier use
    {
        Recipe[] toBeReturned = new Recipe[arr.length];
        for(int i = 0; i < arr.length;i++)
        {
            String temp = arr[i];
            String title;
            String[] ingredients = new String[10];
            String[] procedures = new String[10];
            int indexOfFirstBracket = temp.indexOf("[");
            int indexOfSecondBracket = temp.indexOf("]");
            title = temp.substring(indexOfFirstBracket+1, indexOfSecondBracket);
            temp = temp.substring(indexOfSecondBracket+1);
            indexOfFirstBracket = temp.indexOf("[");
            indexOfSecondBracket = temp.indexOf("]");
            String ingString = temp.substring(indexOfFirstBracket+1, indexOfSecondBracket);
            int tracker;
            int arrayIndex = 0;
            while((tracker = ingString.indexOf("|"))!=-1)
            {
                ingredients[arrayIndex] = ingString.substring(0,tracker);
                arrayIndex++;
                if (arrayIndex == ingredients.length)
                {
                    String[] newIng = new String[ingredients.length+1];
                    for (int j = 0; j < ingredients.length; j++)
                    {
                        newIng[j] = ingredients[j];
                    }
                    ingredients = newIng;
                }
                ingString = ingString.substring(tracker+1);
            }
            ingredients[arrayIndex] = ingString.substring(0,ingString.length());
            
            temp = temp.substring(indexOfSecondBracket+1);
            indexOfFirstBracket = temp.indexOf("[");
            indexOfSecondBracket = temp.indexOf("]");
            arrayIndex = 0;
            String procString = temp.substring(indexOfFirstBracket+1, indexOfSecondBracket);
            while ((tracker = procString.indexOf("|"))!= -1)
            {
                procedures[arrayIndex] = procString.substring(0, tracker);
                arrayIndex++;
                if (arrayIndex == procedures.length)
                {
                    String[] newProc = new String[procedures.length+1];
                    for (int j = 0; j < procedures.length; j++)
                    {
                        newProc[j] = procedures[j];
                    }
                    procedures = newProc;
                }
                procString = procString.substring(tracker+1);
            }
            procedures[arrayIndex] = procString.substring(0,procString.length());
            
            int z = 0;
            while (ingredients[z] != null) 
            {
                z++;
            }
            int k = 0;
            while (procedures[k] != null)
            {
                k++;
            }
            
            String[] fixedIng = new String[z];
            for (int p = 0; p<fixedIng.length;p++)
            {
                fixedIng[p] = ingredients[p];
            }
            String[] fixedProc = new String[k];
            for (int p = 0; p<fixedProc.length;p++)
            {
                fixedProc[p] = procedures[p];
            }
            
            toBeReturned[i] = new Recipe(title, fixedIng, fixedProc);
        }
        
        return toBeReturned;
    }
}