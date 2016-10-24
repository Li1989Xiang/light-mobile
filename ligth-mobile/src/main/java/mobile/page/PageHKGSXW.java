package mobile.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPageHK;
import mobile.page.base.PageManager;
import mobile.page.module.Alert;
import up.light.pagefactory.TestElement;
import up.light.wait.Conditions;
import up.light.wait.WaitUtil;

public class PageHKGSXW extends AbstractPageHK {
	private TestElement oEditCode;
	private TestElement oTextName;
	private TestElement oLabelYWLX;
	private TestElement oEditXWDM;
	private TestElement oLabelSBLX;
	private TestElement oEditWTBH;
	private TestElement oEditSBSL;
	private TestElement oBtnSubmit;
	
	private PageCodeSelect mPageCodeSelect = PageManager.getPage(PageCodeSelect.class);
	
	private boolean isInSelectView;
	
	/**
	 * 输入股票代码
	 * @param code 股票代码
	 */
	public String doInputCode(String code) {
		//等待并点击代码编辑框
		WaitUtil.waitFor(driver, oEditCode, WaitUtil.WAIT_LONG * 2).click();
		//输入代码并等待数据加载
		isInSelectView = true;
		mPageCodeSelect.doInputCode(code);
		isInSelectView = false;
		WebElement eName = oTextName.e();
		WaitUtil.waitForText(driver, eName, WaitUtil.WAIT_LONG, null, Conditions.NOTBLANK);
		
		Alert alert = getAlert();
		if(alert.exists()) {
			throw new RuntimeException(alert.doGetText());
		}
		
		return getText(eName);
	}
	
	/**
	 * 选择业务、申报类型
	 * @param isYW 是否为业务类型
	 * @param type 类型名称
	 */
	public void doChaneType(boolean isYW, String type) {
		if(isYW) {
			oLabelYWLX.e().click();
		} else {
			oLabelSBLX.e().click();
		}
		
		WaitUtil.sleep(200);
		String xpath = "//div[@class='kmc-downMenu']/ul/li[text()='" + type + "']";
		driver.findElement(By.xpath(xpath)).click();
		
		if("撤销".equals(type)) {
			getKeyboard().doInput(oEditWTBH.e(), "0");
		}
	}
	
	/**
	 * 输入行为代码
	 * @param XWCode 行为代码
	 */
	public void doInputXWCode(String XWCode){
		getKeyboard().doInput(oEditXWDM.e(), XWCode);
	}
	
	/**
	 * 输入数量
	 * @param number 数量
	 */
	public void doInputNumber(String number) {
		getKeyboard().doInput(oEditSBSL.e(), number);
	}
	
	/**
	 * 点击提交
	 */
	public void doTrade() {
		oBtnSubmit.e().click();
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
