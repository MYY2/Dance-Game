public class Score {

    private final static int PERFECT_DIS = 15;
    private final static int GOOD_DIS = 50;
    private final static int GOOD_SCORE = 5, MISS_SCORE = -5;
    private final static int PERFECT_SCORE = 10;
    private final static int BAD_DIS = 100;
    private static String message = "Nothing";

    private final static int MISS_DIS = 200;
    private final static int BAD_SCORE = -1;
    public Score(){

    }

    /*
        Calculate the every note's score.
        Returns the total points accumulated so far.
     */
   public int noteScore(int distance, int score){
       //find the score based on the distance
       if (distance <= PERFECT_DIS) {
           score += PERFECT_SCORE;
           message = "PERFECT";
       } else if (distance <= GOOD_DIS) {
           message = "GOOD";
           score += GOOD_SCORE;
       } else if (distance <= BAD_DIS) {
           message = "BAD";
           score += BAD_SCORE;

       } else if (distance <= MISS_DIS) {
           message = "MISS";
           score += MISS_SCORE;
       }

       return score;
   }

    /*
    Get the score message and return the current score message.
    Once the message return, then the message will set to "Nothing"
    in order to don’t obfuscate each message.
     */
    public static String getMessage() {
        // Store the current message
        String currentMessage = message;
        // Reset the message to "Nothing"
        message = "Nothing";
        // Return the current message
        return currentMessage;
    }
}
