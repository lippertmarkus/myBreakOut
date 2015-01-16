import java.awt.*;

/**
 * Some Settings
 */
public class Settings
{
	// some graphic paths
	public static String GAME_BACKGROUND_PATH = "/res/background.jpg";
	public static String BALL_IMAGE_PATH = "/res/ball.png";
	public static String LANDSCAPE_FIRE_IMAGE_PATH = "/res/fire.png";
	public static String LANDSCAPE_WALL_IMAGE_PATH = "/res/wall.jpg";
	public static String BLOCK_IMAGE_PATH = "/res/block.jpg";
	public static String PADDLE_IMAGE_PATH = "/res/paddle.png";

	// not changeable, cause other values don't depend on game size yet
	public static Dimension GAME_SIZE = new Dimension(1024,768);
	public static Dimension PADDLE_SIZE = new Dimension(100, 20);
	public static Dimension BLOCK_SIZE = new Dimension(130, 30);
	public static int WALL_WIDTH = 30;
	public static int BALL_RADIUS = 10;
	public static int BALL_INIT_VX = 5;
	public static int BALL_INIT_VY = -10;

	// adaptable
	public static int LIVES = 3;
	public static int POINTS_PER_BLOCK = 10;
}
