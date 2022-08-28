import java.io.*;
import java.util.ArrayList;

//        "-s out.txt in1.txt in2.txt in3.txt"; <--
//        "-i -a out.txt in.txt";
//        "-d -s out.txt in1.txt in2.txt";

public class Test {
    static int countInputFiles;
    static int indexOutputFile;
    static ArrayList<BufferedReader> readers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        if (args.length == 0){
            System.out.println("Ошибка. Параметры не указаны");
            return;
        }
        initializeCountInputAndOutputFiles(args);
        if (countInputFiles == 0){
            if (indexOutputFile == 0 && args.length != 1){
                System.out.println("Не указан выходной файл");
                return;
            }
            System.out.println("Не указан входной файл");
            return;
        }
        initializeReaders(args);

        boolean stringTypeSort = getType(args);
        boolean ascendingSort = getDirection(args);

        writeMinString(args[indexOutputFile], ascendingSort);
    }

    public static void writeMin(String outputFile) {
        try(FileWriter writer = new FileWriter(outputFile)){
            while (countInputFiles != 0) {
                long min = Long.MAX_VALUE;
                int check = -1;
                for (int i = 0; i < countInputFiles; i++) {
                    try {
                        readers.get(i).mark(1000);

                        long checkedNumber = Long.parseLong(readers.get(i).readLine());
                        try {
                            long secondLine = Long.parseLong(readers.get(i).readLine());
                            if (checkedNumber > secondLine) {
                                readers.get(i).close();
                                readers.remove(i);
                                countInputFiles--;
                                System.out.println("Нарушен порядок сортировки в файле");
                                break;
                            }
                        } catch (NumberFormatException ignore) {}

                        readers.get(i).reset();
                        if (checkedNumber < min) {
                            min = checkedNumber;
                            check = i;
                        }
                    } catch (NumberFormatException e) {
                        readers.get(i).close();
                        readers.remove(i);
                        countInputFiles--;
                        break;
                    }
                }
                if (check != -1) {
                    readers.get(check).readLine();
                    String s = String.valueOf(min);
                    System.out.println(s);
                    writer.write(s + "\n");
                    writer.flush();
                }
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    } // Сортировка чисел по возрастанию
    public static void writeMinString(String outputFile, boolean ascendingSort) {
        try(FileWriter writer = new FileWriter(outputFile)){
            while (countInputFiles != 0) {
                String min = "";
                int check = -1;
                for (int i = 0; i < countInputFiles; i++) { //Назначение min и проверка, что файл не пустой
                    readers.get(i).mark(1000);
                    String firstLine = readers.get(i).readLine();
                    if (firstLine == null) {
                        countInputFiles--;
                        readers.remove(i);
                    } else {
                        min = firstLine;
                        readers.get(i).reset();
                        break;
                    }
                }
                for (int i = 0; i < countInputFiles; i++) {
                    readers.get(i).mark(1000);
                    String firstLine = readers.get(i).readLine();
                    String secondLine = readers.get(i).readLine();
                    if (firstLine == null){
                        countInputFiles--;
                        break;
                    }
                    if (secondLine != null){
                        if (firstLine.compareTo(secondLine) > 0){ //true if first > second ... CBA
                            System.out.println("Нарушен порядок сортировки!");
                            countInputFiles--;
                            readers.remove(i);
                            break;
                        }
                    }

                    if (min.compareTo(firstLine) >= 0){
                        min = firstLine;
                        check = i;
                    }
                    readers.get(i).reset();

                }
                if (check != -1) {
                    readers.get(check).readLine();
                    System.out.println(min);
                    writer.write(min + "\n");
                    writer.flush();
                }
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    } //Сортировка строк по убыванию
    public static boolean getType(String[] args){
        if (args[0].equals("-i") || args[1].equals("-i")){
            return false;
        }else if (args[0].equals("-s") || args[1].equals("-s")){
            return true;
        }else{
            System.out.println("Ошибка! Не указан тип. Отсортируем как строки");
            return true;
        }
    }
    public static boolean getDirection(String[] args){
        return !args[0].equals("-d") && !args[1].equals("-d");
    }
    public static void initializeCountInputAndOutputFiles(String[] args){
        countInputFiles = args.length;
        for (int i = 0; i < args.length; i++) {
            if (args[i].toCharArray().length == 2) {
                countInputFiles--;
            } else {
                indexOutputFile = i;
                countInputFiles--;
                break;
            }
        }
    }
    public static void initializeReaders(String[] args){
        for (int i = args.length - 1; i >= countInputFiles - 1; i--) {
            try{
                readers.add(new BufferedReader(new FileReader(args[i])));
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

}


