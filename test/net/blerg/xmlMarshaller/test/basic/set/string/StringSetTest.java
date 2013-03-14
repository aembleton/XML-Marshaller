package net.blerg.xmlMarshaller.test.basic.set.string;

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

public class StringSetTest extends TestCase {

	private static final String XML_FILE = "testResources" + File.separator + "stringSet.xml";

	@Test
	public void testBasic() {
		XmlMarshaller<StringSetBean> marshall = new XmlMarshaller<StringSetBean>(StringSetBean.class);
		try {
			FileInputStream fin = new FileInputStream(XML_FILE);
			StringSetBean stringSetBean = marshall.read(fin);

			assertEquals(2, stringSetBean.getNames().size());
			
			for (String name : stringSetBean.getNames()) {
				assertTrue("version".equals(name)||"resolution".equals(name));
			}

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
			fail();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

}
