import java.util.Arrays;
import java.util.Scanner;


public class Sudoku {
	
	private static int[][] board = new int[][] {
		{6, 2, 4, 5, 3, 9, 1, 8, 7},
		{5, 1, 9, 7, 2, 8, 6, 3, 4},
		{8, 3, 7, 6, 1, 4, 2, 9, 5},
		{1, 4, 3, 8, 6, 5, 7, 2, 9},
		{9, 5, 8, 2, 4, 7, 3, 6, 1},
		{7, 6, 2, 3, 9, 1, 4, 5, 8},
		{3, 7, 1, 9, 5, 6, 8, 4, 2},
		{4, 9, 6, 1, 8, 2, 5, 7, 3},
		{2, 8, 5, 4, 7, 3, 9, 1, 6}
	};
	private static int[][] board2 = new int[][] {
		{6, 6, 4, 5, 3, 9, 1, 8, 7},
		{5, 1, 9, 7, 2, 8, 6, 3, 4},
		{8, 3, 7, 6, 1, 4, 2, 9, 5},
		{1, 4, 3, 8, 6, 5, 7, 2, 9},
		{9, 5, 8, 2, 4, 7, 3, 6, 1},
		{7, 6, 2, 3, 7, 1, 4, 5, 8},
		{3, 7, 1, 9, 5, 6, 8, 4, 2},
		{4, 9, 6, 1, 8, 2, 5, 7, 3},
		{2, 8, 5, 4, 7, 3, 9, 1, 6}
	};
	
	private static int[][] board3 = new int[][] {
		{6, 1, 4, 5, 3, 9, 1, 3, 7},
		{5, 1, 9, 7, 2, 8, 6, 3, 4},
		{8, 3, 7, 6, 1, 4, 2, 9, 5},
		{1, 4, 3, 8, 6, 5, 7, 2, 9},
		{9, 5, 8, 2, 4, 7, 3, 6, 1},
		{7, 6, 2, 3, 9, 1, 4, 5, 8},
		{3, 7, 1, 9, 5, 6, 8, 4, 2},
		{4, 9, 6, 1, 8, 2, 5, 7, 3},
		{2, 8, 5, 4, 7, 3, 9, 1, 6}
	};
	final static int n = 9;
	final static int maxT = 9;
	private static Thread[] numThreads = new Thread [maxT];
	
	public static class RowAndColPara{
		int row;
		int col;
		RowAndColPara(int row,int col) {
			this.row = row;
			this.col = col;
		}
	}
	
	public static void main(String[] args) {
		int index = 0;
		Scanner s = new Scanner(System.in);
		System.out.println("Press 1 for an example of invalid board, 2 for another, and 3 for valid solution");
		int input = s.nextInt();
		if (input == 1) {
		board = board2;
			}
		if(input == 2 ) {
		board = board3;
		}
		if(input == 3) {
		board = board;
		System.out.println("You win congrats ");
		}
			Sudoku obj = new Sudoku();
			Thread t1 = new Thread(obj.new RowCheck());
			Thread t2 = new Thread(obj.new ColCheck());
			
			for(int i = 0; i < 9; i++) {
				for(int j = 0; j < 9; j++) {
				if( i % 3 == 0 && j % 3 == 0) {
					numThreads[index++] = new Thread(obj.new GridCheck(i, j));
					}
			}
		}
			for (int i = 0; i < numThreads.length; i++) {
				numThreads[i].start();
				}
			t1.start();
			t2.start();
		
		}
		
	
	
	private class RowCheck implements Runnable{
	
		@Override
		public void run() {
			
			boolean[] checkR = new boolean[n + 1]; // fits every row
			for(int i = 0; i<n; i++) {
				Arrays.fill(checkR, false); 
				for(int j = 0; j < n; j++) {
					int val = board[i][j];
					if ( val < 1 || val > 9 ||checkR[val]){//checking to see if it has ever encountered the value before or if value is valid in sudoku
						checkR[val] = false;
						System.out.println("Invaild solution: " + board[i][j] + " already in row");
						break;
						}
					checkR[val] = true;
				 }
				}
			
	}
}
	private class ColCheck implements Runnable{
		
		@Override
		public void run() {
			boolean[] checkC = new boolean[n + 1]; 
			for(int i = 0; i<n; i++) {
				Arrays.fill(checkC, false); 
				for(int j = 0; j < n; j++) {
					int val = board[j][i];
					if ( val < 1 || val > 9 || checkC[val]) {//same but for columns
						checkC[val] = false;
						System.out.println("Invaild solution: " + board[j][i] + " already in column");
						break;
						}
					checkC[val] = true;
					}
			}
			
	}
		
		
}
	private class GridCheck extends RowAndColPara implements Runnable{
		
		
		GridCheck(int row, int col) {
		super(row, col);
		// TODO Auto-generated constructor stub
	}

		@Override
		public void run() {
			boolean[] checkG = new boolean[n]; 
			
			for(int i = row; i < n-2 ; i += 3) { //divides into 3*3 grid 
				for(int j = col; j < n-2; j+= 3) {
					Arrays.fill(checkG, false);
					for(int a = 0; a < 3;a++) {//moving in current grid
						for (int b = 0; b < 3; b++) {
						int RowX = i + a;
						int ColY = j + b;
						int val = board[RowX][ColY]; //contains the value of the current block
						if (val < 1 || val > 9 || checkG[val - 1]){//same but for grids (minus 1 so array does not go out of bounds)
						checkG[val] = false;
						System.out.println("Invaild solution: " + board[RowX][ColY] + " already in grid");
						break;
					}
					checkG[val-1] = true;
					}
				}
				
		}
	}
		
}
}
}




		
	



