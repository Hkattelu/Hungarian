import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class HungarianTest {

	@Test
	public void step1_test() {
		Hungarian x = new Hungarian();
		int[][] test1 = {{1,2,3},{7,5,4},{3,8,5}};
		test1 = x.rowSubtract(test1);
		assertEquals(Arrays.deepToString(test1),"[[0, 1, 2], [3, 1, 0], [0, 5, 2]]");
	}
	
	@Test
	public void step2_test(){
		Hungarian x = new Hungarian();
		int[][] test2 = {{1,2,3},{7,5,4},{3,8,5}};
	    test2 = x.rowSubtract(test2);
		test2 = x.transpose(x.rowSubtract(x.transpose(test2)));
		assertEquals(Arrays.deepToString(test2),"[[0, 0, 2], [3, 0, 0], [0, 4, 2]]");
	}
	
	@Test
	public void minCost_test(){
		Hungarian x = new Hungarian();
		int[][] sol = {{1, 0, 0}, {0,1,0},{0,0,1}};
		int[][] orig = {{4,7,12},{20,36,51},{102,164,201}};
		assertEquals(x.hungarian_minCost(orig,sol),241);
		int[][] sol2 = {{0,1,1},{1,0,1},{0,0,0}};
		assertEquals(x.hungarian_minCost(orig, sol2), 90);
	}
	
	@Test
	public void pivot_test(){
		Hungarian x = new Hungarian();
		int[][] matrix = {{13,14,0,8},{40,0,12,40},{6,64,0,66},{0,1,90,0}};
		int[][] cover = {{0,0,1,0},{1,1,2,1},{0,0,1,0},{1,1,2,1}};
		int[][] pivoted = {{7,8,0,2},{40,0,18,40},{0,58,0,60},{0,1,96,0}};
		
		assertEquals(pivoted, x.hungarian_pivot(matrix,cover));
	}
	
	@Test
	public void selection_test1(){
		// Simple Test
		Hungarian x = new Hungarian();
		int[][] matrix = {{0,1,1,1},{1,0,1,1},{1,1,0,1},{1,1,1,0}};
		int[][] selection = {{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}};
		int[][] answer = new int[4][4];
		assertEquals(true,x.hungarian_select(matrix, answer, new int[4],0));
		System.out.println(Arrays.deepToString(answer));
		assertEquals(selection,answer);
	}
	
	@Test
	public void selection_test2(){
		// Switched up numbers,harder
		Hungarian x = new Hungarian();
		int[][] matrix = {{70,0,5,0},{30,25,0,0},{85,0,0,5},{0,10,0,10}};
		int[][] selection = {{0,0,0,1},{0,0,1,0},{0,1,0,0},{1,0,0,0}};
		int[][] answer = new int[4][4];
		assertEquals(true,x.hungarian_select(matrix, answer, new int[4],0));
		assertEquals(selection,answer);
	}
	
	@Test
	public void selection_test3(){
		// Switched up more, harder than 2
		Hungarian x = new Hungarian();
		int[][] matrix = {{42,30,7,0},{0,9,11,0},{21,251,0,1},{18,0,71,0}};
		int[][] selection = {{0,0,0,1},{1,0,0,0},{0,0,1,0},{0,1,0,0}};
		int[][] answer = new int[4][4];
		assertEquals(true,x.hungarian_select(matrix, answer, new int[4],0));
		assertEquals(selection,answer);
	}
	
	@Test
	public void selection_test4(){
		// # rows > # cols
		Hungarian x = new Hungarian();
		int[][] matrix = {{1,1,1,0},{0,1,1,0},{1,1,0,1}};
		int[][] selection = {{0,0,0,1},{1,0,0,0},{0,0,1,0}};
		int[][] answer = new int[3][4];
		assertEquals(true,x.hungarian_select(matrix, new int[3][4], new int[3],0));
		assertEquals(selection,answer);
	}
	
	@Test
	public void selection_test5(){
		// # cols > # rows
		Hungarian x = new Hungarian();
		int[][] matrix = {{1,0,1},{0,1,1},{1,1,0},{1,0,1}};
		int[][] selection = {{0,1,0},{1,0,0},{0,0,1},{0,0,0}};
		int[][] answer = new int[4][3];
		assertEquals(true,x.hungarian_select(matrix, new int[4][3], new int[4],0));
		assertEquals(selection,answer);
	}

	@Test
	public void cover_test1(){
		// One row, One column
		Hungarian x = new Hungarian();
		int[][] matrix= new int[][]{{3,0,3},{0,0,0},{3,0,3}};
		int[][] cover = new int[][]{{0,1,0},{1,2,1},{0,1,0}};
		assertEquals(cover, x.hungarian_Cover(matrix).cover);
	}
	
	@Test
	public void cover_test2(){

		// 1 Row, 2 columns
		Hungarian x = new Hungarian();
		int[][] matrix= new int[][]{{0,0,1},{0,0,0},{0,0,2}};
		int[][] cover = new int[][]{{1,1,0},{2,2,1},{1,1,0}};
        assertEquals(cover, x.hungarian_Cover(matrix).cover);
	}
	
	@Test
	public void cover_test3(){

		// 2 Rows, 1 column
		Hungarian x = new Hungarian();
		int[][] matrix= new int[][]{{0,0,1},{0,0,0},{7,0,2}};
		int[][] cover = new int[][]{{1,2,1},{1,2,1},{0,1,0}};
		assertEquals(cover, x.hungarian_Cover(matrix).cover);
	}
	
	@Test
	public void lineDirection_test1(){
		Hungarian x = new Hungarian();
		int[][] matrix = new int[][]{{1,0,2},{3,0,0},{4,0,6}};
		assertEquals(-1,x.getLineDirection(matrix,1,1));
	}
	
	@Test
	public void lineDirection_test2(){
		Hungarian x = new Hungarian();
		int[][] matrix = new int[][]{{1,3,2},{7,4,9},{4,3,6}};
		assertEquals(0,x.getLineDirection(matrix,1,1));
	}
	
	@Test
	public void lineDirection_test3(){
		Hungarian x = new Hungarian();
		int[][] matrix = new int[][]{{1,4,2},{0,0,0},{4,0,6}};
		assertEquals(1,x.getLineDirection(matrix,1,1));
	}
	
	@Test
	public void hungarian_fulltest1(){
		
		Hungarian x = new Hungarian();
		int[][] matrix = new int[][]{{90,75,75,80},{35,85,55,65},{125,95,90,105},{45,110,95,115}};
		int[][] solution = new int[][]{{0,0,0,1},{0,0,1,0},{0,1,0,0},{1,0,0,0}};
		int[][] algorithm = x.hungarian_algorithm(matrix);
		assertEquals(solution,algorithm);
		int cost = x.hungarian_minCost(matrix,algorithm);
		assertEquals(275,cost);
		
		
	}
	
	@Test
	public void hungarian_fulltest2(){
		
		Hungarian x = new Hungarian();
		int[][] matrix = new int[][]{{82,83,69,92},{77,37,49,92},{11,69,5,86},{8,9,98,23}};
		int[][] solution = new int[][]{{0,0,1,0},{0,1,0,0},{1,0,0,0},{0,0,0,1}};
		int[][] algorithm = x.hungarian_algorithm(matrix);
		assertEquals(solution,algorithm);
		int cost = x.hungarian_minCost(matrix,algorithm);
		assertEquals(140,cost);
	
	}
}
