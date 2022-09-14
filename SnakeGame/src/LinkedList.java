
import java.awt.Color;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Corey
 * @param <Data>
 */
public class LinkedList<Data extends Cell> implements Iterable<LinkedList.Node> {
    
    Node head;
    Node tail;
    int size;
    static Color color;
    
    public LinkedList(Data data, Color color) {
        LinkedList.color = color;
        this.head = new Node(data);
        this.tail = head;
        size++;
    }
    public LinkedList(Data data) {
        head = new Node(data);
        tail = head;
    }

    @Override
    public Iterator<LinkedList.Node> iterator() {
        return new NodeItr();
    }
    
    public class NodeItr implements Iterator<LinkedList.Node> {
        Node temp = head;
        @Override
        public boolean hasNext() {
            return temp != null;
        }

        @Override
        public Node next() {
            Node curr = temp;
            temp = temp.next;
            return curr;
        }
        
    } 
    public class Node {
        
        Data cell;
        int direction;
        
        Node next;
        Node prev;
        
        public Node(Data cell) {
            this.cell = cell;
            cell.setBackground(color);
        }
    }
    
    public void add(Data cell) {
        Node newNode = new Node(cell);
        this.tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        size++;
    }
    void delete() {
        LinkedList.Node temp = head;
        
        while(temp != null) {
            temp.cell.setBackground(temp.cell.defaultColor);
            temp = temp.next;
            size--;
        }
        head = null;
        tail = null;
    }
    void forward() {
        for(Node temp = head; temp != null; temp = temp.next) {
            System.out.print(temp.cell + "->");
        }
        System.out.println("null");
    }
    
    void backward() {
        for(Node temp = tail; temp != null; temp = temp.prev) {
            System.out.print(temp.cell + "->");
        }
        System.out.println("null");
    }   
    
}