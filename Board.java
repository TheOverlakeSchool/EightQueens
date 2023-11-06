// CSE 143, Spring 2014

// Represents a chess board on which queens can be placed.
// Stores information relevant to solving the n queens problem of
// placing n queens on an n-by-n chess board.

public class Board {
   protected static final int MAX_PAUSE = 1000; // milliseconds
   private static final int UNASSIGNED = 100; // unassigned column

   private int[] board; // stores board info
   private int delay; // time to pause
   private int placements; // number of times place() has been called

   /**
    * Constructs an empty size-by-size board.
    * 
    * @param size
    *           the size of the board.
    * @throws IllegalArgumentException
    *            if size is less than 1.
    */
   public Board(int size) {
      if (size < 0) {
         throw new IllegalArgumentException();
      }
      board = new int[size];
      for (int i = 0; i < size; i++) {
         board[i] = UNASSIGNED;
      }
      delay = 500;
      placements = 0;
   }

   /**
    * Returns a count of the number of times place() has been called.
    */
   public int getPlacements() {
      return placements;
   }

   /**
    * Returns <code>true</code> if it is safe to place a queen at (row, col).
    * 
    * @param row
    *           row of position to check.
    * @param col
    *           column of position to check.
    * @return <code>true</code> if it is safe to place a queen at (row, col).
    * @throws IllegalArgumentException
    *            if row and col do not represent a legal board position.
    */
   public boolean isSafe(int row, int col) {
      // first reset row and col to array range (0..size-1)
      row--;
      col--;

      // next check that row, col are in bounds
      if (!legal(row, col)) {
         throw new IllegalArgumentException();
      }

      // next check that the current column is empty
      if (board[col] != UNASSIGNED) {
         return false;
      }

      // now check for conflicts with other columns
      for (int currCol = 0; currCol < board.length; currCol++) {
         int distance = col - currCol;
         // check for diagonal conflict
         if (board[currCol] == row - distance) {
            return false;
         }
         // check for conflict in this row
         if (board[currCol] == row) {
            return false;
         }
         // check for other diagonal conflict
         if (board[currCol] == row + distance) {
            return false;
         }
      }
      return true;
   }

   /**
    * Places a queen at position (row, col).
    * 
    * @param row
    *           row of position to check.
    * @param col
    *           column of position to check.
    * @throws IllegalArgumentException
    *            if it is not safe to place a queen at the given position.
    */
   public void place(int row, int col) {
      if (!isSafe(row, col)) {
         throw new IllegalArgumentException();
      }
      board[col - 1] = row - 1;
      placements++;
   }

   /**
    * Causes the board to pause for a given amount of time.
    */
   public void pause() {
      try {
         Thread.sleep(delay);
      } catch (InterruptedException e) {
      }
   }

   /**
    * Removes the queen at position (row, col).
    * 
    * @param row
    *           row of position to check.
    * @param col
    *           column of position to check.
    * @exception IllegalArgumentException
    *               if there is no queen at the given position.
    */
   public void remove(int row, int col) {
      if (!legal(row - 1, col - 1) || board[col - 1] != row - 1) {
         throw new IllegalArgumentException();
      }
      board[col - 1] = UNASSIGNED;
   }

   // Uses the given delay for future pauses
   protected void setDelay(int ms) {
      delay = ms;
   }

   /**
    * Returns the size of the board.
    * 
    * @return the size of the board.
    */
   public int size() {
      return board.length;
   }

   /**
    * Displays the current board to System.out.
    */
   public String toString() {
      String result = "";
      for (int row = 0; row < board.length; row++) {
         for (int col = 0; col < board.length; col++) {
            if (board[col] == row) {
               result += " Q ";
            } else {
               result += " - ";
            }
         }
         result += "\n";
      }
      return result;
   }

   // Returns true iff row and col are legal for this board
   private boolean legal(int row, int col) {
      return row >= 0 && row < board.length && col >= 0 && col < board.length;
   }
}
