package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageHKZCCX;
import mobile.page.base.PageManager;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;
import up.light.assertutil.AssertUtil;

/**
 * 港股通资产查询测试
 */
public class TestE00HKZCCX extends TestBase {
	private PageHKZCCX mPage = PageManager.getPage(PageHKZCCX.class);

	@BeforeClass
	public void before() {
		ViewNavigator.navigate("港股通资产查询", mPage);
	}
	
	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testHKZCCX(Map<String, String> param) {
		Map<String, String> map = mPage.doGetVaules();
		String actualKQ = map.get("可取");
		String actualKY = map.get("可用");
		AssertUtil.assertEquals(param.get("验证1"), actualKQ);
		AssertUtil.assertEquals(param.get("验证2"), actualKY);
	}
}
