package holyshatots.controller;

import holyshatots.model.ConvertVideo;
import holyshatots.model.DelaunayImage;
import holyshatots.view.ImagePanel;
import holyshatots.view.VideoPanel;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;

public class AppController 
{
	private final String windowTitle = "Delaunay Image Generator";
	
	private BufferedImage image;
	private String imagePath;
	private JFrame window;
	private DelaunayImage delaunayImage;
	private int numberOfPoints;
	private ImagePanel imagePanel;
	private VideoPanel videoPanel;
	
	public AppController()
	{
		this.numberOfPoints = 2000;
	}
	
	public void run() throws IOException
	{
		/*
		 * Cool ideas of what to do with this:
		 * - Wallpaper generation
		 * - Process video
		 * - Game incorporating this rendering into it's graphics ( could be a good background )
		 */
//		System.out.println("In run() of appController");
	   JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Images", "jpg", "gif", "png", "wmv", "mpg" ,".mpeg", ".mp1", ".mp2", ".mp3", ".m1v", ".m1a", ".m2a", ".mpa", ".mpv");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
//	       System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
	    	imagePath = chooser.getSelectedFile().getPath(); 
	    }
	    
		window = new JFrame();
	    
	    for(int i=0; i < filter.getExtensions().length; i++)
	    {
	    	if(imagePath.contains(".jpg") ||
	    	   imagePath.contains(".gif") ||
	    	   imagePath.contains(".png"))
	    	{
	    		// This is an image, so let's display it
	    		image = ImageIO.read(new File(imagePath));
	    		delaunayImage = new DelaunayImage(this, image, numberOfPoints);
	    		image = delaunayImage.renderDelaunay(image, numberOfPoints);
	    
	    		imagePanel = new ImagePanel(this, image);
	    		
	    		window.setSize(image.getWidth(), image.getHeight());
	    		window.setTitle(windowTitle);
	    		window.setLayout(new BorderLayout());
	    		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    		window.add(imagePanel);
	    		window.setVisible(true);
	    	}
	    	else
	    	{
	    		videoPanel = new VideoPanel();
	    		// This is a video, so let's convert it
	    		ConvertVideo convertVideo = new ConvertVideo(this, imagePath, numberOfPoints);	    		
	    	}
	    }

	}
	
	public void setVideoPanelSize(int width, int height)
	{
		videoPanel.setSize(width, height);
	}
	
	public void updateVideo(MarvinImage image)
	{
		videoPanel.setImage(image);
	}
	
	public void changeDisplayImage(BufferedImage image)
	{
		imagePanel.setNewImage(delaunayImage.renderDelaunay(image, numberOfPoints));
	}
}
