import java.awt.*;

/**
 * Actor
 */
public abstract class Actor
{
	protected int x, y, width, height;

	public Rectangle getBoundingBox()
	{
		return new Rectangle(x,y,width,height);
	}

	public abstract void paint(Graphics2D g);

	public int getX() { return x; }

	public int getY() { return y; }

	public int getWidth() { return  width; }
}
