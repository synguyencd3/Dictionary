import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class Dict {

    private Map<String, String> data= new TreeMap<String, String>();
    private String FilePath;

    private static History history;

    public Dict(String filepath) {
        try {
            File inputFile = new File(filepath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();


            NodeList nList = doc.getElementsByTagName("record");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String word=eElement
                            .getElementsByTagName("word")
                            .item(0)
                            .getTextContent();
                    String meaning =eElement
                            .getElementsByTagName("meaning")
                            .item(0)
                            .getTextContent();
                    data.put(word, meaning);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.FilePath=filepath;
        if (history==null)
        {
            history= new History("history.txt");
        }
    }

    public String Translate(String word)
    {
        String rtrn=data.get(word);
        if (rtrn!=null)
            history.Add(word);
        return rtrn;

    }

    public void Add(String word, String meaning)
    {
        try {
            RandomAccessFile file = new RandomAccessFile(this.FilePath, "rw");
            file.seek(file.length()-13); //Nhảy đến dòng phía trên </dictionary>
            String data="  <record>\n" +
                    "    <word>"+word+"</word>\n" +
                    "    <meaning>"+meaning+"\n\n" +
                    "</meaning>\n" +
                    "  </record>\n" +
                    "</dictionary>";
            byte[] a=data.getBytes();
            file.write(a);

        }
        catch (IOException e)
        {
            System.out.println("file error");
        }
    }

    public void AddToFavorite(String word, String filepath)
    {
        try {
            FileWriter file = new FileWriter(filepath, true);
            file.write(word+"\n");
            file.close();
        } catch (IOException e) {
            System.out.println("error add to favorite");
        }
    }

    public static String ThongKe(String TuNgay, String DenNgay)
    {
        LocalDate Start=LocalDate.parse(TuNgay, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate End=LocalDate.parse(DenNgay,DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Map<String, Integer> frequencymap=history.GetHistory(Start,End);
        String retrn="";
        for (Map.Entry<String, Integer> entry : frequencymap.entrySet()) {
            retrn=retrn+entry.getKey() + ":" + entry.getValue().toString()+'\n';
        }
        return retrn;
    }
}

