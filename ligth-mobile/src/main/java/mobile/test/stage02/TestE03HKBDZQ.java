package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageHKBDZQ;
import mobile.page.base.PageManager;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;
import up.light.assertutil.AssertUtil;

/**
 * 港股通标的证券测试
 */
public class TestE03HKBDZQ extends TestBase {
	private PageHKBDZQ mPage = PageManager.getPage(PageHKBDZQ.class);

	@BeforeClass
	public void before1() {
		ViewNavigator.navigate("标的证券", mPage);
	}
	
	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testHKBDZQ(Map<String, String> param) {
		mPage.doInitializeMap();
		AssertUtil.assertEquals(param.get("名称"), mPage.doGetName(param.get("代码")));
	}
}
