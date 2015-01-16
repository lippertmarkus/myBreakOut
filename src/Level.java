import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Level
 */
public class Level
{
	private Game game;
	private Landscape landscape;
	private int blockCount;
	private Block[][] blocks = new Block[10][6];
	private int speed;


	public Level(Game game, Landscape landscape, int speed, int blockCount)
	{
		this.game = game;
		this.landscape = landscape;
		this.speed = speed;
		this.blockCount = blockCount;

		createBlocks();
	}

	public Block[][] getBlocks()
	{
		return blocks;
	}

	private int getRemainingBlocks()
	{
		int count = 0;

		for (Block[] block : blocks)
		{
			for (Block aBlock : block)
			{
				if (aBlock != null) count++;
			}
		}
		return count;
	}

	public void blockHit()
	{
		if(getRemainingBlocks() < 1)
			game.nextLevel();
	}

	private void createBlocks()
	{
		BufferedImage blockTexture = null;
		try
		{
			blockTexture = ImageIO.read(getClass().getResource(Settings.BLOCK_IMAGE_PATH));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		int leftStart = (int) ((Settings.GAME_SIZE.getWidth() - (blocks[0].length * Settings.BLOCK_SIZE.getWidth())) / 2);

		for (int y = 0; y < blocks.length; y++)
		{
			for (int x = 0; x < blocks[y].length; x++)
			{
				blocks[y][x] = new Block((int) (x*Settings.BLOCK_SIZE.getWidth()+leftStart), (int) (y*Settings.BLOCK_SIZE.getHeight()+100), blockTexture);
			}
		}

		Random random = new Random();
		int randomXNumber;
		int randomYNumber;

		for(int i = 0; i < (blocks.length*blocks[0].length-blockCount); i++) // remove blocks until blockCount is reached
		{
			do
			{
				randomXNumber = random.nextInt((blocks[0].length));
				randomYNumber = random.nextInt((blocks.length));

				if(blocks[randomYNumber][randomXNumber] != null)
				{
					blocks[randomYNumber][randomXNumber] = null;
					break;
				}
			} while(true);
		}
	}

	public void paint(Graphics2D g)
	{
		landscape.paint(g);

		for (Block[] block : blocks)
		{
			for (Block aBlock : block)
			{
				if (aBlock != null)
					aBlock.paint(g);
			}
		}
	}

	public int getLevelSpeed()
	{
		return speed;
	}

	public Rectangle getPaddleSpace()
	{
		return landscape.getAvailablePaddleSpace();
	}

	public Dimension getDimension()
	{
		return landscape.getDimension();
	}

	public Rectangle[] getWallBoundingBoxes()
	{
		return landscape.getBoundingBoxes();
	}
}
