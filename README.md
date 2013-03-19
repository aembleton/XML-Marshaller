# XML Marshaller

This is a simple library for marshalling values from an XML file into POJOs using Xpaths.  There's still quite a bit of work to go on this, especially around supporting more types.  Currently only int, Integer, String and Boolean are supported.  Sets are supported and are instantiated as a HashSet.  Lists are instantiated as LinkedLists.

## Examples

With the following XML:

```
<rdr>
  <details>
    <detail>
        <name>version</name>
        <value>15.0</value>
    </detail>
    <detail>
        <name>resolution</name>
        <value>1080X1920</value>
    </detail>
  </details>
</rdr>
```

### Get the version and resolution values

Create the following POJO, and annotate as you can see below:

```
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
```

then, to trigger this make the following calls (where `XML_FILE` is the location of the file):

```
FileInputStream fin = new FileInputStream(XML_FILE);
XmlMarshaller<BasicBean> marshall = new XmlMarshaller<BasicBean>(BasicBean.class);
BasicBean bean = marshall.read(fin);

assert("15.0".equals(bean.getVersion()));
assert("1080X1920".equals(bean.getResolution));
```

XmlMarshaller.read takes an InputStream.  The asserts demonstrate what you'd see populated inside the bean.

### Get all of the names as a Set

Create the following POJO, and annotate as you can see below:

The fact that it is a Set of names can be found through reflection, but it is not possible to determine at runtime the generic type of Set.  This means that it is necessary to explicitly set the type using the `clazz` in the `@Xpath` annotation. 

```
public class StringSetBean {

	@Xpath(value="//name", clazz=String.class)
	private Set<String> names;
	
	public Set<String> getNames(){
		return names;
	}
}
```

then, to trigger this make the following calls (where `XML_FILE` is the location of the file):

```
FileInputStream fin = new FileInputStream(XML_FILE);
XmlMarshaller<StringSetBean> marshall = new XmlMarshaller<StringSetBean>(StringSetBean.class);
StringSetBean bean = marshall.read(fin);

assertEquals(2, bean.getNames().size());
			
for (String name : bean.getNames()) {
	assertTrue("version".equals(name)||"resolution".equals(name));
}
```

### Example of a nested Set
With the following XML:
```
<rdr>
  <details>
    <detail>
    	<sums>
	    	<sum>
	        	<name>version</name>
	        </sum>
	        <sum>
	        	<name>hello</name>
	        </sum>
	        <sum>
	        	<name>world</name>
	        </sum>
	        <value>15.0</value>
        </sums>
    </detail>
    <detail>
    	<sums>
    		<sum>
        		<name>resolution</name>
        	</sum>
        </sums>
        <value>1080X1920</value>
    </detail>
  </details>
</rdr>
```
we can create the following POJO to represent Detail:
```
public class DetailBean {

	@Xpath(value=".//name", clazz=String.class)
	private Set<String> names;
	
	public Set<String> getNames(){
		return names;
	}
}
```
Note, that the given XPath for the Set of names is `.//name`.  If you miss of the initial `.` then the names field will be populated with all names across the whole document.

The following POJO represents the whole document and contains a Set of DetailBean objects:
```
public class NestedStringSetBean {

	@Xpath(value = "//detail", clazz = DetailBean.class)
	private Set<DetailBean> details;

	public Set<DetailBean> getDetails() {
		return details;
	}
}
```
The marshalling is triggered in the usaul way (as in the previous examples) and the following is an example of a unit test for this where `XML_FILE` is the location of the XML input file:
```
XmlMarshaller<NestedStringSetBean> marshall = new XmlMarshaller<NestedStringSetBean>(NestedStringSetBean.class);

FileInputStream fin = new FileInputStream(XML_FILE);
NestedStringSetBean stringSetBean = marshall.read(fin);

assertEquals(2, stringSetBean.getDetails().size());

for (DetailBean detail : stringSetBean.getDetails()) {
	if (detail.getNames().size() == 1) {
		assertEquals("resolution", detail.getNames().toArray()[0]);
		continue;
	}
	if (detail.getNames().size() == 3) {
		// iterate over them
		for (String name : detail.getNames()) {
			assertTrue("version".equals(name) || "hello".equals(name) || "world".equals(name));
		}
		continue;
	}

	fail("Detail has " + detail.getNames().size() + " names");
}
```
