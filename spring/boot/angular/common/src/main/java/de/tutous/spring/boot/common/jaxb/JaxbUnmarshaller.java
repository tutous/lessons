package de.tutous.spring.boot.common.jaxb;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.validation.Schema;

import org.xml.sax.SAXException;

public class JaxbUnmarshaller
{

    /**
     * Wandelt XML in Object
     * 
     * @param in
     *            Xml inpout stream
     * @param unmarshalClass
     *            Ziel-Klasse
     * @param validationEventHandler
     *            Optinaler Validation Event Handler (kann null sein)
     * @return Instanz der Zielklasse
     * @throws JAXBException
     * @throws SAXException
     */
    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(InputStream in, Class<T> unmarshalClass,
            ValidationEventHandler validationEventHandler, Schema schema) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(unmarshalClass);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        if (schema != null)
            jaxbUnmarshaller.setSchema(schema);

        if (validationEventHandler != null)
            jaxbUnmarshaller.setEventHandler(validationEventHandler);

        return (T) jaxbUnmarshaller.unmarshal(in);
    }

    /**
     * Wandelt XML in Object
     * 
     * @param <T>
     * @param bytesIn
     *            Xml inpout stream
     * @param unmarshalClass
     *            Ziel-Klasse
     * @return Instanz der Zielklasse
     * @throws JAXBException
     */
    public static <T> T unmarshal(byte[] bytesIn, Class<T> unmarshalClass,
            ValidationEventHandler validationEventHandler, Schema schema) throws JAXBException
    {
        return unmarshal(new ByteArrayInputStream(bytesIn), unmarshalClass, validationEventHandler, schema);
    }

}
