import bagel.Image;

public class Note {
    private String max_node = "nothing";
    Hold holdNote = new Hold();
    Normal normalNote = new Normal();
    private final Image HOLD_RIGHT = new Image("res/holdNoteRight.png");
    private final Image HOLD_LEFT = new Image("res/holdNoteLeft.png");
    private final Image HOLD_UP = new Image("res/holdNoteUp.png");
    private final Image HOLD_DOWN = new Image("res/holdNoteDown.png");
    private final static int TIME = 30;
    private final static int INITIAL_H = 24;
    private final static int INITIAL_N = 100;
    private final static int N_COUNT = 0, F_COUNT = 1, X_COUNT = 2, Y_COUNT = 3, K_COUNT = 4;
    private final Image NOTE_LEFT = new Image("res/noteLeft.png");
    private final Image NOTE_DOWN = new Image("res/noteDown.png");
    private final Image NOTE_RIGHT = new Image("res/noteRight.png");
    private final Image NOTE_UP = new Image("res/noteUp.png");
    private final static int STEP = 2;
    private final static int FRAME_STEP = 1;
    public Note() {

    }
    /*
        It is going to find which note will be pressed first and store the score first.
        */
    public int derive(String note, int score) {
        String[][] holds = Hold.getHolds();
        String[][] normals = Normal.getNormals();
        int max = 0;
        int count = 0;
        //check the current max y coordinate note in hold
        for (int i = 0; i < Hold.getCount(); i++) {
            if (holds[i][N_COUNT] != null) {
                //if the note is moving
                if (note.equals(holds[i][N_COUNT]) && Integer.parseInt(holds[i][Y_COUNT]) > INITIAL_H
                        && "Nothing".equals(holds[i][K_COUNT])) {
                    if (max < Integer.parseInt(holds[i][Y_COUNT])) {
                        max = Integer.parseInt(holds[i][Y_COUNT]);
                        count = i;
                        max_node = "Hold";
                    }
                }
            }
        }
        //check the current max y coordinate note in normal
        for (int j = 0; j < Normal.getCount(); j++) {
            if (normals[j][N_COUNT] != null) {
                //if the note is moving
                if (note.equals(normals[j][N_COUNT]) && Integer.parseInt(normals[j][Y_COUNT]) > INITIAL_N
                        && "Nothing".equals(normals[j][K_COUNT])) {
                    //find the biggest note y position to fit the estimate
                    if (max < Integer.parseInt(normals[j][Y_COUNT])) {
                        max = Integer.parseInt(normals[j][Y_COUNT]);
                        count = j;
                        max_node = "Normal";
                    }
                }
            }
        }
        //if the normal is the node to have the max y,so need first access it
        if ("Normal".equals(max_node)) {
            //total score
            score = normalNote.normalScore(count, max, score);
            // if the hold is the node to have the max y,so need first access it
        }else if("Hold".equals(max_node)){
            //total score
            score = holdNote.pressScore(count, max, score);
        }else{
            return score;
        }
        return score;
    }


    /*
       It is going to draw each note that satisfy in that frame with pause or not pause.
       Also, update the frame and y position of satisfying notes.
        */
    public void drawNote(int frame, Boolean pause, int count, String[][] note,String type){
        for(int i = 0; i < count; i++){
            if(note[i][N_COUNT] != null){
                //if the frame is the same
                if(Integer.parseInt(note[i][F_COUNT]) == frame) {
                    switch (note[i][N_COUNT]) {
                        case "Left":
                            if("normal".equals(type)) {
                                NOTE_LEFT.draw(Integer.parseInt(note[i][X_COUNT]), Integer.parseInt(note[i][Y_COUNT]));
                            }else{
                                HOLD_LEFT.draw(Integer.parseInt(note[i][X_COUNT]), Integer.parseInt(note[i][Y_COUNT]));
                            }
                            break;
                        case "Right":
                            if("normal".equals(type)) {
                                NOTE_RIGHT.draw(Integer.parseInt(note[i][X_COUNT]), Integer.parseInt(note[i][Y_COUNT]));
                            }else{
                                HOLD_RIGHT.draw(Integer.parseInt(note[i][X_COUNT]), Integer.parseInt(note[i][Y_COUNT]));
                            }
                            break;
                        case "Up":
                            if("normal".equals(type)) {
                                NOTE_UP.draw(Integer.parseInt(note[i][X_COUNT]), Integer.parseInt(note[i][Y_COUNT]));
                            }else{
                                HOLD_UP.draw(Integer.parseInt(note[i][X_COUNT]), Integer.parseInt(note[i][Y_COUNT]));
                            }
                            break;
                        case "Down":
                            if("normal".equals(type)) {
                                NOTE_DOWN.draw(Integer.parseInt(note[i][X_COUNT]), Integer.parseInt(note[i][Y_COUNT]));
                            }else{
                                HOLD_DOWN.draw(Integer.parseInt(note[i][X_COUNT]), Integer.parseInt(note[i][Y_COUNT]));
                            }
                            break;
                    }
                    //if it is not paused, then add frame and step size to
                    if(!pause){
                        note[i][F_COUNT] = String.valueOf(Integer.parseInt(note[i][F_COUNT]) + FRAME_STEP);
                        note[i][Y_COUNT] = String.valueOf(Integer.parseInt(note[i][Y_COUNT]) + STEP);
                    }
                }
            }


        }

    }

    /*
    To check if the array is null or not
         */
    public static Boolean isNullArray(int count, String[][] note){
        //to check whether the array is null
        for(int i = 0; i < count; i ++){
            if(note[i][N_COUNT] != null){
                return false;
            }
        }
        return true;
    }

    /*
       Draw each score message for 30 frame if appeared.
        */
    public void drawText(int frame) {
        //if the stop time of normal is bigger than hold, so the normal one press after hold one
        if (Normal.getStopTime() > Hold.getStopTime()) {
            //if not a nothing message
            if (!"Nothing".equals(Normal.getMessage())) {
                //run 30 frame score text
                if ((frame - Normal.getStopTime()) <= TIME) {
                    Message quote = new Message(Normal.getMessage());
                    quote.scoreMessage();
                }
            }
            //if not a nothing message
        } else {
            if (!"Nothing".equals(Hold.getMessage())) {
                //run 30 frame score text
                if ((frame - Hold.getStopTime()) <= TIME) {
                    Message quote = new Message(Hold.getMessage());
                    quote.scoreMessage();
                }
            }
        }
    }
}
