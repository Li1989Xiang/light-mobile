package mobile.test.base;

import mobile.navigator.ViewNavigator;
import mobile.page.base.AbstractPage;
import up.light.Setting;
import up.light.utils.ArgumentUtil;
import up.light.utils.LogUtil;

public class TestBase {

	public void recovery() {
		AbstractPage page = ViewNavigator.getCurrentPage();
		
		if (page != null) {
			LogUtil.log.debug("[Recovery] current page: " + page.getClass().getName());
			page.reset();
		} else {
			System.err.println("Current page is null");
		}
	}
	
	protected String lineSeparator(String str) {
		ArgumentUtil.notNull(str, "str must not be null");
		return str.replaceAll("[\\r\\n]{1,2}", Setting.LINE_SEPARATOR);
	}
}
