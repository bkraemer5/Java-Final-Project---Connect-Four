import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ConnectFour extends JFrame implements KeyListener {
	
	// the JFrame is of CardLayout
	CardLayout cardLayout = new CardLayout();
	
	// components for the title screen card
	private JPanel titleScreen = new JPanel();
	private JLabel title1 = new JLabel("Welcome to ");
	private JLabel title2 = new JLabel("Connect Four");
	private JLabel space1 = new JLabel("   ");
	private JLabel statement = new JLabel("Press Enter to begin");
	
	// components for the game screen card
	private JPanel gameScreen = new JPanel(new BorderLayout());
	private JPanel side1 = new JPanel();
	private JLabel player1 = new JLabel(" Player 1: ");
	private JLabel icon1 = new JLabel();
	private JPanel side2 = new JPanel();
	private JLabel player2 = new JLabel(" Player 2: ");
	private JLabel icon2 = new JLabel();
	private JLabel turn = new JLabel("Player 1's Turn");
	
	// array and components for the actual game board
	private final int ROWS = 7;
	private final int COLS = 7;
	private JPanel gameBoard = new JPanel(new GridLayout(ROWS, COLS));
	private JPanel[][] gameSquare = new JPanel[ROWS][COLS];
	private JLabel[][] mover = new JLabel[ROWS][COLS];
	private int moverRow = 0;
	private int moverCol = 3;
	private int player = 1;
	private int tempRow;
	private int tempCol;
	private boolean win;
	
	// background color for game board
	private Color black = Color.BLACK;
	
	// converting the image
	ImageIcon redCircleImg = new ImageIcon("//Users/brittanykraemer/Desktop/red-circle-icon-hi.png");
	ImageIcon blueCircleImg = new ImageIcon("//Users/brittanykraemer/Desktop/fwU59s-blue-circle-clipart-transparent.png");
	ImageIcon whiteCircleImg = new ImageIcon("//Users/brittanykraemer/Desktop/white-circle-png2e5-4968-8f34-657d02dde547.png");
	ImageIcon empty = new ImageIcon();
	Image rc = redCircleImg.getImage().getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH);
	Image bc = blueCircleImg.getImage().getScaledInstance(52, 52, java.awt.Image.SCALE_SMOOTH);
	Image wc = whiteCircleImg.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
	ImageIcon redCircle = new ImageIcon(rc);
	ImageIcon blueCircle = new ImageIcon(bc);
	ImageIcon whiteCircle = new ImageIcon(wc);
	
	ConnectFour() {
		
		super("Connect Four");
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(cardLayout);
		
		// setting the fonts
		title1.setFont(new Font("Arial", Font.BOLD, 60));
		title2.setFont(new Font("Arial", Font.BOLD, 60));
		statement.setFont(new Font("Arial", Font.PLAIN, 30));
		turn.setFont(new Font("Arial", Font.PLAIN, 30));
		
		// putting JPanels & JFrames into the array
		for(int x = 0; x < ROWS; x++) {
			for (int y = 0; y < COLS; y++) {
				gameSquare[x][y] = new JPanel();
				gameBoard.add(gameSquare[x][y]);
				mover[x][y] = new JLabel();
				gameSquare[x][y].add(mover[x][y]);
				
				// this ensures that the first row is blank for the mover
				if (x > 0) {
					gameSquare[x][y].setBackground(black);
					mover[x][y].setIcon(whiteCircle);
				}
			}
		}
		// sets the initial spot of the mover
		mover[moverRow][moverCol].setIcon(blueCircle);
		
		// setting up the first card -- title screen
		titleScreen.setLayout(new GridLayout(4, 1));
		title1.setHorizontalAlignment(JLabel.CENTER);
		title2.setHorizontalAlignment(JLabel.CENTER);
		statement.setHorizontalAlignment(JLabel.CENTER);
		titleScreen.add(space1);
		titleScreen.add(title1);
		titleScreen.add(title2);
		titleScreen.add(statement);
		add(titleScreen, "Panel1");
		
		// created a JLabel for the icon so i could place it within a panel
		icon1.setIcon(blueCircle);
		icon2.setIcon(redCircle);
		
		// sets up the left side of the board
		side1.setLayout(new FlowLayout());
		side1.add(player1);
		side1.add(icon1);
		
		// sets up the right side of the board
		side2.setLayout(new FlowLayout());
		side2.add(player2);
		side2.add(icon2);
		
		// aligns the top header stating whose move it is
		turn.setHorizontalAlignment(JLabel.CENTER);

		// setting up the second card -- game screen
		gameScreen.setLayout(new BorderLayout());
		gameScreen.add(gameBoard, BorderLayout.CENTER);
		gameScreen.add(side1, BorderLayout.WEST);
		gameScreen.add(side2, BorderLayout.EAST);
		gameScreen.add(turn, BorderLayout.NORTH);
		add(gameScreen, "Panel2");
		
		// allows KeyListener to be used
		addKeyListener(this);
		
	}
	
	// checkGame() is intended to check whether someone has won the game or not
	// iterates through each combination on the board using for loops
	public boolean checkGame() {
		win = false;
		// horizontal match
		for (int j = 1; j < 7; j++) {
			for (int k = 0; k < 4; k++) {
				if (mover[j][k].getIcon() == mover[j][k+1].getIcon() 
					&& mover[j][k].getIcon() == mover[j][k+2].getIcon() 
					&& mover[j][k].getIcon() == mover[j][k+3].getIcon()) {
					if (mover[j][k].getIcon() != whiteCircle) {
						win = true;
					}
				}
			}
		}
		
		// vertical match
		for (int k = 0; k < 7; k++) {
			for (int j = 0; j < 4; j++) {
				if (mover[j][k].getIcon() == mover[j+1][k].getIcon() 
					&& mover[j][k].getIcon() == mover[j+2][k].getIcon() 
					&& mover[j][k].getIcon() == mover[j+3][k].getIcon()) {
					if (mover[j][k].getIcon() != whiteCircle) {
						win = true;
					}
				}
			}
		}
		
		// left diagonal match
		for (int j = 1; j < 4; j++) {
			for (int k = 0; k < 4; k++) {
				if (mover[j][k].getIcon() == mover[j+1][k+1].getIcon() 
					&& mover[j][k].getIcon() == mover[j+2][k+2].getIcon() 
					&& mover[j][k].getIcon() == mover[j+3][k+3].getIcon()) {
					if (mover[j][k].getIcon() != whiteCircle) {
						win = true;
					}
				}
			}
		}
		
		// right diagonal match
		for (int j = 1; j < 4; j++) {
			for (int k = 6; k > 2; k--) {
				if (mover[j][k].getIcon() == mover[j+1][k-1].getIcon() 
					&& mover[j][k].getIcon() == mover[j+2][k-2].getIcon() 
					&& mover[j][k].getIcon() == mover[j+3][k-3].getIcon()) {
					if (mover[j][k].getIcon() != whiteCircle) {
						win = true;
					}
				}
			}
		}
		
		return win;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		// if the user presses enter, they will be able to begin the game
		// present on the title screen
		if (keyCode == KeyEvent.VK_ENTER) {
			cardLayout.show(getContentPane(), "Panel2");
		}
		
		// this shifts the mover to the right
		else if (keyCode == KeyEvent.VK_RIGHT) {
			if (moverCol < 6) {
				mover[moverRow][moverCol].setIcon(empty);
				moverCol++;
				if (player == 1) {
					mover[moverRow][moverCol].setIcon(blueCircle);
				}
				else if (player == 2) {
					mover[moverRow][moverCol].setIcon(redCircle);
				}
			}
		}
		
		// this shifts the mover to the left
		else if (keyCode == KeyEvent.VK_LEFT) {
			if (moverCol > 0) {
				mover[moverRow][moverCol].setIcon(empty);
				moverCol--;
				if (player == 1) {
					mover[moverRow][moverCol].setIcon(blueCircle);
				}
				else if (player == 2) {
					mover[moverRow][moverCol].setIcon(redCircle);
				}
			}
		}
		
		/* when the user presses space, a marker will be placed directly below 
		 * the mover's location where there is an empty space. After, the player is switched.
		 * IF checkGame() is true, the top header will change revealing who won the game
		 */
		else if (keyCode == KeyEvent.VK_SPACE) {
			tempRow = 1;
			if (mover[6][moverCol].getIcon() == whiteCircle) {
				tempRow = 6;
			}
			else {
				while (mover[tempRow+1][moverCol].getIcon() == whiteCircle && tempRow < 5) {
					tempRow++;
				}
			}
		
			if (player == 1) {
				if (mover[tempRow][moverCol].getIcon() == whiteCircle) {
					mover[tempRow][moverCol].setIcon(blueCircle);
				}
				
				player++;
				mover[moverRow][moverCol].setIcon(redCircle);
				turn.setText("Player 2's Turn");
				if (checkGame() == true) {
					turn.setFont(new Font("Arial", Font.BOLD, 50));
					turn.setText("PLAYER 1 WINS");
				}
			}
			else if (player == 2) {
				if (mover[tempRow][moverCol].getIcon() == whiteCircle) {
					mover[tempRow][moverCol].setIcon(redCircle);
				}
				player--;
				mover[moverRow][moverCol].setIcon(blueCircle);
				turn.setText("Player 1's Turn");
				if (checkGame() == true) {
					turn.setFont(new Font("Arial", Font.BOLD, 50));
					turn.setText("PLAYER 2 WINS");
				}
				}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}

public static void main(String[] args) {
	ConnectFour game = new ConnectFour();
	game.setVisible(true);
}

}
