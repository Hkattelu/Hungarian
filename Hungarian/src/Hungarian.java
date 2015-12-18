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
		
		// Subtract each row by the minimum element in that row.
		matrix = rowSubtract(matrix);
		matrix = transpose(matrix);
		
		// Subtract each column by the minimum element in that column.
		matrix = rowSubtract(matrix);
		matrix = transpose(matrix);
		
		
		
		return null;
		
	}
	
	/**
	 * Finds and returns the minimum element in a specified array
	 * @param array The specified array
	 * @return the minimum element
	 */
	private int arrayMin(int[] array){
		
		int min = Integer.MAX_VALUE;
		
		for(int i=0;i < array.length;i++){
			min = Math.min(array[i], min);
		}
		
		return min;
		
	}
	
	/**
	 * Subtract each row in a matrix by the lowest 
	 * element in that row. Corresponds to step 1 in 
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
}
