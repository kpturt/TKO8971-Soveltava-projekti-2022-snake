import java.awt.*;

public class Apple {

    private int xCrd, yCrd, width, height;

    //constructor
    public Apple(int xCrd, int yCrd, int tileSize) {
        this.xCrd = xCrd;
        this.yCrd = yCrd;
        width = tileSize;
        height = tileSize;
    }
    public void tick() {
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(xCrd*width, yCrd*height, width, height);
        g.setColor(Color.green);
        g.fillRect(xCrd*width, yCrd*height-4, width-6, height-6);
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
