import javax.swing.JOptionPane;

class GuessNumber {
    public static void main(String[] args) {
        //TASK 1  
        boolean playAgain;

        do {
            playGame();

            int option = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play Again?", JOptionPane.YES_NO_OPTION);
            playAgain = (option == JOptionPane.YES_OPTION);
        } while (playAgain);

        JOptionPane.showMessageDialog(null, "Thank you for playing!");
    }

    public static void playGame() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 10;
        int randomNumber;
        boolean run = true;

        JOptionPane.showMessageDialog(null, "Welcome to the Number Guessing Game!");
        JOptionPane.showMessageDialog(null, "I have selected a number between 1 and 100. Try to guess it!");

        randomNumber = (int) (Math.random() * 100) + 1; //random number

        while (run) {
            String guessedString = JOptionPane.showInputDialog("Enter Your Guess: ");
            int guessedNumber;

            try {
                guessedNumber = Integer.parseInt(guessedString);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number!");
                continue; 
            }

            attempts++;

            if (guessedNumber == randomNumber) {
                JOptionPane.showMessageDialog(null, "Congratulations! You guessed the number " + guessedNumber + " correctly in " + attempts + " attempts!");
                run = false;
            } else if (guessedNumber < randomNumber) {
                JOptionPane.showMessageDialog(null, "You guessed smaller! Guess bigger.");
            } else if (guessedNumber > randomNumber) {
                JOptionPane.showMessageDialog(null, "You guessed bigger! Guess smaller.");
            }

            if (attempts == MAX_ATTEMPTS) {
                JOptionPane.showMessageDialog(null, "Sorry, Maximum attempts are done. The secret number was: " + randomNumber);
                break;
            }
        }
    }
}
