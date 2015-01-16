import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Paddle (Player)
 */
public class Paddle extends Actor
{
	private BufferedImage paddleTexture;

	public Paddle()
	{
		width = (int) Settings.PADDLE_SIZE.getWidth();
		height = (int) Settings.PADDLE_SIZE.getHeight();

		try
		{
			paddleTexture = ImageIO.read(getClass().getResource(Settings.PADDLE_IMAGE_PATH));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		reset();
	}

	@Override
	public void paint(Graphics2D g)
	{
		g.drawImage(paddleTexture, x,y,width,height,null);
		g.setColor(Color.darkGray);
		g.setStroke(new BasicStroke(2));
		g.drawRoundRect(x, y, width, height, 5, 5);
	}

	public void move(int mouseXLocation)
	{
		x = mouseXLocation - (width / 2);
	}

	public void reset()
	{
		x = (int) (Settings.GAME_SIZE.getWidth() / 2) - (width / 2);
		y = (int) (Settings.GAME_SIZE.getHeight() - Settings.WALL_WIDTH * 2);
	}

	public Point getTopCenter()
	{
		return new Point(x+width/2, y);
	}


}
