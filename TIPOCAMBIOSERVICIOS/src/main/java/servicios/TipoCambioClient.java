/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

/**
 *
 * @author Israael Gatica
 */
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.SoapMessage;


@Service
public class TipoCambioClient {

    private final WebServiceTemplate webServiceTemplate;

    public TipoCambioClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public String obtenerTipoCambio() {
        // Define la estructura del mensaje SOAP en XML
        String request = 
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://www.banguat.gob.gt/variables/ws/\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                    "<web:TipoCambioDia/>" + 
                "</soapenv:Body>" +
            "</soapenv:Envelope>";

        // URL del servicio SOAP del Banco de Guatemala
        String url = "https://www.banguat.gob.gt/variables/ws/TipoCambio.asmx?wsdl"; 

        // Realiza la solicitud al servicio SOAP y recibe la respuesta
        String response = (String) webServiceTemplate.marshalSendAndReceive(url, request, new WebServiceMessageCallback() {
            @Override
            public void doWithMessage(org.springframework.ws.WebServiceMessage message) {
                SoapMessage soapMessage = (SoapMessage) message;
                soapMessage.setSoapAction("http://www.banguat.gob.gt/variables/ws/TipoCambioDia");
            }
        });

        return (response != null) ? response : "No se recibió respuesta del servicio SOAP.";
    }
}