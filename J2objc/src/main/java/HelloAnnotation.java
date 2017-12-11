
import com.google.j2objc.annotations.AutoreleasePool;

public class HelloAnnotation {

	public static void main(String[] args) {
		System.out.println("Hello Annotation.");
		for (@AutoreleasePool int i = 0; i < 100; i++) {
			getSomething();
		}
	}

	@AutoreleasePool
	private static void getSomething(){
		System.out.print("getSomething");
	}

}
