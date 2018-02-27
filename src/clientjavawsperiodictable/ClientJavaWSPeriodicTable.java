package clientjavawsperiodictable;

import com.sun.org.apache.xml.internal.serialize.Serializer;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ClientJavaWSPeriodicTable {

    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
        //Serializer serializer = new Serializer();
        Scanner sc = new Scanner(System.in);
        System.out.println("Seleccione que desea hacer: " +"\n1. getAtomicNumber\n"
                + "2. getAtomicWeight\n3. getAtoms\n4. getElementSymbol");
        
        int option = sc.nextInt();
        
        if(option == 1){
            System.out.println("Introduzca el nombre del elemento: ");
            Scanner sc2 = new Scanner(System.in);
            String element = sc2.nextLine();
            xmlReaderDOM(element, getAtomicNumber(element), 0);
            //System.out.println("Numero atomico de " + element + " : " +atomicNumber);
            
        }else if(option == 2){
            System.out.println("Introduzca el nombre del elemento: ");
            Scanner sc3 = new Scanner(System.in);
            String element2 = sc3.nextLine();
            xmlReaderDOM(element2, getAtomicWeight(element2), 1);
            //System.out.println("Peso atomico de " + element2 + " : " + atomicWeight);
            
        }else if(option == 3){
            System.out.println("Listado de todos los atomos: ");
            xmlReaderDOM("nothing", getAtoms(), 2);   
            
        }else if (option == 4){
            System.out.println("Introduzca el nombre del elemento");
            Scanner sc4 = new Scanner(System.in);
            String element3 = sc4.nextLine();
            xmlReaderDOM(element3, getElementSymbol(element3), 3);
            //System.out.println("Simbolo: " + symbol);
            
        }else{
            System.out.println("No ha introuducido una opción valida");
        }
    }

    private static String getAtomicNumber(java.lang.String elementName) {
        net.webservicex.Periodictable service = new net.webservicex.Periodictable();
        net.webservicex.PeriodictableSoap port = service.getPeriodictableSoap();
        return port.getAtomicNumber(elementName);
    }

    private static String getAtomicWeight(java.lang.String elementName) {
        net.webservicex.Periodictable service = new net.webservicex.Periodictable();
        net.webservicex.PeriodictableSoap port = service.getPeriodictableSoap();
        return port.getAtomicWeight(elementName);
    }

    private static String getAtoms() {
        net.webservicex.Periodictable service = new net.webservicex.Periodictable();
        net.webservicex.PeriodictableSoap port = service.getPeriodictableSoap();
        return port.getAtoms();
    }

    private static String getElementSymbol(java.lang.String elementName) {
        net.webservicex.Periodictable service = new net.webservicex.Periodictable();
        net.webservicex.PeriodictableSoap port = service.getPeriodictableSoap();
        return port.getElementSymbol(elementName);
    }
    
    private static void xmlReaderDOM (String text, String xmlRecords, int option) throws SAXException, IOException, ParserConfigurationException{

        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xmlRecords));

        org.w3c.dom.Document doc = db.parse(is);
        NodeList nodes = doc.getElementsByTagName("Table");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
           
            if(option == 0){
                NodeList number = element.getElementsByTagName("AtomicNumber");
                Element line = (Element) number.item(0);
                System.out.println("AtomicNumber: " + getCharacterDataFromElement(line));
            }else if (option == 1){
                NodeList name = element.getElementsByTagName("AtomicWeight");
                Element line = (Element) name.item(0);
                System.out.println("AtomicWeight: " + getCharacterDataFromElement(line));
            }else if(option == 2){
                NodeList names = element.getElementsByTagName("ElementName");
                for(int j = 0; j < names.getLength(); j++){
                    Element name = (Element) names.item(j);
                System.out.println("ElementName: " + getCharacterDataFromElement(name));
                }
                
            }else if(option == 3){
                NodeList symbol = element.getElementsByTagName("Symbol");
                Element line = (Element) symbol.item(0);
                System.out.println("Symbol: " + getCharacterDataFromElement(line));
            }

        }
    }
    
    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";

    }
    
}
