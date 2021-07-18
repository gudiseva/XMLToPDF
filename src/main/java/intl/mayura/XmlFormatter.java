package intl.mayura;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Nag Arvind Gudiseva
 *
 */
public class XmlFormatter
{
    public String format(String input) {
        return prettyFormat(input, "1");
    }

    public static String prettyFormat(String input, String indent) {
        Source xmlInput = new StreamSource(new StringReader(input));
        StringWriter stringWriter = new StringWriter();
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
            transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", indent);
            transformer.transform(xmlInput, new StreamResult(stringWriter));

            return stringWriter.toString().trim();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void printPDF(String output, String content) {

        Document document = new Document();
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(output));
            document.open();
            document.add(new Paragraph(content));
            document.close();
            writer.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]) {

        XmlFormatter xmlFormatter = new XmlFormatter();

        String xmlFile = "src/input_folder/books.xml";
        String pdfFile = "src/output_folder/books.pdf";

        try {
            String file = new String(Files.readAllBytes(Paths.get(xmlFile)));
            printPDF(pdfFile, xmlFormatter.format(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
