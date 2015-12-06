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

    private int rotate;
    private double angle;

    private int x;
    private int y;

    private int dx;
    private int dy;
    private int speed;

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

    public void setRelativeDx(double relativeDx) { this.relativeDx = relativeDx; }
    public void setRelativeDy(double relativeDy) { this.relativeDy = relativeDy; }

    public int getImageWidth() { return imageWidth; }
    public int getImageHeight() { return imageHeight; }

    public int getRestRadius() { return restRadius; }

    public void setAngle(double angle) { this.angle = angle; }

}