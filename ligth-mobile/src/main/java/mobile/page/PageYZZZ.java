package mobile.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import up.light.pagefactory.TestElement;
import up.light.utils.ArgumentUtil;
import up.light.wait.WaitUtil;

/**
 * 银证转账
 * @category Stage-01
 * @since 2016-08-01
 */
public class PageYZZZ extends AbstractPage {
	private TestElement oTitleYZZ;	//银转证
	private TestElement oTitleZZY;	//证转银
	private TestElement oMenuBank;	//银行选择下拉菜单
	private TestElement oEditNum;	//转账金额
	private TestElement oEditPwd;	//密码输入框
	private TestElement oBtnOK;		//确定按钮
	private TestElement oImgLoad;	//加载浮层
	
	/**
	 * 切换类型：银转证、证转银
	 * @param type 类型：银行转证券、证券转银行
	 */
	public void doSwithTo(String type) {
		TestElement e = null;
		WaitUtil.untilGone(driver, oImgLoad, WaitUtil.WAIT_LONG);
		
		if ("银行转证券".equals(type)) {
			e = oTitleYZZ;
		} else if ("证券转银行".equals(type)) {
			e = oTitleZZY;
		}

		ArgumentUtil.notNull(e, "unsupport type: " + type);
		e.e().click();
		WaitUtil.sleep(500);
		WaitUtil.untilGone(driver, oImgLoad, WaitUtil.WAIT_LONG);
	}

	/**
	 * 设置银行
	 * @param bank 银行名称
	 * @param currency 币种
	 */
	public void doSetBank(String bank, String currency) {
		oMenuBank.e().click();
		
		String xpath = "//label[text()='" + bank + "']";
		List<WebElement> es = driver.findElements(By.xpath(xpath));
		ArgumentUtil.notEmpty(es, "can't find bank: " + bank);
		
		WebElement e = null;

		if (es.size() > 1 && "USD".equals(currency)) {
			e = es.get(1);
		} else {
			e = es.get(0);
		}

		e.click();
	}

	/**
	 * 输入金额
	 * @param num 转账金额
	 */
	public void doInputNum(String num) {
		getKeyboard().doInput(oEditNum.e(), num);
	}

	/**
	 * 输入密码
	 * @param pwd 密码
	 */
	public void doInputPwd(String pwd) {
		getKeyboard().doInput(oEditPwd.e(), pwd);
	}

	/**
	 * 点击确定按钮
	 */
	public void doTrade() {
		oBtnOK.e().click();
	}
}
