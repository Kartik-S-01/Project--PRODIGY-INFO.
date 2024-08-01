import java.util.*;

public class quiz 
{
    public static void main(String[] args) 
    {
        
        Scanner sc = new Scanner(System.in);
        Random ran = new Random();
        int guessingnumber = ran.nextInt(100) + 1; //This function is use for generating a number between 1 to 100
        int atmp = 0;
        boolean xyz = false;
        
        System.out.println("Welcome to the Guessing the Number Quiz");
        System.out.println("Start selecting a number between 1 to 100");
        
        while (!xyz) 
        {
            System.out.print("\nEnter your guess: ");
            int abc = sc.nextInt();
            atmp++;
            
            if (abc < guessingnumber) 
            {
                System.out.println("\nNumber is too low, Try again.");
            } 
            else if (abc > guessingnumber) 
            {
                System.out.println("\nNumber is too high, Try again.");
            } 
            else 
            {
                xyz = true;
                System.out.println("\nCongrats, You have guessed the correct number " + guessingnumber + " in " + atmp + " attempts.");
            }
        }
        
        sc.close();
    }
}
