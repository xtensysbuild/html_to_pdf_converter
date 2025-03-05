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

import com.itextpdf.html2pdf.HtmlConverter;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * HTML to PDF converter
 */
public class HtmlToPdfConverter {
    private static final String PDF_EXTENSION = ".pdf";
    private final HtmlToPdfFileManager fileManager;
    private final HtmlToPdfLogger htmlToPdfLogger;

    /**
     * Constructor for the converter
     * @param fileManager the file manager class
     * @throws IOException if there is an error writing to File
     * @throws HtmlToPdfLoggerException if the logger can't write to file
     */
    public HtmlToPdfConverter(HtmlToPdfFileManager fileManager) throws IOException, HtmlToPdfLoggerException {
        this.fileManager = fileManager;
        this.htmlToPdfLogger = new HtmlToPdfLogger(fileManager.getLogDirectory());
    }

    /**
     * Executes the HTML to PDF conversion
     * @return true if it is successful without throwing exceptions
     * @throws IOException if there is an error writing to File
     * @throws HtmlToPdfLoggerException if the logger can't write to file
     */
    public boolean executeConversion() throws IOException, HtmlToPdfLoggerException {
        try{
            this.processDirectory(fileManager.getSourceDirectory(), fileManager.getPdfDirectory());
        }finally {
            htmlToPdfLogger.close();
        }

        return true;
    }

    /**
     * Reads the HTML files from the source directory, and attempts to write them to PDF in target directory
     * @param sourceDirectory the source directory read from
     * @param targetDirectory the target directory written to
     * @throws IOException if there is an error writing to File
     * @throws HtmlToPdfLoggerException if the logger can't write to file
     */
    private void processDirectory(File sourceDirectory, File targetDirectory) throws IOException, HtmlToPdfLoggerException {
        for(File sourceFile : Objects.requireNonNull(sourceDirectory.listFiles())){
            if(sourceFile.isDirectory()){
                this.processSubdirectory(sourceFile, targetDirectory);
            }else {
                this.createPdfFile(sourceFile, targetDirectory);
            }
        }
    }

    /**
     * Reads the HTML files from the source directory, and attempts to write them to PDF in target directory
     * @param sourceSubdirectory the source directory read from
     * @param targetDirectory the target directory written to
     * @throws IOException if there is an error writing to File
     * @throws HtmlToPdfLoggerException if the logger can't write to file
     */
    private void processSubdirectory(File sourceSubdirectory, File targetDirectory) throws IOException, HtmlToPdfLoggerException {
        File targetSubdirectory = new File(targetDirectory, sourceSubdirectory.getName());
        if(!targetDirectory.exists()){
            targetSubdirectory.mkdirs();
        }
        this.processDirectory(sourceSubdirectory, targetSubdirectory);
    }

    /**
     * Makes call to covert HTML file to PDF and logs result
     * @param sourceFile the HTML file
     * @param targetDirectory the directory the PDF file will be created in
     * @throws IOException if there is an error writing to File
     * @throws HtmlToPdfLoggerException if the logger can't write to file
     */
    private void createPdfFile(File sourceFile, File targetDirectory) throws IOException, HtmlToPdfLoggerException {
        File pdfFile = this.getTargetFile(targetDirectory, sourceFile);
        if(!pdfFile.getParentFile().exists()){
            pdfFile.getParentFile().mkdirs();
        }
        boolean success = this.convertToPdf(sourceFile, pdfFile);
        if(success){
            htmlToPdfLogger.logSuccess(sourceFile, pdfFile);
        }else{
            htmlToPdfLogger.logFailure(sourceFile, pdfFile);
        }
    }

    /**
     * Converts HTML file to PDF
     * @param sourceFile the HTML file
     * @param pdfFile the PDF file
     * @return true if PDF conversion is successful
     */
    private boolean convertToPdf(File sourceFile, File pdfFile) {
        try {
            HtmlConverter.convertToPdf(sourceFile, pdfFile);
        } catch (Exception e) {
            return false;
        }
       return true;
    }

    /**
     * Get the name and path for the target PDF file
     * @param targetDirectory the target directory
     * @param sourceFile the source HTML file
     * @return the new PDF file
     */
    private File getTargetFile(File targetDirectory, File sourceFile) {
        String filename = FilenameUtils.getBaseName(sourceFile.getName()) + PDF_EXTENSION;
        return new File(targetDirectory, filename);
    }
}
