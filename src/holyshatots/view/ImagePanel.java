package holyshatots.view;

import holyshatots.controller.AppController;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import delaunay.Pnt;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel
{
    public static int pointRadius = 3;
    
    private AppController controller;              // Controller for DT
    private Graphics g;                         // Stored graphics context
    private BufferedImage image;				// The image given to display
    
	private ImageIcon activityImage;
	private JLabel activityLabel;
	
	private boolean useGradient;
    
    public ImagePanel(AppController controller, BufferedImage image)
    {
    	this.controller = controller;
    	this.image = image;
    	setupActivityIndicator();
    }
    
    public ImagePanel(AppController controller)
    {
    	this.controller = controller;
    	setupActivityIndicator();
    }
    
	private void setupActivityIndicator()
	{
//		activityImage = new ImageIcon("C:/Users/mmca8446/Downloads/activityIndicator.gif");
		activityLabel = new JLabel("Working...");
		activityLabel.setVisible(false);
		add(activityLabel);
	}
	
	public void displayActivityIndicator(boolean toDisplay)
	{
		if(toDisplay == true)
		{
			// Show the activity indicator
			activityLabel.setVisible(true);
		}
		else
		{
			// Hide it
			activityLabel.setVisible(false);
		}
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
		Graphics2D g2d = (Graphics2D) g.create();
        if(image != null)
        {
        	// We have an image so display it
	        this.g = g;
			g2d.drawImage(image, 0, 0, this);
			g2d.dispose();
        }
        else if(useGradient = true)
        {
        	// Create a gradient
        	newImage(g2d, Color.CYAN, Color.GRAY, controller.getGradientWidth(), controller.getGradientHeight());
        }
        else
        {
        	// No image
        }
    }
    
	public void newImage(Graphics2D g2d, Color color1, Color color2, int width, int height)
	{
		GradientPaint gradient = new GradientPaint(0, 0, color1, width, height, color2);
		
	}

}
