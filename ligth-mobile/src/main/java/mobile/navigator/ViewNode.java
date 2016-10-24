package mobile.navigator;

import mobile.page.base.PageManager;
import mobile.page.module.ToolBar;
import up.light.pagefactory.TestElement;

public class ViewNode {
	// back element
	private static ToolBar toolBar = PageManager.getPage(ToolBar.class);

	// login infomation
	private static Loginer loginer = new Loginer();
	private String groupName;

	// node infomation
	private ViewNode parent;
	private String name;
	private int depth;
	private TestElement element;
	private INavigationHandler handler;

	public ViewNode(ViewNode parent, String name, int depth, TestElement element, INavigationHandler handler) {
		this.parent = parent;
		this.name = name;
		this.depth = depth;
		this.element = element;
		this.handler = handler;
	}

	public void enter() {
		element.e().click();

		if (loginer.needLogin(this)) {
			loginer.login(groupName);
		}
	}

	/*
	 * invoked by ViewNavigator
	 */
	void enterWithHandler() {
		if (handler != null) {
			handler.enter(this);
		} else {
			enter();
		}
	}

	public void back() {
		if (NoOperationBack.isNoOperation(name))
			return;

		toolBar.doBack();
	}

	/*
	 * invoked by ViewNavigator
	 */
	void backWithHandler() {
		if (handler != null) {
			handler.back(this);
		} else {
			back();
		}
	}

	// setter and getter
	public static Loginer getLoginer() {
		return loginer;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public ViewNode getParent() {
		return parent;
	}

	public void setParent(ViewNode parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public TestElement getElement() {
		return element;
	}

	public void setElement(TestElement element) {
		this.element = element;
	}

	public INavigationHandler getHandler() {
		return handler;
	}

	public void setHandler(INavigationHandler handler) {
		this.handler = handler;
	}

	@Override
	public String toString() {
		String p = parent != null ? parent.getName() : null;
		return String.format("ViewNode[%s(%s), %d]", name, p, depth);
	}

}
