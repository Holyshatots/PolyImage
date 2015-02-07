package holyshatots.model;

import holyshatots.controller.AppController;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;
import marvin.video.MarvinJavaCVAdapter;
import marvin.video.MarvinVideoInterface;
import marvin.video.MarvinVideoInterfaceException;

public class ConvertVideo implements Runnable
{
    
    private int numberOfPoints;
    private AppController controller;

    public ConvertVideo(AppController controller, String path, int numberOfPoints){
    	this.numberOfPoints = numberOfPoints;
    	this.controller = controller;

        try{
            // Create the VideoAdapter used to load the video file
            videoAdapter = new MarvinJavaCVAdapter();
            videoAdapter.loadResource(path);
        	this.controller.setVideoPanelSize(videoAdapter.getImageWidth(), videoAdapter.getImageHeight());
            // Start the thread for requesting the video frames 
            new Thread(this).start();
        }
        catch(MarvinVideoInterfaceException e){e.printStackTrace();}
    }

    public void run() {
        try{
            while(true){
                // Request a video frame
                videoFrame = videoAdapter.getFrame();
                DelaunayImage image = new DelaunayImage(controller, videoFrame.getBufferedImage(), numberOfPoints);
                videoFrame.setBufferedImage(image.getImage());
                
            }
        }catch(MarvinVideoInterfaceException e){e.printStackTrace();}
    }
    

}
