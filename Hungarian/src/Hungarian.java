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
	public int[][] hungarian_algorithm(int[][] data){
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
		int[][] matrixCover = hungarian_Cover(matrix);
		
		// Step 4: Pivot the matrix by subtracting each uncovered element by
		// the minimum uncovered element of that column. Also add that amount
		// to elements that have been "double covered". Go back to Step 3.
		while(!hungarian_isCovered(matrixCover)){
			matrix = hungarian_pivot(matrix,matrixCover);
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
		//Helper method for step 1
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
		//Helper method for step 1
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
		//Helper method
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
	private int[][] hungarian_Cover(int[][] matrix){
		
		char[][] cover = new char[matrix.length][matrix[0].length];
		int[] rowLines = new int[matrix.length];
		int[] colLines = new int[matrix[0].length];
		
		for(int i=0; i < cover.length; i++){
			for(int j=0; j < cover[i].length; j++){
				if(matrix[i][j] == 0 && rowLines[i] == 0 && colLines[j] == 0){
					cover[i][j] = 'x';
					rowLines[i] = 1;
					colLines[j] = 1;
				} else if(matrix[i][j] == 0){
					cover[i][j] = 'y';
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Return whether or not the matrix is fully covered. A
	 * matrix is fully covered if every element is a 2. Corresponds
	 * to hungarian algorithm step 3.
	 * @param matrix the matrix to check
	 * @return True if it is fully covered. False otherwise.
	 */
	public boolean hungarian_isCovered(int[][] matrix){
        for(int i = 0;i< matrix.length;i++){
        	for(int j = 0;j< matrix[i].length;j++){
        		if(matrix[i][j] == 0)
        			return false;
        	}
        }
        return true;

	}
	
	/**
	 * "Pivot" the matrix if the solution has not been found. To do so,
	 *  we subtract all uncovered elements by the lowest element in the
	 *  matrix. We also add this value onto each element that
	 *  has been "Double covered". Corresponds to step 4 in the hungarian algorithm.
	 * @param matrix The matrix to pivot
	 * @param cover The matrix representing how the lines covered the matrix elements
	 * @return the pivoted matrix
	 */
	public int[][] hungarian_pivot(int[][] matrix, int[][] cover){
		
		int minUncovered = Integer.MAX_VALUE;
		//Find the minimum uncovered element
		for(int i= 0;i < matrix.length;i++){
			for(int j=0;j < matrix[i].length;j++){
				if(cover[i][j] == 0)
					minUncovered = Math.min(minUncovered, matrix[i][j]);
			}
		}
		//Now subtract that element from all uncovered elements and add it to all double covered elements
		for(int i= 0;i < matrix.length;i++){
			for(int j=0;j < matrix[i].length;j++){
				if(cover[i][j] == 0){
					matrix[i][j] -= minUncovered;
				}else if(cover[i][j] == 2){
					matrix[i][j] += minUncovered;
				}
			}
		}
		
		return matrix;
		
	}
	
	/**
	 * "Selects" elements from the the fully pivoted matrix. Essentially this
	 *  returns a matrix with  a 1 in position i,j if worker i has been assigned
	 *  to job j. All other elements will be 0.
	 * @param pivotedMatrix The fully pivoted matrix
	 * @return The assignment matrix
	 */
	public int[][] hungarian_select(int[][] pivotedMatrix){
		
	   int numCols = pivotedMatrix.length;
	   int numRows = pivotedMatrix[0].length;
	   int[][] selection = new int[numCols][numRows];

	   int numToSelect = numCols;
	   int[] assignments = new int[numToSelect];
	  
	   for(int i=0;i<numToSelect;i++){
		   assignments[i] = -1;
	   }
		  
	   for(int i = 0;i < numToSelect;i++){
		   int index = containsOneZero(pivotedMatrix[i]);
	       
		   if (index >= 0){
			   boolean canSelect = true;
			   for(int j=0; j<assignments.length;j++){
				   if(assignments[j] == index)
					   canSelect = false;
			   }
			   if(canSelect){
			     selection[i][index]= 1;
			     assignments[i] = index;
			   }
			   
		   }else if (index == -1){
			   //Do nothing
		   }else if (index == -2){
			   index = getAvailableZeroIndex(assignments,pivotedMatrix[i]);
			   if(index == -1){
			   	   return null;
			   }else{
				   selection[i][index] = 1;
				   assignments[i] = index;
			   }
		   }
		   
	   }

	   return selection;
		
	}
	
	/**
	 * If the specified array contains a 0, return the index of the 
	 * Occurrence of a 0. Return -1 if there are no zeroes. Return -2 if there are multiple zeroes
	 * @param array the specified array
	 * @return -2, if multiple zeroes, -1 if no zeroes, otherwise return the index of the zero.
	 */
	private int containsOneZero(int[] array){
		//Helper method for select
		int index = -1;
		boolean zeroFound = false;
		
		for(int i = 0; i < array.length;i++){
			if(array[i] == 0){
				if(zeroFound)
				    return -2;
				else{
					index = i;
					zeroFound = true;
				}
			}
		}
		
		return index;
	}
	
	/**
	 * Return the index of the first zero in an array where you can
	 * specify certain indices as invalid zeroes.
	 * @param filledIndices specified invalid indices
	 * @param array the array to check
	 * @return the index of the first valid zero, -1 if no valid zeroes.
	 */
	private int getAvailableZeroIndex(int[] filledIndices, int[] array){
		//Helper method for select
		boolean isAvailable = true;
		
		for(int i=0;i < array.length;i++){
			
			if(array[i] == 0){
				isAvailable = true;
				
				for(int j=0; j < filledIndices.length;j++){
					if(filledIndices[j] == i){
						isAvailable = false;
					}
				}
				if(isAvailable)
					return i;
			}
			
		}
		
		return -1;
	}
	

}
