import java.util.ArrayList;

public class GetInfo {
    public static SortingDirection getDirection(String[] args){
        if (!args[0].equals("-d") && !args[1].equals("-d")){
            return SortingDirection.ASCENDING;
        }
        else return SortingDirection.DESCENDING;
    }
    public static String getOutputFile(String[] args){
        for (String arg : args) {
            if (arg.length() > 2) {
                return arg;
            }
        }
        return null;
    }
    public static ArrayList<String> getInputFiles(String[] args){
        ArrayList<String> arrayList = new ArrayList<>();
        for (String s : args) {
            if (s.length() > 2){
                arrayList.add(s);
            }
        }
        arrayList.remove(getOutputFile(args));
        return arrayList;
    }
    public static SortedDataType getType(String[] args){
        if (args[0].equals("-i") || args[1].equals("-i")){
            return SortedDataType.INTEGER;
        }else if (args[0].equals("-s") || args[1].equals("-s")){
            return SortedDataType.STRING;
        }else{
            System.out.println("Не указан тип. Отсортируем как строки");
            return SortedDataType.STRING;
        }
    }

}
