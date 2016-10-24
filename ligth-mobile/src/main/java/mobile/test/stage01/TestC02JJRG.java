package mobile.test.stage01;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageJJ;
import mobile.page.base.PageManager;
import mobile.page.module.Alert;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;
import up.light.assertutil.AssertUtil;

/**
 * 基金认购测试
 */
public class TestC02JJRG extends TestBase {
private PageJJ mPage = PageManager.getPage(PageJJ.class);
	
	@BeforeClass
	public void before() {
		ViewNavigator.navigate("基金认购", mPage);
	}
	
	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testJJRG(Map<String, String> param) {
		// 输入代码并校验名称
		String vActualName = mPage.doInputCode(param.get("代码"));
		String vExpectName = param.get("名称");
		AssertUtil.assertEquals(vExpectName, vActualName);

		// 读取基金净值和可用资金并替换
		String vActualPrice = mPage.doGetPrice();
		String vActualMoney = mPage.doGetMoney();
		String vCheckPoint1 = param.get("验证1");
		vCheckPoint1 = vCheckPoint1.replace("{PRICE}", vActualPrice).replace("{MONEY}", vActualMoney);

		// 输入数量、点击买入
		mPage.doInputNumber(param.get("数量"));
		mPage.doTrade();

		// 风险提示框处理
		mPage.doHandleRiskAlert();
		
		// 获取对话框1内容并校验
		Alert alert = mPage.getAlert();
		String vActualCheckPoint1 = alert.doGetText();
		AssertUtil.assertEquals(vCheckPoint1, vActualCheckPoint1);
		alert.doAccept();

		// 获取对话框2内容并校验
		String vCheckPoint2 = param.get("验证2");
		String vActualCheckPoint2 = alert.doGetText();
		AssertUtil.assertContains(vActualCheckPoint2, vCheckPoint2);
		alert.doAccept();

		// 参数中加入委托编号
		String vNo = vActualCheckPoint2.substring(vActualCheckPoint2.indexOf("：") + 1, vActualCheckPoint2.length());
		param.put("委托编号", vNo);
	}
}
