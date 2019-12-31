package brickBreaker;

import javax.swing.JFrame;



public class Main
{
  public static final int HEIGHT = 600;
  public static final int WIDTH = 700;

  public static void main(String args[])
  {
    JFrame obj = new    JFrame();
    gameplay gamePlay = new gameplay();
    obj.setBounds(10, 10, WIDTH, HEIGHT + 30);
    obj.setTitle("Brick Bracker");
    obj.setResizable(true);
    obj.setVisible(true);
    obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    obj.add(gamePlay);
  }
}
