package net.blerg.xmlMarshaller.test.basic.single;

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

public class BasicMultipleTest extends TestCase{

	private static final String XML_FILE = "testResources"+File.separator+"basic.xml";
	
	@Test
	public void testBasic() {
		XmlMarshaller<BasicBean> marshall = new XmlMarshaller<BasicBean>(BasicBean.class);
		try {
			FileInputStream fin = new FileInputStream(XML_FILE);
			BasicBean bean =marshall.read(fin);
			
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
