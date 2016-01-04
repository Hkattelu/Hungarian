/*
 * A class that can perform the Hungarian algorithm
 * on a set of data to solve an assignment problem.
 * 
 * @Author: Himanshu Kattelu
 * @Version: 12/19/2015
 */

import java.util.Arrays;

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
		
		// Step 2: Subtract each column by the minimum element in that column.
		// NOTE: Applying row subtract on the transpose is the same operation
		matrix = transpose(matrix);
		matrix = rowSubtract(matrix);
		matrix = transpose(matrix);
		
		// Step 3: Try to cover all 0's using linesNeeded lines. If this fails, pivot
		// if it works, then proceed to Step 5.
		char[][] matrixCover = hungarian_Cover(matrix);
		
		// Step 4: Pivot the matrix by subtracting each uncovered element by
		// the minimum uncovered element of that column. Also add that amount
		// to elements that have been "double covered". Go back to Step 3.
		while(!hungarian_isCovered(matrixCover)){
			hungarian_pivot(matrix);
			matrixCover = hungarian_Cover(matrix);
		}
		
		// Step 5: The matrix has now been "solved". Now we select a 0 from
		// Each row such that each row and each column contains only one zero.
		// Replace those zeroes with 1's and turn all other elements to 0's.
		return hungarian_select(matrix);

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
	 * element in that row. 
	 * @param matrix : the matrix to operate on
	 * @return the matrix after subtractions
	 */
	public int[][] rowSubtract(int[][] matrix){
		
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
	public int[][] transpose(int[][] matrix){
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
	 * is denoted by an x in its place. Double covered elements
	 * are denoted by a y. Uncovered elements are denoted by z's.
	 * Corresponds to hungarian algorithm step 3.
	 * @param matrix the matrix to cover
	 * @return A matrix representing which elements have been covered.
	 */
	private char[][] hungarian_Cover(int[][] matrix){
		char[][] cover = new char[matrix.length][matrix[0].length];
		for(int i = 0;i< matrix.length;i++){
			for(int j = 0;j< matrix[i].length;j++){
				if (matrix[i][j] == 0){
					if(cover[i][j] == 'x'){
						//If the element is covered, double cover it
						cover[i][j] = 'y';
					}else if(cover[i][j] == 'y'){
					   // Do nothing if it is already double cover
					   // Although this should'nt happen in the first place
					} else{
					   // Cover all elements in this row and column.
					   for(int k = 0;k < matrix.length;k++){
						   if(cover[k][j] != 'y')
						    cover[k][j] = 'x';
					   }
					   for(int k = 0;k < matrix[i].length;k++){
						   if(cover[k][j] != 'y')
						    cover[i][k] = 'x';
					   }
					}
				}else{
				  if (cover[i][j] == 'x' || cover[i][j] == 'y'){
				  }else{
					  cover[i][j] = 'z';  
				  }
									  
				}
					
			}
		}
		return cover;
	}
	
	/**
	 * Return whether or not the matrix is fully covered. A
	 * matrix is fully covered if every element is a 2. Corresponds
	 * to hungarian algorithm step 3.
	 * @param matrix the matrix to check
	 * @return True if it is fully covered. False otherwise.
	 */
	public boolean hungarian_isCovered(char[][] matrix){
        for(int i = 0;i< matrix.length;i++){
        	for(int j = 0;j< matrix[i].length;j++){
        		if(matrix[i][j] == 'z')
        			return false;
        	}
        }
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
	
	/**
	 * "Selects" elements from the the fully pivoted matrix. Essentially this
	 *  returns a matrix with  a 1 in position i,j if worker i has been assigned
	 *  to job j. All other elements will be 0.
	 * @param coveredMatrix The fully pivoted matrix
	 * @return The assignment matrix
	 */
	private int[][] hungarian_select(int[][] coveredMatrix){
		return null;
	}
	
    /*
	public static void main(String[] args){
		Hungarian x = new Hungarian();
		int[][] test2 = {{1,2,3},{7,5,4},{3,8,5}};
	    test2 = x.rowSubtract(test2);
		test2 = x.transpose(x.rowSubtract(x.transpose(test2)));
		System.out.println(Arrays.deepToString(test2));
		System.out.println(Arrays.deepToString(x.hungarian_Cover(test2)));
	}
	*/
}
