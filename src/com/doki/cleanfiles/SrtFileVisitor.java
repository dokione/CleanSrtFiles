package com.doki.cleanfiles;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SrtFileVisitor extends SimpleFileVisitor<Path> {

	private static String SRT_EXTENSION_WITH_DOT = ".srt";

	private final List<Path> directories = new ArrayList<>();
	private boolean isUnusedDirectory = false;

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		if (isValidDirectory(dir, attrs)) {
			isUnusedDirectory = true;
		} else {
			isUnusedDirectory = false;
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		if (isUnusedDirectory && isSrtFile(file)) {
			return FileVisitResult.CONTINUE;
		}
		isUnusedDirectory = false;
		return FileVisitResult.SKIP_SUBTREE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		isUnusedDirectory = false;
		return FileVisitResult.SKIP_SUBTREE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		if (isUnusedDirectory) {
			directories.add(dir);
			isUnusedDirectory = false;
		}
		return FileVisitResult.CONTINUE;
	}

	private static boolean isValidDirectory(final Path path, final BasicFileAttributes fileAttributes) {
		boolean result = false;
		try {
			result = fileAttributes != null && path != null && Files.isReadable(path) && fileAttributes.isDirectory()
					&& Files.list(path).count() > 0L;
		} catch (SecurityException | IOException e) {
			// do nothing because it is not a valid directory for srt files
		}
		return result;
	}

	private static boolean isSrtFile(Path path) {
		boolean result = false;
		if (path != null && !Files.isDirectory(path)) {
			final Path filename = path.getFileName();
			if (filename != null && filename.toString().lastIndexOf('.') != -1
					&& filename.toString().substring(filename.toString().lastIndexOf('.')).trim()
							.toLowerCase(Locale.ENGLISH).equals(SRT_EXTENSION_WITH_DOT)) {
				result = true;
			}
		}
		return result;
	}

	public List<Path> getDirectories() {
		return directories;
	}

}
