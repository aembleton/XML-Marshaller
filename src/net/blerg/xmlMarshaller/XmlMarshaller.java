package net.blerg.xmlMarshaller;

import java.io.IOException;
import java.io.InputStream;
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

	private static final XPathFactory XPATH_FACTORY = XPathFactory
			.newInstance();
	private static final XPath NEW_XPATH = XPATH_FACTORY.newXPath();

	private Class<T> clazz;

	public XmlMarshaller(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T read(InputStream inputStream) throws InstantiationException,
			IllegalAccessException {

		T marshalledObject = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();

		Document document = getDocument(inputStream);

		for (Field field : fields) {
			Xpath xpath = field.getAnnotation(Xpath.class);
			if (xpath != null) {
				field.setAccessible(true);
				Object fieldValue = null;

				Class<?> fieldType = field.getType();
				if (fieldType.equals(String.class)) {
					fieldValue = evaluateAsString(xpath.value(), document);
				} else if (fieldType.equals(Boolean.class)
						|| fieldType.equals(Boolean.TYPE)) {
					fieldValue = evaluateAsBoolean(xpath.value(), document);
				} else if (fieldType.equals(Integer.class)) {
					fieldValue = evaluateAsInteger(xpath.value(), document);
				} else if (fieldType.equals(Integer.TYPE)) {
					Integer intValue = evaluateAsInteger(xpath.value(),
							document);
					if (intValue != null) {
						// got an int value, so set that
						fieldValue = intValue.intValue();
					}
				}

				if (fieldValue != null) {
					//only set the field value if it is not null
					field.set(marshalledObject, fieldValue);
				}
			}
		}

		return marshalledObject;
	}

	private String evaluateAsString(String xpath, Document document) {
		String result = "";

		try {
			XPathExpression expression = NEW_XPATH.compile(xpath);
			result = (String) expression.evaluate(document,
					XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result == null ? null : result.trim();
	}

	private boolean evaluateAsBoolean(String xpath, Document document) {
		return "1".equals(evaluateAsString(xpath, document));
	}

	/**
	 * Evaluates the xpath to get an Integer returned. If an Integer cannot be
	 * returned then null is returned.
	 * 
	 * @param xpath
	 *            The Xpath to evaluate
	 * @param document
	 *            The document to evaluate against
	 * @return An Integer if one is found, otherwise null
	 */
	private Integer evaluateAsInteger(String xpath, Document document) {
		String str = evaluateAsString(xpath, document);

		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private Document getDocument(InputStream inputStream) {
		Document document = null;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
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
