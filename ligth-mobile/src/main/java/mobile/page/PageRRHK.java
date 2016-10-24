package mobile.page;

import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import mobile.page.base.PageManager;
import mobile.page.module.Alert;
import up.light.pagefactory.TestElement;
import up.light.wait.Conditions;
import up.light.wait.WaitUtil;

/**
 * 融资融券还款界面
 * 
 * @category Stage-02
 * @since 2016-09-20
 */
// TODO repository
public class PageRRHK extends AbstractPage {
	private TestElement oBtnMQHK;
	private TestElement oBtnXJHK;
	private TestElement oEditCode;
	private TestElement oTextName;
	private TestElement oEditNum;
	private TestElement oBtnOK;

	private TestElement oEditMoney;
	private TestElement oTextKHZJ;
	private TestElement oTextXHKE;

	private PageCodeSelect mPageCodeSelect = PageManager.getPage(PageCodeSelect.class);

	public void doSwitchType(String type) {
		if ("卖券还款".equals(type)) {
			oBtnMQHK.e().click();
		} else {
			oBtnXJHK.e().click();
		}
	}

	public String doInputCode(String code) {
		// 等待并点击代码编辑框
		WaitUtil.waitFor(driver, oEditCode, WaitUtil.WAIT_LONG * 2).click();
		WaitUtil.sleep(500);
		// 输入代码并选择第一条
		mPageCodeSelect.doInputCode(code);
		// 等待加载完成
		WebElement e = oTextName.e();
		WaitUtil.waitForText(driver, e, WaitUtil.WAIT_MEDIUM, null, Conditions.NOTBLANK);
		// 检查对话框
		Alert alert = getAlert();
		if (alert.exists()) {
			throw new RuntimeException(alert.doGetText());
		}

		return getText(e);
	}

	public void doInputNum(String num) {
		getKeyboard().doInput(oEditNum.e(), num);
	}

	public void doTrade() {
		oBtnOK.e().click();
	}

	public void doInputMoney(String money) {
		getKeyboard().doInput(oEditMoney.e(), money);
	}

	public float getKHZJ() {
		return Float.valueOf(getText(oTextKHZJ.e()).replace(",", ""));
	}

	public float getXHKE() {
		return Float.valueOf(getText(oTextXHKE.e()).replace(",", ""));
	}
}
