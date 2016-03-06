//Major thanks to Gunther on StackOverflow for how to import XML in Java
//Major thanks to GK27 on StackOverflow for how to get attributes from XML code


import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xpath.*;
import javax.swing.JFrame;
import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.util.Stack;
import java.util.Scanner;
import org.jfree.ui.RefineryUtilities;

public class NMR {

    public String inchi;
    public String accessurl;
    public String spectraselect;
    public NodeList xpoints;
    public SpectrumRenderV2 renderer;
    Stack<Double> xvalues = new Stack<Double>();
    public NodeList signals;

    //Precondition- Valid Smiles Code, Spectra Type (13C, 1H)
    public NMR(String name, String SpectraType) {
    	try {
        inchi = getInchi.getInchi(name);
    	}
    	catch(Exception e)
    	{
    		System.out.println("InChI Unobtainable / Invalid Name");
    	}
    	
    	
        spectraselect = SpectraType;

        InchIEncoder accessinchi = new InchIEncoder(inchi);
        
        

        accessurl = "http://nmrshiftdb.nmr.uni-koeln.de/NmrshiftdbServlet/nmrshiftdbaction/exportcmlbyinchi/inchi/" + accessinchi.Encode() + "/spectrumtype/" + spectraselect;

        System.out.println(accessurl);

        try {
            xpoints = SpectraLookup.lookup(accessurl);
            signals = SpectraLookup.lookup(accessurl);
        }
        catch(Exception e)
        {
            System.out.println("Invalid InChI Key.");
        }
        
        //Place xpoints into a stack
        for (int i = 0; i < signals.getLength(); i++) {
            Node currentItem = signals.item(i);
            
            String key = currentItem.getAttributes().getNamedItem("xValue").getNodeValue();

            Double xvalue = Double.parseDouble(key);

            xvalues.add(xvalue);
        }
        
        //Build the plot.
        renderer = new SpectrumRenderV2(xvalues, name +" Proton NMR Spectrum");
        
        
        
        
        
    }
    
    public Stack<Double> getvalues()
    {
        return xvalues;
    }

    public NodeList getpoints()
    {
        return xpoints;
    }
    
    public SpectrumRenderV2 getchart()
    {
        return renderer;
    }
   



    public static void main(String[] args) {

        Stack<Double> xvalues = new Stack<Double>();        

        Scanner inchiinput = new Scanner(System.in);

      	String moleculename = inchiinput.next();

      	NMR mynmr = new NMR(moleculename, "1H");
       
        
        mynmr.getchart().pack();
        
        RefineryUtilities.centerFrameOnScreen(mynmr.getchart());
        
        mynmr.getchart().setVisible(true);
        
        
        
        
         

    }

}


