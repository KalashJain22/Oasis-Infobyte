import java.util.Random;
import java.util.Scanner;

public class guessTheNumberGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("-------------------------------------------------------");
        System.out.println("\tWelcome to the Number Guessing Game!");
        System.out.println("-------------------------------------------------------");
        System.out.println("\n------Choose your difficulty level------\n");
        System.out.println("1. Easy (Range: 1-50, 7 attempts)");
        System.out.println("2. Medium (Range: 1-100, 5 attempts)");
        System.out.println("3. Hard (Range: 1-200, 3 attempts)");
        System.out.print("\nEnter your choice (1/2/3): ");
        int choice = scanner.nextInt();

        int maxAttempts;
        int numberRange;
        double scoreMultiplier;

        switch(choice) {
            case 1:
                maxAttempts = 7;
                numberRange = 50;
                scoreMultiplier = 1.0;
                System.out.println("\n-----Easy mode selected.-----");
                break;
            case 2:
                maxAttempts = 5;
                numberRange = 100;
                scoreMultiplier = 1.5;
                System.out.println("\n-----Medium mode selected.-----");
                break;
            case 3:
                maxAttempts = 3;
                numberRange = 200;
                scoreMultiplier = 2.0;
                System.out.println("\n-----Hard mode selected.-----");
                break;
            default:
                System.out.println("\n----Invalid choice. Defaulting to Medium.----");
                maxAttempts = 5;
                numberRange = 100;
                scoreMultiplier = 1.5;
        }

        final int TOTAL_ROUNDS = 3;
        int score = 0;

        for(int round = 1; round <= TOTAL_ROUNDS; round++) {
            int numberToGuess = random.nextInt(numberRange) + 1;
            int attempts = 0;
            boolean isGuessed = false;

            System.out.println("\n\tRound " + round + " begins! \n(Guess a number between 1 and " + numberRange + ")\n");

            while(attempts < maxAttempts) {
                System.out.print("Attempt " + (attempts + 1) + "/" + maxAttempts + "\nEnter your guess (or 0 to quit): ");
                int userGuess = scanner.nextInt();

                if (userGuess == 0) {
                    System.out.println("\nYou choose to quit! Your final score: " + score);
                    System.out.println("\n---------------Thanks for playing-----------------");
                    scanner.close();
                    return;
                }

                attempts++;

                if (userGuess == numberToGuess) {
                    System.out.println("Correct! You guessed it in " + attempts + " attempts.");
                    int roundScore = (int) ((maxAttempts - attempts + 1) * 10 * scoreMultiplier);
                    score += roundScore;
                    System.out.println("\nYou earned " + roundScore + " points in this round!");
                    isGuessed = true;
                    break;
                } else if (userGuess < numberToGuess) {
                    System.out.println("Too low!, Guess the higher number.");
                } else {
                    System.out.println("Too high!, Guess the smaller number.");
                }

                if (attempts < maxAttempts) {
                    System.out.println("You have " + (maxAttempts - attempts) + " attempts left.\n");
                }
            }

            if (!isGuessed) {
                System.out.println("\nOut of attempts! The correct number was: " + numberToGuess);
            }

            System.out.println("\n----- Score after Round " + round + ": " + score + " -----");
        }

        System.out.println("\nGame Over! Your final score: " + score);
        System.out.println("\n---------------Thanks for playing-----------------");
        scanner.close();
    }
}
