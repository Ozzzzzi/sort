import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.util.*;

//        "-s out.txt in1.txt in2.txt in3.txt";
//        "-i -a out.txt in.txt";
//        "-d -s out.txt in1.txt in2.txt";

public class CFTexercise {
    static int countInputFiles;
    static int indexOutputFile = 0;

    public static void main(String[] args) throws IOException {
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



        ArrayList<BufferedReader> readers = new ArrayList<>();
        System.out.println(args[indexOutputFile]);

        for (int i = args.length - 1; i >= countInputFiles - 1; i--) {
            readers.add(new BufferedReader(new FileReader(args[i])));
        }
        FileWriter writer = new FileWriter(args[indexOutputFile],false);

        while (countInputFiles != 0) {
            long min = Long.MAX_VALUE;
            int check = -1;
            for (int i = 0; i < countInputFiles; i++) {
                try{
                    readers.get(i).mark(1000);
                    long checkedNumber = Long.parseLong(readers.get(i).readLine());
                    readers.get(i).reset();
                    if (checkedNumber < min) {
                        min = checkedNumber;
                        check = i;
                    }
                }catch (NumberFormatException e){
                    readers.get(i).close();
                    readers.remove(i);
                    countInputFiles--;
                    break;
                }
            }
            if (check != -1){
                readers.get(check).readLine();
                String s = String.valueOf(min);
                writer.write(s + "\n");
                writer.flush();
            }
        }
        writer.close();
    }
    public static int[] sortArray(int [] array){
        if (array == null) return null;
        if (array.length < 2) return array;

        int [] leftArray = new int[array.length/2];
        int [] rightArray = new int[array.length - leftArray.length];
        System.arraycopy(array,0,leftArray,0,leftArray.length);
        System.arraycopy(array,leftArray.length,rightArray,0,rightArray.length);
        leftArray = sortArray(leftArray);
        rightArray = sortArray(rightArray);

        return mergeArray(leftArray,rightArray);
    }
    public static int[] mergeArray(int[] arrayLeft, int[] arrayRight){
        int[] sortArray = new int[arrayLeft.length + arrayRight.length];
        int positionOnLeft = 0;
        int positionOnRight = 0;
        int positionMain = 0;

        while (positionOnLeft < arrayLeft.length && positionOnRight < arrayRight.length){
            if (arrayLeft[positionOnLeft] < arrayRight[positionOnRight]){
                sortArray[positionMain] = arrayLeft[positionOnLeft];
                positionOnLeft++;
            }else{
                sortArray[positionMain] = arrayRight[positionOnRight];
                positionOnRight++;
            }
            positionMain++;
        }

        while (positionOnLeft != arrayLeft.length){ //ЗАменить на System.arraycopy
            sortArray[positionMain] = arrayLeft[positionOnLeft];
            positionOnLeft++;
            positionMain++;
        }
        while (positionOnRight != arrayRight.length){
            sortArray[positionMain] = arrayRight[positionOnRight];
            positionOnRight++;
            positionMain++;
        }
        return sortArray;
    }
}
