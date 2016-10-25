package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageFundFixed;
import mobile.page.base.PageManager;
import mobile.page.module.Alert;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;
import up.light.assertutil.AssertFailException;
import up.light.assertutil.AssertUtil;

public class TestC05FundDS extends TestBase{
	
	private PageFundFixed mPage = PageManager.getPage(PageFundFixed.class);

	/*
	 * BeforeClass方法分成两个是为了拦截登录
	 */
	
	@BeforeClass
	public void before1() {
		ViewNavigator.navigate("基金定投", mPage);
		mPage.doChooseDTorDS("基金定赎");
		mPage.doAddSG();
	}
	
	@Test(dataProvider="dp", dataProviderClass=TestDataProvider.class)
	public void testDT(Map<String, String> param){
		// 输入代码并校验名称
		String vActualName = mPage.doInputCode(param.get("代码"));
		String vExpectName = param.get("名称");
		AssertUtil.assertEquals(vExpectName, vActualName);
		
		// 读取基金净值并替换
		//String vActualPrice = mPageFundFixed.doGetPrice();
		//输入每期定投金额
		mPage.doInputNumber(param.get("金额"));
		
		//选择扣款周期
		mPage.doChooseTime(param.get("扣款周期"),param.get("扣款日"));
		
		//选择巨额标志
		mPage.doChooseMark(param.get("巨额标志"));
		
		//获取开始日期和结束日期
		String begintime=mPage.doGetBeginTime();
		String endtime=mPage.doGetEndTime();
		mPage.doSG();
		
		// 风险提示框处理
		mPage.doHandleRiskAlert();
		
		//获取验证点2
		String vCheckPoint2 = param.get("验证2");
		vCheckPoint2 = vCheckPoint2.replace("{BeginTime}", begintime).replace("{EndTime}", endtime);
		
		
		//获取弹出框2
		Alert vAlert = mPage.getAlert();
		String vActualCheckPoint2 = vAlert.doGetText();
		AssertUtil.assertEquals(vCheckPoint2, vActualCheckPoint2);
		vAlert.doAccept();
		
		// 获取对话框3内容并校验
		String vCheckPoint3 = param.get("验证3");
		String vActualCheckPoint3 = vAlert.doGetText();
		if (vActualCheckPoint3.indexOf(vCheckPoint3) < 0) {
			throw new AssertFailException(vCheckPoint3, vActualCheckPoint3);
		}

		vAlert.doAccept();
		// 参数中加入委托编号
		String vNo = vActualCheckPoint3.substring(vActualCheckPoint3.indexOf("：") + 1, vActualCheckPoint3.length());
		param.put("委托编号", vNo);
		
		//mPageFundFixed.doClear();
	}
}
