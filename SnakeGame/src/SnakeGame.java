
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class SnakeGame extends JFrame implements KeyListener {
    
    public final int rowcol = 25;
    private final int startIndex = (12*25)+12;
    private int position = startIndex;
    private final Timer timer = new Timer();
    
    public Cell[] cells = new Cell[rowcol*rowcol];
    private Snake snake;
    MyThread snakeThread;
    
    private JLayeredPane content = new JLayeredPane();
    
    private final Panel grid = new Panel();
    private final Panel function = new Panel(new FlowLayout());
    
    private final BorderLayout borderLayout = new BorderLayout();
    private final GridLayout gridLayout = new GridLayout(rowcol, rowcol);
   
    
    private static final int UP = VK_UP;
    private static final int DOWN = VK_DOWN;
    private static final int LEFT = VK_LEFT;
    private static final int RIGHT = VK_RIGHT;
    
    public final static Color green = new Color(183, 235, 189);
    public final static Color white = new Color(254,251,234);
    public final static Color skinColor = new Color(255,182,115);
    public int direction;
    public int fruitIndex;
    
    {
        populateCells();
        spawnSnake();
        spawnFruit();
    }
    
    public static Random rand = new Random();
    
    public SnakeGame() {
        super("Snake Game");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(borderLayout);
        setResizable(false);
        setPreferredSize(new Dimension(1000, 1000));
        grid.setLayout(gridLayout);
        add(grid, borderLayout.CENTER);
        addKeyListener(this);
        pack();
        setLocationRelativeTo(null);
    }
    
    public void spawnFruit() {
        Outter: while(true) {
            fruitIndex = rand.nextInt(rowcol*rowcol);
            for(LinkedList.Node node : snake.body) {
                if(node.cell.index == fruitIndex) continue Outter;
            }
            break;
        }
        cells[fruitIndex].selectFruit();
    }
    public void clearFruit() {
        if(fruitIndex != -1) {
            cells[fruitIndex].removeFruit();
        }
        fruitIndex = -1;
    }
    public void spawnSnake() {
        snake = new Snake(cells[startIndex], this);
        snakeThread = new MyThread(snake);
    }
    
    //helper method to keep the construct from getting too messy
    private void populateCells() {
        Cell cell;
        int index;
        for (int i = 0; i < rowcol; i++) {
            for(int j = 0; j < rowcol; j++) {
                index = (i*rowcol) + j;
                
                if(i%2==0) {
                    if(j%2==0) cell = new Cell(green, index);
                    else cell = new Cell(white, index);
                } else {
                    if(j%2==0) cell = new Cell(white, index);
                    else cell = new Cell(green, index);
                }
                
                cells[index] = cell;
                grid.add(cell);
                cell.addKeyListener(this);
            }
        }
        
    }
    //check if a cell is alive
    public boolean isEmpty(int index) {
        return !cells[index].getBackground().equals(snake.skin) ||
                cells[index].getBackground().equals(cells[index].defaultColor);
    }
    
    public void reset() {
        position = startIndex;
        clearFruit();
        spawnSnake();
        spawnFruit();
        pack();
    }
    
    @Override
    public void keyTyped(KeyEvent key) {}

    @Override
    public void keyPressed(KeyEvent key) {
        int keyCode = key.getKeyCode();
        if(snake.body.size > 1) {
            if((direction == UP && keyCode == DOWN) ||
               (direction == DOWN && keyCode == UP) ||  
               (direction == LEFT && keyCode == RIGHT) ||
               (direction == RIGHT && keyCode == LEFT)) return;
        }
        direction = keyCode;
        if(snakeThread.isAlive()) return;
        snakeThread.start();
    }

    @Override
    public void keyReleased(KeyEvent key) {}
    
    public static void main(String... args) {
        SnakeGame wn = new SnakeGame();
    }
}
