import javax.swing.*;
import java.net.URL;

public class Image {
    public static URL foodUrl = Image.class.getResource("apple.png");
    public static ImageIcon food = new ImageIcon(foodUrl);
    public static URL bodyUrl = Image.class.getResource("dot.png");
    public static ImageIcon body = new ImageIcon(bodyUrl);
    public static URL headUrl = Image.class.getResource("head.png");
    public static ImageIcon head = new ImageIcon(headUrl);
}
