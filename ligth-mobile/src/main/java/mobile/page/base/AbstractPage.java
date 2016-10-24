package mobile.page.base;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import mobile.page.module.Alert;
import mobile.page.module.Keyboard;
import mobile.page.module.ToolBar;
import up.light.DriverFactory;
import up.light.pagefactory.PageBase;
import up.light.utils.LogUtil;
import up.light.wait.WaitUtil;

public abstract class AbstractPage extends PageBase {
	@SuppressWarnings("unchecked")
	protected static AppiumDriver<WebElement> driver = (AppiumDriver<WebElement>)DriverFactory.getDriver();
	// 网页空格ASCII码是160，但trim只能处理ASCII为32的空格
	private static final String STRIP_CHARS = new String(new char[] { 32, 160 });

	/**
	 * 获取工具条
	 * @return
	 */
	public ToolBar getToolBar() {
		return PageManager.getPage(ToolBar.class);
	}

	/**
	 * 获取键盘
	 * @return
	 */
	public Keyboard getKeyboard() {
		return PageManager.getPage(Keyboard.class);
	}

	/**
	 * 获取对话框
	 * @return
	 */
	public Alert getAlert() {
		return PageManager.getPage(Alert.class);
	}

	/**
	 * 对于编辑框等输入控件获取其内容
	 * @param e
	 * @return
	 */
	protected String getValue(WebElement e) {
		return StringUtils.strip(e.getAttribute("value"), STRIP_CHARS);
	}

	/**
	 * 获取控件文本，对于输入类控件请使用{@link #getValue(WebElement)}
	 * @param e
	 * @return
	 */
	protected String getText(WebElement e) {
		return StringUtils.strip(e.getText(), STRIP_CHARS);
	}

	public void reset() {
		String context = driver.getContext();
		if(context.startsWith("WEBVIEW_")) {
			driver.navigate().refresh();
			LogUtil.log.info("[Page reset] refresh: " + driver.getCurrentUrl());
			WaitUtil.sleep(500);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void resetDriver() {
		driver = (AppiumDriver<WebElement>)DriverFactory.getDriver();
	}
}
