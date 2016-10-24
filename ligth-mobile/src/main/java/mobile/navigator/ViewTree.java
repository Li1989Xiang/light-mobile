package mobile.navigator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import up.light.utils.ArgumentUtil;
import up.light.utils.LogUtil;

public class ViewTree {
	public static final String ENTER = "enter";
	public static final String BACK = "back";

	private List<ViewNode> nodes = new ArrayList<>();

	public ViewNode getRoot() {
		if (nodes.size() > 0)
			return nodes.get(0);
		return null;
	}

	public List<ViewNode> getNodes() {
		return nodes;
	}
	
	public void addNode(ViewNode node) {
		nodes.add(node);
	}

	public ViewNode getNode(String name) {
		ArgumentUtil.notNull(name, "name must not be null");

		for (ViewNode n : nodes) {
			if (n.getName().equals(name)) {
				return n;
			}
		}

		String msg = "[ViewTree] can't find node with name: " + name;
		LogUtil.log.error(msg);
		throw new RuntimeException(msg);
	}

	public Map<String, List<ViewNode>> getPath(String from, String to) {
		Map<String, List<ViewNode>> result = new HashMap<>();

		ViewNode nodeFrom = getNode(from);
		ViewNode nodeTo = getNode(to);
		List<ViewNode> listFrom = getPathToRoot(nodeFrom);
		List<ViewNode> listTo = getPathToRoot(nodeTo);

		boolean flag = false;
		int i = 0, j = 0;

		loop: for (i = 0; i < listFrom.size(); ++i) {
			for (j = 0; j < listTo.size(); ++j) {
				if (listFrom.get(i).getName().equals(listTo.get(j).getName())) {
					flag = true;
					break loop;
				}
			}
		}

		if (flag) {
			List<ViewNode> tmp = listFrom.subList(0, i + 1);

			if (tmp.size() > 0) {
				tmp.remove(0);
			}

			result.put(BACK, tmp);
			tmp = listTo.subList(0, j);
			Collections.reverse(tmp);
			result.put(ENTER, tmp);
		} else {
			String msg = "there is no same node in path";
			LogUtil.log.error(msg);
			throw new RuntimeException(msg);
		}

		return result;
	}

	private List<ViewNode> getPathToRoot(ViewNode node) {
		List<ViewNode> path = new ArrayList<>();
		path.add(node);
		ViewNode parent = node.getParent();

		while (parent != null) {
			path.add(parent);
			parent = parent.getParent();
		}

		return path;
	}
}
