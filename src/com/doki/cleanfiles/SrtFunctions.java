package com.doki.cleanfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SrtFunctions {

	public static List<Path> getForderWithOnlySrtFile(final Settings config) {
		Messages.showStartSearch(config);
		List<Path> result = new ArrayList<>();
		File[] roots = File.listRoots();
		if (roots != null) {
			for (final File root : roots) {
				try {
					SrtFileVisitor visitor = new SrtFileVisitor();
					Files.walkFileTree(root.toPath(), visitor);
					if (visitor.getDirectories() != null && !visitor.getDirectories().isEmpty()) {
						result.addAll(visitor.getDirectories());
					}
				} catch (final SecurityException | IOException e) {
					Messages.showError(config, e);
				}
			}
		}
		return result;
	}

	public static void deleteDirectories(final Settings config, List<Path> folders) {
		if (folders != null && !folders.isEmpty()) {
			Messages.showStartDelete(config);
			folders.stream().forEach((Path path) -> {
				File folder = path.toFile();
				try {
					if (!folder.delete() && folder.isDirectory()) {
						File[] files = folder.listFiles();
						if (files != null) {
							for (File file : files) {
								if (config != null && !config.isNoDelete()) {
									file.delete();
								}
							}
						}
						if (config != null && !config.isNoDelete()) {
							folder.delete();
						}
					}
					Messages.showDeleted(config, folder);
				} catch (SecurityException e) {
					Messages.showNotDeleted(folder);
				}
			});
		} else {
			Messages.showNoDirectory(config);
		}
	}

}
