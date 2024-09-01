import java.util.ArrayList;
import java.util.Arrays;

public class Exam_1 {
	public static void main(String args[]) {
		int array[] = { 2, 3, 5, 7, 11 };
		int lenArray = array.length;
		array[0] = 11;
		int value = 10;
		ArrayList<ArrayList<Integer>> answer = new ArrayList<ArrayList<Integer>>();  
		for (int i = 0; i < lenArray; i++) {
			for (int j = i; j < lenArray; j++) {
				if (array[i] + array[j] == value) {
					answer.add(new ArrayList<Integer>(Arrays.asList(array[i],array[j]))) ;   
				}
			}
		} 
		System.out.println(answer);
	}
 
}