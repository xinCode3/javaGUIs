import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Corey
 */
public class Fruit extends JLabel {
    
    private static final String FILE_EXTENSION = ".png";
    public String name;
    public String resFile;
    
    private BufferedImage img;
    private Image dimg;
    
    private final static int bananaScale = 50;
    private final ImageIcon fruit;
    public Fruit(String name) {
        this.name = name;
        this.resFile = name + FILE_EXTENSION;
        setVisible(true);
        try {
            img = ImageIO.read(getClass().getClassLoader().getResource(resFile));
        } catch (IOException ex) { }
        scaleImage(name);
//        dimg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        fruit = new ImageIcon(dimg);
        setIcon(fruit);
    }
    
    private final void scaleImage(String str) {
        
        switch(str) {
            case "orange":
                dimg = img.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
                break;
            case "banana":
                dimg = img.getScaledInstance(bananaScale, bananaScale, Image.SCALE_SMOOTH);
                break;
            default:
                dimg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                break;
        }
        
        
    }
    
    @Override
    public String toString() {
        return name;
    }
}
