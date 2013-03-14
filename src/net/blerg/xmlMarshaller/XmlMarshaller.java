package net.blerg.xmlMarshaller;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlMarshaller<T> {

	private Class<T> clazz;

	public XmlMarshaller(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T read(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException, InstantiationException, IllegalAccessException, XPathExpressionException {

		Document document = getDocument(inputStream);

		return readNode(document);
	}

	private T readNode(Node node) throws InstantiationException, IllegalAccessException, XPathExpressionException {
		
		if (clazz.equals(String.class)) {
			return (T) node.getTextContent();
		} else if (clazz.equals(Integer.class) || clazz.equals(Integer.TYPE)) {
			return (T) Integer.valueOf(node.getTextContent());
		}
		
		T marshalledObject = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath factoryXpath = xPathFactory.newXPath();

		for (Field field : fields) {
			Xpath xpath = field.getAnnotation(Xpath.class);
			if (xpath != null) {
				field.setAccessible(true);
				Object fieldValue = null;

				Class<?> fieldType = field.getType();
				if (fieldType.equals(String.class)) {
					fieldValue = evaluateAsString(xpath.value(), node, factoryXpath);
				} else if (fieldType.equals(Integer.class)) {
					fieldValue = evaluateAsInteger(xpath.value(), node, factoryXpath);
				} else if (fieldType.equals(Integer.TYPE)) {
					Integer value = evaluateAsInteger(xpath.value(), node, factoryXpath);
					if (value != null) {
						fieldValue = value;
					}
				} else if (fieldType.equals(Boolean.class) || fieldType.equals(Boolean.TYPE)) {
					fieldValue = evaluateAsBoolean(xpath.value(), node, factoryXpath);
				} else if (fieldType.equals(List.class)) {
					// this is a list, so need to find child matches for this
					fieldValue = evaluateAsList(xpath.value(), node, factoryXpath, xpath.clazz());
				}else if (fieldType.equals(Set.class)) {
					// this is a set, so need to find child matches for this
					fieldValue = evaluateAsSet(xpath.value(), node, factoryXpath, xpath.clazz());
				}
				field.set(marshalledObject, fieldValue);
			}
		}

		return marshalledObject;
	}

	private Integer evaluateAsInteger(String xpath, Node node, XPath factoryXpath) throws XPathExpressionException {
		String asString = evaluateAsString(xpath, node, factoryXpath);
		try {
			return Integer.parseInt(asString);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private String evaluateAsString(String xpath, Node node, XPath factoryXpath) throws XPathExpressionException {
		String result = "";
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath factoryXpath2 = xPathFactory.newXPath();
		XPathExpression expression = factoryXpath2.compile(xpath);

		// Need to clone a node so that it loses the remainder of the document
		Node clonedNode = node.cloneNode(true);
		result = (String) expression.evaluate(clonedNode, XPathConstants.STRING);

		return result == null ? null : result.trim();
	}

	private boolean evaluateAsBoolean(String xpath, Node node, XPath factoryXpath) throws XPathExpressionException {
		return "1".equals(evaluateAsString(xpath, node, factoryXpath));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <CT> List<CT> evaluateAsList(String xpath, Node node, XPath factoryXpath, Class<CT> type) {
		Class<LinkedList> collectionType = LinkedList.class;
		List<CT> list = new LinkedList<CT>();
		try {
			list = evaluateAsCollection(xpath, node, factoryXpath, collectionType, type);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <CT> Set<CT> evaluateAsSet(String xpath, Node node, XPath factoryXpath, Class<CT> type) {
		Class<HashSet> collectionType = HashSet.class;
		Set<CT> set = new HashSet<CT>();
		try {
			set = evaluateAsCollection(xpath, node, factoryXpath, collectionType, type);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return set;
	}

	private <CT, C extends Collection<CT>> C evaluateAsCollection(String xpath, Node node, XPath factoryXpath, Class<C> collectionType, Class<CT> type) throws XPathExpressionException,
			InstantiationException, IllegalAccessException {
		C collection = collectionType.newInstance();

		NodeList nodes = (NodeList) factoryXpath.evaluate(xpath, node, XPathConstants.NODESET);
		XmlMarshaller<CT> marshall = new XmlMarshaller<CT>(type);

		for (int i = 0; i < nodes.getLength(); i++) {
			Node childNode = nodes.item(i);
			try {
				CT element = marshall.readNode(childNode);
				collection.add(element);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

		return collection;
	}

	private Document getDocument(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
		Document document = null;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.parse(inputStream);

		return document;
	}
}
