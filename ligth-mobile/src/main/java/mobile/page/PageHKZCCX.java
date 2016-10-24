package mobile.page;

import java.util.HashMap;
import java.util.Map;

import mobile.page.base.AbstractPage;
import up.light.pagefactory.TestElement;
import up.light.wait.WaitUtil;

/**
 * 港股通资产查询界面
 * @category Stage-02
 * @since 2016-08-15
 */
public class PageHKZCCX extends AbstractPage {
	private TestElement oTextKQ;
	private TestElement oTextKY;
	private TestElement oImgLoad;
	
	public Map<String, String> doGetVaules() {
		WaitUtil.untilGone(driver, oImgLoad, WaitUtil.WAIT_MEDIUM);
		Map<String, String> map = new HashMap<>();
		map.put("可取", "可取:" + getText(oTextKQ.e()));
		map.put("可用", "可用:" + getText(oTextKY.e()));
		return map;
	}

	@Override
	public void reset() {
		// do nothing
	}

}
