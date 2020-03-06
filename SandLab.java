import java.awt.*;

public class SandLab {
	public static void main(String[] args) {
		SandLab lab = new SandLab(120, 80);
		lab.run();
	}

	// add constants for particle types here
	public static final int EMPTY = 0, METAL = 1, SAND = 2, WATER = 3;
	// do not add any more fields
	private int[][] grid;
	private SandDisplay display;

	public SandLab(int numRows, int numCols) {
		String[] names;
		names = new String[4];
		names[EMPTY] = "Empty";
		names[METAL] = "Metal";
		names[SAND] = "Sand";
		names[WATER] = "Water";
		display = new SandDisplay("Falling Sand", numRows, numCols, names);
		grid = new int[numRows][numCols];
	}

	// called when the user clicks on a location using the given tool
	private void locationClicked(int row, int col, int tool) {
		grid[row][col] = tool;
	}

	// copies each element of grid into the display
	public void updateDisplay() {
		
		for (int row = 0; row < grid.length; row++) {
			
			for (int col = 0; col < grid[row].length; col++) {
				
				if (grid[row][col] == EMPTY) {
					display.setColor(row, col, Color.BLACK);
				}
				
				else if (grid[row][col] == METAL) {
					display.setColor(row, col, Color.GRAY);
				}
				
				else if (grid[row][col] == SAND) {
					display.setColor(row, col, new Color(239, 221, 111));
				}
				
				else if (grid[row][col] == WATER) {
					display.setColor(row, col, new Color(0, 47, 75));
				}
			}
		}
	}

	// called repeatedly.
	// causes one random particle to maybe do something.
	public void step() {
		int row = (int) (Math.random() * grid.length);
		int col = (int) (Math.random() * grid[0].length);
		
		if (grid[row][col] == SAND && row + 1 != grid.length && grid[row + 1][col] == EMPTY) {
			grid[row][col] = EMPTY;
			grid[row + 1][col] = SAND;
		}
		
		else if (grid[row][col] == WATER && grid[row - 1][col] == SAND) {
			grid[row][col] = SAND;
			grid[row - 1][col] = WATER;
		}
		
		else if (grid[row][col] == WATER) {
			int s = (int) (Math.random() * 3);
			
			if (s == 0 && row + 1 != grid.length && grid[row + 1][col] == EMPTY) {
				grid[row][col] = EMPTY;
				grid[row + 1][col] = WATER;
			}
			
			else if (s == 1 && col + 1 < grid[0].length && grid[row][col + 1] == EMPTY) {
				grid[row][col] = EMPTY;
				grid[row][col + 1] = WATER;
			}
			
			else if (s == 2 && col - 1 >= 0 && grid[row][col - 1] == EMPTY) {
				grid[row][col] = EMPTY;
				grid[row][col - 1] = WATER;
			}
		}
	}

	// do not modify
	public void run() {
		
		while (true) {
			
			for (int i = 0; i < display.getSpeed(); i++)
				step();
			
			updateDisplay();
			display.repaint();
			display.pause(1);// wait for redrawing and for mouse
			int[] mouseLoc = display.getMouseLocation();
			
			if (mouseLoc != null)// test if mouse clicked
				locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
		}
	}
}