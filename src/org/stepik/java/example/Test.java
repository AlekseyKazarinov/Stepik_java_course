package org.stepik.java.example;

public class Test {
    public static void main(String[] args) {
        System.out.println(getCallerClassAndMethodName());

        anotherMethod();
    }

    private static void anotherMethod() {
        System.out.println(getCallerClassAndMethodName());
    }

    public static String getCallerClassAndMethodName() {
        StackTraceElement[] ste = new Throwable().getStackTrace();
        //return Arrays.toString(ste);
        String methodName = ste[1].getMethodName();
        if (ste.length < 3)
            return null;
        return ste[1].getClassName()+"#"+ste[2].getMethodName();
    }
}
