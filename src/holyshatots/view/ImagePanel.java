package holyshatots.view;

import holyshatots.controller.AppController;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import delaunay.Pnt;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel
{
    public static int pointRadius = 3;
    
    private AppController controller;              // Controller for DT
    private Graphics g;                         // Stored graphics context
    private BufferedImage image;				// The image given to display
    
    public ImagePanel(AppController controller, BufferedImage image)
    {
    	this.controller = controller;
    	this.image = image;
    }
    
    public ImagePanel(AppController controller)
    {
    	this.controller = controller;
    }
    
    public void setNewImage(BufferedImage image)
    {
    	this.image = image;
    	setSize(image.getWidth(), image.getHeight());
    	repaint();
    }
    
    public BufferedImage getCurrentImage()
    {
    	return this.image;
    }
    
    /* Higher Level Drawing Methods */

    /**
     * Handles painting entire contents of DelaunayPanel.
     * Called automatically; requested via call to repaint().
     * @param g the Graphics context
     */
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        if(image != null)
        {
	        this.g = g;
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.drawImage(image, 0, 0, this);
			g2d.dispose();
        }
    }
    
    

}
