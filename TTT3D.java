import static java.lang.System.out;

import java.util.*;

public class TTT3D 
{
	public static void main(String[] args)
	{
		Grid grid = new Grid();
		grid.startGame();
	}
}   

class Grid {

    public int SIZE = 4;
    public int[][][] board = new int[SIZE][SIZE][SIZE];
    public static enum GameStatus { ILLEGAL, ONGOING, TIE, USER_WON, COMPUTER_WON; }

    public void startGame()
    {
    	out.println("Welcome to 3D Tic Tac Toe!\n");
        Scanner io = new Scanner(System.in);

        out.println("Who's going to start the game? (X: you, O: computer)");

        String cPlayer = "";
        while(!"X".equals(cPlayer) && !"O".equals(cPlayer))
        {
        	cPlayer = io.next();
        }

        out.println("Initializing The Grid...\n");
        printBoard();
        GameStatus status = GameStatus.ONGOING;
        int round = 0;
        String starter = cPlayer;

        
        while (status == GameStatus.ONGOING) 
        {
        	if("X".equals(cPlayer))
        	{
        		if(starter.equals(cPlayer))
        			out.printf("\n|---------------- Round %d ----------------|\n", ++round);

        		int x, y, z;
        		x = y = z = -1;
        		String errInt = "Your entry was invalid, please enter an integer (0-3).";
        		if(!starter.equals(cPlayer))
        			out.println("=================================");
        		out.println("   Your turn: ");
	            out.println("=================================");
	            out.print("Enter a level: ");
	            /*do
				{
					String s = io.nextLine().trim(); // trim so that numbers with whitespace are valid

					if (s.matches("[0-9]")) { // if the string contains only numbers 0-9
						x = Integer.parseInt(s);
						if(x >= 0 && x <= 3)
							break;
					}
					else
						System.out.println(errInt);
				} while(x < 0 || x > 3 || !io.hasNextLine());*/

	            x = io.nextInt();
	            out.print("Enter a row: ");
	            y = io.nextInt();
	            out.print("Enter a column: ");
	            z = io.nextInt();
	            out.println("=================================");

            	status = setPiece(x, y, z, 1);

            	if (status == GameStatus.ONGOING) 
            	{
            		printBoard();
            		cPlayer = "O";
            	}
            	else if (status == GameStatus.USER_WON)
            	{
            		printBoard();
            	}
            	else if(status == GameStatus.ILLEGAL) 
            	{
            		round--;
	            	out.println("\n!!!!!-----ERROR: Spot already taken; Choose another one.-----!!!!!");
	            	status = GameStatus.ONGOING;
	            }
			}
			if("O".equals(cPlayer)) {
				if(starter.equals(cPlayer))
        		out.printf("\n|---------------- Round %d ----------------|\n", ++round);

            	status = makeMove();
            	printBoard();
            	cPlayer = "X";
			}
        }

        if (status == GameStatus.USER_WON)
        	out.println("\nYOU Wins!");
        else if (status == GameStatus.COMPUTER_WON)
        	out.println("\nComputer Wins!");
        
    }

    public GameStatus setPiece(int x, int y, int z, int player)
    {
    	GameStatus status = GameStatus.ILLEGAL;

        if (board[x][y][z] == 0)
        {
            board[x][y][z] = player;
            
            if (checkWin()) 
            {
            	if(player == 1)
            		status = GameStatus.USER_WON;
            	
            	else
            		status = GameStatus.COMPUTER_WON;
            }
            else 
            	status = GameStatus.ONGOING;
        }

        return status;
    }

    public GameStatus makeMove()
	{
		GameStatus status = GameStatus.ILLEGAL;
        while (status == GameStatus.ILLEGAL)
        {
            int x = randInt(0, SIZE - 1), y = randInt(0, SIZE - 1), z = randInt(0, SIZE - 1);
            
            status = setPiece(x,y,z, 2);
            if (status == GameStatus.ONGOING || status == GameStatus.COMPUTER_WON)
            {
            	out.println("=================================");
                System.out.printf("Computer played on level %d: (%d,%d)%n", x, y, z);
                out.println("=================================");
            }
        }
        return status;
    }
    
