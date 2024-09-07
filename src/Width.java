public class Width {
    private final int windowWidth;
    private final int fontSize;
    private final static double DIVIDES = 2.0;

    /*
    Find the message width for different message.
        */
    public Width(int windowWidth,int fontSize) {
        this.windowWidth = windowWidth;
        this.fontSize = fontSize;
    }
    public int messageWidth(){
        return (int)((windowWidth - fontSize) / DIVIDES);
    }

}