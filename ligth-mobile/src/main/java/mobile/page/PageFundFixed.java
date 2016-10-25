package mobile.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import up.light.pagefactory.TestElement;
import up.light.wait.WaitUtil;

/** 
 *基金定投与定赎封装界面
 */
public class PageFundFixed extends AbstractPage{

	private TestElement oTextDT;//基金定投
	private TestElement oTextDS;//基金定赎
	
	private TestElement oTextSQ;//申请新的定投计划
	
	private TestElement oEditCode;//输入基金代码
	private TestElement oTextName;//基金名称
	private TestElement oTextPrice;//基金净值
	private TestElement oEditNumber;//输入定投金额
	private TestElement oBtnTime;//扣款周期
	private TestElement oTextMonth;//月
	private TestElement oTextWeek;//周
	private TestElement oMenuMonth;//扣款周期：月
	private TestElement oMenuWeek;//扣款周期：周
	private TestElement oBtnDescription;//投资描述
	private TestElement oMenuDescription;//投资描述选择项
	private TestElement oBtnCondition;//期满条件
	private TestElement oMenuCondition;//期满条件选择项
	private TestElement oBtnMark;//巨额标记
	private TestElement oMenuMark;//巨额标记
	private TestElement oEditBeginTime;//开始日期
	private TestElement oEditEndTime;//终止日期
	private TestElement oEditQMCS;//期满次数
	private TestElement oEditQMJE;//期满金额
	private TestElement oBtnSG;//申购按钮
	private TestElement oMsgRisk;//风险提示
	private TestElement oImgLoad;//请等待...

	private boolean isInSelectView;
	
	//涉及多次使用，通过Singleton实例化
	//	private PageFundFixed() {}


	
	/**
	 * 点击基金定投或者基金定赎
	 */
	public void doChooseDTorDS(String type){
        TestElement vToClick = null;
		
		switch(type){
		case"基金定投":
			vToClick = oTextDT;
			//vToClick = oTextDS;
			break;
		case"基金定赎":
			vToClick = oTextDS;
			break;
		default:
			throw new RuntimeException(type + " is unsupported");
		}
		WaitUtil.waitFor(driver, vToClick, WaitUtil.WAIT_LONG);
		WaitUtil.waitFor(driver, vToClick, WaitUtil.WAIT_LONG);
		vToClick.e().click();
		while(WaitUtil.exists(driver, oImgLoad, 1)){
			WaitUtil.sleep(1000);
		}
	}
	
	public void doAddSG(){
		WaitUtil.sleep(2000);
		if(WaitUtil.exists(driver, oTextSQ, WaitUtil.WAIT_SHORT*3)) {
			oTextSQ.e().click();
		}

	}
	
	/**
	 * 输入基金代码,并获取基金名称
	 * @param code 基金代码
	 */
	public String doInputCode(String code) {
		WaitUtil.sleep(1000);
		//等待并点击代码编辑框
		WaitUtil.waitFor(driver, oEditCode, WaitUtil.WAIT_LONG);
		driver.navigate().refresh();
		WaitUtil.waitFor(driver, oEditCode, WaitUtil.WAIT_LONG);
		WaitUtil.sleep(1000);
		//输入代码并等待数据加载
		getKeyboard().doInput(oEditCode.e(), code);
		//WaitUtil.waitForText(driver, oTextName, WaitUtil.WAIT_LONG, null, Conditions.NOTBLANK);
		String vTestName = getText(oTextName.e());
		while(vTestName.equals("")){
			int x = 0;
			if(x > 3){
				break;
			}
			WaitUtil.sleep(1000);
			vTestName = getText(oTextName.e());
			x++;
		} 
		return vTestName;
	}
	
	public void doClear(){
		
		oTextPrice.e().clear();
	}
	/**
	 * 获取基金净值编辑框中的文本
	 * @return 基金净值文本
	 */
	public String doGetPrice() {
		return getText(oTextPrice.e());
	}
	
	/**
	 * 输入定投金额or每期定赎份额
	 * @param number 
	 * @param number 定投金额
	 */
	public void doInputNumber(String number) {
	
		WaitUtil.sleep(1000);
		String numberValue=getText(oEditNumber.e());
		
		if(numberValue.equals("请输入定赎份额") || numberValue.equals("请输入定投金额")){
			
			getKeyboard().doInput(oEditNumber.e(), number);
		}else{
			
			WaitUtil.sleep(1000);
			oEditNumber.e().click();
			//oEditNumber.e().clear();
			getKeyboard().doInput(oEditNumber.e(), number);
			
		}
	
	}
	
	
	/**
	 * 扣款周期选择，月或者日
	 */
	public void doChooseTime(String type,String time){
		WaitUtil.sleep(3000);
		oBtnTime.e().click();
    
		switch(type){
		case"每月":
			doChooseMonth(time) ;
			break;
		case"每周":
			doChooseWeek(time);
			break;
		default:
			throw new RuntimeException(type + " is unsupported");
		}
	}
	
