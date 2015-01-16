import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Block
 */
public class Block extends Actor
{
	private BufferedImage texture;

	public Block(int x, int y, BufferedImage texture)
	{
		this.x = x;
		this.y = y;

		width = (int) Settings.BLOCK_SIZE.getWidth();
		height = (int) Settings.BLOCK_SIZE.getHeight();
		this.texture = texture;
	}

	@Override
	public void paint(Graphics2D g)
	{
		g.drawImage(texture, x,y,width,height, null);
		g.setColor(Color.GRAY);
	}
}
