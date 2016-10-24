package mobile.page;

import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPageHK;
import mobile.page.base.PageManager;
import up.light.pagefactory.TestElement;
import up.light.wait.Conditions;
import up.light.wait.WaitUtil;

/**
 * 港股通买入、卖出界面
 * @category Stage-02
 * @since 2016-08-15
 */
public class PageHKJY extends AbstractPageHK {
	private TestElement oEditCode;
	private TestElement oTextName;
	private TestElement oEditPrice;
	private TestElement oEditNum;
	private TestElement oBtnTrade;

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
	 * 输入数量
	 * @param number 数量
	 */
	public void doInputNumber(String number) {
		getKeyboard().doInput(oEditNum.e(), number);
	}

	/**
	 * 点击买入或卖出按钮
	 */
	public void doTrade() {
		oBtnTrade.e().click();
	}

	/**
	 * 获取价格编辑框中的文本
	 * @return 股票价格文本
	 */
	public String doGetPrice() {
		return getValue(oEditPrice.e());
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
