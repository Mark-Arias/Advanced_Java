import java.awt.image.BufferedImage;

/**
 * This is the class that contains and loads the sheet containing the artwork assets
 * that will be used within this game
 * @author Mark Arias
 *
 */
public class SpriteSheet
{
	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image)
	{
		this.image = image;
	}
	
	// are able to pass in arguments to this function, whose purpose is to select an asset as a 
	// portion of the total sheet, and pick it by passing in a specified number to that asset
	public BufferedImage grabImage(int col, int row, int width, int height)
	{
		return image.getSubimage((col * 32) -32, (row * 32)-32, width, height);
	}

}
