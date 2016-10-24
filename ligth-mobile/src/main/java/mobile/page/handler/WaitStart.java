package mobile.page.handler;

import mobile.navigator.INavigationHandler;
import mobile.navigator.ViewNode;
import mobile.page.base.PageManager;
import mobile.page.module.ToolBar;

/*
 * 等待交易按钮出现
 */
public class WaitStart implements INavigationHandler {
	private boolean hasStarted;

	@Override
	public void enter(ViewNode node) {
		if (!hasStarted) {
			ToolBar bar = PageManager.getPage(ToolBar.class);
			bar.doWaitForBtnJY();
			bar.doWaitAndCloseMsgBox();
			hasStarted = true;
		}

		node.enter();
	}

	@Override
	public void back(ViewNode node) {
		node.back();
	}

	public void reset() {
		hasStarted = false;
	}
}
