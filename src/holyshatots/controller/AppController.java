package holyshatots.controller;

import holyshatots.model.DelaunayImage;
import holyshatots.view.ImageFrame;
import holyshatots.view.ImagePanel;
import holyshatots.view.VideoPanel;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AppController 
{
	private final String windowTitle = "Delaunay Image Generator";
	private int numberOfPoints;
	private final int defaultNumberOfPoints = 2000;

	private ImageFrame imageFrame;
	
	public AppController()
	{
		this.numberOfPoints = defaultNumberOfPoints;
		imageFrame = new ImageFrame(this);
	}
	
	public void run() throws IOException
	{ 
		/*
		 * Cool ideas of what to do with this:
		 * - Wallpaper generation
		 * - Process video
		 * - Game incorporating this rendering into it's graphics ( could be a good background )
		 */
		

	}
	
	public int getDefaultNumberOfPoints()
	{
		return defaultNumberOfPoints;
	}
	
	public void setNumberOfPoints(int numberOfPoints)
	{
		this.numberOfPoints = numberOfPoints;
	}
	
	public int getNumberOfPoints()
	{
		return this.numberOfPoints;
	}
	
	public void setVideoPanelSize(int width, int height)
	{

	}
	
//	public void updateVideo(MarvinImage image)
//	{
//		videoPanel.setImage(image);
//	}
	

	
//	public void changeDisplayImage(BufferedImage image)
//	{
//		imagePanel.setNewImage(delaunayImage.renderDelaunay(image, numberOfPoints));
//	}
	
	private void setupListeners()
	{
		
	}
}
