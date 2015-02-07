package holyshatots.controller;

import java.io.IOException;

public class DelaunayRunner
{
	public static void main(String[] args)
	{
		AppController controller = new AppController();
		try
		{
			controller.run();
		}
		catch (IOException e)
		{
			System.out.println("Image not found or not selected.. exiting!");
		}
	}
}
