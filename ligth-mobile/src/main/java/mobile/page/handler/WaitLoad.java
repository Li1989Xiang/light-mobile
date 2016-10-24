package mobile.page.handler;

import mobile.navigator.INavigationHandler;
import mobile.navigator.ViewNode;
import up.light.DriverFactory;
import up.light.pagefactory.TestElement;
import up.light.repository.LocatorBean;
import up.light.repository.Repository;
import up.light.wait.WaitUtil;

/*
 * 等待港股通加载进度条消失
 */
public class WaitLoad implements INavigationHandler {
	private TestElement e;
	
	@Override
	public void enter(ViewNode node) {
		initializeElement();
		node.enter();
		WaitUtil.untilGone(DriverFactory.getDriver(), e, WaitUtil.WAIT_MEDIUM);
	}

	@Override
	public void back(ViewNode node) {
		node.back();
		WaitUtil.untilGone(DriverFactory.getDriver(), e, WaitUtil.WAIT_MEDIUM);
	}

	@Override
	public void reset() {
	}

	private void initializeElement() {
		if(e == null) {
			LocatorBean bean = Repository.getLocatorBean("PageMainHK", "oImgLoad");
			e = new TestElement(bean.getBys().get(0), bean.getIn());
		}
	}
}
