import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Controller implements MouseMotionListener, MouseListener {

    public Controller() {
        x = 600;
        y = 400;
    }

    private static int x;
    private static int y;
    private static boolean firing;

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean getFiring() {
        return firing;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        firing = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        firing = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        firing = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        x = e.getX();
        y = e.getY();

    }
}
