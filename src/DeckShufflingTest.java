import java.util.Arrays;
import java.util.Random;

public class DeckShufflingTest {
    public static void main(String[] args) {
        Card[] expectedDeck = {
                new Card(Rank.Ace, Suit.Spades),
                new Card(Rank.Two, Suit.Hearts),
                new Card(Rank.Three, Suit.Clubs),
        };

        // Create a copy of the expected deck for shuffling
        Card[] actualDeck = Arrays.copyOf(expectedDeck, expectedDeck.length);

        // Shuffle the deck
        shuffleDeck(actualDeck);

        // Compare the actual deck with the expected output (expected deck)
        boolean match = Arrays.equals(expectedDeck, actualDeck);

        System.out.println("Expected Deck: \n" + Arrays.toString(expectedDeck));
        System.out.println("Actual Deck: \n" + Arrays.toString(actualDeck));

        if (match) {
            System.out.println("Deck shuffling test passed! The expected deck matches the actual deck.");
        } else {
            System.out.println("Deck shuffling test failed! The expected deck does not match the actual deck.");
        }
    }

    private static void shuffleDeck(Card[] deck) {
        Random random = new Random();
        int n = deck.length;

        for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
    }
}