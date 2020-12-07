package unluckyrobot;

import java.util.Random;
import java.util.Scanner;

/**
 * @author Alex Nguyen
 */
public class UnluckyRobot {

    public static void main(String[] args) {
        Scanner con = new Scanner(System.in);
        
        int totalScore = 300;
        int x = 0;
        int y = 0;
        int itrCount;
        
        for (itrCount = 0; !isGameOver(x, y, totalScore, itrCount); itrCount++) {
            displayInfo(x, y, itrCount, totalScore);
            char direction = inputDirection();

            if (doesExceed(x, y, direction)) {
                System.out.println("Exceed boundary, -2000 damage applied");
                totalScore -= 2000;
            }
            
            int reward = reward();   
            switch (direction) {
                case 'w':
                    y += 1;
                    totalScore += punishOrMercy(direction, reward) - 10;
                    break;
                case 'a':
                    x -= 1;
                    totalScore += reward - 50;
                    break;
                case 's':
                    y -= 1;
                    totalScore += reward - 50;
                    break;
                default:
                    x += 1;
                    totalScore += reward - 50;
                    break;
            }
            System.out.println("");
        }
        System.out.print("Enter your first and last name: ");
        String name = toTitleCase(con.nextLine());
        evaluation(name, totalScore);
    }
    
    /**
     * Prints your current location as well as the total score and the 
     * number of iteration so far.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param itrCount The number of iterations.
     * @param totalScore The total score.
     */
    public static void displayInfo(int x, int y, int itrCount, int totalScore) {
        System.out.printf("%s (X=%d, Y=%d) %s: %d %s: %d\n", "For point", x, y, 
                "at iterations", itrCount, "the total score is", totalScore);
    }
    
    /**
     * Checks if the robot is outside of the 5x5 grid.
     * @param x The x coordinate.
     * @param y The x coordinate.
     * @param direction 
     * @return Returns true if the robot exceeds the 5x5 grid, false if not.  
     */
    public static boolean doesExceed(int x, int y, char direction) {
        if (direction == 'w' && y + 1 > 4)
            return true;
        else if (direction == 'a' && x - 1 < 0)
            return true;
        else if (direction == 's' && y - 1 < 0)
            return true;
        else return direction == 'd' && x + 1 > 4;
    }
    
    /**
     * Adds or subtracts when the robot makes a move
     * @return -100 for 1, -200 for 2, -300 for 3, 300 for 4, 400 for 5 and 
     * 600 for 6
     */
    public static int reward() {
        Random rng = new Random();
        int ranNum = rng.nextInt(6) + 1;
        switch (ranNum) {
            case 1:
                System.out.println("Dice: 1, reward: -100");
                return -100;
            case 2:
                System.out.println("Dice: 2, reward: -200");
                return -200;
            case 3:
                System.out.println("Dice: 3, reward: -300");
                return -300;
            case 4:
                System.out.println("Dice: 4, reward: 300");
                return 300;
            case 5:
                System.out.println("Dice: 5, reward: 400");
                return 400;
            default:
                System.out.println("Dice: 6, reward: 600");
                return 600;
        }
    }
    
    /**
     * Gives a 1 in 2 chance of nullifying the negative rewards if they
     * inputted "w" for "up".
     * @param direction the direction.
     * @param reward The points.
     * @return Either 0 to nullify the negative reward or 1 to apply the 
     * negative reward.
     */
    public static int punishOrMercy(char direction, int reward) {
        Random rng = new Random();
        int ranNum = rng.nextInt(2);
        
        if (reward < 0 && direction == 'w')
            switch(ranNum) {
                case 0:
                    System.out.println("Coin: tail | Mercy, the negative reward is removed.");
                    return 0;
                case 1:
                    System.out.println("Coin: head | No mercy, the negative reward is applied.");
                    return reward; 

            }
        return reward;
    }
    
    /**
     * Brings a 2 worded string to title case.
     * @param str The string.
     * @return The string in title case.
     */
    public static String toTitleCase(String str) {
        char firstC = Character.toTitleCase(str.charAt(0));
        char lastC = Character.toTitleCase(str.charAt(str.indexOf(" ") + 1));
        String firstStr = str.substring(1, str.indexOf(" ")).toLowerCase();
        String lastStr = str.substring(str.indexOf(" ") + 2).toLowerCase();
        return firstC + firstStr + " " +lastC + lastStr;
    }
    
    /**
     * Prints the end results of the game.
     * @param name Your name.
     * @param totalScore The total amount of score accumulated during the game.
     */
    // The PDF didn't consider the other ways to win so whenever you reach one 
    // of the end tiles or exceed the 20th iteration, it'll print the else part.
    public static void evaluation(String name, int totalScore) { 
        if (totalScore >= 2000 ) 
            System.out.printf("%s, %s, %s %d\n", "Victory", name, 
                    "your score is", totalScore);
        else
            System.out.printf("%s, %s, %s %d\n", "Mission failed", name,
                    "your score is", totalScore);       
    }
    
    /**
     * User is asked to input a direction (WASD -> up, left, down, 
     * right respectively).
     * @return A direction.
     */
    public static char inputDirection() {
        Scanner con = new Scanner(System.in);
        char c;
        
        do {
        System.out.print("Enter a valid direction (WASD): ");
        c = con.next().toLowerCase().charAt(0);
        } while (c != 'w' && c != 'a' && c != 's' && c != 'd');
        
        return c;
    }
    
    /**
     * Checks if the game is over when any of the condition is met.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param totalScore The total score.
     * @param itrCount The number of iterations.
     * @return True if the game is over, false if not.
     */
    public static boolean isGameOver(int x, int y, int totalScore, int itrCount) {
        return itrCount > 20 || totalScore < -1000 || totalScore > 2000 ||
                x == 4 && y == 4 || x == 4 && y == 0;
    }
}
