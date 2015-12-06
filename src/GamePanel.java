import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel implements
    Runnable {

    public static int WIDTH = 1200;
    public static int HEIGHT = 800;

    private int mouseX;
    private int mouseY;
    private int playerX;
    private int playerY;
    private int playerDx;
    private int playerDy;
    private int sumDxDy;
    private double relativeDx;
    private double relativeDy;
    private double angle;

    private Graphics2D g;
    private BufferedImage image;

    private Thread thread;
    private boolean running;

    private Controller controller;

    private int FPS = 30;
    private double averageFPS;

    public static Player player;
    public static ArrayList<Bullet> bullets;


//    Constructor
    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void run() {

        running = true;

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

//        FPS
        long startTime;
        long URDTimeMillis;
        long waitTime;

//        long totalTime = 0;
        player = new Player();
        bullets = new ArrayList<Bullet>();

        int frameCount = 0;
        int maxFrameCount = 30;

        long targetTime = 1000 / FPS;

        while(running) {

            startTime = System.nanoTime();

            gameUpdate();
            gameRender();
            gameDraw();

            URDTimeMillis = (System.nanoTime() - startTime) / 10000000;

            waitTime = targetTime - URDTimeMillis;

            try {
                Thread.sleep(waitTime);
            } catch (Exception e) {}

//            totalTime += System.nanoTime() - startTime;
//            frameCount++;
//            if(frameCount == maxFrameCount) {
//                averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
//                frameCount = 0;
//                totalTime = 0;
//            }
        }
    }

    @Override
    public void addNotify() {

        super.addNotify();
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }

        controller = new Controller();
        addMouseListener(controller);
        addMouseMotionListener(controller);

    }

    private void gameUpdate() {

        mouseX = controller.getX();
        mouseY = controller.getY();

//  player movement
        playerX = player.getX() + player.getImageWidth() / 2;
        playerY = player.getY() + player.getImageHeight() / 2;

        playerDx = playerX - mouseX;
        playerDy = playerY - mouseY;

        sumDxDy = Math.abs(playerDx) + Math.abs(playerDy);

        relativeDx = (double) playerDx / (double) sumDxDy;
        relativeDy = (double) playerDy / (double) sumDxDy;

        player.setRelativeDx(relativeDx);
        player.setRelativeDy(relativeDy);

        if(Math.abs(mouseX - playerX) <= player.getRestRadius() &&
                Math.abs(mouseY - playerY) <= player.getRestRadius()) {
            player.setRelativeDx(0);
            player.setRelativeDy(0);
        }

//  player rotate
        angle = Math.atan2(playerY - mouseY, playerX - mouseX) - Math.PI / 2;
        player.setAngle(angle);

        player.update();
//  check bullets position
        player.setFiring(controller.getFiring());
        for(int i = 0; i < bullets.size(); i++) {
            boolean remove = bullets.get(i).update();
            if (remove) {
                bullets.remove(i);
                i--;
            }
        }

        for(int i = 0; i < bullets.size(); i++) {

            Bullet b = bullets.get(i);
            double bx = b.getX();
            double by = b.getY();
            double br = b.getR();

        }

    }

    private void gameRender() {
//          Background
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        player.draw(g);
//          draw bullets
        for(int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g);
        }


    }

    private void gameDraw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

}
