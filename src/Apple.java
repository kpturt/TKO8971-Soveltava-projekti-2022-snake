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

    //draws the apple
    public void draw(Graphics g) {
        //apple
        g.setColor(Color.red);
        g.fillRect(xCrd*width, yCrd*height, width, height);
        //reflection
        g.setColor(Color.white);
        g.fillRect(xCrd*width+6, yCrd*height+2, 2, 2);
        //leaf
        g.setColor(Color.green);
        g.fillRect(xCrd*width-1, yCrd*height-4, width-6, height-6);
        //stick
        g.setColor(new Color(111, 78, 55));
        g.fillRect(xCrd*width+5, yCrd*height-4, 2, 4);
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
