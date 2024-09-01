import java.util.HashSet;
import java.util.Set;

public class Exam_2 {
	public static void main(String args[]) {
		String input = "The quick brown fox jumps over he lazy dog"; // Hello world
		input = input.toLowerCase();
		String answer = "";
		Set<Character> alphabatic = new HashSet<Character>();
		for (char i = 'a'; i <= 'z'; i++) {
			alphabatic.add(i);
		}
		Set<Character> alphabatic2 = new HashSet<>(alphabatic);
		int fontIndex, lastIndex = 0;
		for (char i = 0; i < input.length(); i++) {
			alphabatic.remove(input.charAt(i));
			if (input.charAt(i) == ' ' || i == input.length() - 1) {
				fontIndex = lastIndex;
				lastIndex = i + 1;
				String newAnswer = input.substring(fontIndex, lastIndex).trim();
				if (newAnswer.length() >= answer.length())
					answer = newAnswer;
			}

		}
		if (!alphabatic.isEmpty())
			answer = "Not a Pangram";
		System.out.print(answer);
	}

}