import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class ComparatorTry {

    public static ArrayList<BufferedReader> getBufferedArray(ArrayList<String> args){
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
        if (args.length == 0){
            System.out.println("Ошибка. Параметры не указаны");
            return;
        }
        SortingDirection direction = GetInfo.getDirection(args);
        SortedDataType type = GetInfo.getType(args);

        String outputFile = GetInfo.getOutputFile(args);
        if (outputFile == null){
            System.out.println("Отсутствует выходной файл");
            return;
        }

        ArrayList<String> inputFiles = GetInfo.getInputFiles(args);
        if (inputFiles.size() == 0){
            System.out.println("Отсутствуют входные файлы");
            return;
        }

        ArrayList<BufferedReader> readers = getBufferedArray(inputFiles);
        ArrayList<String> strings = new ArrayList<>();

        Iterator<BufferedReader> iterator = readers.iterator();
        while (iterator.hasNext()){
            BufferedReader reader = iterator.next();
            String s = reader.readLine();

            if (s == null){
                reader.close();
                iterator.remove();
            }else {
                strings.add(s);
            }
        }
        DataParser<String> stringDataParser = new ParserToString();
        DataParser<Integer> integerDataParser = new ParserToInt();
        Comparator<String> stringComparator = String::compareTo;
        Comparator<Integer> integerComparator = Integer::compareTo;

        while (!strings.isEmpty()){
            int indexMin;
            if (type == SortedDataType.INTEGER){
                if (direction == SortingDirection.DESCENDING){
                    indexMin = Sort.getIndexOnArray(integerDataParser, integerComparator.reversed(), strings);
                }else {
                    indexMin = Sort.getIndexOnArray(integerDataParser, integerComparator, strings);
                }
            }else if (direction == SortingDirection.DESCENDING){
                indexMin = Sort.getIndexOnArray(stringDataParser, stringComparator.reversed(), strings);
            }else {
                indexMin = Sort.getIndexOnArray(stringDataParser, stringComparator, strings);
            }

            WriteOnFile.write(outputFile,strings.get(indexMin));

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

