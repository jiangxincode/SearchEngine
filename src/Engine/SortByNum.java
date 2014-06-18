package Engine;

import java.util.Comparator;

public class SortByNum implements Comparator {
	// class SortByAge implements Comparator {
	public int compare(Object o1, Object o2) {
		ResultModel s1 = (ResultModel) o1;
		ResultModel s2 = (ResultModel) o2;
		if (s1.getWordV() > s2.getWordV())
			return 1;
		return 0;
	}
}
