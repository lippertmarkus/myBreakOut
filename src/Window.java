import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Main Window
 */
public class Window extends JFrame
{
	private Game game;


	public Window()
	{
		game = new Game(this);

		add(game, BorderLayout.CENTER);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		// Cursor ausblenden
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16,16, BufferedImage.TYPE_INT_ARGB), new Point(0,0), "blankCursor"));

		setVisible(true);

		game.requestFocus();
	}

	public void showLostMessage()
	{
		JOptionPane.showMessageDialog(this, "Du hast verloren! Es wird ein neues Spiel gestartet..");
		game.startNewGame();
	}

	public static void main(String[] args)
	{
		new Window();
	}

	public void showWonMessage()
	{
		JOptionPane.showMessageDialog(this, "Du hast das Spiel gewonnen! Es wird ein neues Spiel gestartet..");
		game.startNewGame();
	}
}
