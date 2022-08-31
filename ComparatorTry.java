import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class ComparatorTry {
    static ArrayList<BufferedReader> readers = new ArrayList<>();

    public static ArrayList<BufferedReader> getBufferedArray(String[] args){
        ArrayList<BufferedReader> readers = new ArrayList<>();
        for (String arg : args) {
            try {
                readers.add(new BufferedReader(new FileReader(arg)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return readers;
            }
        }
        return readers;
    }



    public static void main(String[] args) throws IOException {
        readers = getBufferedArray(args);
        ArrayList<String> strings = new ArrayList<>();

        Comparator<String> stringComparator = String::compareTo;
        Comparator<Integer> integerComparator = Integer::compareTo;
        DataParser<String> stringDataParser = new ParserToString();
        DataParser<Integer> integerDataParser = new ParserToInt();

        for (BufferedReader r : readers) {
            strings.add(r.readLine());
        }


        while (!strings.isEmpty()){
            int indexMin = Sort.getIndexOnArray(stringDataParser, stringComparator, strings); //0
            System.out.println(strings.get(indexMin));
            String s = readers.get(indexMin).readLine();
            if (s == null){
                readers.get(indexMin).close();
                readers.remove(indexMin);
                strings.remove(indexMin);
            }else {
                strings.set(indexMin,s);
            }
        }
    }
}

