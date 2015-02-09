package holyshatots.view;

import holyshatots.controller.AppController;
import holyshatots.model.DelaunayImage;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageFrame extends JFrame
{
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem openFileMenu;
	private JMenuItem saveFileMenu;
	private AppController controller;
	private BufferedImage image;
	private String imagePath;
	private ImagePanel imagePanel;

	public ImageFrame(AppController controller)
	{
		this.controller = controller;
		imagePanel = new ImagePanel(controller);
		
		setSize(200, 200);
		setLocationRelativeTo(null);
//		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Poly Image Generator");
		setResizable(false);
		
		menuBar = new JMenuBar();
		file = new JMenu("File") ;
		
		openFileMenu = new JMenuItem("Open");
		openFileMenu.setToolTipText("Open a new file");
		
		saveFileMenu = new JMenuItem("Save");
		
		setupListeners();
		
		menuBar.add(file);
		file.add(openFileMenu);
		file.add(saveFileMenu);
		
		setJMenuBar(menuBar);
		add(imagePanel);
		
		setVisible(true);
	}

	private void setupListeners()
	{
		openFileMenu.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent event)
			{
				openImage();
			}

		});
		
		saveFileMenu.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent event)
			{
				saveImage();
			}
			
		});
	}
	
	public void openImage()
	{
		BufferedImage tempImage = getNewImageFromUser();
		setSize(tempImage.getWidth(), tempImage.getHeight());
		imagePanel.setNewImage(tempImage);
	}
	
	public void saveImage()
	{
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showSaveDialog(this);
		File file = fileChooser.getSelectedFile();
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				ImageIO.write(imagePanel.getCurrentImage(), "png", file);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public int getNumberOfPointsFromUser()
	{
		String inputValue = "";
		inputValue = JOptionPane.showInputDialog("How many points? (default = 2000)");
		if(inputValue.equals(""))
		{
			// If no input use the default number of points
			return controller.getDefaultNumberOfPoints();
		}
		return Integer.parseInt(inputValue);
	}

	
	public BufferedImage getNewImageFromUser()
	{
		DelaunayImage delaunayImage;
	   JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Images", "jpg", "gif", "png", "wmv", "mpg" ,".mpeg", ".mp1", ".mp2", ".mp3", ".m1v", ".m1a", ".m2a", ".mpa", ".mpv");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	imagePath = chooser.getSelectedFile().getPath(); 
	    }

		// This is an image, so let's display it
		try
		{
			image = ImageIO.read(new File(imagePath));
		}
		catch (IOException e)
		{
			
		}
		
		controller.setNumberOfPoints(getNumberOfPointsFromUser());
		
		delaunayImage = new DelaunayImage(controller, image, controller.getNumberOfPoints());
		image = delaunayImage.renderDelaunay(image, controller.getNumberOfPoints());
	    
	    return image;
	}
}
