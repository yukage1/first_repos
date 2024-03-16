package KPP.Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Inspector {
    public  static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        Student student = new Student("Name", 18, 2);
        System.out.println("----------------Inspect class----------------");
        System.out.println(Inspector.task1("KPP.Lab1.Student"));
        System.out.println("----------------Show fields and methods. Invoke methods without parameters----------------");
        task2(student);
        System.out.println("----------------Invoke methods----------------");
        task3(student, "setName","Misha");
        task3(student, "setName","Tommy");
        task3(student, "getYear",  null);
    }

    public static String task1(String nameOfClass){
        Class<?> dclass = null;
        try {
            dclass = Class.forName(nameOfClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Entered name - ").append(nameOfClass);

        String name = dclass.getName();
        Package pack1 = dclass.getPackage();
        int modif = dclass.getModifiers();
        Class<?> superclass = dclass.getSuperclass();

        stringBuilder.append("Package: ").append(pack1).append("\nModifiers:  ").append(modif).append("\nClass name: ").append(name).append("\nSuperclass name: ").append(superclass).append("\n");

        stringBuilder.append("Interfaces: " + "\n");
        Class<?>[] interf = dclass.getInterfaces();
        for (Class<?> aClass : interf) {
            stringBuilder.append(aClass.toGenericString()).append("\n");
        }
        stringBuilder.append("\n");


        stringBuilder.append("Fields" + "\n");
        Field[] fields = dclass.getDeclaredFields();
        for (Field field : fields) {
            stringBuilder.append(field.toGenericString()).append("\n");
        }
        stringBuilder.append("\n");

        stringBuilder.append("Constructors" + "\n");
        Constructor<?>[] constructs = dclass.getConstructors();
        for (Constructor<?> construct : constructs) {
            stringBuilder.append(construct.toGenericString()).append("\n");
        }
        stringBuilder.append("\n");

        Method[] meth = dclass.getMethods();
        stringBuilder.append("Methods" + "\n");
        for (Method method : meth) {
            stringBuilder.append(method.toGenericString()).append("\n");
        }
        stringBuilder.append("\n");

        return stringBuilder.toString();

    }

    public static void task2(Object object){
        AtomicInteger a = new AtomicInteger();
        int choice;
        List<Method> list = getMethodWithPublicMod(object);
        showFields(object);

        System.out.println("All public methods: ");
        list.forEach(method -> {
            System.out.println(method.toGenericString());
        });
        list = getMethodsWithoutParam(list);
        System.out.println("All public methods without parameters: ");
        list.forEach(method -> {
            System.out.println((a.incrementAndGet() + ") ") + method.toGenericString());
        });

        System.out.println("Choose method -> ");
        choice = scan();
        while (choice > 0 && choice <= list.size()) {//чтобы выйти нужно ввести любое число, которое не будет совпадать с номером метода
            try {
                System.out.println(list.get(choice - 1) + "\n result: " + list.get(choice - 1).invoke(object));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            System.out.println("(if you want to stop typing methods, then enter a number out of range)");
            System.out.println("Choose method ->");
            choice = scan();
        }

    }

    public static void showFields(Object object){
        Class<?> someClass = object.getClass();
        Field[] fields =  Optional.ofNullable(someClass.getDeclaredFields()).orElse(new Field[0]);
        System.out.println("Fields in class " + someClass.getSimpleName());
        Arrays.stream(fields).forEach(field -> {
            try {
                if(!field.isAccessible()){
                    field.setAccessible(true);
                }
                System.out.println(field.getType() + " " + field.getName() + " = " + field.get(object));
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private static List<Method> getMethodWithPublicMod(Object object) {
        Class<?> someClass = object.getClass();
        Method[] methods = Optional.ofNullable(someClass.getDeclaredMethods()).orElse(new Method[0]);

        List<Method> arrayList = Arrays.asList(methods);
        arrayList = arrayList.stream().filter(method -> Modifier.isPublic(method.getModifiers())).collect(Collectors.toList());
        return arrayList;
    }

    private static List<Method> getMethodsWithoutParam(List<Method> list){
        list = list.stream().filter(method -> method.getParameterCount() == 0).collect(Collectors.toList());
        return list;
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

    public static void task3(Object obj, String nameOfMethod, Object...parameters){
        Object result;
        Class<?> someClass = obj.getClass();
        Class<?>[] parameterTypes = Arrays.stream(Optional.ofNullable(parameters).orElse(new Object[0]))
                .map(param -> param.getClass())
                .collect(Collectors.toList())
                .toArray(new Class<?>[0]);
        try {
            Method method = someClass.getDeclaredMethod(nameOfMethod, parameterTypes);
            System.out.println("Method state before " + obj);
            result = method.invoke(obj, parameters);
            System.out.println("Result = " + Optional.ofNullable(result).orElse("void"));
            System.out.println("Method state after " + obj);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}
