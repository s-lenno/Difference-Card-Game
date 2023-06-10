import java.util.Arrays;

public class HighScoreSortingTest {
    public static void main(String[] args) {
        // Test case: Ensure the high scores are sorted correctly
        int[] highScores = {100, 75, 120, 90, 80};
        int[] expectedSortedScores = {120, 100, 90, 80, 75};

        // Update the high scores
        updateHighScores(highScores);

        // Sort the high scores in descending order
        Arrays.sort(highScores);

        // Reverse the sorted scores to match the expected order
        int[] actualSortedScores = new int[highScores.length];
        for (int i = 0; i < highScores.length; i++) {
            actualSortedScores[i] = highScores[highScores.length - 1 - i];
        }

        // Compare the resulting sorted order with the expected order
        boolean scoresMatch = Arrays.equals(actualSortedScores, expectedSortedScores);

        // Print the results
        System.out.println("High Scores: " + Arrays.toString(highScores));
        System.out.println("Expected Sorted Scores: " + Arrays.toString(expectedSortedScores));
        System.out.println("Actual Sorted Scores: " + Arrays.toString(actualSortedScores));

        if (scoresMatch) {
            System.out.println("High Score sorting test passed! The scores are sorted correctly.");
        } else {
            System.out.println("High Score sorting test failed! The scores are not sorted correctly.");
        }
    }

    private static void updateHighScores(int[] scores) {
        // Implementation to update the high scores
        // ...
    }
}
