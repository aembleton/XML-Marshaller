package net.blerg.xmlMarshaller.test.bool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import net.blerg.xmlMarshaller.XmlMarshaller;

import org.junit.Test;

import junit.framework.TestCase;

public class BooleanTest extends TestCase{

	private static final String XML_FILE = "testResources"+File.separator+"boolean.xml";
	
	@Test
	public void testBoolean() {
		XmlMarshaller<BooleanBean> marshall = new XmlMarshaller<BooleanBean>(BooleanBean.class);
		try {
			FileInputStream fin = new FileInputStream(XML_FILE);
			BooleanBean bean =marshall.read(fin);
			
			assertEquals("1234",bean.getAlias());
			assertEquals("15.0", bean.getVersion());
			assertEquals("1080X1920", bean.getResolution());
			assertTrue(bean.isInstalled());
			assertFalse(bean.isEnabled());
			
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
