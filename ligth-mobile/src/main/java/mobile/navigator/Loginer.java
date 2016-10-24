package mobile.navigator;

import java.util.ArrayList;
import java.util.List;

import mobile.controller.StopException;
import mobile.page.PageLogin;
import mobile.page.base.PageManager;
import up.light.Setting;
import up.light.utils.LogUtil;

public class Loginer {
	private List<String> hasLoginedGroup = new ArrayList<>();
	private int hookLoginDepth;

	public boolean needLogin(ViewNode node) {
		if (node.getDepth() == hookLoginDepth) {
			return !hasLoginedGroup.contains(node.getGroupName());
		}

		return false;
	}

	public void login(String groupName) {
		PageLogin page = PageManager.getPage(PageLogin.class);
		String group = null;

		if ("普通交易".equals(groupName)) {
			group = "PTJY";
		} else {
			group = "RZRQ";
		}

		String username = Setting.getProperty("username." + group);
		String password = Setting.getProperty("password." + group);

		try {
			page.doLogin(username, password);
			LogUtil.log.info(String.format("[Login] %s, username: %s, password: %s", group, username, password));
		} catch (Exception e) {
			throw new StopException(e);
		}

		hasLoginedGroup.add(groupName);
	}

	public void reset() {
		hasLoginedGroup.clear();
	}

	public int getHookLoginDepth() {
		return hookLoginDepth;
	}

	public void setHookLoginDepth(int hookLoginDepth) {
		this.hookLoginDepth = hookLoginDepth;
	}

}
