package managers;

import java.io.File;

/**
 * This class contains utilities necessary for enabling cross-platform
 * operation of the program.
 * 
 * @author Lawrence Patrick Calulo
 * 
 */
public class PlatformUtils {

	/**
	 * Windows uses backslashes (\) to separate directories in file paths.
	 * However, Unix-like systems (such as OS X and Linux) use forward slashes
	 * (/). This utility function accepts a variable number of strings, and
	 * turns these into a valid path using the current platform's conventions.
	 * 
	 * @param folders
	 *            The folders/file paths to join together into a proper path
	 * @return A path that uses either forward or back slashes, whichever is
	 *         appropriate for the platform
	 */
	public static String joinPath(String... folders) {
		String path = "";
		boolean first = true;

		for (String folder : folders) {
			/*
			 * The file path needs to be added in between the folders. That way,
			 * if the last parameter is a file and not a folder, stuff still
			 * works.
			 * 
			 * Example: these/are/folders some/file/example.xml
			 */
			if (!first) {
				path += File.separator;
			}
			path += folder;
			first = false;
		}

		return path;
	}
}
