package generarXml;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Xml {
	public static final String xmlFilePath = "\\\\192.168.1.99\\xml\\"
			+ (new java.sql.Date(System.currentTimeMillis())) + ".xml";

	public static void CrearXmlPaquets() {
		try {
			Connection con = Conn.connectDatabase();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM paquets");

			DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = df.newDocumentBuilder();
			Document document = db.newDocument();

			// Arrel
			Element arrel = document.createElement("envios");
			document.appendChild(arrel);

			while (rs.next()) {
				var estat1 = rs.getBoolean("Estat");

				if (estat1 == true) {
					String entregat = "Entregat";
					
					// Pare
					Element pare = document.createElement("envio");
					arrel.appendChild(pare);

					// Declarar id a l'element pare

					Attr id = document.createAttribute("codi");
					id.setValue(rs.getString("Id_paquets"));
					pare.setAttributeNode(id);

					// Dni del Receptor
					Element dniReceptor = document.createElement("dniReceptor");
					dniReceptor.appendChild(document.createTextNode(rs.getString("Dni_receptor")));
					pare.appendChild(dniReceptor);

					// Nom del receptor
					Element nomReceptor = document.createElement("nomReceptor");
					nomReceptor.appendChild(document.createTextNode(rs.getString("Nom_receptor")));
					pare.appendChild(nomReceptor);

					// direccio del receptor
					Element direccioReceptor = document.createElement("direccioReceptor");
					direccioReceptor.appendChild(document.createTextNode(rs.getString("Direccio_receptor")));
					pare.appendChild(direccioReceptor);

					// nom del producte
					Element nomProducte = document.createElement("nomProducte");
					nomProducte.appendChild(document.createTextNode(rs.getString("Nom_Producte")));
					pare.appendChild(nomProducte);

					// telefon
					Element telefon = document.createElement("telefonReceptor");
					telefon.appendChild(document.createTextNode(rs.getString("telefon_receptor")));
					pare.appendChild(telefon);
					
					// latitud
					Element latitud = document.createElement("latitud");
					latitud.appendChild(document.createTextNode(rs.getString("latitud")));
					pare.appendChild(latitud);

					// longitud
					Element longitud = document.createElement("longitud");
					longitud.appendChild(document.createTextNode(rs.getString("longintud")));
					pare.appendChild(longitud);
					
					// estat
					Element estat = document.createElement("estat");
					estat.appendChild(document.createTextNode(entregat));
					pare.appendChild(estat);

					// crear l'arxiu XML
					// transformar l'objecte DOM a XML

				} else {
					String entregat = "No entregat";

					// Pare
					Element pare = document.createElement("envio");
					arrel.appendChild(pare);

					// Declarar id a l'element pare

					Attr id = document.createAttribute("codi");
					id.setValue(rs.getString("Id_paquets"));
					pare.setAttributeNode(id);

					// Dni del Receptor
					Element dniReceptor = document.createElement("dniReceptor");
					dniReceptor.appendChild(document.createTextNode(rs.getString("Dni_receptor")));
					pare.appendChild(dniReceptor);

					// Nom del receptor
					Element nomReceptor = document.createElement("nomReceptor");
					nomReceptor.appendChild(document.createTextNode(rs.getString("Nom_receptor")));
					pare.appendChild(nomReceptor);

					// direccio del receptor
					Element direccioReceptor = document.createElement("direccioReceptor");
					direccioReceptor.appendChild(document.createTextNode(rs.getString("Direccio_receptor")));
					pare.appendChild(direccioReceptor);

					// nom del producte
					Element nomProducte = document.createElement("nomProducte");
					nomProducte.appendChild(document.createTextNode(rs.getString("Nom_Producte")));
					pare.appendChild(nomProducte);

					// telefon
					Element telefon = document.createElement("telefonReceptor");
					telefon.appendChild(document.createTextNode(rs.getString("telefon_receptor")));
					pare.appendChild(telefon);
					
					// latitud
					Element latitud = document.createElement("latitud");
					latitud.appendChild(document.createTextNode(rs.getString("latitud")));
					pare.appendChild(latitud);

					// longitud
					Element longitud = document.createElement("longitud");
					longitud.appendChild(document.createTextNode(rs.getString("longintud")));
					pare.appendChild(longitud);
					
					// estat
					Element estat = document.createElement("estat");
					estat.appendChild(document.createTextNode(entregat));
					pare.appendChild(estat);

					// crear l'arxiu XML
					// transformar l'objecte DOM a XML
				}

			}

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			DOMSource ds = new DOMSource(document);
			StreamResult sr = new StreamResult(new File(xmlFilePath));

			t.transform(ds, sr);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException te) {
			te.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error de connexió.");
			e.printStackTrace();
		}
	}
}
