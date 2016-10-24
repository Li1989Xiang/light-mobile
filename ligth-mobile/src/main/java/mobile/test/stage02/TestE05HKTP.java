package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageHKTP;
import mobile.page.base.PageManager;
import mobile.page.module.AlertHK;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;
import up.light.assertutil.AssertUtil;

/**
 * 港股通投票测试
 */
public class TestE05HKTP extends TestBase {
	private PageHKTP mPage = PageManager.getPage(PageHKTP.class);

	@BeforeClass
	public void before1() {
		ViewNavigator.navigate("投票", mPage);
	}
	
	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testHKTP(Map<String, String> param) {
		// 输入代码并校验名称
		String vActualName = mPage.doInputCode(param.get("代码"));
		String vExpectName = param.get("名称");
		AssertUtil.assertEquals(vExpectName, vActualName);
		// 
		mPage.doInputNos(param.get("公告编号"), param.get("议案编号"));
		mPage.doInputNumbers(param.get("赞成数量"), param.get("反对数量"), param.get("弃权数量"));
		mPage.doTrade();
		// 获取对话框1内容并校验
		AlertHK alert = mPage.getAlertHK();
		String vCheckPoint1 = param.get("验证1");
		String vActualCheckPoint1 = alert.doGetConfirmText();
		AssertUtil.assertEquals(vCheckPoint1, vActualCheckPoint1);
		alert.doAcceptConfirm();
		// 获取对话框2内容并校验
		String vCheckPoint2 = param.get("验证2");
		String vActualCheckPoint2 = alert.doGetResultText();
		AssertUtil.assertContains(vActualCheckPoint2, vCheckPoint2);
		alert.doAcceptResult();
		// 参数中加入委托编号
		String vNo = vActualCheckPoint2.substring(vActualCheckPoint2.indexOf("：") + 1, vActualCheckPoint2.length());
		param.put("委托编号", vNo);
	}
}
