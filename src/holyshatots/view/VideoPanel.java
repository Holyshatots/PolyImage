package holyshatots.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;

public class VideoPanel extends JPanel
{
	private MarvinImagePanel panel;
	
	public VideoPanel()
	{
		panel = new MarvinImagePanel();
        setLayout(new BorderLayout());
        setVisible(true);
	}
	
	public void setImage(MarvinImage image)
	{
		panel.setImage(image);
	}
}
