import bagel.*;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2023
 * Please enter your name below
 * @author Yueyue Ma
 */
public class ShadowDance extends AbstractGame  {
    private final static int FRAME_STEP = 1;
    private final static int INDEX_0=0;
    private final static int INDEX_1 =1;
    private final static int INDEX_2=2;
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Font font30 = new Font("res/FSO8BITR.TTF",30);
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");
    private int totalScore = 0;
    private static int leftLane, rightLane, upLane, downLane;
    private final static int LANE_Y = 384;
    private boolean pause = false;
    private boolean begin = false;
    private final Normal normals = new Normal();
    private final Hold holds= new Hold();
    private final Lane lanes= new Lane();
    private final Note notes= new Note();
    private final Message message = new Message(totalScore);
    private final static int MESSAGE_POSITION = 35;
    private final static double DIVIDES = 2.0;
    private static int frame = 0;
    public ShadowDance(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        readCSV();
    }

    public static int getWindowHeight() {
        return Window.getHeight();
    }
    public static int getWindowWidth(){
        return Window.getWidth();
    }

    /**
     * Method used to read file and create objects (you can change
     * this method as you wish).
     */



    private void readCSV() {
        String text;
        try (BufferedReader br = new BufferedReader(new FileReader("res/level1.csv"))) {
            while ((text = br.readLine()) != null) {
                String[] column = text.split(",");
                //put csv into 2D array
                //If the row is a lane, the record is the x coordinate of that lane
                if("Lane".equals(column[INDEX_0])) {
                    switch (column[INDEX_1]) {
                        case "Left":
                            leftLane = Integer.parseInt(column[INDEX_2]);
                            lanes.LaneArray(column[INDEX_1],leftLane, LANE_Y);
                            break;
                        case "Right":
                            rightLane = Integer.parseInt(column[INDEX_2]);
                            lanes.LaneArray(column[INDEX_1],rightLane, LANE_Y);
                            break;
                        case "Up":
                            upLane = Integer.parseInt(column[INDEX_2]);
                            lanes.LaneArray(column[INDEX_1],upLane, LANE_Y);
                            break;
                        case "Down":
                            downLane = Integer.parseInt(column[INDEX_2]);
                            lanes.LaneArray(column[INDEX_1],downLane, LANE_Y);
                            break;
                        }
                    }
                String key = "Nothing";
                //if the line is a note not the lane
                switch (column[INDEX_0]) {
                    case "Left":
                        if("Hold".equals(column[INDEX_1])){
                            holds.holdArray(column[INDEX_0], column[INDEX_2], leftLane, key);
                        }else{
                            normals.normalArray(column[INDEX_0], column[INDEX_2], leftLane, key);
                        }
                        break;
                    case "Right":
                        if("Hold".equals(column[INDEX_1])){
                            holds.holdArray(column[INDEX_0],column[INDEX_2],rightLane, key);
                        }else{
                            normals.normalArray(column[INDEX_0],column[INDEX_2],rightLane, key);
                        }
                        break;
                    case "Down":
                        if("Hold".equals(column[INDEX_1])){
                            holds.holdArray(column[INDEX_0],column[INDEX_2],downLane, key);
                        }else{
                            normals.normalArray(column[INDEX_0],column[INDEX_2],downLane, key);
                        }
                        break;
                    case "Up":
                        if("Hold".equals(column[INDEX_1])){
                            holds.holdArray(column[INDEX_0],column[INDEX_2],upLane, key);
                        }else{
                            normals.normalArray(column[INDEX_0],column[INDEX_2],upLane, key);
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * The entry point for the program.
     */

    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();

        game.run();
    }
    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    /*
    Update the notes and score and message in every frame
     */
    protected void update(Input input) {


        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        if (input.wasPressed(Keys.SPACE)) {
            begin = true;
        }
        if (input.wasPressed(Keys.TAB)) {
            pause = ! pause;
        }

        BACKGROUND_IMAGE.draw(Window.getWidth() / DIVIDES, Window.getHeight() / DIVIDES);

        //begin the main game
        if (begin) {
            //if two array is not empty
            if (! Note.isNullArray(Normal.getCount(), Normal.getNormals()) || !Note.isNullArray(Hold.getCount(), Hold.getHolds())) {
                //if not pause
                if(! pause) {
                    frame++;
                    //draw 4 lanes
                    Lane lane = new Lane();
                    lane.drawLane();
                    //draw the notes in every frame
                    normals.drawNote(frame,pause);
                    holds.drawNote(frame,pause);
                    //find the score if there is a note leave the window without press any key
                    totalScore = normals.remove(totalScore);
                    totalScore = holds.remove(totalScore);
                    // press the key to find the specific score
                    if (input.wasPressed(Keys.LEFT)) {
                        totalScore = notes.derive("Left", totalScore);
                    }
                    if (input.wasPressed(Keys.UP)) {
                        totalScore = notes.derive("Up", totalScore);
                    }
                    if (input.wasPressed(Keys.DOWN)) {
                        totalScore = notes.derive("Down", totalScore);
                    }
                    if (input.wasPressed(Keys.RIGHT)) {
                        totalScore = notes.derive("Right", totalScore);
                    }
                    if (input.wasReleased(Keys.LEFT)) {
                        totalScore = holds.releasedScore("Left", totalScore);
                    }
                    if (input.wasReleased(Keys.UP)) {
                        totalScore = holds.releasedScore("Up", totalScore);
                    }
                    if (input.wasReleased(Keys.DOWN)) {
                        totalScore = holds.releasedScore("Down", totalScore);
                    }
                    if (input.wasReleased(Keys.RIGHT)) {
                        totalScore = holds.releasedScore("Right", totalScore);
                    }
                    //draw score
                    font30.drawString("SCORE " + totalScore, MESSAGE_POSITION, MESSAGE_POSITION);
                    //to print score message
                    notes.drawText(frame);
                    //if paused, draw the stationary note at that frame
                }else{
                    Lane lane = new Lane();
                    lane.drawLane();
                    normals.drawNote(frame + FRAME_STEP, pause);
                    holds.drawNote(frame + FRAME_STEP, pause);
                    font30.drawString("SCORE " + totalScore, MESSAGE_POSITION, MESSAGE_POSITION);
                }
            } else {
                //print the game finish text
                message.gameFinish();
            }
        } else {
            //present the start game window at first
            message.gameBegin();
        }
    }
}