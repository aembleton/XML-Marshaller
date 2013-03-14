package net.blerg.xmlMarshaller.test.basic.multiple;

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

public class BasicTest extends TestCase {

	private static final String XML_FILE = "testResources" + File.separator + "basic.xml";

	@Test
	public void testBasic() {
		XmlMarshaller<BasicDetailWrapperBean> marshall = new XmlMarshaller<BasicDetailWrapperBean>(BasicDetailWrapperBean.class);
		try {
			FileInputStream fin = new FileInputStream(XML_FILE);
			BasicDetailWrapperBean basicDetailWrapperBean = marshall.read(fin);

			assertEquals(2, basicDetailWrapperBean.getDetails().size());

			DetailBean detailBean1 = basicDetailWrapperBean.getDetails().get(0);
			DetailBean detailBean2 = basicDetailWrapperBean.getDetails().get(1);

			assertNotNull(detailBean1);
			assertEquals("version", detailBean1.getName());
			assertEquals("15.0", detailBean1.getValue());

			assertNotNull(detailBean2);
			assertEquals("resolution", detailBean2.getName());
			assertEquals("1080X1920", detailBean2.getValue());

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
