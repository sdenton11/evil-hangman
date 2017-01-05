// *******************
//
// TextOrganizer Class
//
// This is class will have a method to return the words of the right length
//
// *******************

import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class TextOrganizer
{
    private ArrayList<String> dictionary; 
    public ArrayList<String> organizeddict;
    
    // This constructor will initialize the two instance variables
    public TextOrganizer(String fileName) throws FileNotFoundException
    {
        
        // creates an input of the dictionary file
        File inFile = new File(fileName);
        Scanner input = new Scanner(inFile);
        String word;
        
        dictionary = new ArrayList<String>();
        
        // this will go through each word of dictionary and add to ArrayList
        while (input.hasNext())
        {
            word = input.nextLine();
            word = word.trim();
            
            dictionary.add(word);
        }
        
        input.close();
    }
    
    // This method will return an Array of length n
    public ArrayList<String> lengthN(int n)
    {
        // this first checks that the length is a valid number
        if (!(n > 1))
        {
            System.out.print("Length N method error\n");
            System.out.print("Please enter a valid length! ");
            System.out.print("The dictionary will be working with an empty");
            System.out.print(" list\n\n");
            
            // return an empty Array
            ArrayList<String> list = new ArrayList<String>();
            return list;
        }
        else
        {
            // create the output ArrayList
            organizeddict = new ArrayList<String>();
            
            // runs through each word in the list
            for (int i = 0; i < dictionary.size(); i++)
            {
                // if the word is the correct length add it to the output
                if (dictionary.get(i).length() == n)
                {
                    organizeddict.add(dictionary.get(i));
                }
            }
            
            // create an Array to return
            //String[] list = organizeddict.toArray(new String[organizeddict.size()]);
            
            // return the Array List
            return organizeddict;
        }
    }
}