package net.blerg.xmlMarshaller.test.integer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import net.blerg.xmlMarshaller.XmlMarshaller;

import org.junit.Test;

import junit.framework.TestCase;

public class IntegerTest extends TestCase{

	private static final String XML_FILE = "testResources"+File.separator+"basic.xml";
	
	@Test
	public void testBasic() {
		XmlMarshaller<IntegerBean> marshall = new XmlMarshaller<IntegerBean>(IntegerBean.class);
		try {
			FileInputStream fin = new FileInputStream(XML_FILE);
			IntegerBean bean =marshall.read(fin);
			
			assertEquals(new Integer(15), bean.getVersion());
			assertEquals(1080, bean.getHeight());
			assertEquals(1920, bean.getWidth());
			
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
