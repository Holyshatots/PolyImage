package holyshatots.model;

import holyshatots.controller.AppController;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import delaunay.DelaunayAp;
import delaunay.Pnt;
import delaunay.Triangle;
import delaunay.Triangulation;

/**
 * Implements a lot of code from Paul Chew with some modifications
 * @author Holyshatots
 */
@SuppressWarnings("serial")
public class DelaunayImage
{
	private BufferedImage image;
    public static int pointRadius = 3;

    private AppController controller;              // Controller for DT
    private Triangulation dt;                   // Delaunay triangulation
    private Map<Object, Color> colorTable;      // Remembers colors for display
    private Triangle initialTriangle;           // Initial triangle
    private static double initialSize = 10000.0;     // Size of initial triangle
    private Graphics2D g;                         // Stored graphics context
    private Random random = new Random();       // Source of random numbers
    private int numberOfPoints;
  
	
    public DelaunayImage(AppController controller, BufferedImage image, int numberOfPoints)
    {
    	this.image = image;
//    	System.out.println("In DelaunayImage constructor");
    	this.controller = controller;
    	this.initialTriangle = new Triangle(
    			new Pnt(-initialSize, -initialSize),
    			new Pnt( initialSize, -initialSize),
    			new Pnt (          0,  initialSize));
    	this.dt = new Triangulation(initialTriangle);
    	this.colorTable = new HashMap<Object, Color>();
    	this.numberOfPoints = numberOfPoints;
    	this.g = this.image.createGraphics();
    	renderDelaunay(this.image, this.numberOfPoints);
    }
    
	public void setImage(BufferedImage image)
	{
		renderDelaunay(image, numberOfPoints);
	}
	
	public BufferedImage getImage()
	{
		return this.image;
	}
	
