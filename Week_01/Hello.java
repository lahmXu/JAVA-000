public class Hello {
    public static void main(String[] args) {
        long a = 1L;
        short b = 2;
        int c = 3;
        String d = "4";
        int e = b * Integer.valueOf(d);
        int f = (int) a + b;
        int g = e / b;
        for (int i = 0; i < 10; i++) {
            if (g < 10) {
                g = g + c;
            }
        }

        int l=0,m=0,n=0;
        l += 1;
        m++;
        n=n+1;

    }
}
