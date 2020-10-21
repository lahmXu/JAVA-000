学习笔记
1. 自定义类加载器加载字节码
    - 读取字节码文件
    - ClassLoader.defineClass加载字节码
    `扩展: 加载外部类也可以通过URLClassLoader.addURL添加，使用Class.forName来获取`
2. JVM内存参数Xmx、Xms、Xmn、Meta、DirectMemory、Xss关系