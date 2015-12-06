import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Player {

    private int imageWidth = 96;
    private int imageHeight = 78;

    private double relativeDx;
    private double relativeDy;

    private double angle;

    private int x;
    private int y;

    private int dx;
    private int dy;
    private int speed;

    private boolean firing;
    private long firingTimer;
    private long firingDelay;

    private int restRadius;

    private Image image;

//    Constructor
    public Player() {

        x = GamePanel.WIDTH / 2 - imageWidth / 2;
        y = GamePanel.HEIGHT / 2 - imageHeight / 2;

        angle = 0;

        dx = 0;
        dy = 0;
        speed = 7;

        firing = false;
        firingTimer = System.nanoTime();
        firingDelay = 200;

        restRadius = 10;

//    Load image
        BufferedImage sourceImage;
        try {
            URL url = this.getClass().getClassLoader().getResource("player.png");
            sourceImage = ImageIO.read(url);
            image = Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
        } catch (IOException ex) {}

    }

    public void update() {

        dx = -(int)((double)speed * relativeDx);
        dy = -(int)((double)speed * relativeDy);

        x += dx;
        y += dy;

        if(firing) {
            long elapsed = (System.nanoTime() - firingTimer) / 1000000;
            if(elapsed > firingDelay) {
                GamePanel.bullets.add(new Bullet(Math.toDegrees(angle) - 90, x + imageWidth / 2, y + imageHeight / 2));
                firingTimer = System.nanoTime();
            }
        }

    }

    public void draw(Graphics2D g) {
        AffineTransform transform = g.getTransform();
        g.rotate(angle, x + imageWidth / 2, y + imageHeight / 2);
        g.drawImage(image, x, y, null);
        g.setTransform(transform);
    }

//    Functions
    public int getX() { return x; }
    public int getY() { return y; }
    public int getSpeed() { return speed; }
    public int getImageWidth() { return imageWidth; }
    public int getImageHeight() { return imageHeight; }
    public int getRestRadius() { return restRadius; }

    public void setRelativeDx(double relativeDx) { this.relativeDx = relativeDx; }
    public void setRelativeDy(double relativeDy) { this.relativeDy = relativeDy; }
    public void setAngle(double angle) { this.angle = angle; }
    public void setFiring(boolean b) { firing = b; }


}