import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 自定义类加载器加载字节码
 */
public class HelloClassLoader extends ClassLoader {

    private static final String PREFIX = "Week_01/";
    private static final String SUFFIX = ".xlass";

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String className = "Hello";
        Class<?> hello = new HelloClassLoader().findClass(className);
        hello.getMethod("hello").invoke(hello.newInstance());
    }

    @Override
    protected Class<?> findClass(String className) {
        byte[] byteArr = new byte[0];
        try {
            byteArr = Files.readAllBytes(Paths.get(PREFIX + className + SUFFIX));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < byteArr.length; i++) {
            byteArr[i] = (byte) (255 - byteArr[i]);
        }
        return defineClass(className, byteArr, 0, byteArr.length);
    }
}
