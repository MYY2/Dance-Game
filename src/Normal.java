
public class Normal {
    private static int count = 0;
    private final static int WINDOW_HEIGHT = 768;
    private final static int N_COUNT = 0, F_COUNT = 1, X_COUNT = 2, Y_COUNT = 3, K_COUNT = 4;
    private final static int MAX = 400;
    private final static int LANE_NOTE = 657;
    private final static int MISS_SCORE = -5;
    private final static int MAX_RANK = 5;
    private static final String[][] normals = new String[MAX][MAX_RANK];
    private static int stopTime = 0;
    private final static int INITIAL_NOTE_Y = 100;
    private static String message = "Nothing";
    public Normal(){

    }
    /*
    this method is going to store 2D array with all normal notes
    */
    public void normalArray(String note, String frame, int x, String key){
        normals[count][N_COUNT] = note;
        normals[count][F_COUNT] = frame;
        normals[count][X_COUNT] = String.valueOf(x);
        normals[count][Y_COUNT] = String.valueOf(INITIAL_NOTE_Y);
        normals[count][K_COUNT] = key;
        count ++;

    }
    /*
    This method is going to draw the every normal notes in that frame of the game.
     */
    public void drawNote(int frame, Boolean pause){
        // draw the normal note in pause or not pause
        if(! Note.isNullArray(count, normals)){
            Note note = new Note();
            note.drawNote(frame, pause, count ,normals, "normal");
        }
    }

    /* To remove the notes if it out of the screen or is pressed.
       To record and return a score of the notes when it is moving out the screen
       without any key pressed.
     */
    public int remove(int missingScore) {
        //in order to find the score that the key is not pressed
        for (int i = 0; i < count; i++) {
            //if note is leave the window
            if (normals[i][N_COUNT] != null) {
                if (Integer.parseInt(normals[i][Y_COUNT]) >= WINDOW_HEIGHT) {
                    //set null
                    normals[i][N_COUNT] = null;
                    //get the frame of the note when they are leaving the window
                    stopTime = Integer.parseInt(normals[i][F_COUNT]);
                    missingScore += MISS_SCORE;
                    message = "MISS";
                    //if the note has pressed,remove it
                } else if ("release".equals(normals[i][K_COUNT])) {
                        normals[i][N_COUNT] = null;
                    }
                }
            }
        return missingScore;
    }

    private final Score findScore = new Score();

    /*
    To get the score of the normal note and return the total score up to now.
         */
    public int normalScore(int count, int y, int score){
        if(normals[count][N_COUNT] != null) {
                int distance = Math.abs(y - LANE_NOTE);
                //if there is a score change
                if((score - findScore.noteScore(distance, score)) != 0) {
                    //update total score
                    score = findScore.noteScore(distance, score);
                    normals[count][K_COUNT] = "release";
                    //get the frame of the note
                    stopTime = Integer.parseInt(normals[count][F_COUNT]);
                    message = Score.getMessage();
                }
        }
        return score;
    }

    public static String[][] getNormals() {
        return normals;
    }

    public static int getCount() {
        return count;
    }

    public static int getStopTime() {
        return stopTime;
    }
    public static String getMessage(){
        return message;

    }

}