    /**
     * 选择月
     * @param month
     */
	public void doChooseMonth(String month) {
		boolean vFlag = false;
		WaitUtil.sleep(1000);
		oTextMonth.e().click();
	
		WaitUtil.sleep(1000);
		for(WebElement e : oMenuMonth.es()) {
			if(getText(e).equals(month)) {
				e.click();
				vFlag = true;
				break;
			}
		}
		
		if(!vFlag) {
			throw new RuntimeException("can't find month: " + month);
		}
	}
	
	/**
     * 选择周
     * @param week
     */
	public void doChooseWeek(String week) {
		boolean vFlag = false;
		
		oTextWeek.e().click();
		WaitUtil.sleep(1000);
		for(WebElement e : oMenuWeek.es()) {
			if(getText(e).equals(week)) {
				e.click();
				vFlag = true;
				break;
			}
		}
		
		if(!vFlag) {
			throw new RuntimeException("can't find week: " + week);
		}
	}
	
	/**
     * 选择投资描述
     * @param descriptionk  
     */
	public void doChooseDescription(String description) {
		boolean vFlag = false;
		
		oBtnDescription.e().click();
		WaitUtil.sleep(1000);
		
		for(WebElement e : oMenuDescription.es()) {
			if(getText(e).equals(description)) {
				WaitUtil.sleep(1000);
				e.click();
				vFlag = true;
				break;
			}
		}
		
		if(!vFlag) {
			throw new RuntimeException("can't find description: " + description);
		}
	}
	
	/**
     * 选择期满条件
     * @param condition  
     */
	public void doChooseCondition(String condition) {
		boolean vFlag = false;
		
		oBtnCondition.e().click();
		WaitUtil.sleep(1000);
		
		for(WebElement e : oMenuCondition.es()) {
			
			if(getText(e).equals(condition)) {
				e.click();
				vFlag = true;
				break;
			}
		}
		
		if(!vFlag) {
			throw new RuntimeException("can't find condition: " + condition);
		}
	}
	
	public void doChooseMark(String mark){
        boolean vFlag = false;
		
		oBtnMark.e().click();
		WaitUtil.sleep(1000);
	
		for(WebElement e : oMenuMark.es()) {
	
			if(getText(e).equals(mark)) {
				e.click();
				vFlag = true;
				break;
			}
		}
		
		if(!vFlag) {
			throw new RuntimeException("can't find condition: " + mark);
		}
		
	}
	/**
	 * 获取开始日期
	 * @param beginTime
	 */
	public String doGetBeginTime() {
		String vbeginTime="";
		if(WaitUtil.exists(driver,oEditBeginTime,WaitUtil.WAIT_SHORT)){
		  //vbeginTime= getText(oEditBeginTime);
		  vbeginTime= getValue(oEditBeginTime.e());
		}
		return vbeginTime;
	}
	/**
	 * 获取结束日期
	 * @param endTime
	 */
	public String doGetEndTime() {
		String vendTime="";
		if(WaitUtil.exists(driver,oEditBeginTime,WaitUtil.WAIT_SHORT)){
			//vendTime= getText(oEditEndTime);
			vendTime= getValue(oEditEndTime.e());
		}
		return vendTime;
	}
	/**
	 * 输入期满次数或者期满金额
	 * @param num
	 * @param pric
	 */
	public void doInputCondition(String num,String pric){
		if(WaitUtil.exists(driver,oEditQMCS,WaitUtil.WAIT_SHORT)){
			getKeyboard().doInput(oEditQMCS.e(), num);
		}
		if(WaitUtil.exists(driver,oEditQMJE,WaitUtil.WAIT_SHORT)){
			getKeyboard().doInput(oEditQMJE.e(), pric);
		}
		
	}
	public void doSG(){
		WaitUtil.sleep(1000);
		//refresh();
		oBtnSG.e().click();
	}
	
	public void doHandleRiskAlert(){
		if(WaitUtil.exists(driver,oMsgRisk, 1)) {
			getAlert().doAccept();
		}
	}
	
	
	@Override
	public void reset() {
		if (isInSelectView) {
			isInSelectView = false;
		}

		super.reset();
	}
}
