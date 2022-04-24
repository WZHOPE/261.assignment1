import javax.swing.*;

public class Start extends JFrame {
    public Start() {
        setSize(850, 900);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel gamePanel = new GamePanel();
        gamePanel.setSize(850, 800);
        gamePanel.setFocusable(true);
        getContentPane().add(gamePanel);
    }

    public static void main(String[] args) {
        Start frame = new Start();
        frame.setVisible(true);
    }
}
