package org.devefx.wx.common.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class ClassUtils {
	
	/**
	 * 扫描包里面的全部Class
	 * @param basePackage
	 * @return Set<Class<?>>
	 */
	public static Set<Class<?>> getClasses(String basePackage) {
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		
		String packageName = basePackage;
		String packageDirName = packageName.replace('.', '/');
		
		Enumeration<URL> dirs = null;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					findClasses(classes, packageName, filePath);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classes;
	}
	
	private static void findClasses(Set<Class<?>> classes, String packageName, String packagePath) {
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		File[] files = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory() || (file.isFile() && file.getName().endsWith(".class"));
			}
		});
		for (File file : files) {
			if (file.isDirectory()) {
				findClasses(classes, packageName + "." + file.getName(), file.getPath());
			} else {
				String className = file.getName();
				className = className.substring(0, className.length() - 6);
				try {
					ClassLoader loader = Thread.currentThread().getContextClassLoader();
					classes.add(loader.loadClass(packageName + "." + className));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
