public class Hold {
    private final static int INITIAL_NOTE_H = 24;
    private final static int PERFECT_DIS = 15;
    private final static int GOOD_DIS = 50;
    private final static int HOLD_LENGTH = 82;
    private final static int GOOD_SCORE = 5, MISS_SCORE = -5,  BAD_SCORE = -1;
    private final static int PERFECT_SCORE = 10;
    private final static int BAD_DIS = 100;
    private static int count = 0;
    private final static int MAX = 80;
    private final static int MAX_RANK = 5;
    private static final String[][] holds = new String[MAX][MAX_RANK];
    private final static int WINDOW_HEIGHT = 768;
    private static int stopTime = 0;
    private final static int LANE_Y_NOTE = 657;
    private final static int MISS_DIS = 200;
    private final static int N_COUNT = 0;
    private final static int F_COUNT = 1;
    private final static int X_COUNT = 2;
    private final static int Y_COUNT = 3;
    private final static int K_COUNT = 4;

    private final Score findScore = new Score();
    private static String message = "Nothing";

    public Hold() {

    }
    /*
    This method is going to store 2D array with all hold notes
     */
    public void holdArray(String note, String frame, int x, String key) {
        holds[count][N_COUNT] = note;
        holds[count][F_COUNT] = frame;
        holds[count][X_COUNT] = String.valueOf(x);
        holds[count][Y_COUNT] = String.valueOf(INITIAL_NOTE_H);
        holds[count][K_COUNT] = key;
        count++;
    }
    /*
    This method is going to draw the every hold notes in that frame of the game.
     */
    public void drawNote(int frame, Boolean pause) {
        if(! Note.isNullArray(count, holds)) {
            Note note = new Note();
            note.drawNote(frame, pause, count, holds, "hold");
        }
    }

    /*
    This pressScore method is going to record the score when the press key
    is accessed of the hold note.
    It will return an integer that store the total score of the current notes.
    Also, the method will update the score message.
     */
    public int pressScore(int count, int y, int score) {
        int distance = Math.abs(y + HOLD_LENGTH - LANE_Y_NOTE);
        //calculate the score
        if ("Nothing".equals(holds[count][K_COUNT])) {
            //if the score is changed
            if ((score - findScore.noteScore(distance, score)) != 0) {
                message = Score.getMessage();
                score = findScore.noteScore(distance, score);
                stopTime = Integer.parseInt(holds[count][F_COUNT]);
                holds[count][K_COUNT] = "release";
                //if the adding score is representing miss, then the key can have another change to press
                if ((findScore.noteScore(distance, score) - score) == MISS_SCORE) {
                    holds[count][K_COUNT] = "Nothing";
                    message = "MISS";
                }
            }
        }
        return score;
    }


    /*
    releasedScore method is going to record the hold score when the key is released.
    It returns the total score of the current notes.
    Also, the method will update the score message.
     */
    public int releasedScore (String note, int score) {
        for(int i = 0; i < count; i++) {
            if (holds[i][N_COUNT] != null) {
                //if the note is not pressed before and is moving
                if (holds[i][K_COUNT].equals("release") && note.equals(holds[i][N_COUNT]) &&
                        Integer.parseInt(holds[i][Y_COUNT]) > INITIAL_NOTE_H ) {
                    //calculate the score
                    int distance = Math.abs(Integer.parseInt(holds[i][Y_COUNT]) - HOLD_LENGTH - LANE_Y_NOTE);
                    if (distance <= PERFECT_DIS) {
                        score += PERFECT_SCORE;
                        message = "PERFECT";
                        stopTime = Integer.parseInt(holds[i][F_COUNT]);
                        holds[i][K_COUNT] = "false";
                        break;
                    } else if (distance <= GOOD_DIS) {
                        score += GOOD_SCORE;
                        message = "GOOD";
                        stopTime = Integer.parseInt(holds[i][F_COUNT]);
                        holds[i][K_COUNT] = "false";
                        break;
                    } else if (distance <= BAD_DIS) {
                        score += BAD_SCORE;
                        message = "BAD";
                        stopTime = Integer.parseInt(holds[i][F_COUNT]);
                        holds[i][K_COUNT] = "false";
                        break;
                    } else if (distance <= MISS_DIS) {
                        score += MISS_SCORE;
                        message = "MISS";
                        stopTime = Integer.parseInt(holds[i][F_COUNT]);
                        holds[i][K_COUNT] = "false";
                        break;
                    } else {
                        score += MISS_SCORE;
                        stopTime = Integer.parseInt(holds[i][F_COUNT]);
                        holds[i][K_COUNT] = "false";
                        message = "MISS";
                        break;
                    }
                }
            }

        }
        return score;
    }

    /*
    To remove the notes if it out of the screen or is released.
    To record and return a score of the notes when it is moving out the screen
    without any key pressed.
    */
    public int remove(int missingScore){
        for(int i = 0; i < count; i++){
            //if top note is leave the window
            if(holds[i][N_COUNT] != null){
                if(Integer.parseInt(holds[i][Y_COUNT]) - HOLD_LENGTH >= WINDOW_HEIGHT){
                    missingScore += MISS_SCORE;
                    stopTime = Integer.parseInt(holds[i][F_COUNT]);
                    message = "MISS";
                    //set null
                    holds[i][N_COUNT] = null;
                }
                //if the note is released, set null
                if("false".equals(holds[i][K_COUNT])){
                    holds[i][N_COUNT] = null;
                }
            }
        }
        return missingScore;
    }


    /*
    Get a 2D array that contain holds notes
     */
    public static String[][] getHolds() {
        return holds;
    }

    /*
    Get a frame number when hold note is missing
     */
    public static int getStopTime() {
        return stopTime;
    }
    /*
    Get message that present the current score message
     */
    public static String getMessage(){
        return message;
    }

    public static int getCount() {
        return count;
    }
}

