package net.blerg.xmlMarshaller.test.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import net.blerg.xmlMarshaller.XmlMarshaller;

import org.junit.Test;

import junit.framework.TestCase;

public class BasicTest extends TestCase{

	private static final String XML_FILE = "testResources"+File.separator+"basic.xml";
	
	@Test
	public void testBasic() {
		XmlMarshaller<BasicBean> marshall = new XmlMarshaller<BasicBean>(BasicBean.class);
		try {
			FileInputStream fin = new FileInputStream(XML_FILE);
			BasicBean bean = marshall.read(fin);
			
			assertEquals("15.0", bean.getVersion());
			assertEquals("1080X1920", bean.getResolution());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

}
