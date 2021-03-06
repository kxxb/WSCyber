
package com.cyber.sms.cyberws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "CyberWSService", targetNamespace = "http://sms.cyber.com/CyberWS", wsdlLocation = "file:/C:/Documents%20and%20Settings/peter.s/\u041c\u043e\u0438%20\u0434\u043e\u043a\u0443\u043c\u0435\u043d\u0442\u044b/NetBeansProjects/03.06/CyberWS/src/conf/xml-resources/web-services/CyberWS/wsdl/CyberWS.wsdl")
public class CyberWSService
    extends Service
{

    private final static URL CYBERWSSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.cyber.sms.cyberws.CyberWSService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.cyber.sms.cyberws.CyberWSService.class.getResource(".");
            url = new URL(baseUrl, "file:/C:/Documents%20and%20Settings/peter.s/\u041c\u043e\u0438%20\u0434\u043e\u043a\u0443\u043c\u0435\u043d\u0442\u044b/NetBeansProjects/03.06/CyberWS/src/conf/xml-resources/web-services/CyberWS/wsdl/CyberWS.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'file:/C:/Documents%20and%20Settings/peter.s/\u041c\u043e\u0438%20\u0434\u043e\u043a\u0443\u043c\u0435\u043d\u0442\u044b/NetBeansProjects/03.06/CyberWS/src/conf/xml-resources/web-services/CyberWS/wsdl/CyberWS.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        CYBERWSSERVICE_WSDL_LOCATION = url;
    }

    public CyberWSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CyberWSService() {
        super(CYBERWSSERVICE_WSDL_LOCATION, new QName("http://sms.cyber.com/CyberWS", "CyberWSService"));
    }

    /**
     * 
     * @return
     *     returns CyberWSPortType
     */
    @WebEndpoint(name = "CyberWSPort")
    public CyberWSPortType getCyberWSPort() {
        return super.getPort(new QName("http://sms.cyber.com/CyberWS", "CyberWSPort"), CyberWSPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CyberWSPortType
     */
    @WebEndpoint(name = "CyberWSPort")
    public CyberWSPortType getCyberWSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://sms.cyber.com/CyberWS", "CyberWSPort"), CyberWSPortType.class, features);
    }

}
