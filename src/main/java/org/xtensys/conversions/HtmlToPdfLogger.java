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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Logging class for the application
 */
public class HtmlToPdfLogger {
    private static final String HEADER = "Timestamp|Code|Message|HTML File|PDF File";
    private static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    private static final String BASE_FILENAME = "html_to_pdf_log_";
    private static final String EXTENSION = ".txt";
    private static final String DELIMITER = "|";
    private static final String PDF_CONVERSION_SUCCESS_CODE = "0";
    private static final String PDF_CONVERSION_SUCCESS_MESSAGE = "PDF CONVERSION SUCCESS";
    private static final String PDF_CONVERSION_ERROR_CODE = "1";
    private static final String PDF_CONVERSION_ERROR_MESSAGE = "PDF CONVERSION FAILURE";
    private final SimpleDateFormat sdf;
    private final File logFile;
    private final BufferedWriter writer;

    /**
     * Constructor for the logging application
     * @param loggingDirectory the directory where the log file will be written
     * @throws HtmlToPdfLoggerException if the BufferedWriter throws an IOException
     */
    public HtmlToPdfLogger(File loggingDirectory) throws HtmlToPdfLoggerException {
        sdf = new SimpleDateFormat(TIMESTAMP_FORMAT);
        this.logFile = new File(loggingDirectory, this.getFilename());
        try {
            writer = new BufferedWriter(new FileWriter(logFile));
            writer.append(HEADER);
        } catch (IOException e) {
            throw new HtmlToPdfLoggerException(HtmlToPdfLoggerException.MESSAGE + logFile.getPath());
        }
    }

    /**
     * Logs success in converting an HTML file to a PDF file
     * @param htmlFile the source HTML file
     * @param pdfFile the target PDF file
     * @throws HtmlToPdfLoggerException if the BufferedWriter throws an IOException
     */
    public void logSuccess(File htmlFile, File pdfFile) throws HtmlToPdfLoggerException {
        try {
            writer.newLine();
            writer.append(this.getEntry(PDF_CONVERSION_SUCCESS_CODE, PDF_CONVERSION_SUCCESS_MESSAGE, htmlFile.getPath(), pdfFile.getPath()));
        } catch (IOException e) {
            throw new HtmlToPdfLoggerException(HtmlToPdfLoggerException.MESSAGE + logFile.getPath());
        }
    }

    /**
     * Logs a failure to convert an HTML file to a PDF file
     * @param htmlFile the source HTML file
     * @param pdfFile the target PDF file
     * @throws HtmlToPdfLoggerException if the BufferedWriter throws an IOException
     */
    public void logFailure(File htmlFile, File pdfFile) throws HtmlToPdfLoggerException {
        try {
            writer.newLine();
            writer.append(this.getEntry(PDF_CONVERSION_ERROR_CODE, PDF_CONVERSION_ERROR_MESSAGE, htmlFile.getPath(), pdfFile.getPath()));
        } catch (IOException e) {
            throw new HtmlToPdfLoggerException(HtmlToPdfLoggerException.MESSAGE + logFile.getPath());
        }
    }


    /**
     * Closes the BufferedWriter, flushing the final buffer
     * @throws HtmlToPdfLoggerException if the BufferedWriter fails to close
     */
    public void close() throws HtmlToPdfLoggerException {
        try {
            writer.close();
        } catch (IOException e) {
            throw new HtmlToPdfLoggerException(HtmlToPdfLoggerException.CLOSE_MESSAGE + logFile.getPath());
        }

    }

    /**
     * Creates a log entry String
     * @param code the code of the loggable item
     * @param message the message corresponding to the code
     * @param htmlFilepath the path of the source html file
     * @param pdfFilepath the path of the target pdf file
     * @return a log entry String
     */
    private String getEntry(String code, String message, String htmlFilepath, String pdfFilepath){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getTimeStamp());
        sb.append(DELIMITER);
        sb.append(code);
        sb.append(DELIMITER);
        sb.append(message);
        sb.append(DELIMITER);
        sb.append(htmlFilepath);
        sb.append(DELIMITER);
        sb.append(pdfFilepath);
        return sb.toString();
    }

    /**
     * Gets the current timestamp as a String
     * @return a string representation of the current timestamp
     */
    private String getTimeStamp(){
        return sdf.format(new Date());
    }

    /**
     * Gets a unique filename for the log file with current timestamp
     * @return a unique filename for the log file
     */
    private String getFilename(){
        return BASE_FILENAME + this.getTimeStamp() + EXTENSION;
    }
}
