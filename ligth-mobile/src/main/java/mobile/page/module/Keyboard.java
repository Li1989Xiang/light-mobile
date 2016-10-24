package mobile.page.module;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import up.light.Setting;
import up.light.pagefactory.TestElement;
import up.light.utils.LogUtil;
import up.light.wait.WaitUtil;

public class Keyboard extends AbstractPage {
	private static final String PLATFORM = Setting.getPlatform();
	// android识别整块，ios识别数字1
	private TestElement oViewKeyboard;
	private TestElement oBtnClose;
	private boolean inited;
	private Map<Character, Point> points;

	public void doInput(WebElement e, String str) {
		LogUtil.log.debug("[Keyboard Input] " + str);

		String att = null;
		e.click();

		try {
			att = e.getAttribute("readonly");
		} catch (Exception ex) {
		}

		if (!"true".equals(att)) {
			e.sendKeys(str);
			return;
		}

		e.click();
		WaitUtil.sleep(1000);
		driver.context("NATIVE_APP");

		if (!inited) {
			WebElement ele = oViewKeyboard.e();
			Point vPoint = ele.getLocation();
			// 键盘左上角坐标
			int vX = vPoint.getX();
			int vY = vPoint.getY();
			Dimension vSize = ele.getSize();
			// 每一块的宽、高
			int vWidth = 0, vHeight = 0;

			// 根据driver类型分类处理
			// android的键盘只能识别为一整块
			// ios的键盘可以单独识别每个按键

			if ("android".equals(PLATFORM)) {
				vWidth = vSize.getWidth() / 3;
				vHeight = vSize.getHeight() / 4;
			} else {
				vWidth = vSize.getWidth();
				vHeight = vSize.getHeight();
			}

			points = new HashMap<>();
			int vRow = 0, vColumn = 0;

			points.put(Character.valueOf('0'), new Point(vX + vWidth + vWidth / 2, vY + 3 * vHeight + vHeight / 2));

			for (char i = '1'; i <= '9'; ++i) {
				vRow = (i - 49) / 3;
				vColumn = (i - 49) % 3;
				Point p = new Point(vX + vColumn * vWidth + vWidth / 2, vY + vRow * vHeight + vHeight / 2);
				points.put(Character.valueOf(i), p);
			}

			inited = true;
			LogUtil.log.debug("[Initialize Keyboard] " + points);
		}

		// 循环参数字符串中的每个字符计算坐标并点击
		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			Point p = points.get(c);
			driver.tap(1, p.x, p.y, 100);
			LogUtil.log.debug("[Keyboard Input] tap point: " + p.x + ", " + p.y);
		}

		if (exists()) {
			oBtnClose.e().click();
		}
	}

	public boolean exists() {
		return WaitUtil.exists(driver, oViewKeyboard, WaitUtil.WAIT_SHORT);
	}
}
