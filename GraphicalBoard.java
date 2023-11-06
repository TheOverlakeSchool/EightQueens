// CSE 143, Spring 2014

// This is a variation of the 8 queens board class that provides an animation
// of the recursive backtracking.

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class GraphicalBoard extends Board implements ChangeListener {
   private JButton[][] buttons;
   private static final int FONT_SIZE = 70;

   public GraphicalBoard(int size) {
      super(size);
      JFrame f = new JFrame();
      f.setSize(60 * size + 50, 60 * size + 80);
      f.setTitle("N Queens");
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // add buttons in the middle for the chess squares
      JPanel center = new JPanel(new GridLayout(size, size, 1, 1));
      f.add(center);
      center.setBackground(Color.black);
      buttons = new JButton[size][size];
      Font font24 = new Font("SansSerif", Font.BOLD, 24);
      for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
            JButton button = new JButton();
            button.setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.BLACK);
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setForeground((i + j) % 2 == 1 ? Color.WHITE : Color.BLACK);
            button.setFont(font24);
            buttons[i][j] = button;
            center.add(button);
         }
      }

      // initialize delay and add slider to control it at bottom
      JSlider slider = new JSlider(0, MAX_PAUSE - 15);
      setDelay(slider.getValue());
      slider.addChangeListener(this);
      JPanel south = new JPanel();
      south.add(new JLabel("slow"));
      south.add(slider);
      south.add(new JLabel("fast"));
      f.add(south, BorderLayout.SOUTH);

      // bring it on...
      f.setVisible(true);
      f.toFront();
   }

   public void place(int row, int col) {
      super.place(row, col);
      buttons[row - 1][col - 1].setFont(new Font("SansSerif", Font.PLAIN, FONT_SIZE));
      buttons[row - 1][col - 1].setText("Q");
      pause();
   }

   public boolean isSafe(int row, int col) {
      buttons[row - 1][col - 1].setFont(new Font("SansSerif", Font.PLAIN, FONT_SIZE));
      buttons[row - 1][col - 1].setText("?");
      pause();
      buttons[row - 1][col - 1].setText("");
      return super.isSafe(row, col);
   }

   public void remove(int row, int col) {
      super.remove(row, col);
      buttons[row - 1][col - 1].setFont(new Font("SansSerif", Font.PLAIN, FONT_SIZE));
      buttons[row - 1][col - 1].setText("X");
      pause();
      buttons[row - 1][col - 1].setText("");
   }

   // ChangeListener for slider
   public void stateChanged(ChangeEvent e) {
      setDelay(MAX_PAUSE - ((JSlider) e.getSource()).getValue());
   }
}
