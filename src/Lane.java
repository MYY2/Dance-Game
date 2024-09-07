import bagel.Image;

public class Lane {
    private final Image LEFT_LANE = new Image("res/laneLeft.png");
    private final Image RIGHT_LANE = new Image("res/laneRight.png");
    private final Image UP_LANE = new Image("res/laneUp.png");
    private final Image DOWN_LANE = new Image("res/laneDown.png");
    private final static int MAX_COUNT = 4;
    private final static int MAX_RANK = 3;
    private final static String[][] Lanes = new String[MAX_COUNT][MAX_RANK];
    private final static int N_COUNT = 0;
    private final static int X_COUNT = 1;
    private final static int Y_COUNT = 2;
    private static int count = 0;
    public Lane() {

    }

    /*
    Get a 2D lane array to store it direction, coordinate of x and y.
     */
    public void LaneArray(String direction, int x, int y) {
        Lanes[count][N_COUNT] = direction;
        Lanes[count][X_COUNT] = String.valueOf(x);
        Lanes[count][Y_COUNT] = String.valueOf(y);
        count++;
    }

    /*
    It will find the correct lane and coordinate.
    Then draw 4 lanes in every frame.
     */
    public void drawLane() {
        //find the matched lane and coordinates
        for (int i = 0; i < MAX_COUNT; i++) {
            switch (Lanes[i][N_COUNT]) {
                case "Left":
                    LEFT_LANE.draw(Integer.parseInt(Lanes[i][X_COUNT]), Integer.parseInt(Lanes[i][Y_COUNT]));
                    break;
                case "Right":
                    RIGHT_LANE.draw(Integer.parseInt(Lanes[i][X_COUNT]), Integer.parseInt(Lanes[i][Y_COUNT]));
                    break;
                case "Up":
                    UP_LANE.draw(Integer.parseInt(Lanes[i][X_COUNT]), Integer.parseInt(Lanes[i][Y_COUNT]));
                    break;
                case "Down":
                    DOWN_LANE.draw(Integer.parseInt(Lanes[i][X_COUNT]), Integer.parseInt(Lanes[i][Y_COUNT]));
                    break;
            }
        }
    }
}
