package holyshatots.view;

import holyshatots.controller.AppController;
import holyshatots.model.DelaunayImage;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageFrame extends JFrame
{
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem newFileMenu;
	private JMenuItem openFileMenu;
	private JMenuItem saveFileMenu;
	private JMenuItem exitFileMenu;
	
	private JMenu edit;
	private JMenuItem pointsChangeMenu;
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
		file = new JMenu("File");
		edit = new JMenu("Edit");
		
		newFileMenu = new JMenuItem("New");
		openFileMenu = new JMenuItem("Open");
		saveFileMenu = new JMenuItem("Save");
		exitFileMenu = new JMenuItem("Exit");
		openFileMenu.setToolTipText("Open a new file");
		
		pointsChangeMenu = new JMenuItem("Edit number of points");
		
		setupListeners();
		
		menuBar.add(file);
		file.add(newFileMenu);
		file.add(openFileMenu);
		file.add(saveFileMenu);
		file.add(exitFileMenu);
		
		setJMenuBar(menuBar);
		add(imagePanel);
		
		setVisible(true);
	}
	
	private void setupListeners()
	{
		newFileMenu.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent event)
			{
				
			}
			
		});
		
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
		
		exitFileMenu.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
			
		});
	}
	

	
	public void openImage()
	{
		BufferedImage tempImage = getNewImageFromUser();
		imagePanel.displayActivityIndicator(true);
		DelaunayImage delaunayImage = new DelaunayImage(controller, tempImage, controller.getNumberOfPoints());
		tempImage = delaunayImage.getImage();
		setSize(tempImage.getWidth(), tempImage.getHeight());
		imagePanel.displayActivityIndicator(false);
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
			// Could not read file
		}
	
		controller.setNumberOfPoints(getNumberOfPointsFromUser());

	    return image;
	}
}
