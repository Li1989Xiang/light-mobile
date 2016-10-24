package mobile.page;

import mobile.page.base.AbstractPage;
import up.light.pagefactory.TestElement;
import up.light.wait.WaitUtil;

public class PageCodeSelect extends AbstractPage {
	private TestElement oEditCode;
	private TestElement oTextItem;
	private TestElement oBtnCancel;
	
	public void doInputCode(String code) {
		WaitUtil.sleep(1000);
		getKeyboard().doInput(oEditCode.e(), code);
		WaitUtil.sleep(500);
		oTextItem.e().click();
	}

	@Override
	public void reset() {
		oBtnCancel.e().click();
	}
}
