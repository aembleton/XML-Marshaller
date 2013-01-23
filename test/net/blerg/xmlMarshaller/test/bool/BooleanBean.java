package net.blerg.xmlMarshaller.test.bool;

import net.blerg.xmlMarshaller.Xpath;

public class BooleanBean {

	@Xpath("//alias")
	private String alias;
	
	@Xpath("//detail[name='version']/value")
	private String version;

	@Xpath("//detail[name='resolution']/value")
	private String resolution;

	@Xpath("//detail[name='installed']/value")
	private boolean installed;

	@Xpath("//detail[name='enabled']/value")
	private boolean enabled;

	public String getAlias() {
		return alias;
	}
	
	public String getVersion() {
		return version;
	}

	public String getResolution() {
		return resolution;
	}

	public boolean isInstalled() {
		return installed;
	}

	public boolean isEnabled() {
		return enabled;
	}

}
