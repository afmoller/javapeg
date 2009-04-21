package moller.javapeg.program;
/**
 * This class was created : 2009-02-27 by Fredrik Möller
 * Latest changed         : 
 */

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

public class PropertiesExtended extends Properties {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Hashtable<String, String> getKeys (String key) {
        Hashtable<String, String> h = new Hashtable<String, String>();
        Set<Object> s = new HashSet<Object>();
        s = super.keySet();

        for (Object keyValue : s) {
            if (keyValue.toString().startsWith(key)) {
                h.put(keyValue.toString(), super.getProperty(keyValue.toString()));
            }
        }
        return h;
    }
}