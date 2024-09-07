import bagel.Font;

public class Message {
    private final Font font64 = new Font("res/FSO8BITR.TTF", 64);
    private final Font font40 = new Font("res/FSO8BITR.TTF", 40);
    private final Font font24 = new Font("res/FSO8BITR.TTF", 24);
    private static final int HEIGHT = ShadowDance.getWindowHeight();
    private static final int WIDTH = ShadowDance.getWindowWidth();
    private static final int MESSAGE_HEIGHT = HEIGHT / 2;
    private final static int SUBTITLE_HEIGHT = 440;
    private final static int TITLE_HEIGHT = 250;
    private final static int WIN_SCORE = 150;
    private int totalScore;
    private String message;

    public Message(int totalScore) {
        this.totalScore=totalScore;
    }
    public Message(String message) {
        this.message=message;
    }


    /*
    To get a message when the game is finish.
    If the total gain score is bigger than 150, then message is "CLEAR!".
    If the total gain score is smaller than 150, then message is "TRY AGAIN".
     */
    public void gameFinish() {
        //if the total score is bigger than the win score
        if (totalScore >= WIN_SCORE) {
            //draw the win message
            Width finalMessage = new Width(WIDTH, (int) font64.getWidth("CLEAR!"));
            font64.drawString("CLEAR!", finalMessage.messageWidth(), MESSAGE_HEIGHT);
        } else {
            //draw the fail message
            Width finalMessage = new Width(WIDTH, (int) font64.getWidth("TRY AGAIN"));
            font64.drawString("TRY AGAIN", finalMessage.messageWidth(), MESSAGE_HEIGHT);
        }
    }

    /*
    To get a message when the game is not begin yet.
    String will be drawn at center.
        */
    public void gameBegin() {
        Width widthTitle = new Width(WIDTH, (int) font64.getWidth("SHADOW DANCE"));
        font64.drawString("SHADOW DANCE", widthTitle.messageWidth(), TITLE_HEIGHT);
        Width widthSubtitle = new Width(WIDTH, (int) font24.getWidth("PRESS SPACE TO START"));
        font24.drawString("PRESS SPACE TO START\nUSE ARROW KEYS TO PLAY",
                widthSubtitle.messageWidth(), SUBTITLE_HEIGHT);
    }
     /*
    To get a message when the score is gained.
    String will be drawn at center.
        */
    public void scoreMessage() {
        Width scoreMessage = new Width(WIDTH, (int) font40.getWidth(message));
        font40.drawString(message, scoreMessage.messageWidth(), MESSAGE_HEIGHT);
    }

}
