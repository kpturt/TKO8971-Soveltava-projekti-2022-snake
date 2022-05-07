import java.awt.*;

public class BodyPart {

    private int xCrd, yCrd, width, height;

    //constructor
    public BodyPart( int xCrd, int yCrd, int tileSize) {
        this.xCrd = xCrd;
        this.yCrd = yCrd;
        width = tileSize;
        height = tileSize;
    }

    //draws the snake bodypart
    public void draw(Graphics g) {
        g.setColor(new Color(0,128,0));
        g.fillRect(xCrd*width, yCrd*height, width, height);
        g.setColor(Color.GREEN);
        g.fillRect(xCrd*width+2, yCrd*height+2, width - 4, height - 4);
    }

    public int getxCrd() {
        return xCrd;
    }
    public void setxCrd(int xCrd) {
        this.xCrd = xCrd;
    }
    public int getyCrd() {
        return yCrd;
    }
    public void setyCrd(int yCrd) {
        this.yCrd = yCrd;
    }
}
