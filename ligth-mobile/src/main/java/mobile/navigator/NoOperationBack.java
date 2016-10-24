package mobile.navigator;

import java.util.ArrayList;
import java.util.List;

class NoOperationBack {
	private static List<String> nameList = new ArrayList<>();
	
	static {
		nameList.add("ROOT");
		nameList.add("交易");
	}

	public static boolean isNoOperation(String name) {
		return nameList.contains(name);
	}
}
