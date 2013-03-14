package net.blerg.xmlMarshaller.test.basic.set.string;

import java.util.Set;

import net.blerg.xmlMarshaller.Xpath;

public class StringSetBean {

	@Xpath(value="//name", clazz=String.class)
	private Set<String> names;
	
	public Set<String> getNames(){
		return names;
	}
}
