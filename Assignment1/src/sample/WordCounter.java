package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.*;
import java.math.*;

public class WordCounter
{

    private Map<String, Integer> wordCounts;
    private static ArrayList<TestFile> fileList = new ArrayList<>();
    public  Map<String, Float> probability;
    private int totalFiles;
    public static float accuracy;
    public static float percision;

    public WordCounter(File mainDirectoy)
    {
        File trainHam = new File(mainDirectoy + "/train/ham");
        File trainSpam = new File(mainDirectoy + "/train/spam");
        totalFiles = trainHam.listFiles().length + trainSpam.listFiles().length;
        probability(trainHam,trainSpam);
        File testHam = new File(mainDirectoy + "/test/ham");
        File testSpam = new File(mainDirectoy + "/test/spam");
        computeProbabilitySpam(testHam,testSpam);
        calculateAccuracy();
    }

    public void parseFile(File file,Map<String, Integer> list) throws IOException
    {
        //System.out.println("Starting parsing the file:" + file.getAbsolutePath());

        if(file.isDirectory()){
            //parse each file inside the directory
            File[] content = file.listFiles();
            for(File current: content){
                parseFile(current, list);
            }
        }else{
            Scanner scanner = new Scanner(file);
            // scanning token by token
            Set<String> set = new HashSet<String>();

            while (scanner.hasNext()){
                String  token = scanner.next();
                if (isValidWord(token)){
                    set.add(token);
                }
            }
            Iterator it = set.iterator();
            while(it.hasNext())
            {
                countWord(it.next().toString(),list);
            }
        }
    }

    private static boolean isValidWord(String word)
    {
        String allLetters = "^[a-zA-Z]+$";
        // returns true if the word is composed by only letters otherwise returns false;
        return word.matches(allLetters);

    }

    private void countWord(String word,Map<String, Integer> list)
    {
        if(list.containsKey(word)){
            int previous = list.get(word);
            list.put(word, previous+1);
        }else{
            list.put(word, 1);
        }
    }

    public  void probability(File ham, File spam)
    {
        Map<String, Integer> trainHamFreq = new TreeMap<>();
        Map<String, Integer> trainSpamFreq = new TreeMap<>();
        probability = new TreeMap<>();

        File dataDir = ham;
        File dataDir2 = spam;

        try{
            parseFile(dataDir, trainHamFreq);
            parseFile(dataDir2, trainSpamFreq);

            int totalHam = dataDir.list().length;
            int totalSpam = dataDir2.list().length;
            for(Map.Entry<String, Integer> i : trainSpamFreq.entrySet())
            {
                try{
                    float prWS = (float) i.getValue() / (float) totalSpam;
                    float prWH = (float) trainHamFreq.get(i.getKey()) / (float) totalHam;
                    float prSW = prWS / (prWS + prWH);
                    probability.put(i.getKey(), prSW);
                }
                catch (NullPointerException e)
                {
                    probability.put(i.getKey(), 0.0f);
                }
            }

        }catch(FileNotFoundException e){
            System.err.println("Invalid input dir: " + dataDir.getAbsolutePath());
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void computeProbabilitySpam(File ham, File spam)
    {
        File[] hamFiles = ham.listFiles();
        File[] spamFiles = spam.listFiles();
        try
        {
            compute(hamFiles, "Ham");
            compute(spamFiles, "Spam");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void compute(File[] listFiles,String Class) throws IOException
    {

        for(File currentFile: listFiles)
        {
            double fileProbability = 0.0;
            double fileN = 0.0;
            Scanner hamScanner = new Scanner(currentFile);

            while(hamScanner.hasNext())
            {
                String currentWord = hamScanner.next();
                if(isValidWord(currentWord))
                {
                    double getWordProb = 0.0;
                    try
                    {
                        getWordProb = (double) probability.get(currentWord);
                    }
                    catch(NullPointerException e)
                    {
                        getWordProb = 0.0;
                    }

                    double t =  Math.log(1-getWordProb);
                    double l =  Math.log(getWordProb);
                    double s =  t - l;
                    System.out.println("____________________");
                    fileN = fileN + s;
                }
            }
            fileProbability = (double) (1/(1 + Math.pow(Math.E, fileN)));
            TestFile temp = new TestFile(currentFile.getName(), fileProbability, Class);
            fileList.add(temp);
        }

    }
    public static ObservableList<TestFile> getFileList()
    {
        ObservableList<TestFile> temp = FXCollections.observableArrayList();
        for(TestFile current : fileList)
        {
            temp.add(current);
        }
        return temp;
    }

    public void calculateAccuracy()
    {
        double numTruePositives = 0.0;
        double numTrueNegatives = 0.0;
        double numFalsePositives = 0.0;

        for(TestFile current: fileList)
        {
            String actualClass = current.getActualClass();
            double prob = current.getSpamProbability();
            if(actualClass.equals("Spam") && prob > 0.5)
            {
                numTruePositives++;
            }
            if(actualClass.equals("Ham") && prob < 0.5)
            {
                numTrueNegatives++;
            }
            if(actualClass.equals("Ham") && prob > 0.5)
            {
                numFalsePositives++;
            }
        }
        accuracy = (float) ((numTruePositives + numTrueNegatives) / totalFiles);
        percision = (float) ((numTruePositives / (numFalsePositives + numTruePositives)));
    }

    public static String getAccuracy()
    {
        return String.valueOf(accuracy);
    }
    public static String getPercision()
    {
        return String.valueOf(percision);
    }
}

