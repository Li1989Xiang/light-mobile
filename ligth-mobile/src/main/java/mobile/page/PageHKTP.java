package mobile.page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPageHK;
import mobile.page.base.PageManager;
import up.light.pagefactory.TestElement;
import up.light.wait.Conditions;
import up.light.wait.WaitUtil;

/**
 * 港股通投票界面
 * @category Stage-02
 * @since 2016-08-15
 */
public class PageHKTP extends AbstractPageHK {
	private TestElement oEditCode;
	private TestElement oTextName;
	private TestElement oEditGGBH;
	private TestElement oEditYABH;
	private TestElement oEditZCSL;
	private TestElement oEditFDSL;
	private TestElement oEditQQSL;
	private TestElement oBtnSubmit;
	
	private PageCodeSelect mPageCodeSelect = PageManager.getPage(PageCodeSelect.class);
	
	private boolean isInSelectView;
	
	/**
	 * 输入股票代码
	 * @param code 股票代码
	 * @return 股票名称编辑框中的文本
	 */
	public String doInputCode(String code) {
		// 等待并点击代码编辑框
		WaitUtil.waitFor(driver, oEditCode, WaitUtil.WAIT_LONG * 2).click();
		WaitUtil.sleep(500);
		// 输入代码并选择第一条
		isInSelectView = true;
		mPageCodeSelect.doInputCode(code);
		isInSelectView = false;
		// 等待加载完成
		WebElement e = oTextName.e();
		WaitUtil.waitForText(driver, e, WaitUtil.WAIT_LONG, null, Conditions.NOTBLANK);

		return getText(e);
	}
	
	/**
	 * 输入公告编号和议案编号
	 */
	public void doInputNos(String ggbh, String yabh) {
		// getKeyboard().doInput(oEditGGBH, ggbh);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("document.getElementById('ggbh').removeAttribute('readonly');;");
		oEditGGBH.e().sendKeys(ggbh);
		getKeyboard().doInput(oEditYABH.e(), yabh);
	}
	
	/**
	 * 输入数量
	 */
	public void doInputNumbers(String zcsl, String fdsl, String qqsl) {
		getKeyboard().doInput(oEditZCSL.e(), zcsl);
		getKeyboard().doInput(oEditFDSL.e(), fdsl);
		getKeyboard().doInput(oEditQQSL.e(), qqsl);
	}
	
	/**
	 * 点击提交按钮
	 */
	public void doTrade() {
		oBtnSubmit.e().click();
	}

	/*
	 * 因需要处理位于股票选择界面的异常，所以覆写父类reset方法
	 */
	@Override
	public void reset() {
		if (isInSelectView) {
			mPageCodeSelect.reset();
			isInSelectView = false;
		}
		super.reset();
	}
}
