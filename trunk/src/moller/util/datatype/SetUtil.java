package moller.util.datatype;

import java.util.LinkedHashSet;
import java.util.Set;

public class SetUtil {
	
	public static Set<Integer> getContinuousSet(int start, int end) {
		Set<Integer> set = new LinkedHashSet<Integer>();
		for (int i = start; i <= end; i++) {
			set.add(i);
		}
		return set;
	}
}
