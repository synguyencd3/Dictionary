import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

public class Dict {

    protected Map<String, String> data= new TreeMap<String, String>();
    protected String Type;

    private static History history;

    public Dict(String type)
    {
        this.Type=type;
        String sep=System.getProperty("file.separator");
        File f = new File("XML"+sep+type+".txt");
        if(f.exists() && !f.isDirectory()) {
            ReadObjecfile("XML"+sep+type+".txt");
        }
        else
            ReadXML("XML"+sep+type+".xml");
    }

    /*
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
*/
    public void Save(String file)
    {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            oos.writeObject(this.data);
        }
        catch (IOException exc) {
            System.out.println("Error occur when write to file");
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
        this.data.put(word,meaning);
        String sep=System.getProperty("file.separator");
        Save("XML"+sep+this.Type+".txt");
    }

    public void Delete(String word)
    {
        this.data.remove(word);
        String sep=System.getProperty("file.separator");
        Save("XML"+sep+this.Type+".txt");
    }

    public void ReadXML(String filepath)
    {
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
        if (history==null)
        {
            history= new History("history.txt");
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

    private void ReadObjecfile(String file)
    {
        this.data=new TreeMap<String, String>();
        try
        {
            InputStream fos = new FileInputStream(file);
            ObjectInputStream dos = new ObjectInputStream(fos);
            this.data= (TreeMap<String, String>) dos.readObject();
        }
        catch(IOException exc)
        {
            System.out.println("Error occur when read from file");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
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

