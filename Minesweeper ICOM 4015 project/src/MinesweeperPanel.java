import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;
import javax.swing.JPanel;

public class MinesweeperPanel extends JPanel {
	private static final long serialVersionUID = -9191774027614103611L;
	private static final int GRID_X = 50;
	private static final int GRID_Y = 50;
	private static final int INNER_CELL_SIZE = 29;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 9;   
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	//private Random valueOfMine = new Random();
	private int numberOfBombs = 10;
	public boolean[][] mineLocation = new boolean[TOTAL_COLUMNS][TOTAL_ROWS];

	public MinesweeperPanel() {  
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}
		for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.WHITE;
			}
		}
//		for (int i=0; i<TOTAL_COLUMNS; i++)	{
//			for (int j=0; j< TOTAL_ROWS; j++){
//				mineLocation[i][j] = valueOfMine.nextBoolean();
//			}
//		}
		for (int t=0; t<numberOfBombs; t++)	{
			Random i = new Random();
			Random j = new Random();
			mineLocation[i.nextInt(TOTAL_COLUMNS)][j.nextInt(TOTAL_ROWS)] = true;
		}
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;

		//Paint the background
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x1, y1, width + 1, height + 1);

		//Draw the grid minus the bottom row (which has only one cell)
		//By default, the grid will be 10x10 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS)));
		}

		//Draw an additional cell at the top center
		//g.drawRect(x1 + GRID_X + ((INNER_CELL_SIZE + 1)*4) , y1 + GRID_Y - INNER_CELL_SIZE , INNER_CELL_SIZE + 1, INNER_CELL_SIZE + 1);

		//Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				Color c = colorArray[x][y];
				g.setColor(c);
				g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
			}
		}

	}



	public int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}
	public int getGridY(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
	public boolean surroundedByMine(int x, int y){
		for(int i=x-1; i<(x+2); i++){
			for(int j=y-1; j<(y+2); j++){
				if(i <0 || j <0 || i > (TOTAL_COLUMNS-1) || j > (TOTAL_ROWS-1)){
					//Do nothing
				}
				else{
					if(mineLocation[i][j]==true){
						return true;
					}
				} 

			}
		}
		return false; 
	}
	public void uncoveringForLoop(int x, int y){
		Color newColor = null;

		for(int i = x-1; i < x+2; i++){
			for(int j = y-1; j < y+2; j++){
				if(i <0 || j <0 || i > (TOTAL_COLUMNS-1) || j > (TOTAL_ROWS-1)){
					//Do nothing
				}
				else{
					if(this.surroundedByMine(i, j)==true){
						newColor = Color.GREEN;
						colorArray[i][j] = newColor;
						repaint();
					}
					else{
						newColor = Color.LIGHT_GRAY;
						colorArray[i][j] = newColor;
						repaint();
					}
				}
			}

		}

	}
	
	public int scanForNearBombs(int  x, int y){
		int numberOfBombs =0;
		for(int i = x-1; i < x+2; i++){
			for(int j = y-1; j < y+2; j++){
				if(i <0 || j <0 || i > (TOTAL_COLUMNS-1) || j > (TOTAL_ROWS-1)){
				}
				else { 
					if (mineLocation[i][j]= true){
					numberOfBombs++;
					}
				} 
			}
		}
		return numberOfBombs;
	}
}