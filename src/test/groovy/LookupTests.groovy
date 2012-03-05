import org.mule.DefaultMuleMessage
import org.mule.api.MuleMessage
import org.mule.api.client.MuleClient
import org.mule.tck.FunctionalTestCase

//import org.mule.util.IOUtils;

class IntacctInvoiceTest extends FunctionalTestCase {





    protected String getConfigResources() {
        // TODO You'll need to edit this file to make the test applicable to
        // your module
        return "mule-config.xml";
    }



    public void testInsertRecord() throws Exception {

        MuleClient client = muleContext.getClient();

        //Create Customer
        def inputCustomerJSON = new File('src/test/resources/insertcustomer.json').getText()
        println inputCustomerJSON
        MuleMessage newCustomer = new DefaultMuleMessage(inputCustomerJSON, ['http.method': 'POST'], muleContext)
        MuleMessage resultCustomer = client.send("http://localhost:8881/customer", newCustomer)

        println 'Customer create result = '+resultCustomer.getPayloadAsString()

        def inputJSON = new File('src/test/resources/insertvalue.json').getText()
        println inputJSON
        MuleMessage mm = new DefaultMuleMessage(inputJSON, ['http.method': 'POST'], muleContext)
        MuleMessage result = client.send("http://localhost:8881/lookup", mm)

        def inputCooperhawkJSON = new File('src/test/resources/insertvalue2.json').getText()
        println inputCooperhawkJSON
        MuleMessage mmCH = new DefaultMuleMessage(inputCooperhawkJSON, ['http.method': 'POST'], muleContext)
        MuleMessage resultCH = client.send("http://localhost:8881/lookup", mmCH)


        //assertNotNull(result)
        //assertNull(result.getExceptionPayload())


        MuleMessage mmQ = new DefaultMuleMessage('', ['http.method': 'GET'], muleContext)
        MuleMessage resultQ = client.send("http://localhost:8881/lookup/iapp4hire/noodles/jobtitle?pm-jobtitle=manager", mmQ)
        println "Qresult = " + resultQ.getPayloadAsString()


        MuleMessage mmQallNoodles = new DefaultMuleMessage('', ['http.method': 'GET'], muleContext)
        MuleMessage resultQallNoodles = client.send("http://localhost:8881/lookup/iapp4hire/noodles", mmQallNoodles)
        println "resultQallNoodles = " + resultQallNoodles.getPayloadAsString()

        MuleMessage mmQNotFound = new DefaultMuleMessage('', ['http.method': 'GET'], muleContext)
        MuleMessage resultQNotFound = client.send("http://localhost:8881/lookup/iapp4hire/noodles/jobtitle?pm-jobtitle=dishwasher", mmQNotFound)
        println "Qresult not found = " + resultQNotFound.getPayloadAsString()

        def updateJSON = new File('src/test/resources/addvalue.json').getText()
        MuleMessage mmU = new DefaultMuleMessage(updateJSON, ['http.method': 'POST'], muleContext)
        MuleMessage resultU = client.send("http://localhost:8881/lookup/iapp4hire/noodles/jobtitle?pm-jobtitle=manager", mmU)
        println "Uresult = " + resultU.getPayloadAsString()


        MuleMessage mmQ2 = new DefaultMuleMessage('', ['http.method': 'GET'], muleContext)
        MuleMessage resultQ2 = client.send("http://localhost:8881/lookup/iapp4hire/noodles/jobtitle?pm-jobtitle=manager", mmQ2)
        println "Qresult2 = " + resultQ2.getPayloadAsString()


        MuleMessage mmDELnoodles = new DefaultMuleMessage('', ['http.method': 'DELETE'], muleContext)
        MuleMessage resultDELnoodles = client.send("http://localhost:8881/lookup/iapp4hire/noodles/jobtitle/"+result.getPayloadAsString(), mmDELnoodles)


        MuleMessage mmDELCH = new DefaultMuleMessage('', ['http.method': 'DELETE'], muleContext)
        MuleMessage resultDELCH = client.send("http://localhost:8881/lookup/iapp4hire/cooperhawk/jobtitle/"+resultCH.getPayloadAsString(), mmDELCH)


//        MuleMessage mmTMP = new DefaultMuleMessage('', ['http.method': 'DELETE'], muleContext)
//        MuleMessage resultTMP = client.send("http://localhost:8881/lookup/iapp4hire/cooperhawk/jobtitle/4f3e61df744ee23e3c7c5fa0", mmTMP)

        //Unable to verify that everything has been deleted via printout here


    }
    /*
    public void testRemoveRecord() throws Exception {

        MuleClient client = muleContext.getClient();

        def inputJSON = new File('src/test/resources/deletevalue.json').getText()
        println inputJSON
        MuleMessage mm = new DefaultMuleMessage('', ['http.method': 'DELETE'], muleContext)
        MuleMessage result = client.send("http://localhost:8881/lookup/4f3d41e2744ed1501e9d0123", mm)


        //assertNotNull(result)
        //assertNull(result.getExceptionPayload())


        System.out.println("result = " + result.getPayloadAsString())

        //String xml = IOUtils.toString(getClass().getResourceAsStream("/oagis-invoice-result001.xml"))

        // TODO Assert the correct data has been received
        //assertEquals(new File('src/test/resources/oagis-invoice-result-001.xml').getText(), result.getPayloadAsString())


    }
    */



}