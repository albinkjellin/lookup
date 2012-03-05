import org.mule.DefaultMuleMessage
import org.mule.api.MuleMessage
import org.mule.api.client.MuleClient
import org.mule.tck.FunctionalTestCase

//import org.mule.util.IOUtils;

class LoadTest extends FunctionalTestCase {


    protected String getConfigResources() {
        // TODO You'll need to edit this file to make the test applicable to
        // your module
        return "mule-config.xml";
    }



    public void testInsertRecord() throws Exception {

        MuleClient client = muleContext.getClient();

        def inputJSON = new File('src/test/resources/insertvalue.json').getText()
        println inputJSON
        100.each {
            MuleMessage mm = new DefaultMuleMessage(inputJSON, ['http.method': 'POST'], muleContext)
            MuleMessage result = client.send("http://localhost:8881/lookup", mm)
        }


    }


}