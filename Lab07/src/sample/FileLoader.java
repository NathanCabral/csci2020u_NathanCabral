package sample;
import java.io.*;
import java.util.*;

public class FileLoader {
    private String filename;
    private ArrayList<String> values = new ArrayList<>();
    private Map<String, Integer> sectors;

    public FileLoader(String filename){
        this.filename = filename;
        sectors = new TreeMap<>();
    }
    public void loadFile(){
        String line = "";

        try{
            BufferedReader br = new BufferedReader(new FileReader(this.filename));
            while ((line = br.readLine())!=null){
                String[] columns = line.split(",");
                values.add(columns[5]);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void count()
    {
        for(int x = 0; x < values.size();x++)
        {
            String token = values.get(x);
            if(sectors.containsKey(token))
            {
                int previous = sectors.get(token);
                sectors.put(token,previous+1);
            }
            else{
                sectors.put(token,1);
            }
        }
    }
    public Map<String, Integer> getSectors()
    {
        return sectors;
    }
}