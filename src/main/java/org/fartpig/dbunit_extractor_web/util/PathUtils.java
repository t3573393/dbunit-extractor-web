package org.fartpig.dbunit_extractor_web.util;

import org.fartpig.dbunit_extractor_web.App;
import org.springframework.boot.ApplicationHome;

public class PathUtils {

	public static String getLocation() {
		ApplicationHome home = new ApplicationHome(App.class);
		return home.getDir().getAbsolutePath();
		// URL url =
		// App.class.getProtectionDomain().getCodeSource().getLocation();
		// String filePath = null;
		// try {
		// filePath = URLDecoder.decode(url.getPath(), "utf-8");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// System.out.println("1." + filePath);
		//
		// if (filePath.toLowerCase().endsWith(".jar") ||
		// filePath.toLowerCase().endsWith(".jar!/")) {
		// File file = new File(filePath);
		// filePath = file.getParentFile().getAbsolutePath();
		// System.out.println("2." + filePath);
		// } else {
		// File file = new File(filePath);
		// filePath = file.getAbsolutePath();
		// System.out.println("3." + filePath);
		// }
		// return filePath;
	}

}
