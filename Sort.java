import java.util.ArrayList;
import java.util.Comparator;

public class Sort {
    public static <T> int getIndexOnArray(DataParser<T> parser, Comparator<T> comparator, ArrayList<String> arrayList){
        int index = 0;
        ArrayList<T> tArrayList = new ArrayList<>();
        for (String s : arrayList) {
            tArrayList.add(parser.parse(s));
        }
        T temp = tArrayList.get(index);

        for (int i = 0; i < tArrayList.size(); i++) {
            if (comparator.compare(tArrayList.get(i),temp) < 0){
                temp = tArrayList.get(i);
                index = i;
            }
        }
        return index;
    }
}
