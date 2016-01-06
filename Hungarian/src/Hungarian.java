/*
 * A class that can perform the Hungarian algorithm
 * on a set of data to solve an assignment problem.
 * 
 * @Author: Himanshu Kattelu
 * @Version: 12/19/2015
 */

import java.util.Arrays;

public class Hungarian {

	/*
	 * A class whose objects represent matrices that have been covered
	 * in the fashion of the hungarian method's 3rd step.
	 */
	public static class coveredMatrix{
	   
		/*
		 * A matrix representing lines covering a matrix.A 1 means
		 * there is a single line going through that element and a
		 * 2 means there are two lines going through it. A 0 means
		 * there are no lines going through.
		 */
		public int[][] cover;
		
		/** The minimum number of lines needed to cover this matrix **/
		public int lineCover;
		
		public coveredMatrix(int[][] matrix, int lines){
			lineCover = lines;
			cover = matrix;
		}
	}
	
	/**
	 * Performs Hungarian algorithm on a 2D matrix
	 * @param data : The matrix to perform the algorithm on
	 * @return A matrix with the same size as data which contains a 1
	 * on element i,j if row i has been assigned to column j. The number
	 * of 1's is equal to the minimum of number of rows and the number of
	 * columns in data. All other elements in the returned matrix will be 0.
	 * 
	 */
	public static int[][] hungarian_algorithm(int[][] data){
		
		//There was some error where only the reference was being copied
		//and not the actual matrix, so we do it manually.
		int[][] mat = new int[data.length][data[0].length];
		
		for(int i = 0; i < mat.length; i++){
			for(int j = 0; j < mat[0].length; j++){
				mat[i][j]= data[i][j];
			}
		}
		
		int linesNeeded = Math.min(mat.length,mat[0].length);
		
		// Step 1: Subtract each row by the minimum element in that row.
		mat = rowSubtract(mat);
		
		// Step 2: Subtract each column by the minimum element in that column.
		// NOTE: Applying row subtract on the transpose is the same operation
		mat = transpose(mat);
		mat = rowSubtract(mat);
		mat = transpose(mat);

		// Step 3: Try to cover all 0's using linesNeeded lines. If this fails, pivot
		// if it works, then proceed to Step 5.
		coveredMatrix matrixCover = hungarian_Cover(mat);
 
		// Step 4: Pivot the matrix by subtracting each uncovered element by
		// the minimum uncovered element of that column. Also add that amount
		// to elements that have been "double covered". Go back to Step 3.
		while(!hungarian_isFullyCovered(matrixCover)){
			mat = hungarian_pivot(mat,matrixCover.cover);
			matrixCover = hungarian_Cover(mat);
		}
		// Step 5: The matrix has now been "solved". Now we select a 0 from
		// Each row such that each row and each column contains only one zero.
		// Replace those zeroes with 1's and turn all other elements to 0's.
		int[][] selection = new int[mat.length][mat[0].length];
		System.out.println(Arrays.deepToString(mat));
		hungarian_select(mat,selection,new int[mat[0].length],0);
		return selection;

	}
	
