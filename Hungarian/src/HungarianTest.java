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
	public void step3_test(){
		Hungarian x = new Hungarian();
		int[][] test3 = {{1,2,3},{7,5,4},{3,8,5}};
	    test3 = x.rowSubtract(test3);
		test3 = x.transpose(x.rowSubtract(x.transpose(test3)));
		
		
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

}
