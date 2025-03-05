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

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Class to store the configuration elements and read the configuration from a JSON file
 */
public class HtmlToPdfConfiguration {
    private final String sourceDirectoryPath;
    private final String targetDirectoryPath;

    /**
     * Constructor for Configuration object
     * @param sourceDirectoryPath the source directory path HTML files are read from
     * @param targetDirectoryPath the target directory path PDF files are written to
     */
    public HtmlToPdfConfiguration(String sourceDirectoryPath, String targetDirectoryPath) {
        this.sourceDirectoryPath = sourceDirectoryPath;
        this.targetDirectoryPath = targetDirectoryPath;
    }

    /**
     * Reads the JSON configuration file to a configuration object
     * @param file the file containing the JSON configuration
     * @return a new configuration object
     * @throws FileNotFoundException if the configuration file is not found
     */
    public static HtmlToPdfConfiguration fromJson(File file) throws FileNotFoundException {
        return new Gson().fromJson(new FileReader(file), HtmlToPdfConfiguration.class);
    }

    /**
     * Gets the source directory path HTML files are read from
     * @return the source directory path HTML files are read from
     */
    public String getSourceDirectoryPath() {
        return sourceDirectoryPath;
    }

    /**
     * Gets the target directory path PDF files are written to
     * @return the target directory path PDF files are written to
     */
    public String getTargetDirectoryPath() {
        return targetDirectoryPath;
    }
}
