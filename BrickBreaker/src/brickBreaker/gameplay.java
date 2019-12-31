package brickBreaker;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.JPanel;

public class gameplay extends JPanel implements KeyListener, ActionListener
{
  public static final int SPEED = 20;


  private boolean play = false;
  private int score = 0;
  private int totalBricks = 21;
  private Timer timer;
  private int delay = 8;

  private int playerX = 310;
  private int ballposX = 120 -25;
  private int ballposY = 350 + 20;
  private int ballXdir = -1;
  private int ballYdir = -2;

  private mapGenerator map;

  public gameplay() {
    map = new mapGenerator(3, 7);
    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    timer = new Timer(delay, this);
    timer.start();
  }

  public void paint(Graphics g) {
    // backgroung
    g.setColor(Color.black);
    g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

    // bricks
    map.draw((Graphics2D)g);

    // border
    g.setColor(Color.red);
    g.fillRect(0, Main.HEIGHT - 10, Main.WIDTH, 10);
    // g.fillRect(0, 0, 10, brickBreaker.HEIGHT);
    // g.fillRect(0, 0, brickBreaker.WIDTH, 10);
    // g.fillRect(brickBreaker.WIDTH - 10, 0, 10, brickBreaker.HEIGHT);

    //score
    g.setColor(Color.white);
    g.setFont(new Font("serif", Font.BOLD, 25));
    g.drawString("" + score, 590, 30);
    // paddle
    g.setColor(Color.green);
    g.fillRect(playerX, 550, 100, 10);

    g.setColor(Color.yellow);
    g.fillOval(ballposX, ballposY, 20, 20);

    if(ballposY > 560) {
      play = false;
      ballXdir = ballYdir = 0;
      g.setFont(new Font("serif", Font.BOLD, 30));
      g.drawString("Game over, score: " + score, 200, 300);

      g.setFont(new Font("serif", Font.BOLD, 20));
      g.drawString("Press enter to restart", 15, 30);
    }
    g.dispose();
  }



  @Override
  public void actionPerformed(ActionEvent e) {
    timer.start();
    if(play) {
      if(550 <= ballposY + 20 && ballposY + 20 <= 560 &&
        playerX <= ballposX && ballposX <= playerX + 100 ) {
        // make corect borders
        ballYdir = - ballYdir;
      }

formap:
      for (int i = 0; i < map.map.length; ++i) {
        for (int j = 0; j < map.map[0].length; ++j) {
          if(map.map[i][j] > 0) {
            int brickX = j * map.brickWidth + 80;
            int brickY = i * map.brickHeight + 100;

            Rectangle brickRect = new Rectangle(brickX - 5, brickY - 5, map.brickWidth - 5, map.brickHeight - 5);
            Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
            if(ballRect.intersects(brickRect)) {
              map.setBrickValue(0, i, j);
              --totalBricks;
              ++score;

              if(ballposX + 23 >= brickRect.x || ballposX - 3>= brickRect.x + brickRect.width) {
                // make corect borders
                ballXdir = -ballXdir;
              } else {
                ballYdir = -ballYdir;
              }
              break formap;
            }
          }
        }
      }

      ballposX +=ballXdir;
      ballposY +=ballYdir;
      if(ballposX < 10){
        ballXdir = - ballXdir;
      }
      if(ballposX > Main.WIDTH - 20 -10){
        ballXdir = - ballXdir;
      }
      if(ballposY < 10){
        ballYdir = - ballYdir;
      }
    }
    repaint();

    // For Linux
    // Prevents lagging
    Toolkit.getDefaultToolkit().sync();
  }
  @Override
  public void keyTyped(KeyEvent e) {}
  @Override
  public void keyReleased(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
      if(playerX >= Main.WIDTH - 100 - 10) {
        playerX = Main.WIDTH - 100 - 10;
      }
      else {
        moveRight();
      }
    }
    if(e.getKeyCode() == KeyEvent.VK_LEFT) {
      if(playerX <= 10) {
        playerX = 10;
      }
      else {
        moveLeft();
      }
    }
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (!play) {
        play = true;
        playerX = 310;
        ballposX = 120;
        ballposY = 350;
        ballXdir = -1;
        ballYdir = -2;

      }
    }
  }

  public void moveRight() {
    play = true;
    playerX += SPEED;
  }
  public void moveLeft() {
    play = true;
    playerX -= SPEED;
  }
}
