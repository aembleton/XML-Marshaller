package net.blerg.xmlMarshaller.test.bool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import net.blerg.xmlMarshaller.XmlMarshaller;

import org.junit.Test;
import org.xml.sax.SAXException;

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
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
