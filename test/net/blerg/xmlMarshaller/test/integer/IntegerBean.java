package net.blerg.xmlMarshaller.test.integer;

import net.blerg.xmlMarshaller.Xpath;

public class IntegerBean {

	@Xpath("//detail[name='version']/value")
	private Integer version;

	@Xpath("//detail[name='width']/value")
	private int width;

	@Xpath("//detail[name='height']/value")
	private int height;

	public Integer getVersion() {
		return version;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
