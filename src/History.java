import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class History {

    Map<LocalDate, ArrayList<String>> map=new TreeMap<LocalDate, ArrayList<String>>();
    String Filepath;
    LocalDate current;

    History(String filepath)
    {
        this.Filepath=filepath;
        current=java.time.LocalDate.now();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            List<String> lines = new ArrayList<>();
            String line = null;
            while ((line = reader.readLine()) != null)
                {
                    List<String> data= Arrays.asList(line.split(";"));
                    if (map.get(LocalDate.parse(data.get(0)))!=null)
                    {
                        map.get(LocalDate.parse(data.get(0))).add(data.get(1));
                    }
                    else
                    {
                        ArrayList<String> tmp= new ArrayList<>();
                        tmp.add(data.get(1));
                       map.put(LocalDate.parse(data.get(0)),tmp);
                    }
                }
            }
            catch (FileNotFoundException e) {
                    System.out.println("File not found");
                } catch (IOException e) {
                    System.out.println("error");
                }
    }

        public void Add(String word)
    {
        try {
            FileWriter file = new FileWriter(Filepath,true);
            file.write(String.valueOf(current)+";"+word+'\n');
            file.close();
            System.out.println(String.valueOf(current));
        }
        catch (IOException e)
        {
            System.out.println("error history");
        }

    }
    public Map<String, Integer> GetHistory(LocalDate start, LocalDate end)
    {
        ArrayList<String> list= new ArrayList<String>();
        Map<String, Integer> frequencymap = new HashMap<String, Integer>();
        for (LocalDate date = start; date.isBefore(end.plusDays(1)); date = date.plusDays(1)) {
            if (map.get(date)!=null)
         list.addAll(map.get(date));
        }

        for (int i=0;i<list.size();i++)
        {
        if(frequencymap.containsKey(list.get(i))) {
            frequencymap.put(list.get(i), frequencymap.get(list.get(i))+1);
        }
        else{ frequencymap.put(list.get(i), 1); }
        }
        return frequencymap;
    }
}
