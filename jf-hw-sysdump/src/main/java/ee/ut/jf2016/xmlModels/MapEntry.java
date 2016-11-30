package ee.ut.jf2016.xmlModels;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by Erdem on 9/28/2016.
 */
public class MapEntry {
    public MapEntry() {
    }

    @XmlAttribute
    public String key;

    @XmlValue
    public String value;

    public MapEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