    public int randInt(int min, int max) {
        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

	private boolean checkWin()
	{
		/* winning positions: 40 */
		//Scenario 1 "Statndard"
		if(checkLayer(0) || checkLayer(1) || checkLayer(2) || checkLayer(3)) 
			return true;
		
		/* winning positions: 16 */
		//Scenario 2 "straight up / down"
		int i, j;
		for(i = 0; i < SIZE; i++){
			for(j = 0; j < SIZE; j++){
				if(board[0][i][j] == board[1][i][j] && board[0][i][j] == board[2][i][j] && board[0][i][j] == board[3][i][j]
					&& board[0][i][j] != 0)
					return true;
			}
		}
		
		/* winning positions: 4 */
		//Scenario 3 "Straight Staircases"
		//First by row
		for(i = 0; i < SIZE; i++){
			if(board[0][i][0] == board[1][i][1] && board[0][i][0] == board[2][i][2] && board[0][i][0] == board[3][i][3]
				&& board[0][i][0] != 0)
				return true;
		}
		/* winning positions: 4 */
		for(i = 0; i < SIZE; i++){
			if(board[0][i][3] == board[1][i][2] && board[0][i][3] == board[2][i][1] && board[0][i][3] == board[3][i][0]
				&& board[0][i][3] != 0)
				return true;
		}
		
		/* winning positions: 4 */
		//Now by column
		for(j = 0; j < SIZE; j++){
			if(board[0][0][j] == board[1][1][j] && board[0][0][j] == board[2][2][j] && board[0][0][j] == board[3][3][j]
				&& board[0][0][j] != 0)
				return true;
		}
		/* winning positions: 4 */
		for(j = 0; j < SIZE; j++){
			if(board[0][3][j] == board[1][2][j] && board[0][3][j] == board[2][1][j] && board[0][3][j] == board[3][0][j]
				&& board[0][3][j] != 0)
				return true;
		}
		
		/* winning positions: 4 */
		//Scenario 4 "The Diagonal Staircase"
		//Top left to bottom right
		if(board[0][0][0] == board[1][1][1] && board[0][0][0] == board[2][2][2] && board[0][0][0] == board[3][3][3]
			&& board[0][0][0] != 0)
			return true;
		

		if(board[0][3][3] == board[1][2][2] && board[0][3][3] == board[2][1][1] && board[0][3][3] == board[3][0][0]
			&& board[0][3][3] != 0)
			return true;
		

		//Bottom Left to Top Right
		if(board[0][3][0] == board[1][2][1] && board[0][3][0] == board[2][1][2] && board[0][3][0] == board[3][0][3]
			&& board[0][3][0] != 0)
			return true;
		

		if(board[0][0][3] == board[1][1][2] && board[0][0][3] == board[2][2][1] && board[0][0][3] == board[3][3][0]
			&& board[0][0][3] != 0)
			return true;
		
		
		//No Win
		return false;
	}

	public boolean checkLayer(int x)
	{
		//Check by rows
		for(int i = 0; i < SIZE; i++){
			if(board[x][i][0] == board[x][i][1] && board[x][i][0] == board[x][i][2] && board[x][i][0] == board[x][i][3] && board[x][i][0] != 0) 
				return true;
		}
		
		//CHeck Columns
		for(int j = 0; j < SIZE; j++){
			if(board[x][0][j] == board[x][1][j] && board[x][0][j] == board[x][2][j] && board[x][0][j] == board[x][3][j] && board[x][0][j] != 0) 
				return true;
		}
		
		//Top left to bottom right diagonal
		if(board[x][0][0] == board[x][1][1] && board[x][0][0] == board[x][2][2] && board[x][0][0] == board[x][3][3] && board[x][0][0] != 0) 
			return true;
		
		
		//Bottom left to top right
		if(board[x][3][0] == board[x][2][1] && board[x][3][0] == board[x][1][2] && board[x][3][0] == board[x][0][3] && board[x][3][0] != 0) 
			return true;
		
		
		return false;
	}

	public void printBoard()
	{
		for(int i = -1; i < 4; i++){
			if(i == -1)
				out.print("        ");
			else
				out.print("   " + i);
		}
		out.println();

		for(int i = 0; i < 4; i++)
		{
			out.print((i == 0) ? "         ________________" : "|       |________|_______");
			if(i != 0)
				out.print("|");
			out.print(" << LEVEL: " + i + "\n");

			for(int j = 0; j < 4; j++)
			{
				// out.print(j + " ");
				if(i == 0)
				{
					if(j == 0) out.print(j + " ----> ");
					else if(j == 1) out.print(j + " --> ");
					else if(j == 2) out.print(j + " ->");
					else out.print(j+ " ");
				}
				else
				{
					if(j == 0) out.print("|       ");
					else if(j == 1) out.print("|     ");
					else if(j == 2) out.print("|   ");
					else out.print("| ");
				}
				out.print("/");

				for(int k = 0; k < 4; k++)
				{
					if(board[i][j][k] == 1)
						out.print(" X /");
                	else if(board[i][j][k] == 2)
                		out.print(" O /");
                	else
                		out.print(" . /");
				}

				if(i != 3)
				{
					if(j == 0) out.print("|");
					else if(j == 1) out.print("  |");
					else if(j == 2) out.print("    |");
					else out.print("      |");
				}
				out.println();
				out.print((i == 0) ? " " : "|");

				if(j == 0) out.print("      ");
				else if(j == 1) out.print("    ");
				else if(j == 2) out.print( "  ");
				else out.print("");
				out.print("/");
				for(int k = 0; k < 4; k++)
				{
					out.print("___/");
				}
				if(i != 3)
				{
					if(j == 0) out.print(" |");
					else if(j == 1) out.print("   |");
					else if(j == 2) out.print("     |");
					else out.print("       |");
				}
				out.println();
			}
		}
	}
}