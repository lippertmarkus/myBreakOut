import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Ball
 */
public class Ball extends Actor
{
	private Game game;
	private int vx = Settings.BALL_INIT_VX;
	private int vy = Settings.BALL_INIT_VY;
	private int d = Settings.BALL_RADIUS * 2;

	private BufferedImage ballImage;


	public Ball(Game game)
	{
		this.game = game;
		width = height = d;

		try
		{
			ballImage = ImageIO.read(getClass().getResource(Settings.BALL_IMAGE_PATH));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics2D g)
	{
		g.drawImage(ballImage,x,y,d,d,null);
		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(1));
	}

	public void moveWithPaddle(Point paddleTopCenter)
	{
		x = (int) (paddleTopCenter.getX() - d/2);
		y = (int) (paddleTopCenter.getY() - d - 1);
	}

	public void move()
	{
		x += vx;
		y += vy;

		if(y > Settings.GAME_SIZE.getHeight()) game.ballLost();
	}

	public void reverseXDirection()
	{
		vx = -vx;
	}

	public void reverseYDirection()
	{
		vy = -vy;
	}

	public int getVx() { return vx; }

	public int getVy() { return vy; }

	public int getD(){ return d; }
}