	public BufferedImage renderDelaunay(int numberOfPoints)
	{
		if(this.image != null)
		{
			// Get random points that are weighted based on the color differences in
			// the image. 
			Random random = new Random();
			for(int i=0; i < numberOfPoints; i++)
			{
				addSite(new Pnt((double) this.image.getWidth() * random.nextDouble(), (double) this.image.getHeight() * random.nextDouble()));
			}
			// Use the random points to create a delaunay triangulation.
			// Draw filled polygons/ triangles with the color set to the middle
			// of the triangle.
			//drawAllDelaunay(true);
			drawAllVoronoi(true, false);

			// Return the finished image
			return this.image;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Takes in a buffered image and does a delaunay triangulation with
	 * coloring on it
	 * @param image
	 * @return
	 */
	public BufferedImage renderDelaunay(BufferedImage image, int numberOfPoints)
	{
		if(image != null)
		{
			this.image = image;
		}
		// Get random points that are weighted based on the color differences in
		// the image. 
		addRandomPoints(numberOfPoints);

		// Use the random points to create a delaunay triangulation.
		// Draw filled polygons/ triangles with the color set to the middle
		// of the triangle.
		//drawAllDelaunay(true);
		drawAllVoronoi(true, false);

		// Return the finished image
		return this.image;

	}
	
	private void addRandomPoints(int numberOfPoints)

	{
		// idea:
		// Get the average color of the entire picture
		// Create an array of weights by subtracting the pixel value from the average value of the entire picture
		// Generate points based on the weights
		Random random = new Random();
		
		double[][] weightedPixels = getWeightedPixels();

		// Determine maximum roll possible
		double maxRoll = 0.0d;
		double roll;
		
		for(int row = 0; row < weightedPixels.length; row++)
		{
			for(int column = 0; column < weightedPixels[0].length; column++)
			{
				maxRoll += weightedPixels[row][column];
			}
		}
		
		// For every point that needs to be generated, choose a random pixel based on the weight
		for(int i=0; i < numberOfPoints; i++)
		{
//			addSite(new Pnt((double) this.image.getWidth() * random.nextDouble(), (double) this.image.getHeight() * random.nextDouble()));
			
			roll = random.nextDouble() * maxRoll;
			for (int row = 0; row < weightedPixels.length; row++)
			{
				for (int column = 0; column < weightedPixels[0].length; column++)
				{
					roll -= weightedPixels[row][column];
					if (roll < 0)
					{
						addSite(new Pnt((double) row, (double) column));
						break;
					}
				}
				
				if(roll < 0)
				{
					break;
				}
			}
		}	
	}
	

	/**
	 * Return an array of ints that represent weights based on the difference of this color 
	 * and the average color of the image
	 * @return
	 */
	private double[][] getWeightedPixels()
	{
		double[][] weightedPixels = new double[image.getWidth()][image.getHeight()];
	
		int averageRGB = getAverageColorOfImage();
		
		for(int row = 0; row < image.getWidth(); row++)
		{
			for(int column = 0; column < image.getHeight(); column++)
			{
				weightedPixels[row][column] = Math.abs(averageRGB + image.getRGB(row, column)) / 100;
				
			}
		}
		return weightedPixels;
	}
	
	/**
	 * Instead of using the average color of the image like in getWeightedPixels(),
	 * use the average color of the pixels that are surrounding the current pixel
	 * @return
	 */
	private double[][] getWeightedPixelsLocal()
	{
		double[][] weightedPixels = new double[image.getWidth()][image.getHeight()];
		
		return weightedPixels;
	}

	/**
	 * Gets the average RGB value of the image.
	 * @return
	 */
	private int getAverageColorOfImage()
	{	
		int averageColor;
		int totalRGB = 0;
		for(int row = 0; row < image.getWidth(); row++)
		{
			for(int column=0; column < image.getHeight(); column++)
			{
				totalRGB += image.getRGB(row, column);
			}
		}
		
		averageColor = totalRGB / (image.getWidth() * image.getHeight());
		
		return averageColor;
	}
	
    /**
     * Add a new site to the DT.
     * @param point the site to add
     */
    public void addSite(Pnt point) {
        dt.delaunayPlace(point);
    }

    /**
     * Re-initialize the DT.
     */
    public void clear() {
        dt = new Triangulation(initialTriangle);
    }

    /**
     * Get the color for the specified item; generate a new color if necessary.
     * @param item we want the color for this item
     * @return item's color
     */
    private Color getColor (Pnt item) 
    {
        if (colorTable.containsKey(item)) return colorTable.get(item);
        Color color = new Color(image.getRGB((int) item.coord(0), (int) item.coord(1)));
        colorTable.put(item, color);
        return color;
    }
    
    /**
     * Get the color for the specified item; generate a new color from the average if necessary.
     * @param item we want the color for this item
     * @return item's color
     */
    private Color getColorFromAverage (Triangle triangle) 
    {
        if (colorTable.containsKey(triangle)) return colorTable.get(triangle);
        Color color;
        Pnt[] vertices = new Pnt[3];
        double[] averagePntCoord = new double[2];
        for(int i=0; i < vertices.length; i++)
        {
        	vertices[i] = triangle.get(i);
//        	System.out.println("vertices[" + i + "].coord(0) = " + vertices[i].coord(0));
//        	System.out.println("vertices[" + i + "].coord(1) = " + vertices[i].coord(1));
//        	System.out.println(vertices[i]);
        }
    
        // Check to see if this is the super triangle ( the outside triangle)
        if(vertices[0].equals(new Pnt(10000, -10000)) || vertices[0].equals(new Pnt(-10000, -10000)) || vertices[0].equals(new Pnt(0, 10000)))
        {
//        	System.out.println("Super Triangle");
        	color = new Color((Color.HSBtoRGB(random.nextFloat(), 1.0f, 1.0f)));
        }
        else
        {
            averagePntCoord[0] = (vertices[0].coord(0) + vertices[1].coord(0) + vertices[2].coord(0)) / 3;
//            System.out.println("averagepntCoord[0] = " + averagePntCoord[0]);
            averagePntCoord[1] = (vertices[0].coord(1) + vertices[1].coord(1) + vertices[2].coord(1)) / 3;
//            System.out.println("averagepntCoord[1] = " + averagePntCoord[1]);
            color = new Color(this.image.getRGB((int) averagePntCoord[0],(int) averagePntCoord[1]));
        }
        colorTable.put(triangle, color);
        return color;
    }

    /* Basic Drawing Methods */

    /**
     * Draw a point.
     * @param point the Pnt to draw
     */
    public void draw (Pnt point) {
        int r = pointRadius;
        int x = (int) point.coord(0);
        int y = (int) point.coord(1);
        g.fillOval(x-r, y-r, r+r, r+r);
    }

    /**
     * Draw a circle.
     * @param center the center of the circle
     * @param radius the circle's radius
     * @param fillColor null implies no fill
     */
    public void draw (Pnt center, double radius, Color fillColor) {
        int x = (int) center.coord(0);
        int y = (int) center.coord(1);
        int r = (int) radius;
        if (fillColor != null) {
            Color temp = g.getColor();
            g.setColor(fillColor);
            g.fillOval(x-r, y-r, r+r, r+r);
            g.setColor(temp);
        }
        g.drawOval(x-r, y-r, r+r, r+r);
    }

    /**
     * Draw a polygon.
     * @param polygon an array of polygon vertices
     * @param fillColor null implies no fill
     */
    public void draw (Pnt[] polygon, Color fillColor) {
        int[] x = new int[polygon.length];
        int[] y = new int[polygon.length];
        for (int i = 0; i < polygon.length; i++) {
            x[i] = (int) polygon[i].coord(0);
            y[i] = (int) polygon[i].coord(1);
        }
        if (fillColor != null) {
            g.setColor(fillColor);
            g.fillPolygon(x, y, polygon.length);
        }
        g.drawPolygon(x, y, polygon.length);
    }

    /* Higher Level Drawing Methods */

    /**
     * Draw all the Delaunay triangles.
     * @param withFill true iff drawing Delaunay triangles with fill colors
     */
    public void drawAllDelaunay (boolean withFill) {
        for (Triangle triangle : dt) {
            Pnt[] vertices = triangle.toArray(new Pnt[0]);
            draw(vertices, withFill? getColorFromAverage(triangle) : null);
        }
    }

    /**
     * Draw all the Voronoi cells.
     * @param withFill true iff drawing Voronoi cells with fill colors
     * @param withSites true iff drawing the site for each Voronoi cell
     */
    public void drawAllVoronoi (boolean withFill, boolean withSites) {
        // Keep track of sites done; no drawing for initial triangles sites
        HashSet<Pnt> done = new HashSet<Pnt>(initialTriangle);
        for (Triangle triangle : dt)
            for (Pnt site: triangle) {
                if (done.contains(site)) continue;
                done.add(site);
                List<Triangle> list = dt.surroundingTriangles(site, triangle);
                Pnt[] vertices = new Pnt[list.size()];
                int i = 0;
                for (Triangle tri: list)
                    vertices[i++] = tri.getCircumcenter();
                draw(vertices, withFill? getColor(site) : null);
                if (withSites) draw(site);
            }
    }

    /**
     * Draw all the empty circles (one for each triangle) of the DT.
     */
    public void drawAllCircles () {
        // Loop through all triangles of the DT
        for (Triangle triangle: dt) {
            // Skip circles involving the initial-triangle vertices
            if (triangle.containsAny(initialTriangle)) continue;
            Pnt c = triangle.getCircumcenter();
            double radius = c.subtract(triangle.get(0)).magnitude();
            draw(c, radius, null);
        }
    }

}
