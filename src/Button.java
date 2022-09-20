import java.awt.*;

public class Button {

    public int xpos;
    public int ypos;
    public int width;
    public int height;
    public boolean on;
    public Rectangle rec;

    public Button(int x, int y, int w, int h) {

        xpos = x;
        ypos = y;
        width = w;
        height = h;
        on = false;

        rec = new Rectangle(xpos, ypos, width, height);

    }


}
