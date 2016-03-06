import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class getInchi {
	
	public static String getInchi(String name) throws Exception
	{
		final String url1 = "http://cactus.nci.nih.gov/chemical/structure/";
		final String url2 = "/stdinchi/xml";
		String accessurl = url1 + name + url2;
		
		//Connect to cactus website
		URLConnection cactusconnection = new URL(accessurl).openConnection();
		
		//Get input stream.
		InputStream cactusstream = cactusconnection.getInputStream();
		
		//Create document builder factory.
		DocumentBuilderFactory xmlCactus = DocumentBuilderFactory.newInstance();
		
		//Create document builder
		DocumentBuilder inchibuilder = xmlCactus.newDocumentBuilder();
		
		//Create document through the input stream from Cactus server.
		Document inchidoc = inchibuilder.parse(cactusstream);
		
		//Use XPath to get InChI
		XPathFactory inchifactory = XPathFactory.newInstance();
		
		//Create XPath
		XPath inchipath = inchifactory.newXPath();
		
		String expression = "/request/data[1]/item[1]/text()";
		
		NodeList nodelist = (NodeList) inchipath.evaluate(expression, inchidoc, XPathConstants.NODESET);
		
		String inchikey = nodelist.item(0).getNodeValue();
		
		return inchikey;
		
	}
	
	public static void main(String[] args) {
		try{
		getInchi("acetone");
		}
		catch(Exception e)
		{
			System.out.println("InChI Unobtainable");
		}
	}
	
	
	
	

}
