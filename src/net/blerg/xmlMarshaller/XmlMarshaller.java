package net.blerg.xmlMarshaller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlMarshaller<T> {

	private Class<T> clazz;

	public XmlMarshaller(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T read(InputStream inputStream) throws InstantiationException, IllegalAccessException {

		T marshalledObject = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();
		
		Document document = getDocument(inputStream);
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath factoryXpath = xPathFactory.newXPath();

		for (Field field : fields) {
			Xpath xpath = field.getAnnotation(Xpath.class);
			if (xpath != null) {
				field.setAccessible(true);
				Object fieldValue = null;

				Class<?> fieldType = field.getType();
				if (fieldType.equals(String.class)) {
					fieldValue = evaluateAsString(xpath.value(), document, factoryXpath);
				} else if (fieldType.equals(Boolean.class) || fieldType.equals(Boolean.TYPE)) {
					fieldValue = evaluateAsBoolean(xpath.value(), document, factoryXpath);

				}
				field.set(marshalledObject, fieldValue);
			}
		}

		return marshalledObject;
	}

	private String evaluateAsString(String xpath, Document document, XPath factoryXpath) {
		String result = "";

		try {
			XPathExpression expression = factoryXpath.compile(xpath);
			result = (String) expression.evaluate(document, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result == null ? null : result.trim();
	}

	private boolean evaluateAsBoolean(String xpath, Document document, XPath factoryXpath) {
		return "1".equals(evaluateAsString(xpath, document, factoryXpath));
	}

	private Document getDocument(InputStream inputStream) {
		Document document = null;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(inputStream);
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

		return document;
	}
}
