package mobile.page;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import up.light.pagefactory.TestElement;
import up.light.wait.WaitUtil;

/**
 * 标的证券界面
 * 
 * @category Stage-02
 * @since 2016-08-15
 */
public class PageHKBDZQ extends AbstractPage {
	private TestElement oTdFirstColumn;// 第一列td
	private TestElement oLabelLoad;
	private Map<String, String> mItems;
	private boolean inited;

	public void doInitializeMap() {
		if (!inited) {
			WaitUtil.untilGone(driver, oLabelLoad, WaitUtil.WAIT_MEDIUM);
			WaitUtil.sleep(500);

			mItems = new LinkedHashMap<>();
			String name = null, code = null;

			for (WebElement e : oTdFirstColumn.es()) {
				name = getText(e.findElement(By.xpath("./p[1]")));
				code = getText(e.findElement(By.xpath("./p[2]")));
				mItems.put(code, name);
			}

			inited = true;
		}
	}

	public String doGetName(String code) {
		String name = mItems.get(code);
		return name == null ? "" : name;
	}

	@Override
	public void reset() {
		// do nothing
	}

}
