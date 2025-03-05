/*
 *     HTML to PDF Converter
 *     Copyright (C) 2025  Jorge Torres-Lumsden
 *     jtorreslumsden@gmail.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.xtensys.conversions;

import java.io.File;

/**
 * A class designed to manage all the directories needed for the converter
 */
public class HtmlToPdfFileManager {
    private static final String LOGGING_SUBDIRECTORY = "logs";
    private static final String PDF_SUBDIRECTORY = "pdf";
    private final File sourceDirectory;
    private final File targetDirectory;
    private final File pdfDirectory;
    private final File logDirectory;

    /**
     * Constructor for the file manager classs
     * @param htmlToPdfConfiguration the file configuration
     */
    public HtmlToPdfFileManager(HtmlToPdfConfiguration htmlToPdfConfiguration) {
        this.sourceDirectory = new File(htmlToPdfConfiguration.getSourceDirectoryPath());
        this.targetDirectory = new File(htmlToPdfConfiguration.getTargetDirectoryPath());
        this.pdfDirectory = new File(targetDirectory, PDF_SUBDIRECTORY);
        this.logDirectory = new File(targetDirectory, LOGGING_SUBDIRECTORY);
    }

    /**
     * Returns the source directory
     * @return the source directory
     */
    public File getSourceDirectory() {
        return sourceDirectory;
    }

    /**
     * Returns the target directory
     * @return the target directory
     */
    public File getTargetDirectory() {
        return targetDirectory;
    }

    /**
     * Returns the pdf directory
     * @return the pdf directory
     */
    public File getPdfDirectory() {
        return pdfDirectory;
    }

    /**
     * Returns the log directory
     * @return the log directory
     */
    public File getLogDirectory() {
        return logDirectory;
    }

    /**
     * Tests to see if the configured directories are valid and can be written to
     * @return true if the configured directories are valid
     */
    public boolean hasValidDirectories() {
        boolean sourceDirectoryExists = this.testDirectory(sourceDirectory);
        boolean targetDirectoryExists = this.testDirectory(targetDirectory);
        if(sourceDirectoryExists && targetDirectoryExists){
            pdfDirectory.mkdirs();
            boolean pdfDirectoryExists = this.testDirectory(pdfDirectory);
            logDirectory.mkdirs();
            boolean logDirectoryExists = this.testDirectory(logDirectory);
            return pdfDirectoryExists && logDirectoryExists;
        }else{
            return false;
        }
    }

    /**
     * Tests to see if the directory is a valid directory
     * @param directory the file being tested
     * @return true if the file is a valid directory
     */
    private boolean testDirectory(File directory){
        if(directory == null){
            System.out.println("The specified directory is null");
            return false;
        }
        if(!directory.exists()){
            System.out.println("The specified directory does not exist: " + directory.getPath());
            return false;
        }
        if(!directory.isDirectory()){
            System.out.println("The specified directory is not a valid directory: " + directory.getPath());
            return false;
        }
        return true;
    }
}
