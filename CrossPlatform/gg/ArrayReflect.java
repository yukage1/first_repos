package KPP.Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

public class ArrayReflect {
    public  static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String... args) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        int[] dimension = initDimension(0);

        System.out.println("Enter the full name of the class or a primitive type:");
        System.out.print("=> ");
        String classString = scanLine();
        Object array;
        try {
            array = getArray(classString, dimension);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found.");
            return;
        }

        int choice = -1;
        while (choice != 0){
            menu();
            choice = scan();
            switch (choice){
                case 1:
                    dimension = initDimension(dimension.length);
                    array = setSize(array, dimension, classString);
                    break;
                case 2:
                    System.out.println(showArray(array));
                    break;
            }
        }
        in.close();
    }

    public static void menu(){
        System.out.println("(1) - Change size");
        System.out.println("(2) - Show");
        System.out.println("(0) - Exit");
        System.out.println("--------------------------");
    }

    public static int getDimension(Object array){
        String classString = array.getClass().toString();
        int dimension = 0;

        for (int i = 0; i < classString.length(); i++) {
            if (classString.charAt(i) == '['){
                dimension++;
            }
        }
        return dimension;
    }


    public static Class<?> getClass(Object array) throws ClassNotFoundException {
        String s = array.getClass().toString();
        if (s.indexOf(';') != -1){
            s = s.substring(s.indexOf('L') + 1, s.indexOf(';'));
            return Class.forName(s);
        } else{
            return getSimpleClass(s.charAt(s.length() - 1));
        }

    }

    public static void init1DArray(Object array) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        String classString = getClass(array).getName();
        for (int i = 0; i < Array.getLength(array); i++) {
            System.out.println("Init [" + i + "]: ");
            ClassManager classManager = new ClassManager(classString);
            classManager.newInstance();
            Array.set(array, i, classManager.getObject());
            System.out.println();
        }
    }

    public static void init2DArray(Object array) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Object[][] arrayCopy = (Object[][]) array;
        String classString = getClass(array).getName();

        for (int i = 0; i < arrayCopy.length; i++) {
            for (int j = 0; j < arrayCopy[0].length; j++) {
                System.out.println("Init [" + i +"][" + j + "]: ");
                ClassManager classManager = new ClassManager(classString);
                classManager.newInstance();
                ((Object [][])array)[i][j] = classManager.getObject();
                System.out.println();
            }
        }
    }

    public static void handInit(Object array) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException{
        if (getDimension(array) == 1)
            init1DArray(array);
        else
            init2DArray(array);
    }

    public static Object getArray (Class<?> cls, int [] dimension){
        return Array.newInstance(cls, dimension);
    }

    public static Object getArray(String cls, int [] dimension) throws ClassNotFoundException {
        Class<?> simpleClass;
        return getArray((simpleClass = getSimpleClass(cls)) == null ? Class.forName(cls) : simpleClass, dimension);
    }

    public static Class<?> getSimpleClass(String string){
        switch (string){
            case "int":
                return int.class;
            case "double":
                return double.class;
            case "long":
                return long.class;
            case "float":
                return float.class;
            case "short":
                return short.class;
            case "char":
                return char.class;
            case "boolean":
                return boolean.class;
        }

        return null;
    }


    public static Class<?> getSimpleClass(Character letter){
        switch (letter){
            case 'I':
                return Integer.class;
            case 'D':
                return Double.class;
            case 'J':
                return Long.class;
            case 'F':
                return Float.class;
            case 'S':
                return Short.class;
            case 'C':
                return Character.class;
            case 'Z':
                return Boolean.class;
        }

        return null;
    }


    public static Object setSize(Object array, int [] newDimension, String classString) throws ClassNotFoundException {
        Object newArray = getArray(classString, newDimension);

        int height = Math.min(newDimension[0], Array.getLength(array));

        if(getDimension(array) == 2) {
            int width;

            for (int i = 0; i < height; i++) {
                if (newDimension[1] > Array.getLength(Array.get(array, i)))
                    width = Array.getLength(Array.get(array, i));
                else
                    width = newDimension[1];

                System.arraycopy(Array.get(array, i), 0, Array.get(newArray, i), 0, width);
            }
        }
        else {
            System.arraycopy(array, 0, newArray, 0, height);
        }

        return newArray;
    }

    public static String showArray(Object array){
        StringBuilder stringBuilder = new StringBuilder();
        if (getDimension(array) == 1) {
            for (int i = 0; i < Array.getLength(array); i++){
                stringBuilder.append(Array.get(array, i).toString()).append(" ");
            }
            stringBuilder.append("\n");
        }
        else{
            for (int i = 0; i < Array.getLength(array); i++) {
                for (int j = 0; j < Array.getLength(Array.get(array, i)); j++){
                    stringBuilder.append(Array.get(Array.get(array, i), j)).append(" ");
                }
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public static int [] initDimension(int modifier){
        int [] dimension;
        if (modifier == 0) {
            System.out.print("Enter the number of dimensions of the array (1 - 2): ");
            dimension = new int[scan()];
        }
        else if (modifier == 1)
            dimension = new int[1];
        else
            dimension = new int[2];

        if (dimension.length > 2) {
            System.out.println("Incorrect data.");
            System.exit(0);
        }

        for (int i = 0; i < dimension.length; i++) {
            System.out.print("   Enter size " + (i + 1) + ": ");
            dimension[i] = scan();
        }

        return dimension;
    }

    public static int scan(){
        int choice = 0;
        boolean flag = true;
        while (flag){
            flag = false;
            try {
                choice = Integer.parseInt(in.readLine());
            } catch (NumberFormatException | IOException e){
                //e.printStackTrace();
                flag = true;
                System.err.println("Incorrect number format");
            }
        }
        return choice;
    }

    public static String scanLine(){
        String inputChoice = "";
        try {
            inputChoice = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputChoice;
    }


}