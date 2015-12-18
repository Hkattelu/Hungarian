/*
 * A class that can perform the Hungarian algorithm
 * on a set of data to solve an assignment problem.
 */

public class Hungarian {

	/**
	 * Performs Hungarian algorithm on a 2D matrix
	 * @param data : The matrix to perform the algorithm on
	 * @return A matrix with the same size as data which contains a 1
	 * on element i,j if row i has been assigned to column j. The number
	 * of 1's is equal to the minimum of number of rows and the number of
	 * columns in data. All other elements in the returned matrix will be 0.
	 * 
	 */
	public int[][] hungarian(int[][] data){
		int[][] matrix = data;
		int linesNeeded = Math.min(matrix.length,matrix[0].length);
		
		// Step 1: Subtract each row by the minimum element in that row.
		matrix = rowSubtract(matrix);
		matrix = transpose(matrix);
		
		// Step 2: Subtract each column by the minimum element in that column.
		// NOTE: Applying row subtract on the transpose is the same operation
		matrix = rowSubtract(matrix);
		matrix = transpose(matrix);
		
		// Step 3: Try to cover all 0's using linesNeeded lines. If this fails, pivot
		// if it works, then proceed to Step 5.
		
		
		return null;
		
	}
	
	/**
	 * Given a matrix and the assignment solution, compute the minimum
	 * cost of the solution.
	 * @param matrix The matrix of costs
	 * @param assignment The assignment solution
	 * @return The cost of the optimal assignments in matrix
	 */
	public int hungarian_minCost(int[][] matrix, int[][] assignment){
	  int cost = 0;
	  
	  for(int i=0;i<matrix.length;i++){
		  for(int j=0;j<matrix[0].length;j++){
			  //Add to the running if i was assigned to j in the
			  //assignment matrix.
			  if(assignment[i][j] == 1)
				  cost += matrix[i][j]; 
		  }
	  }
	  
	  return cost;
	}
	
	/**
	 * Finds and returns the minimum element in a specified array
	 * @param array The specified array
	 * @return the minimum element
	 */
	private int arrayMin(int[] array){
		//Return Integer.MAX if the array is empty.
		int min = Integer.MAX_VALUE;
		
		for(int i=0;i < array.length;i++){
			min = Math.min(array[i], min);
		}
		
		return min;
		
	}
	
	/**
	 * Subtract each row in a matrix by the lowest 
	 * element in that row. Corresponds to step 1 & 2 in 
	 * the Hungarian algorithm
	 * @param matrix : the matrix to operate on
	 * @return the matrix after subtractions
	 */
	private int[][] rowSubtract(int[][] matrix){
		
		// Subtract each row by the minimum element in that row.
		for(int i=0;i < matrix.length;i++){
			//Calculate the value of the minimum element in that array.
			int row_min = arrayMin(matrix[i]);
				
			for(int j=0; j < matrix[i].length;j++){
				// Subtract each element by the row minimum.
				matrix[i][j] = matrix[i][j] - row_min;
			}
		}
		return matrix;		
	}
	
	/**
	 * Returns the transpose of a matrix
	 * @param matrix : the matrix to transpose
	 * @return the transposed matrix
	 */
	private int[][] transpose(int[][] matrix){
		int[][] transposed = new int[matrix[0].length][matrix.length];
		
		for(int i=0;i < matrix.length;i++){
			for(int j=0;j < matrix[i].length;j++){
				transposed[j][i] = matrix[i][j];
			}
		}
		
		return transposed;
	}
	
	/**
	 * "Cover" up the matrix with the minimum number of lines
	 * needed in order to cover all of the 0's. A covered element
	 * is denoted by a 2 in its place. Corresponds to hungarian
	 * algorithm step 3.
	 * @param matrix the matrix to cover
	 * @return the covered matrix
	 */
	private int[][] hungarian_Cover(int[][] matrix){
		return null;
	}
	
	/**
	 * Return whether or not the matrix is fully covered. A
	 * matrix is fully covered if every element is a 2. Corresponds
	 * to hungarian algorithm step 3.
	 * @param matrix the matrix to check
	 * @return True if it is fully covered. False otherwise.
	 */
	private boolean hungarian_isCovered(int[][] matrix){
		return true;
	}
	
	/**
	 * "Pivot" the matrix if the solution has not been found. To do so,
	 *  we subtract all uncovered elements by the lowest element in their
	 *  respective columns. We also add this value onto each element that
	 *  has been "Double covered". Corresponds to step 4 in the hungarian algorithm.
	 * @param matrix The matrix to pivot
	 */
	private void hungarian_pivot(int[][] matrix){
		return;
	}
	
	/* Test Cases */
	public static void main(String[] args){
		
	}
}
