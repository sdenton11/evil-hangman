//*********************************
//
// EvilHangman Test Class
//
// This is a test class for the evil hangman game.
//
//*********************************
import java.util.Scanner;

public class EvilHangmanTest{

    public static void main(String[] args){
        
        // Ask the user how many letters they want in their word
        System.out.println("\nHow many letters do you want in your word?");
        
        int num = 0;
        Scanner input = new Scanner(System.in);
        num = input.nextInt();
        
        Game g = new Game(num, "dictionary.txt");
        g.play();
    
    }
}
