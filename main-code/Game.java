// *********************
//
// This is the class that will define the gameplay
//
// **********************

import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class Game
{
    private ArrayList<String> guesses;
    private ArrayList<String> secretwords;
    private int lives;
    private String guessedsofar;
    private boolean success;
    private ArrayList<String> correctletters;
    
    public Game(int length, String fileName)
    {
        try
        {
            // create the empty list of guesses
            guesses = new ArrayList<String>();
    
            // create the list of possible secret words
            TextOrganizer test = new TextOrganizer(fileName);
            secretwords = test.lengthN(length);
        
            // create the number of lives
            lives = length + 4;
        
            // create the empty list of guesses so far
            guessedsofar = "";
        
            // set success to false
            success = false;
        
            // set the correct letters to an empty list
            correctletters = new ArrayList<String>();
        }
        
        catch (Exception e)
	    {
	        System.out.println("Wrong input file");
	    }
    }
    
    // create a method that plays the game
    public void play()
    {
        score();
        while ((!success) && (lives > 0))
        {
            // ask the user for a letter and it it to guesses
            System.out.print("Guess a letter\n");
            
            Scanner input = new Scanner(System.in);
            String letter = input.next();
            guesses.add(letter);
            
            // check if the guessed letter is in every possible secret word
            boolean options = false;
            
            for (int i = 0; i < secretwords.size(); i++)
            {
                // if one of the words doesn't have the letter then
                // there are other options for the word
                if (!(contains(letter, secretwords.get(i))))
                {
                    options = true;
                }
            }
            
            //score();
            
            if (!(options))
            {
                // add the letter to the correct letters
                correctletters.add(letter);
                
                // re set the new list to most common pattern left
                String pattern = "";
                ArrayList<String> newsecretwords = new ArrayList<String>();
                pattern = frequent(secretwords, correctletters);
                for (String word : secretwords)
                {
                    if (wordWithBlanks(word, correctletters).equals(pattern))
                    {
                        newsecretwords.add(word);
                    }
                }
                secretwords = newsecretwords;
                
                // set guessed so far to the pattern
                guessedsofar = pattern;
                
                // check to see if guessed so far has anymore blanks
                if (!(contains("_", guessedsofar)))
                {
                    success = true;
                }
                
                score();
            }
            else
            {
                // create a new list of secret words without the guessedletter
                ArrayList<String> newsecretwords = new ArrayList<String>();
                for (String word : secretwords)
                {
                    if (!(contains(letter, word)))
                    {
                        newsecretwords.add(word);
                    }
                }
                secretwords = newsecretwords;
                
                // decrease the number of lives by 1
                lives -= 1;
                
                score();
            }   
        }
        
        if (success)
        {
            String message = "";
            message += "\nCongratulations! You won with ";
            message += Integer.toString(guesses.size()) + " guesses and had ";
            message += Integer.toString(lives) + " lives left!\n";
            System.out.print(message);
        }
        else
        {
            int number = 0;
            number = (int) Math.random() * secretwords.size();
            String message = "";
            message += "Sorry! The secret word was " + secretwords.get(number);
            message += "\n\n";
            System.out.print(message); 
        }
    }
    
    // create a method to report scores
    public void score ()
    {
        String message = "";
        message += "\nYou have " + Integer.toString(lives) + " lives left";
        message += "\nYou have guessed the following letters:\n";
        for (int i = 0; i < (guesses.size()); i++)
        {
            if (i == (guesses.size() - 1))
            {
                message += guesses.get(i);
            }
            else
            {
                message += guesses.get(i) + ", ";
            }
        }
        message += "\nAnd so far you have guessed: ";
        message += guessedsofar;
        message += "\n";
        System.out.print(message);            
    }
        
    
    // create a method that checks if a letter is in a word
    public boolean contains (String letter, String word)
    {
        boolean containment = false;
        
        for (int i = 0; i < word.length(); i++)
        {
            if (letter.equals(word.substring(i, i + 1)))
            {
                containment = true;
            }
        }
        
        return containment;
    }
    
    // this method will return the word with the filled in letters
    public String wordWithBlanks (String word, ArrayList<String> letters)
    {
        // make the final word an ArrayList of strings
        ArrayList<String> finalword = new ArrayList<String>();
        
        // go through each letter of the word
        for (int i = 0; i < word.length(); i++)
        {
            // assume that the letters doesn't have the right letter
            boolean right = false;
            for (int j = 0; j < letters.size(); j++)
            {
                if (word.substring(i, i + 1).equals(letters.get(j)))
                {
                    right = true;
                }
            }
            
            // if the character is right, add it, otherwise add a blank
            if (right)
            {
                finalword.add(word.substring(i, i + 1));
            }
            else
            {
                finalword.add("_");
            }
        }
        
        // go through the final ArrayList and make it a string
        String answer = "";
        for (int i = 0; i < finalword.size(); i++)
        {
            answer += finalword.get(i);
        }
        
        return answer;
    }
    
    // this method will return the most common pattern of words from letters
    public String frequent (ArrayList<String> words, ArrayList<String> letters)
    {
        // first check to see if there are duplicates
        boolean duplicates = false;
        for (int i = 0; i < (words.size() - 1); i++)
        {
            String word1 = "";
            word1 = wordWithBlanks(words.get(i), letters);
            for (int j = i + 1; j < words.size(); j++)
            {
                String word2 = "";
                word2 = wordWithBlanks(words.get(j), letters);
                if (word1.equals(word2))
                {
                    duplicates = true;
                }
            }
        }
        
        // if there are not duplicates then pick a random pattern
        if (!(duplicates))
        {
            int number = 0;
            number = (int) Math.random() * words.size();
            return wordWithBlanks(words.get(number), letters);
        }
        else
        {
            // make a new ArrayList to call recursion on
            ArrayList<String> newwords = new ArrayList<String>();
            
            // only keep words that are duplicates
            boolean duplicateword = false;
            for (int i = 0; i < (words.size() - 1); i++)
            {
                String word1 = "";
                word1 = wordWithBlanks(words.get(i), letters);
                for (int j = i + 1; j < words.size(); j++)
                {
                    String word2 = "";
                    word2 = wordWithBlanks(words.get(j), letters);
                    if (word1.equals(word2))
                    {
                        duplicateword = true;
                    }
                }
                if (duplicateword)
                {
                    newwords.add(words.get(i));
                }
            }
            
            // return the most common pattern on the new list
            return frequent(newwords, letters);
        }  
    }
}           