package mobile.test.base;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import up.light.FolderType;
import up.light.Setting;
import up.light.io.FileSystemResource;
import up.light.utils.DataIterator;

public class TestDataProvider {
	private static FileSystemResource mResource;

	@DataProvider(name = "dp")
	public static DataIterator data(ITestContext t, Method m) {
		String vClassName = m.getDeclaringClass().getSimpleName();
		String vSheetName = t.getCurrentXmlTest().getParameter(vClassName);

		if (mResource == null) {
			String p = Setting.getPath(FolderType.DATA);
			mResource = new FileSystemResource(p + "data.xls");
		}

		return new DataIterator(mResource, vSheetName);
	}
}
