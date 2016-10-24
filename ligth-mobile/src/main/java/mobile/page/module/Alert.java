package mobile.page.module;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import up.light.Setting;
import up.light.pagefactory.TestElement;
import up.light.utils.LogUtil;
import up.light.wait.WaitUtil;

public class Alert extends AbstractPage {
	private TestElement mControls;
	private TestElement oBtnOK;
	private TestElement oBtnCancel;

	public String doGetText() {
		WaitUtil.sleep(1000);
		StringBuilder sb = new StringBuilder();
		String content = null;
		int i = 1;
		List<WebElement> es = mControls.es();

		for (WebElement e : es) {
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
			throw new RuntimeException("text of alert is null, current context is " + driver.getContext());
		}

		content = sb.toString();
		LogUtil.log.debug("[Alert] text: " + Setting.LINE_SEPARATOR + content);
		return content;
	}

	public void doAccept() {
		oBtnOK.e().click();
	}

	public void doCancel() {
		// oBtnCancel.e().click();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("CloseModal();");
	}

	public boolean exists() {
		boolean ret;

		ret = WaitUtil.exists(driver, oBtnOK, WaitUtil.WAIT_SHORT);

		if (ret)
			return true;

		return WaitUtil.exists(driver, oBtnCancel, WaitUtil.WAIT_SHORT);
	}
}
