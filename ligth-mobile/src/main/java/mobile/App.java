package mobile;

import up.light.Runner;

public class App {

	public static void main(String[] args) {
		String platform = null;

		if (args.length > 0) {
			platform = args[0];
		}

		Runner runner = new Runner();
		runner.run(platform);
	}
}
