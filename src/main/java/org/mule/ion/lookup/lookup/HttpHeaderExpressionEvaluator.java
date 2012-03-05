package org.mule.ion.lookup.lookup;

import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.context.MuleContextAware;
import org.mule.api.expression.ExpressionEvaluator;
import org.mule.api.transport.PropertyScope;
import org.mule.expression.ExpressionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Will process an expression string that can contain other expressions
 */
public class HttpHeaderExpressionEvaluator implements ExpressionEvaluator,
        MuleContextAware {
    public static final String NAME = "http-header";

    private MuleContext context;

    public void setMuleContext(MuleContext context) {
        this.context = context;
    }

    public Object evaluate(String expression, MuleMessage message) {
        if (expression.equals("")) {
            final Map<String, String> qps = new TreeMap<String, String>();
            String[] query = message.getInboundProperty("http.request").toString().split("\\?");
            if (query.length > 1) {
                final StringTokenizer pairs = new StringTokenizer(query[1], "&");
                while (pairs.hasMoreTokens()) {
                    final String pair = pairs.nextToken();
                    final StringTokenizer parts = new StringTokenizer(pair, "=");
                    try {
                        final String name = URLDecoder.decode(parts.nextToken(), "ISO-8859-1");
                        final String value = URLDecoder.decode(parts.nextToken(), "ISO-8859-1");

                        qps.put(name, value);
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }

            }

            return (Object) qps;
        } else {
            String result = (String) ExpressionUtils.getProperty(expression,
                    PropertyScope.INBOUND, message);

            if (result != null) {
                return URLDecoder.decode(result);
            } else {

                return null;
            }
        }
    }

    public Object evaluate(String expression, MuleMessage message,
                           boolean throww) {

        return URLDecoder.decode((String) ExpressionUtils.getProperty(
                expression, PropertyScope.INBOUND, message));
    }

    /**
     * Gts the name of the object
     *
     * @return the name of the object
     */
    public String getName() {
        return NAME;
    }

    /**
     * Sets the name of the object
     *
     * @param name the name of the object
     */
    public void setName(String name) {
        throw new UnsupportedOperationException();
    }
}
