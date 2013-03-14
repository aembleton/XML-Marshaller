package net.blerg.xmlMarshaller.test.basic.multiple;

import net.blerg.xmlMarshaller.Xpath;

public class DetailBean {

	@Xpath("//name")
	private String name;
	
	@Xpath("//value")
	private String value;

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
}
