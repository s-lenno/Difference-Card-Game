import java.util.Random;
import java.util.Scanner;

public class Deck {
    private static final int DEALT_HANDS = 3;
    private static final int CARDS_IN_HAND = 5;
    private static final int HIGH_SCORERS = 3;

    private final Card[] deck;
    private final Card[][] hands;
    private int totalScore;
    private final String[] highScoreNames;
    private final int[] highScores;
    private int numHighScores;
    private final int[][] smallGuesses;
    private final int[][] largeGuesses;

    public Deck() {
        deck = new Card[52];
        hands = new Card[DEALT_HANDS][CARDS_IN_HAND];
        totalScore = 0;
        highScoreNames = new String[HIGH_SCORERS];
        highScores = new int[HIGH_SCORERS];
        numHighScores = 0;
        smallGuesses = new int[DEALT_HANDS][];
        largeGuesses = new int[DEALT_HANDS][];

        initialiseDeck();
        shuffleDeck();
        dealHands();
        newGame();
        displayHighScores();
    }

    private void initialiseDeck() {
        int cardIndex = 0;
        for (Rank rank: Rank.values()) {
            for (Suit suit: Suit.values()) {
                deck[cardIndex] = new Card(rank, suit);
                cardIndex++;
            }
        }
    }

    private void shuffleDeck() {
        Random random = new Random();
        int n = deck.length;

        for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
    }

    private void dealHands() {
        int deckIndex = 0;
        for (int i = 0; i < DEALT_HANDS; i++) {
            for (int j = 0; j < CARDS_IN_HAND; j++) {
                hands[i][j] = deck[deckIndex];
                deckIndex++;
            }
        }
    }

    private void playHand(int handIndex) {
        Card[] hand = hands[handIndex];

        int score;
        int smallestDiff = Integer.MAX_VALUE;
        int largestDiff = Integer.MIN_VALUE;

        for (int i = 0; i < CARDS_IN_HAND - 1; i++) {
            for (int j = i + 1; j < CARDS_IN_HAND; j++) {
                int diff = Math.abs(hand[i].getRankValue() - hand[j].getRankValue());
                smallestDiff = Math.min(smallestDiff, diff);
                largestDiff = Math.max(largestDiff, diff);
            }
        }

        System.out.println("* DIFFERENCE CARD GAME *");
        System.out.println("---Hand " + (handIndex + 1) + " ---");
        for (Card card: hand) {
            System.out.print(card + " ");
        }
        System.out.println();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a guess for the card with the smallest difference: ");
        int smallGuess = validateIntegerInput(scanner);
        smallGuesses[handIndex] = new int[] {
                smallGuess
        };

        System.out.print("Enter a guess for the card with the largest difference: ");
        int largeGuess = validateIntegerInput(scanner);
        largeGuesses[handIndex] = new int[] {
                largeGuess
        };

        System.out.println("\nYour given smallest difference is " + smallGuess);
        System.out.println("Your given largest difference is " + largeGuess);

        if (smallGuess == smallestDiff && largeGuess == largestDiff) {
            score = largestDiff - smallestDiff;
            totalScore += score + 1;
            System.out.println("Bonus point added!");
        } else {
            score = largestDiff - smallestDiff;
            totalScore += score;
            System.out.println("Bonus missed.");
        }

        System.out.println("Smallest difference is " + smallestDiff);
        System.out.println("Largest difference is " + largestDiff);
        System.out.println("Your score is " + score);
        System.out.println();
    }

    public void newGame() {
        for (int i = 0; i < DEALT_HANDS; i++) {
            playHand(i);
        }

        System.out.println("Total score is " + totalScore);

        Scanner scanner = new Scanner(System.in);

        if (isHighScore(totalScore)) {
            System.out.print("You achieved a high score. Enter your name: ");
            String playerName = validateNameInput(scanner);
            updateHighScores(playerName, totalScore);
        }

        displayHighScores();
    }

    private void updateHighScores(String playerName, int score) {
        if (numHighScores < HIGH_SCORERS) {
            highScoreNames[numHighScores] = playerName;
            highScores[numHighScores] = score;
            numHighScores++;
        } else {
            int lowestScore = highScores[HIGH_SCORERS - 1];
            if (score > lowestScore) {
                highScoreNames[HIGH_SCORERS - 1] = playerName;
                highScores[HIGH_SCORERS - 1] = score;
            }
        }

        for (int i = 0; i < numHighScores - 1; i++) {
            for (int j = i + 1; j < numHighScores; j++) {
                if (highScores[j] > highScores[i]) {
                    int tempScore = highScores[i];
                    highScores[i] = highScores[j];
                    highScores[j] = tempScore;

                    String tempName = highScoreNames[i];
                    highScoreNames[i] = highScoreNames[j];
                    highScoreNames[j] = tempName;
                }
            }
        }
    }

    private void displayHighScores() {
        System.out.println("--- High Scores ---");
        if (numHighScores == 0) {
            System.out.println("No high scores!");
        } else {
            for (int i = 0; i < numHighScores; i++) {
                System.out.println((i + 1) + ". " + highScoreNames[i] + ": " + highScores[i]);
            }
        }
        playAgain();
    }

    private boolean isHighScore(int score) {
        return numHighScores < HIGH_SCORERS || score > highScores[HIGH_SCORERS - 1];
    }

    private void playAgain() {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < hands.length; i++) {
            System.out.print("\nDo you want to see the replay before starting a new game? (y/n): ");
            String replayOption = scanner.next();
            if (replayOption.equalsIgnoreCase("y")) {
                replayGame();
            }

            System.out.print("Do you want to play a new game? (y/n): ");
            String newGameOption = scanner.next();
            if (newGameOption.equalsIgnoreCase("y")) {
                newGame();
            } else if (newGameOption.equalsIgnoreCase("n")) {
                System.out.println("Game end - SL 2023");
                System.exit(0);
            }
        }
    }

    private void replayGame() {
        System.out.println("Replaying the game move by move:");
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < DEALT_HANDS; i++) {
            Card[] hand = hands[i];

            System.out.println("---Replaying Hand " + (i + 1) + " ---");
            for (Card card: hand) {
                System.out.print(card + " ");
            }
            System.out.println();

            System.out.print("Press any key to continue.");
            scanner.nextLine();

            int smallGuess = smallGuesses[i][0];
            int largeGuess = largeGuesses[i][0];

            System.out.println("Your given smallest difference was " + smallGuess);
            System.out.println("Your given largest difference was " + largeGuess);

            int smallestDiff = Integer.MAX_VALUE;
            int largestDiff = Integer.MIN_VALUE;

            for (int j = 0; j < CARDS_IN_HAND - 1; j++) {
                for (int k = j + 1; k < CARDS_IN_HAND; k++) {
                    int diff = Math.abs(hand[j].getRankValue() - hand[k].getRankValue());
                    smallestDiff = Math.min(smallestDiff, diff);
                    largestDiff = Math.max(largestDiff, diff);
                }
            }

            System.out.println("Smallest difference is " + smallestDiff);
            System.out.println("Largest difference is " + largestDiff);
            System.out.println();
        }
    }

    private int validateIntegerInput(Scanner scanner) {
        while (true) {
            String input = scanner.next();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! \nPlease enter a number for the difference: ");
            }
        }
    }

    private String validateNameInput(Scanner scanner) {
        while (true) {
            String input = scanner.next();
            if (input.matches("[A-Za-z]+")) {
                return input;
            } else {
                System.out.print("Invalid input. \nPlease enter a valid name: ");
            }
        }
    }
}