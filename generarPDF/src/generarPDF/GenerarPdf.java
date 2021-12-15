package generarPDF;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class GenerarPdf {
	public static final String file = "\\\\192.168.1.99\\xml\\" + (new java.sql.Date(System.currentTimeMillis())) + ".xml";
	public static void main(String[] args) throws ClassNotFoundException,Throwable{
		
		// TODO Auto-generated method stub
		try {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        org.w3c.dom.Document document = db.parse(new File(file));

        document.getDocumentElement();
        NodeList nList = document.getElementsByTagName("envio");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                
                String id = eElement.getAttribute("codi");

                String dniRece = eElement.getElementsByTagName("dniReceptor").item(0).getTextContent();
                String nomRece = eElement.getElementsByTagName("nomReceptor").item(0).getTextContent();
                String dirRece = eElement.getElementsByTagName("direccioReceptor").item(0).getTextContent();
                String nomProd = eElement.getElementsByTagName("nomProducte").item(0).getTextContent();
                String estatProd = eElement.getElementsByTagName("estat").item(0).getTextContent();
                
                if(estatProd.equals("No entregat")) {
                Document pdfDOC = new Document();
                PdfWriter writer = PdfWriter.getInstance(pdfDOC, new FileOutputStream("\\\\192.168.1.99\\xml\\" + id + (new java.sql.Date(System.currentTimeMillis())+ ".pdf")));
                pdfDOC.open();
                pdfDOC.add(new Paragraph("___________VMDEL___________"));
                pdfDOC.add(new Paragraph("  "));
                pdfDOC.add(new Paragraph("Id producte : " + id));
                pdfDOC.add(new Paragraph("DNI receptor : " + dniRece));
                pdfDOC.add(new Paragraph("Nom receptor : " + nomRece));
                pdfDOC.add(new Paragraph("Direcció receptor : " + dirRece));
                pdfDOC.add(new Paragraph("Nom de producte : " + nomProd));
                pdfDOC.add(new Paragraph("___________VMDEL___________"));
                
                pdfDOC.close();
                writer.close();
                }     
            }
            
        }
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
		} catch(SAXException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(DocumentException e) {
			e.printStackTrace();
		}
	}
}
