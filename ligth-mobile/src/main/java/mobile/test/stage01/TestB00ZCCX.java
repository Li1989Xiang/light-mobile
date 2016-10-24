package mobile.test.stage01;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageZCCX;
import mobile.page.base.PageManager;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;
import up.light.assertutil.AssertUtil;

/**
 * 资产查询测试
 */
public class TestB00ZCCX extends TestBase {
	PageZCCX mPage = PageManager.getPage(PageZCCX.class);

	@BeforeClass
	public void before() {
		ViewNavigator.navigate("资产查询", mPage);
	}
	
	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testZCCX(Map<String, String> param) {
		//切换币种
		mPage.doSwitchTo(param.get("资金类型"));
		//获取资金信息
		Map<String, String> m = mPage.doGetNumbers();
		//获取验证信息
		String expectKY = param.get("验证1");
		String expectKQ = param.get("验证2");
		String ky = "可用", kq = "可取";
		//校验可用、可取
		AssertUtil.assertEquals(expectKY, ky + ":" + m.get(ky));
		AssertUtil.assertEquals(expectKQ, kq + ":" + m.get(kq));
	}
}
