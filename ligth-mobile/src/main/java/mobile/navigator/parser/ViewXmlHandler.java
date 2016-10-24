package mobile.navigator.parser;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import mobile.navigator.INavigationHandler;
import mobile.navigator.ViewNavigator;
import mobile.navigator.ViewNode;
import mobile.navigator.ViewTree;
import up.light.exceptions.LightReflectionException;
import up.light.pagefactory.TestElement;
import up.light.repository.LocatorBean;
import up.light.repository.Repository;
import up.light.utils.ArgumentUtil;
import up.light.utils.ClassUtil;
import up.light.utils.Stack;

public class ViewXmlHandler extends DefaultHandler {
	private ViewTree tree = new ViewTree();
	private Stack<String> names = new Stack<>();
	private Stack<ViewNode> nodes = new Stack<>();
	private boolean disable;
	private int disableDepth;
	private int groupDepth;
	private String groupName;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		names.push(qName);

		if ("application".equals(qName)) {
			ViewNode node = new ViewNode(null, "ROOT", 0, null, null);
			tree.addNode(node);
			ViewNavigator.setTree(tree);
			nodes.push(node);
		} else if ("view".equals(qName)) {
			ViewNode node = null;

			if (!isDisabled(attributes)) {
				String name = getValue(attributes, "name", true, null);
				TestElement element = getElement(getValue(attributes, "repo", true, null));
				INavigationHandler handler = getHandler(attributes.getValue("handler"));
				node = new ViewNode(nodes.peek(), name, nodes.size(), element, handler);
				tree.addNode(node);

				if (nodes.size() == groupDepth) {
					groupName = name;
				} else if (nodes.size() > groupDepth) {
					node.setGroupName(groupName);
				}
			}

			nodes.push(node);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		names.pop();
		if (qName.equals("view")) {
			nodes.pop();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String name = names.peek();

		if ("hookLoginDepth".equals(name)) {
			ViewNode.getLoginer().setHookLoginDepth(Integer.valueOf(new String(ch, start, length)));
		} else if ("loginGroupDepth".equals(name)) {
			groupDepth = Integer.valueOf(new String(ch, start, length));
		}
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		throw new RuntimeException(e);
	}

	private String getValue(Attributes attributes, String name, boolean required, String defaultValue) {
		String v = attributes.getValue(name);

		if (StringUtils.isBlank(v)) {
			if (required) {
				throw new RuntimeException(name + " must be set");
			} else {
				v = defaultValue;
			}
		}

		return v;
	}

	private boolean isDisabled(Attributes attributes) {
		// children
		if (disable && nodes.size() > disableDepth) {
			return true;
		}

		// self
		Boolean b = Boolean.valueOf(getValue(attributes, "enable", false, "true"));
		if (Boolean.FALSE.equals(b)) {
			disable = true;
			disableDepth = nodes.size();
			return true;
		}

		disable = false;
		return false;
	}

	private TestElement getElement(String repo) {
		String[] arr = repo.split("\\.");
		ArgumentUtil.isEqual(arr.length, 2, "format error: " + repo);
		LocatorBean bean = Repository.getLocatorBean(arr[0], arr[1]);
		return new TestElement(bean.getBys().get(0), bean.getIn());
	}

	private INavigationHandler getHandler(String name) {
		if (name == null)
			return null;

		INavigationHandler h = null;
		try {
			Class<?> clazz = ClassUtil.getClass(name);
			h = (INavigationHandler) ClassUtil.getInstance(clazz);
		} catch (LightReflectionException e) {
			throw new RuntimeException(e.getCause());
		}
		return h;
	}
}