	/**
	 * Given a matrix and the assignment solution, compute the minimum
	 * cost of the solution.
	 * @param matrix The matrix of costs
	 * @param assignment The assignment solution
	 * @return The cost of the optimal assignments in matrix
	 */
	public static int hungarian_minCost(int[][] matrix, int[][] assignment){
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
	public static int arrayMin(int[] array){
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
	public static int[][] rowSubtract(int[][] matrix){
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
	public static int[][] transpose(int[][] matrix){
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
	public static coveredMatrix hungarian_Cover(int[][] matrix){
		
		int[][] cover = new int[matrix.length][matrix[0].length];
		int[] rowsCovered = new int[matrix.length];
		int[] colsCovered = new int[matrix[0].length];
		
		for(int i=0; i < cover.length; i++){
			for(int j=0; j < cover[i].length; j++){
			  if(matrix[i][j] == 0){	
				int direction = getLineDirection(matrix,i,j);
				if(direction == 1){
					
					if(rowsCovered[i] == 0){
					   coverRow(cover[i]);
					   rowsCovered[i]++;
					}
					
				} else if (direction == -1){
					
					if(colsCovered[j] == 0){
					   coverCol(cover,j);	
					   colsCovered[j]++;
					}
					
				} 					
			  }
			}
		}
		int totalLines = 0;
		for(int i = 0;i < rowsCovered.length;i++){
			if(rowsCovered[i]==1)
				totalLines++;
		}
		for(int i = 0;i < colsCovered.length;i++){
			if(colsCovered[i]==1)
				totalLines++;
		}


		return new coveredMatrix(cover,totalLines);
	}
	
	/**
	 * Cover an array. If an element is already covered (1),
	 * double cover it (2).
	 * @param array The array to cover
	 */
	public static void coverRow(int[] array){
		//Helper for hungarian_Cover
		for(int i = 0;i < array.length;i++){
			if(array[i] == 0)
				array[i] = 1;
			else if(array[i] == 1)
				array[i] = 2;
		}
		
	}
	
	/**
	 * Cover a specified column of a 2d array. If an element
	 * is already covered (1), double cover it (2).
	 * @param matrix the specified 2d array
	 * @param col the specified column
	 */
	public static void coverCol(int[][] matrix, int col){
		//Helper for hungarian_Cover
		for(int i=0; i < matrix[0].length; i++){
			if(matrix[i][col] == 0)
				matrix[i][col] = 1;
			else if(matrix[i][col] == 1)
				matrix[i][col] = 2;
		}
	}
	
	/**
	 * Compare the number of zeroes in the column of an element and
	 * the number of zeroes in the row that element.
	 * @param matrix the matrix to check
	 * @param row the row of the element to check
	 * @param col the column of the element to check
	 * @return 0 if there are no zeroes in the row or column
	 *         1 if there are more zeroes in the row and there are some( or and equal number ) in the column
	 *         -1 if there are more zeroes in the column and there are some in the row
	 */
	public static int getLineDirection(int[][] matrix, int row, int col){
		//Helper for hungarian_Cover
		int rowZeroes = 0;
		int colZeroes = 0;
		
		for(int i = 0; i < matrix.length; i++){
			if(matrix[i][col] == 0)
					colZeroes++;
		}
		
        for(int i = 0; i < matrix[0].length; i++){
			if(matrix[row][i] == 0)
				rowZeroes++;
		}
        
        if(rowZeroes == 0 && colZeroes == 0)
        	return 0;
        if(rowZeroes >= colZeroes){
            return 1;        				
        }else{
        	return -1;
        }
        	
        
	}
	
	/**
	 * Return whether or not the matrix is fully covered. A
	 * matrix is fully covered if every element is a 2. Corresponds
	 * to hungarian algorithm step 3.
	 * @param matrix the matrix to check
	 * @return True if it is fully covered. False otherwise.
	 */
	public static boolean hungarian_isFullyCovered(coveredMatrix matrix){
		
        return matrix.lineCover == Math.min(matrix.cover.length, matrix.cover[0].length);

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
	public static int[][] hungarian_pivot(int[][] matrix, int[][] cover){

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
					matrix[i][j] -= minUncovered ;
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
	 * @param selection Use an empty matrix for this.
	 * @param row Always use 0 for this. Recursion is used.
	 * @param filledRows use empty array for this. 
	 * @return If an element has been selected.
	 */
	public static boolean hungarian_select(int[][] pivotedMatrix, int[][] selection, int[] filledRows, int row){
		
       if (row == pivotedMatrix.length)
    	   return true;
       
	   for(int i = 0; i < pivotedMatrix[row].length; i++){
		   if(pivotedMatrix[row][i] == 0 && filledRows[i] == 0){
			   selection[row][i] = 1;
			   filledRows[i] = 1;
			   if(hungarian_select(pivotedMatrix,selection,filledRows,row+1))
				   return true;
			   selection[row][i] = 0;
			   filledRows[i] = 0;
					   
		   }
	   }
	   
	   return false;
		
	}
	
	/**
	 * If the specified array contains a 0, return the index of the 
	 * Occurrence of a 0. Return -1 if there are no zeroes. Return -2 if there are multiple zeroes
	 * @param array the specified array
	 * @return -2, if multiple zeroes, -1 if no zeroes, otherwise return the index of the zero.
	 */
	public static int containsOneZero(int[] array){
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
	public static int getAvailableZeroIndex(int[] filledIndices, int[] array){
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
