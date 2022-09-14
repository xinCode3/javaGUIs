
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Random;
import javax.swing.JPanel;

public class Cell extends JPanel {
    
    public final int index;
    public final Color defaultColor;
    public static String[] fruits = { "apple", "banana", "blueberry", "cherry", 
                                      "coconut", "grape", "kiwi", "lemon",
                                      "mango",  "orange", "peach", "pineapple",
                                      "strawberry", "watermelon", };
    
    public Fruit fruit;
    private final static BorderLayout layout = new BorderLayout();
    private final static Random rand = new Random();
    public Cell(Color defaultColor, int index) {
        super();
        setOpaque(true);
        setBackground(defaultColor);
        setLayout(layout);
        this.defaultColor = defaultColor;
        this.index = index;
    }
    public void removeFruit() {
        if(fruit != null) {
            fruit.setVisible(false);
            remove(fruit); 
            fruit = null; 
        }
    }
    public void selectFruit() {
        int selection = rand.nextInt(14);
        fruit = new Fruit(fruits[selection]);
        add(fruit, BorderLayout.CENTER);
    }
    public boolean hasFruit() {
        return fruit != null;
    }
    
    @Override
    public String toString() {
        String str = "Cell @index " + String.valueOf(index); 
        if(hasFruit()) {
            str = str + " has a " + fruit.toString() + " in it";
        } else {
            str += " contains no fruit";
        }
        return str;
    }
}