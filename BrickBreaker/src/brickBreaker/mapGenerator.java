package brickBreaker;

import java.awt.Color;
import java.awt.Graphics2D;

public class mapGenerator
{
  public int map[][];
  public int brickWidth;
  public int brickHeight;

  public mapGenerator(int row, int col) {
    map = new int[row][col];
    for (int i = 0; i < map.length; ++i) {
      for (int j = 0; j < map[0].length; ++j) {
        map[i][j] = 1;
      }
    }

    brickWidth = 540 / col;
    brickHeight = 150 / row;
    // make corect borders

  }
  public void draw(Graphics2D g) {
    for (int i = 0; i < map.length; ++i) {
      for (int j = 0; j < map[0].length; ++j) {
        if (map[i][j] > 0) {
          g.setColor(Color.white);
          g.fillRect(j * brickWidth + 80 - 5, i * brickHeight + 100 - 5, brickWidth - 5, brickHeight - 5);
        }
      }
    }
  }

  public void setBrickValue(int value, int row, int col) {
    map[row][col] = value;
  }
}
