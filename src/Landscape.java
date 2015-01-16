import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Set;

/**
 * Landscape
 */
public class Landscape
{
	private Dimension dimension = Settings.GAME_SIZE;
	private int wallWidth = Settings.WALL_WIDTH;

	private BufferedImage fire;
	private BufferedImage wall;

	public Landscape()
	{
		try
		{
			fire = ImageIO.read(getClass().getResource(Settings.LANDSCAPE_FIRE_IMAGE_PATH));
			wall = ImageIO.read(getClass().getResource(Settings.LANDSCAPE_WALL_IMAGE_PATH));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void paint(Graphics2D g)
	{
		for(int x = 0; x < dimension.getWidth() - wallWidth; x+=60)
		{
			g.drawImage(fire, x, (int) dimension.getHeight() - 50, 100,50, null);
		}

		TexturePaint tp = new TexturePaint(wall, new Rectangle(0,0,100,100));
		g.setPaint(tp);

		g.fillRect(0, 0, wallWidth, (int) dimension.getHeight()); // left wall
		g.fillRect(0, 0, (int) dimension.getWidth(), wallWidth); // top wall
		g.fillRect((int) (dimension.getWidth() - wallWidth), 0, (int) dimension.getWidth(), (int) dimension.getHeight()); // right wall

		g.setColor(Color.GRAY);

		g.setStroke(new BasicStroke(2));
		g.drawLine(wallWidth, wallWidth, wallWidth, (int) dimension.getHeight());
		g.drawLine(wallWidth, wallWidth, (int) dimension.getWidth() - wallWidth , wallWidth);
		g.drawLine((int)dimension.getWidth() - wallWidth, wallWidth, (int)dimension.getWidth() - wallWidth, (int)dimension.getHeight());
	}

	public Rectangle getAvailablePaddleSpace()
	{
		return new Rectangle(wallWidth, (int) dimension.getHeight() - (2 * wallWidth), (int) dimension.getWidth() - (2*wallWidth), 2*wallWidth);
	}

	public Dimension getDimension()
	{
		return dimension;
	}

	public Rectangle[] getBoundingBoxes()
	{
		return new Rectangle[]
				{
						new Rectangle(0, 0, wallWidth, (int) dimension.getHeight()), // linke Wand
						new Rectangle(wallWidth, 0, (int) dimension.getWidth() - wallWidth, wallWidth), // Decke
						new Rectangle((int) dimension.getWidth() - wallWidth, 0 , (int) dimension.getWidth(), (int) dimension.getHeight()) // rechte Wand
				};
	}
}
