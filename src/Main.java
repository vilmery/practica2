import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main extends JFrame {

    private static Image dns;
    private static Image dns2;
    private static int dnsY = 0;
    private static int dns2Y = 100;
    private static int dnsX = 0;
    private static double angle = 0;
    private static int radius = 100; 
    private static int centerX;
    private static int centerY;
    private static int dns1Speed = 15;
    private static int dns1Direction = 1; 
    private static Timer timerDns1;
    private static Timer timerDns2;

    public static void main(String[] args) throws IOException {
        BufferedImage originalDns = ImageIO.read(Main.class.getResourceAsStream("dns.png"));
        BufferedImage originalDns2 = ImageIO.read(Main.class.getResourceAsStream("dns2.png"));

        dns = originalDns.getScaledInstance(originalDns.getWidth() / 2, originalDns.getHeight() / 2, Image.SCALE_SMOOTH);
        dns2 = originalDns2.getScaledInstance(originalDns2.getWidth() / 2, originalDns2.getHeight() / 2, Image.SCALE_SMOOTH);

        Main main = new Main();
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        main.setResizable(false);
        main.setSize(1920, 1080);
        main.setLocationRelativeTo(null);

        centerX = main.getWidth() / 2;
        centerY = main.getHeight() / 2;

        GameField gameField = new GameField();

        timerDns1 = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveDns(dns1Speed, dnsX, main.getWidth(), dns.getWidth(null));
                gameField.repaint();
            }
        });
        timerDns1.start();

        timerDns2 = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveDns2();
                gameField.repaint();
            }
        });
        timerDns2.start();

        main.add(gameField);
        main.setVisible(true);
    }

    private static class GameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawDns(g, dns, dnsX, dnsY);
            drawDns(g, dns2, calculateDns2X(), calculateDns2Y());
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1920, 1080);
        }

        private void drawDns(Graphics g, Image image, int x, int y) {
            g.drawImage(image, x, y, null);
        }
    }

    private static void moveDns(int speed, int position, int windowSize, int imageSize) {
        position += dns1Direction * speed;
        if (position + imageSize >= windowSize || position <= 0) {
            dns1Direction *= -1;
        }

        dnsX = position;
    }

    private static void moveDns2() {
        angle += Math.toRadians(2);
        if (angle > 2 * Math.PI) {
            angle = 0;
        }
    }

    private static int calculateDns2X() {
        return (int) (centerX + radius * Math.cos(angle) - dns2.getWidth(null) / 2);
    }

    private static int calculateDns2Y() {
        return (int) (centerY + radius * Math.sin(angle) - dns2.getHeight(null) / 2) + dns2Y;
    }
}
