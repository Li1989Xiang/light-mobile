package mobile.page;

import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import mobile.page.base.PageManager;
import mobile.page.module.Alert;
import up.light.pagefactory.TestElement;
import up.light.wait.Conditions;
import up.light.wait.WaitUtil;

public class PageJY extends AbstractPage {
	private TestElement oEditCode;
	private TestElement oTextName;
	private TestElement oEditNum;
	private TestElement oEditPrice;
	private TestElement oBtnBuyorSell;

	private boolean isInSelectView;

	private PageCodeSelect mPageCodeSelect = PageManager.getPage(PageCodeSelect.class);

	public String doInputCode(String code) {
		// 等待并点击代码编辑框
		WaitUtil.waitFor(driver, oEditCode, WaitUtil.WAIT_LONG * 2).click();
		WaitUtil.sleep(500);
		// 输入代码并选择第一条
		isInSelectView = true;
		mPageCodeSelect.doInputCode(code);
		isInSelectView = false;
		// 检查对话框
		Alert alert = getAlert();
		if (alert.exists()) {
			throw new RuntimeException(alert.doGetText());
		}
		// 等待加载完成
		WebElement e = oTextName.e();
		WaitUtil.waitForText(driver, e, WaitUtil.WAIT_MEDIUM, null, Conditions.NOTBLANK);

		return getText(e);
	}

	public void doInputNumber(String number) {
		getKeyboard().doInput(oEditNum.e(), number);
	}

	public void doTrade() {
		oBtnBuyorSell.e().click();
	}

	public String doGetPrice() {
		return getValue(oEditPrice.e());
	}

	@Override
	public void reset() {
		if (isInSelectView) {
			mPageCodeSelect.reset();
			isInSelectView = false;
		}

		super.reset();
	}

}
