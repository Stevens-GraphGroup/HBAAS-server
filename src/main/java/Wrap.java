import java.net.URL;
import java.net.URLClassLoader;
import java.util.StringTokenizer;

public class Wrap
{
	static {
		System.err.println("debugging info: classloader:");
		ClassLoader cl = ClassLoader.getSystemClassLoader();
			URL[] urls = ((URLClassLoader)cl).getURLs();
			for(URL url: urls){
			System.err.println(url.getFile());
			}
		
		System.err.println("\njava.library.path:");
		String property = System.getProperty("java.library.path");
		StringTokenizer parser = new StringTokenizer(property, ";");
		while (parser.hasMoreTokens()) {
			System.err.println(parser.nextToken());
		}
		System.err.println();

		try {
			//System.load("./Sample1.so");
			System.loadLibrary("Wrap");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
    }
    public native String []    seqpass(String[] n);

    public static void main(String[] args)
    {   
        System.loadLibrary("Wrap");
        Wrap wrap = new Wrap();
        String[]   s = new String[8]; //information stored in 0-3 index the rest are sequences
        s[0] = "very important";
		s[1] = "taxonmic stuff";
		s[2] = "placeholder1";
		s[3] = "placeholder2";
		s[4] = "ASFDAWCDEAAERVRAFERFERVSER"; //these are not actual sequences
		s[5] = "UKYUIKYKYUIKGIGIGYIUMBCRYX";
		s[6] = "ASDFCARFSERTAFARSEACECHYJNY";
		s[7] = "ZXDCERGMBBMJJDADADARERUYIU";

        String[] result = wrap.seqpass(s);

        for(int i =0;i < result.length;i++)
		{
			System.out.println(result[i]);
		}
		
    }
}