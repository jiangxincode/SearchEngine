/**
 * 描述：实现ResultModel的Comparator接口
 * 作者：蒋鑫
**/
package Engine;

import java.util.Comparator;

public class sortByWordNum implements Comparator<Object> {
	public int compare(Object object1, Object object2) {
		ResultModel s1 = (ResultModel) object1;
		ResultModel s2 = (ResultModel) object2;
		if (s1.getWordV() > s2.getWordV())
			return 1;
		return 0;
	}
}
