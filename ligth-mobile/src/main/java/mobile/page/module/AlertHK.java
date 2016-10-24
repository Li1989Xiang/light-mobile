package mobile.page.module;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import up.light.pagefactory.TestElement;
import up.light.wait.WaitUtil;

/**
 * 港股通对话框
 */
public class AlertHK extends AbstractPage {
	// 确认对话框
	private TestElement oTextConfirms;
	private TestElement oBtnConfirmOK;
	// 委托成功对话框
	private TestElement oTextMsgSuccess;
	private TestElement oBtnOK;
	// 委托失败对话框
	private WebElement oTextMsgFail;

	public String doGetConfirmText() {
		return getMsg(oTextConfirms);
	}

	public String doGetResultText() {
		if (WaitUtil.exists(driver, oBtnOK, WaitUtil.WAIT_MEDIUM)) {
			return getMsg(oTextMsgSuccess);
		}

		return getText(oTextMsgFail);
	}

	public void doAcceptConfirm() {
		oBtnConfirmOK.e().click();
	}

	public void doAcceptResult() {
		oBtnOK.e().click();
	}

	private String getMsg(TestElement es) {
		WaitUtil.sleep(1000);
		StringBuilder sb = new StringBuilder();
		String content = null;
		int i = 1;

		for (WebElement e : es.es()) {
			content = getText(e);

			if (StringUtils.isEmpty(content))
				continue;

			sb.append(content);

			if (i++ % 2 == 0) {
				sb.append("\n");
			} else {
				sb.append(" ");
			}
		}

		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		} else {
			throw new RuntimeException("text is null, confirm alert exists, current context: " + driver.getContext());
		}

		return sb.toString();
	}
}
