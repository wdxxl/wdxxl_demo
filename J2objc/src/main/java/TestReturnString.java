import com.google.j2objc.annotations.AutoreleasePool;

public class TestReturnString {

	public static void main(String[] args) {
		LogoInit r =  new LogoInit();
		System.out.println(r.call());
	}

}

class LogoInit {
	@AutoreleasePool
	public String call() {
		ReturnStr r = new ReturnStr();
		return r.returnStr();
	}
}

class ReturnStr {
	public String returnStr() {
		return "ReturnStr.";
	}
}