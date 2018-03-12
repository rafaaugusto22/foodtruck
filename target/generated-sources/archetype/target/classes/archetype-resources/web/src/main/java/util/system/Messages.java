#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.util.system;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
    
    private static final String BUNDLE_NAME = "SystemMessages"; //${symbol_dollar}NON-NLS-1${symbol_dollar}

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private Messages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
