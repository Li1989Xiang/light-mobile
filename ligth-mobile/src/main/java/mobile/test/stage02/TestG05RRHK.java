package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageRRHK;
import mobile.page.base.PageManager;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;

/**
 * 融资融券还款测试
 */
public class TestG05RRHK extends TestBase {
	private PageRRHK mPage = PageManager.getPage(PageRRHK.class);

	@BeforeClass
	public void before() {
		ViewNavigator.navigate("还款", mPage);
	}
	
	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testRRHK(Map<String, String> param) {
		// TODO 未完成
	}
}
