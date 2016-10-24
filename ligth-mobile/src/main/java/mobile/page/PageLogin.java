package mobile.page;

import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import mobile.page.module.Alert;
import mobile.page.module.Keyboard;
import up.light.pagefactory.TestElement;
import up.light.wait.WaitUtil;

public class PageLogin extends AbstractPage {
	private TestElement oEditUser;
	private TestElement oEditPwd;
	private TestElement oEditVerCode;
	private TestElement oTextVerCode;
	private TestElement oBtnLogin;

	public void doLogin(String username, String password) {
		// 等待用户名编辑框出现
		WebElement eu = WaitUtil.waitFor(driver, oEditUser, 10);
		Keyboard kb = getKeyboard();

		// 点击用户名编辑框并输入
		if (!getValue(eu).equals(username)) {
			kb.doInput(eu, username);
		}

		// 点击密码编辑框并输入
		kb.doInput(oEditPwd.e(), password);

		// 输入验证码
		String vCode = oTextVerCode.e().getText();
		kb.doInput(oEditVerCode.e(), vCode);

		// 点击登录
		oBtnLogin.e().click();

		Alert alert = getAlert();
		if (alert.exists()) {
			String t = alert.doGetText();
			if(t.contains("“柜台测试”状态"))
				alert.doAccept();
			else
				throw new RuntimeException(t);
		}
		// 等待登录按钮消失
		WaitUtil.untilGone(driver, oBtnLogin, WaitUtil.WAIT_LONG * 3);
	}
}
