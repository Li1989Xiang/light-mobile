package mobile.page;

import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import up.light.pagefactory.TestElement;
import up.light.wait.Conditions;
import up.light.wait.WaitUtil;

/**
 * 基金认申赎
 * @category Stage-01
 * @since 2016-08-01
 */
public class PageJJ extends AbstractPage {
	private TestElement oEditCode;	//基金代码
	private TestElement oTextName;	//基金名称
	private TestElement oEditNum;	//申购、认购、赎回数量
	private TestElement oBtnTrade;	//交易按钮
	private TestElement oTextPrice;	//基金净值
	private TestElement oTextMoney;	//可用资金
	private TestElement oTextNumber;//可赎份额
	private TestElement oMsgRisk;	//风险提示对话框文本
	
	/**
	 * 输入基金代码
	 * @param code 基金代码
	 */
	public String doInputCode(String code) {
		//等待并点击代码编辑框
		WebElement eCode = WaitUtil.waitFor(driver, oEditCode, WaitUtil.WAIT_LONG * 2);
		//输入代码并等待数据加载
		getKeyboard().doInput(eCode, code);
		
		WebElement eName = oTextName.e();
		WaitUtil.waitForText(driver, eName, WaitUtil.WAIT_LONG, null, Conditions.NOTBLANK);
		
		return getText(eName);
	}
	
	/**
	 * 输入数量
	 * @param number 
	 * @param number 数量
	 */
	public void doInputNumber(String number) {
		getKeyboard().doInput(oEditNum.e(), number);
	}
	
	/**
	 * 点击认购，申购或赎回按钮
	 */
	public void doTrade() {
		oBtnTrade.e().click();
	}
	
	/**
	 * 获取价格编辑框中的文本
	 * @return 股票价格文本
	 */
	public String doGetPrice() {
		return getText(oTextPrice.e());
	}
	
	/**
	 * 获取可用资金文本
	 * @return 可用资金文本
	 */
	public String doGetMoney() {
		return getText(oTextMoney.e());
	}
	
	/**
	 * 获取可赎份额
	 * @return 可赎份额文本
	 */
	public String doGetNumber() {
		return getText(oTextNumber.e());
	}
	
	/**
	 * 处理风险提示对话框
	 */
	public void doHandleRiskAlert() {
		if(WaitUtil.exists(driver, oMsgRisk, 1)) {
			getAlert().doAccept();
		}
	}
}
