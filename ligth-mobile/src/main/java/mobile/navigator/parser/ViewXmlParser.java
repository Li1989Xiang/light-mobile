package mobile.navigator.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import up.light.FolderType;
import up.light.Setting;
import up.light.utils.LogUtil;

public class ViewXmlParser {

	public static void parse() {
		InputStream ins = null;
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		String file = Setting.getPath(FolderType.CONFIGURATION) + "view.xml";

		try {
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema s = sf.newSchema(ViewXmlParser.class.getResource("view.xsd"));
			factory.setSchema(s);

			SAXParser parser = factory.newSAXParser();
			ins = new FileInputStream(file);
			ViewXmlHandler handler = new ViewXmlHandler();
			parser.parse(ins, handler);
		} catch (Exception e) {
			LogUtil.log.error("[ViewXml] parse xml error", e);
			throw new RuntimeException(e);
		} finally {
			try {
				if (ins != null)
					ins.close();
			} catch (IOException e) {
				LogUtil.log.error("[ViewXml] close xml error", e);
				throw new RuntimeException(e);
			}
		}
	}
}
