package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageZJDD;
import mobile.page.base.PageManager;
import mobile.page.module.Alert;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;
import up.light.assertutil.AssertUtil;
import up.light.wait.WaitUtil;

/**
 * 一键归集测试
 */
public class TestD03YJGJ extends TestBase {
	private PageZJDD mPage = PageManager.getPage(PageZJDD.class);

	@BeforeClass
	public void before() {
		ViewNavigator.navigate("资金调度", mPage);
	}
	
	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testYJGJ(Map<String, String> param) {
		//获取所有辅账户总金额
		float expectTotal = mPage.doGetTotalCanTransfer();
		//点击一键归集
		mPage.doCollect();
		//校验对话框中的金额
		Alert alert = mPage.getAlert();
		String text = alert.doGetText();
		text = text.substring(text.indexOf("归集 ") + 3, text.indexOf(" 元到主账户"));
		text = text.replace(",", "");
		float actualTotal = Float.valueOf(text);
		AssertUtil.assertEquals(expectTotal, actualTotal);
		alert.doAccept();
		//校验结果
		AssertUtil.assertContains(alert.doGetText(), param.get("验证1"));
		alert.doAccept();
		WaitUtil.sleep(1000);
	}
}
