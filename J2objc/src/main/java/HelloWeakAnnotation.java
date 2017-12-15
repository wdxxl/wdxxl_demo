import java.util.ArrayList;
import java.util.List;

import com.google.j2objc.annotations.Weak;

public class HelloWeakAnnotation {
	@Weak
	public List<String> kingTest;

	public static void main(String[] args) {
		HelloWeakAnnotation helloWeakAnnotation = new HelloWeakAnnotation();
		helloWeakAnnotation.kingTest = new ArrayList<>();
		helloWeakAnnotation.kingTest.add("testListValue");

		System.out.println("hello weak annotation");
	}

}
