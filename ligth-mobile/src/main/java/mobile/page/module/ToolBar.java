package mobile.page.module;

import org.openqa.selenium.WebDriverException;

import mobile.page.base.AbstractPage;
import up.light.pagefactory.TestElement;
import up.light.wait.WaitUtil;

public class ToolBar extends AbstractPage {
	public static final byte PTJY = 0;
	public static final byte RZRQ = 1;

	private TestElement oBtnPTJY;
	// 上方融资融券按钮
	private TestElement oBtnRZRQ;
	// 下方交易按钮
	private TestElement oBtnJY;
	// 返回按钮
	private TestElement oBtnBack;
	// 通知对话框关闭按钮
	private TestElement oBtnMsg;

	// 当前界面 0 - 普通交易， 1 - 融资融券
	private byte current;

	public void switchTo(byte type) {
		if (current == type) {
			return;
		}

		switch (type) {
		case 0:
			oBtnPTJY.e().click();
			break;
		case 1:
			oBtnRZRQ.e().click();
			break;
		default:
			break;
		}

		current = type;
	}

	public void doBack() {
		oBtnBack.e().click();
		WaitUtil.sleep(1000);
	}

	public void doWaitForBtnJY() {
		WaitUtil.waitFor(driver, oBtnJY, 30);
	}

	public void doWaitAndCloseMsgBox() {
		try {
			WaitUtil.waitFor(driver, oBtnMsg, 10);
			
			while(WaitUtil.exists(driver, oBtnMsg, WaitUtil.WAIT_SHORT)) {
				oBtnMsg.e().click();
			}
		} catch (WebDriverException ex) {
		}
	}
}
