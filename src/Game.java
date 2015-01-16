import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Game
 */
public class Game extends JPanel implements MouseListener, MouseMotionListener, ActionListener
{
	private Window window;

	private Paddle paddle = new Paddle();
	private Ball ball = new Ball(this);
	private Level[] levels;

	private int lives = Settings.LIVES;
	private int points = 0;
	private int currentLevel = 0;

	private Timer timer;

	private boolean started = false;

	private BufferedImage background;


	public Game(Window window)
	{
		this.window = window;

		startNewGame();

		setLayout(null);
		setPreferredSize(levels[currentLevel].getDimension());
		addMouseListener(this);
		addMouseMotionListener(this);

		try
		{
			background = ImageIO.read(getClass().getResource(Settings.GAME_BACKGROUND_PATH));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void loadCurrentLevel()
	{
		timer = new Timer(levels[currentLevel].getLevelSpeed(), this);
	}

	private void createLevels()
	{
		Landscape landscape = new Landscape(); // erstmal nur ein Standard-Landscape

		levels = new Level[]
				{                          // | speed | blöcke
						new Level(this, landscape, 18, 30),
						new Level(this, landscape, 15, 40),
						new Level(this, landscape, 13, 50),
						new Level(this, landscape, 10, 60),
				};
	}

	@Override
	public void paint(Graphics graphics)
	{
		Graphics2D g = (Graphics2D) graphics;

		g.drawImage(background,0,0, (int)Settings.GAME_SIZE.getWidth(), (int)Settings.GAME_SIZE.getHeight(), null);

		ball.paint(g);
		paddle.paint(g);
		levels[currentLevel].paint(g);

		g.setColor(Color.WHITE);
		g.setFont(new Font("default", Font.BOLD, 16));
		g.drawString("Leben: " + lives, 100, 20);
		g.drawString("Punkte: " + points, 450, 20);
		g.drawString("Level: " + (currentLevel + 1) + "/" + levels.length, 850, 20);

		window.repaint();
	}

	public void nextLevel()
	{
		if(currentLevel + 1 < levels.length)
		{
			currentLevel++;
			loadCurrentLevel();
			reset();
		}
		else
			window.showWonMessage();
	}

	public void startNewGame()
	{
		currentLevel = 0;
		createLevels();
		loadCurrentLevel();
		reset();
		lives = Settings.LIVES;
		points = 0;
	}

	public void ballLost()
	{
		if(lives > 1)
		{
			lives--;
		}
		else
			window.showLostMessage();

		reset();
	}

	public void reset()
	{
		ball = new Ball(this);
		ball.moveWithPaddle(paddle.getTopCenter()); // Ball wieder auf Paddel setzen
		started = false;
		timer.stop();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == timer && started)
		{
			// Balls' Kollisionen überprüfen: Wand, Paddle, Blöcke etc
			checkCollisionWithPaddle();
			checkCollisionWithWalls();
			checkCollisionWithBlocks();

			// Ball bewegen
			ball.move();
		}
	}


	private void checkCollisionWithBlocks()
	{
		boolean ballTopRight = false;
		boolean ballTopLeft = false;
		boolean ballBottomLeft = false;
		boolean ballBottomRight = false;

		boolean alreadyKilledOne = false;


		for (int y = 0; y < levels[currentLevel].getBlocks().length; y++)
		{
			for(int x = 0; x < levels[currentLevel].getBlocks()[y].length; x++)
			{
				if(levels[currentLevel].getBlocks()[y][x] != null && ball.getBoundingBox().intersects(levels[currentLevel].getBlocks()[y][x].getBoundingBox()))
				{
					// temporäre Variablen für aktuellen Durchlauf
					boolean btr = false,
							btl = false,
							bbl = false,
							bbr = false;

					// rechte obere ecke des Balls
					if(levels[currentLevel].getBlocks()[y][x].getBoundingBox().contains(ball.getX() + ball.getD(), ball.getY()))
						btr = true;

					//linke Obere ecke des Balls
					if(levels[currentLevel].getBlocks()[y][x].getBoundingBox().contains(ball.getX(), ball.getY()))
						btl = true;

					// linke untere ecke des balls
					if(levels[currentLevel].getBlocks()[y][x].getBoundingBox().contains(ball.getX(), ball.getY()+ball.getD()))
						bbl = true;

					// rechte untere ecke des balls
					if(levels[currentLevel].getBlocks()[y][x].getBoundingBox().contains(ball.getX()+ball.getD(), ball.getY() + ball.getD()))
						bbr = true;

					if((btr||btl||bbl||bbr) && !alreadyKilledOne)
					{
						levels[currentLevel].getBlocks()[y][x] = null; // Block löschen
						points += Settings.POINTS_PER_BLOCK;
						alreadyKilledOne = true;
						levels[currentLevel].blockHit();
					}


					if(btl) ballTopLeft = btl;
					if(btr) ballTopRight = btr;
					if(bbl) ballBottomLeft = bbl;
					if(bbr) ballBottomRight = bbr;

				}
			}
		}

		if((ballTopRight && ballTopLeft) || (ballBottomLeft && ballBottomRight)) // links oder rechts
		{
			ball.reverseYDirection();
		}
		else if((ballTopLeft && ballBottomLeft) || (ballTopRight && ballBottomRight)) // oben oder unten
		{
			ball.reverseXDirection();
		}
		else if(ballTopLeft || ballTopRight || ballBottomLeft || ballBottomRight) // wenn nur eine ecke des balles berührt
		{
			if (ballTopLeft)
			{
				if(ball.getVx() < 0 && ball.getVy() < 0) // direkt drauf
				{
					ball.reverseXDirection();
				}
				else if(ball.getVx() > 0 && ball.getVy() < 0) // ball fliegt nach rechts oben und streift
				{
					ball.reverseYDirection();
				}
				else if(ball.getVx() < 0 && ball.getVy() > 0) // ball fliegt nach links unten und streift
				{
					ball.reverseXDirection();
				}
			}
			else if(ballTopRight)
			{
				if(ball.getVx() > 0 && ball.getVy() <0) // direkt drauf
				{
					ball.reverseXDirection();
				}
				else if(ball.getVx() < 0 && ball.getVy() < 0) // ball fliegt nach links oben und streift
				{
					ball.reverseYDirection();
				}
				else if(ball.getVx() > 0 && ball.getVy() > 0) // ball fliegt nach rechts unten und streift
				{
					ball.reverseXDirection();
				}
			}
			else if(ballBottomLeft)
			{
				if(ball.getVx() < 0 && ball.getVy() > 0) // direkt drauf
				{
					ball.reverseXDirection();
				}
				else if(ball.getVx() > 0 && ball.getVy() > 0) // ball fliegt nach rechts unten und streift
				{
					ball.reverseYDirection();
				}
				else if(ball.getVx() < 0 && ball.getVy() < 0) // ball fliegt nach links oben und streift
				{
					ball.reverseXDirection();
				}
			}
			else if(ballBottomRight)
			{
				if(ball.getVx() > 0 && ball.getVy() > 0) // direkt drauf
				{
					ball.reverseXDirection();
				}
				else if(ball.getVx() > 0 && ball.getVy() < 0) // ball fliegt nach rechts oben und streift
				{
					ball.reverseXDirection();
				}
				else if(ball.getVx() < 0 & ball.getVy() > 0) // ball fliegt nach links unten und streift
				{
					ball.reverseYDirection();
				}
			}
		}
	}

	private void checkCollisionWithPaddle()
	{
		if (ball.getBoundingBox().intersectsLine(paddle.getX(), paddle.getY(), paddle.getX() + paddle.getWidth(), paddle.getY()))
		{
			ball.reverseYDirection();
		}
	}

	private void checkCollisionWithWalls()
	{
		Rectangle[] wallBoundingBoxes = levels[currentLevel].getWallBoundingBoxes();

		if(ball.getBoundingBox().intersects(wallBoundingBoxes[0])) ball.reverseXDirection(); // linke Wand
		if(ball.getBoundingBox().intersects(wallBoundingBoxes[1])) ball.reverseYDirection(); // Decke
		if(ball.getBoundingBox().intersects(wallBoundingBoxes[2])) ball.reverseXDirection(); // rechte Wand
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// ball starten
		timer.start();
		started = true;
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		int mouseXPosition = (int) e.getPoint().getX();
		Rectangle paddleSpace = levels[currentLevel].getPaddleSpace();

		// Paddel soll nur zwischen den beiden Wänden sein
		if(paddleSpace.contains(new Point(mouseXPosition - paddle.getWidth() / 2, paddle.getY()))
				&& paddleSpace.contains(new Point(mouseXPosition + paddle.getWidth()/2, paddle.getY())))
		{
			paddle.move(mouseXPosition);

			if(!started) ball.moveWithPaddle(paddle.getTopCenter());

			repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e){ mouseMoved(e); }

	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
