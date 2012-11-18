package com.megusta.footagehandler

import java.security.SecureRandom

/**
 * Provides various operations with file names.
 */
class FileUtils {

    /**
     * Extension delimiter.
     */
    private static final String EXTENSION_DELIMITER = "."

    /**
     * File delimiter.
     */
    private static final String FILE_DELIMITER = "/"

    /**
     * Gets file extension from file name.
     *
     * @param fileName name of file
     * @return file extension
     */
    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(EXTENSION_DELIMITER) + 1)
    }

    /**
     * Gets file name without extension from the file name string.
     *
     * @param fileName the file name string to parse
     * @return name of the file without extension
     */
    public static String getSingleFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf(EXTENSION_DELIMITER))
    }

    /**
     * Gets file name from the absolute link.
     *
     * @param absoluteLink Amazon S3 absolute link to parse
     * @return file name with extension
     */
    public static String getFileName(String absoluteLink) {
        return absoluteLink.substring(absoluteLink.lastIndexOf(FILE_DELIMITER) + 1)
    }

    /**
     * Randomly generates file name.
     *
     * @return new file name
     */
    public static String generateFileName() {
        return new BigInteger(128, SecureRandom.getInstance("SHA1PRNG")).toString(16)
    }

    /**
     * Returns file with the random name.
     *
     * @param extension file extension
     * @return file with random name
     */
    public static File getTempFile(String extension) {
        return File.createTempFile('footage', '.' + extension)
    }

    /**
     * Checks is file empty or not.
     *
     * @param file file to check
     * @return true if ile is empty, false otherwise
     */
    public static boolean isEmpty(def file) {
        boolean result
        if (file instanceof File) {
            result = file.length() == 0L
        } else {
            result = file.isEmpty()
        }

        result
    }

    /**
     * Gets file size.
     *
     * @param file specified file
     * @return length of file
     */
    public static long getFileSize(def file) {
        long result
        if (file instanceof File) {
            result = file.length()
        } else {
            result = file.getSize()
        }

        result
    }

    /**
     * Gets original file name.
     *
     * @param file specified file
     * @return file name
     */
    public static String getFileName(def file) {
        String result
        if (file instanceof File) {
            result = file.getName()
        } else {
            result = file.getOriginalFilename()
        }

        result
    }

    public static def getFileInputStream(def file) {
        InputStream is
        if (file instanceof File) {
            is = file.newInputStream()
        } else {
            is = file.getinputStream()
        }

        is
    }

    public static void copy(File source, File destination) {
        def reader = source.newReader()
        destination.withWriter { writer ->
            writer << reader
        }
        reader.close()
    }

}