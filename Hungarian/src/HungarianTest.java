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
	public void isCovered_test(){
		Hungarian x = new Hungarian();
		char[][] cover = {{'x','y','x'},{'x','y','y'},{'x','x','y'}};
		//assertEquals(x.hungarian_isCovered(cover),true);
		cover[2][2] = 'z';
		//assertEquals(x.hungarian_isCovered(cover),false);
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
		assertEquals(selection,x.hungarian_select(matrix));
	}
	
	@Test
	public void selection_test2(){
		// Switched up numbers,harder
		Hungarian x = new Hungarian();
		int[][] matrix = {{0,1,1,1},{0,1,1,0},{1,1,0,1},{1,0,0,0}};
		int[][] selection = {{1,0,0,0},{0,0,0,1},{0,0,1,0},{0,1,0,0}};
		assertEquals(selection,x.hungarian_select(matrix));
	}
	
	@Test
	public void selection_test3(){
		// Switched up more, harder than 2
		Hungarian x = new Hungarian();
		int[][] matrix = {{1,1,1,0},{0,1,1,0},{1,1,0,1},{1,0,1,0}};
		int[][] selection = {{0,0,0,1},{1,0,0,0},{0,0,1,0},{0,1,0,0}};
		assertEquals(selection,x.hungarian_select(matrix));
	}
	
	@Test
	public void selection_test4(){
		// # rows > # cols
		Hungarian x = new Hungarian();
		int[][] matrix = {{1,1,1,0},{0,1,1,0},{1,1,0,1}};
		int[][] selection = {{0,0,0,1},{1,0,0,0},{0,0,1,0}};
      	assertEquals(selection,x.hungarian_select(matrix));
	}
	
	@Test
	public void selection_test5(){
		// # cols > # rows
		Hungarian x = new Hungarian();
		int[][] matrix = {{1,0,1},{0,1,1},{1,1,0},{1,0,1}};
		int[][] selection = {{0,1,0},{1,0,0},{0,0,1},{0,0,0}};
		assertEquals(selection,x.hungarian_select(matrix));
	}

}
