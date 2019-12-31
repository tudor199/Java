
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class gameplay extends JPanel implements KeyListener, ActionListener{
	private int[] snakelengthX = new int[750];
	private int[] snakelengthY = new int[750];
	
	private ImageIcon titleImage;
	
	private boolean standby = false;	
	
	private boolean left= false;
	private boolean right= true;
	private boolean up= false;
	private boolean down= false;
	
	private ImageIcon snakemouth;
	private ImageIcon snakeimage;
	
	private int lengthofsnake = 3;

	private ImageIcon enemyimage;
	Random random = new Random();
	private int enemyposX = 150 + random.nextInt(29) * 25;
	private int enemyposY = 150 + random.nextInt(20) * 25;
	
	private Timer timer;
	private int delay = 100;
	
	private int score = 0;
	
	private int moves = 0;
	
	public gameplay() {
		snakelengthX[0] = 100;
		snakelengthX[1] = 75;
		snakelengthX[2] = 50;
		snakelengthY[0] = snakelengthY[1] = snakelengthY[2] = 100;
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		if(standby) {
			return;
		}
		
		// draw title image border
		g.setColor(Color.white);
		g.drawRect(24,  10,  851,  55);
		
		// draw border for gameplay
		g.drawRect(24, 74, 851, 577);
		
		// draw title image
		titleImage = new ImageIcon("res/img/snaketitle.jpg");
		titleImage.paintIcon(this, g, 25, 11);
		
		// draw background gameplay
		g.setColor(Color.black);
		g.fillRect(25, 75, 850, 575);
		
		// stats
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Score: " + score, 780, 30);

		snakeimage = new ImageIcon("res/img/snakeimage.png");
		for(int i = 1; i < lengthofsnake; ++i) {
			snakeimage.paintIcon(this, g, snakelengthX[i], snakelengthY[i]);
		}
		
		snakemouth = new ImageIcon("res/img/rightmouth.png");
		if(left) {
			snakemouth = new ImageIcon("res/img/leftmouth.png");
		}
		else if(up) {
			snakemouth = new ImageIcon("res/img/upmouth.png");
		}
		else if(down) {
			snakemouth = new ImageIcon("res/img/downmouth.png");
		}
		snakemouth.paintIcon(this, g, snakelengthX[0], snakelengthY[0]);
		
		
		enemyimage = new ImageIcon("res/img/enemy.png");
		enemyimage.paintIcon(this, g, enemyposX, enemyposY);
		if(enemyposX == snakelengthX[0] && enemyposY == snakelengthY[0]) {
			++lengthofsnake;
			++score;
			enemyposX = 25 + random.nextInt(34) * 25;
			enemyposY = 75 + random.nextInt(22) * 25;
		}

		for(int i = 1; i < lengthofsnake; ++i) {
			if(snakelengthX[i] == snakelengthX[0] && snakelengthY[i] == snakelengthY[0]) {
				standby = true;
				left = right = up = down = false;
				g.setColor(Color.white);
				g.setFont(new Font("arial", Font.BOLD, 50));
				g.drawString("Game over!", 300, 300);
			}
		}
		
		g.dispose();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			standby = !standby;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(standby) {
				return;
			}
			++moves;
			if(!left) {
				right = true;
			}
			up = down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(standby) {
				return;
			}
			++moves;
			if(!right) {
				left = true;
			}
			up = down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(standby) {
				return;
			}
			++moves;
			if(!down) {
				up = true;
			}
			left = right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN ) {
			if(standby) {
				return;
			}
			++moves;
			if(!up) {
				down = true;
			}
			left = right = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.restart();
		if(standby) {
			return;
		}
		if(left) {
			for(int i = lengthofsnake - 1; i >= 0; --i) {
				snakelengthY[i + 1] = snakelengthY[i];
			}
			for(int i = lengthofsnake; i >= 0; --i) {
				if(i == 0) {
					snakelengthX[i] -= 25;
				} else {
					snakelengthX[i] = snakelengthX[i - 1];
				}
				if(snakelengthX[i] < 25) {
					snakelengthX[i] = 850;
				}
			}
			
		}
		else if(right) {
			for(int i = lengthofsnake - 1; i >= 0; --i) {
				snakelengthY[i + 1] = snakelengthY[i];
			}
			for(int i = lengthofsnake; i >= 0; --i) {
				if(i == 0) {
					snakelengthX[i] += 25;
				} else {
					snakelengthX[i] = snakelengthX[i - 1];
				}
				if(snakelengthX[i] > 850) {
					snakelengthX[i] = 25;
				}
			}
			
		}
		else if(up) {
			for(int i = lengthofsnake - 1; i >= 0; --i) {
				snakelengthX[i + 1] = snakelengthX[i];
			}
			for(int i = lengthofsnake; i >= 0; --i) {
				if(i == 0) {
					snakelengthY[i] -= 25;
				} else {
					snakelengthY[i] = snakelengthY[i - 1];
				}
				if(snakelengthY[i] < 75) {
					snakelengthY[i] = 625;
				}
			}
			
		}
		else if(down) {
			for(int i = lengthofsnake - 1; i >= 0; --i) {
				snakelengthX[i + 1] = snakelengthX[i];
			}
			for(int i = lengthofsnake; i >= 0; --i) {
				if(i == 0) {
					snakelengthY[i] += 25;
				} else {
					snakelengthY[i] = snakelengthY[i - 1];
				}
				if(snakelengthY[i] > 625) {
					snakelengthY[i] = 75;
				}
			}
			
		}
		
		repaint();

		// For Linux
		// Prevents lagging
		Toolkit.getDefaultToolkit().sync();
	}
}
