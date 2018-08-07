package IoT;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Actor {

    BufferedImage image;
    private Integer x;
    private Integer y;
    private Integer dx;
    private Integer dy;

    public Actor(int x, int y, int maxX, int maxY){
        try {
            image = ImageIO.read(new File("src/SwingContent/person.png"));
        } catch (IOException e){
            image = null;
        }
        this.x = x;
        this.y = y;
        dx = 0;
        dy = 0;

    }

    public void move(){
        x += dx;
        y += dy;
    }

    public void reset(){
        x -= dx;
        y -= dy;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Integer getTopLeftX() {
        return x;
    }

    public Integer getTopLeftY() {
        return y;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 2;
        }

    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
        else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}
