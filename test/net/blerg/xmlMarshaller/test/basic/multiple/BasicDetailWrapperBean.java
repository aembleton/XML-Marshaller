package net.blerg.xmlMarshaller.test.basic.multiple;

import java.util.List;

import net.blerg.xmlMarshaller.Xpath;

public class BasicDetailWrapperBean {

	@Xpath(value="//detail", clazz=DetailBean.class)
	private List<DetailBean> details;

	public List<DetailBean> getDetails() {
		return details;
	}
	
	
}
