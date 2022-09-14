
import java.awt.Color;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;

public class Snake implements Runnable {

    public LinkedList<Cell> body;
    public final Color skin = Color.cyan;

    private final SnakeGame wn;

    private static final int CONTINUE = 0;
    private static final int IGNORE = 1;
    private static final int CRASH = 2;
    
    public Snake(Cell head, SnakeGame wn) {
        body = new LinkedList(head, skin);
        this.wn = wn;
    }

    private void addCell() {
        int newCellIndex = body.tail.cell.index;
       
        Cell cell;
        switch(body.tail.direction) {
            case VK_UP:
                newCellIndex+=wn.rowcol;
                break;
            case VK_DOWN:
                newCellIndex-=wn.rowcol;
                break;
            case VK_LEFT:
                newCellIndex++;
                break;
            case VK_RIGHT:
                newCellIndex--;
                break;
        }
        cell = wn.cells[newCellIndex];
        body.add(cell);
    }

    private void eat() {
        addCell();
        wn.clearFruit();
        wn.spawnFruit();
        wn.pack();
    }    
    
    private void follow(LinkedList.Node node) {
        if(node == null) return;
        follow(node.next);
        if(body.tail == node) node.cell.setBackground(node.cell.defaultColor);
        node.direction = node.prev.direction;
        move(node, node.direction);
    }
    
    private int move(LinkedList.Node node, int direction) {
        
        switch(direction) {
            case VK_UP:
                int topIndex = node.cell.index - wn.rowcol;
                if(node == body.head) {
                    if (topIndex < 0 || !wn.isEmpty(topIndex)) return CRASH;
                    
                    if(wn.cells[topIndex].hasFruit()) eat();
                    if(body.head != body.tail) {
                        node.cell = wn.cells[topIndex];
                        node.cell.setBackground(skin);
                        return CONTINUE;
                    }
                    node.cell.setBackground(node.cell.defaultColor);
                    node.cell = wn.cells[topIndex];
                    node.cell.setBackground(skin);
                    
                    return IGNORE;
                }
                node.cell = wn.cells[topIndex];
                break;

            case VK_DOWN:
                int botIndex = node.cell.index + wn.rowcol;
                if (node == body.head) {
                    if(botIndex >= wn.rowcol * wn.rowcol
                            || !wn.isEmpty(botIndex)) return CRASH;

                    if(wn.cells[botIndex].hasFruit()) eat();
                    if(body.head != body.tail) {
                        node.cell = wn.cells[botIndex];
                        node.cell.setBackground(skin);
                        return CONTINUE;
                    }
                    node.cell.setBackground(node.cell.defaultColor);
                    node.cell = wn.cells[botIndex];
                    node.cell.setBackground(skin);
                    return IGNORE;
                }
                node.cell = wn.cells[botIndex];
                break;

            case VK_LEFT:
                int leftIndex = node.cell.index - 1;
                if(node == body.head) {
                    if(node.cell.index % wn.rowcol == 0
                            || !wn.isEmpty(leftIndex)) return CRASH;

                    if(wn.cells[leftIndex].hasFruit()) eat();
                    if(body.head != body.tail) {
                        node.cell = wn.cells[leftIndex];
                        node.cell.setBackground(skin);
                        return CONTINUE;
                    }
                    node.cell.setBackground(node.cell.defaultColor);
                    node.cell = wn.cells[leftIndex];
                    node.cell.setBackground(skin);
                    return IGNORE;
                }
                node.cell = wn.cells[leftIndex];
                break;

            case VK_RIGHT:
                int rightIndex = node.cell.index + 1;

                if(node == body.head) {
                    if(node.cell.index % wn.rowcol == (wn.rowcol - 1)
                            || !wn.isEmpty(rightIndex)) return CRASH;

                    if(wn.cells[rightIndex].hasFruit()) eat();
                    if(body.head != body.tail) {
                        node.cell = wn.cells[rightIndex];
                        node.cell.setBackground(skin);
                        return CONTINUE;
                        
                    }
                    node.cell.setBackground(node.cell.defaultColor);
                    node.cell = wn.cells[rightIndex];
                    node.cell.setBackground(skin);
                    return IGNORE;
                }
                node.cell = wn.cells[rightIndex];
                break;

            default:
                return IGNORE;
        }
        return CONTINUE;
    }
    
    public void move(int direction) {
        
        int result = move(body.head, direction);
        if(result == CRASH) {
            if(Thread.currentThread() instanceof MyThread) {
                MyThread t = (MyThread) Thread.currentThread();
                t.interrupt();
        }
            }
        if(result == IGNORE) return;
        follow(body.head.next);
        body.head.direction = direction;
    }

    @Override
    public void run() {
        int maxSpeed = 100;
        int speed = 200;
        while(true) {
            move(wn.direction);
            if(speed > maxSpeed) { 
                if(body.size % 3 == 0 && body.size % 4 == 0) speed-=7; 
                else if(body.size % 3 == 0) speed-=5;
            }
            try {
                Thread.sleep(speed);
            } catch (InterruptedException ex) {
                body.delete();
                wn.reset();
                break;
            }
        } 
    }
}
    

