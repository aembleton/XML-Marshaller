package net.blerg.xmlMarshaller.test.basic;

import net.blerg.xmlMarshaller.Xpath;

public class BasicBean {

	@Xpath("//detail[name='version']/value")
	private String version;
	
	@Xpath("//detail[name='resolution']/value")
	private String resolution;

	public String getVersion() {
		return version;
	}

	public String getResolution() {
		return resolution;
	}
	
	

}
