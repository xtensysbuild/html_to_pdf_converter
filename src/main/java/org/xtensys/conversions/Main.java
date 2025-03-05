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

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * The Main method class
 */
public class Main {
    private static final String NO_FILE_SPECIFIED = "There is no configuration file specified";
    private static final String FILE_NOT_FOUND = "The specified configuration file was not found: ";
    private static final String FILE_INVALID = "The specified configuration file is not valid: ";
    private static final String CHOOSE_CONFIGURATION_FILE_MESSAGE = "Please select a valid configuration file";
    private static final String USER_DIRECTORY_PROPERTY = "user.dir";

    /**
     * Main method for the HTML to PDF Converter. Will prompt user to choose a JSON configuration  file if one is not
     * passed in via command line
     * @param args the path of the JSON configuration file can be passed in via args[0]
     */
    public static void main(String[] args) {
        File file;
        if(args.length > 0){
            file = new File(args[0]);
        }else{
            file = Main.chooseFile();
        }
        if(file == null){
            System.out.println(NO_FILE_SPECIFIED);
        }else if (!file.exists()){
            System.out.println(FILE_NOT_FOUND + file.getPath());
        }else{
            try {
                HtmlToPdfConfiguration htmlToPdfConfiguration = HtmlToPdfConfiguration.fromJson(file);
                HtmlToPdfFileManager htmlToPdfFileManager = new HtmlToPdfFileManager(htmlToPdfConfiguration);
                if(htmlToPdfFileManager.hasValidDirectories()){
                    HtmlToPdfConverter htmlToPdfConverter = new HtmlToPdfConverter(htmlToPdfFileManager);
                    boolean success = htmlToPdfConverter.executeConversion();
                    if(success){
                        System.out.println("Successfully executed HTML to PDF conversions");
                    }else{
                        System.out.println("Failed to successfully executed HTML to PDF conversions");
                    }
                    System.out.println("See logs for details");
                }else{
                    throw new HtmlToPdfConfigurationException("The configuration file contains invalid directories. Aborting.");

                }
            } catch (IOException e) {
                System.out.println(FILE_INVALID + file.getPath());
            } catch (HtmlToPdfLoggerException e) {
                //Do Nothing
            } catch (HtmlToPdfConfigurationException e) {
                //Do nothing
            }
        }
    }

    /**
     * JFileChooser implementation to choose the JSON file representation of the HtmlToPdfConfiguration object
     * @return the selected file
     */
    private static File chooseFile(){
        File file = new File("");
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle(CHOOSE_CONFIGURATION_FILE_MESSAGE);
        chooser.setCurrentDirectory(new File(System.getProperty(USER_DIRECTORY_PROPERTY)).getParentFile());
        int resp = chooser.showOpenDialog(null);
        if (resp == JFileChooser.APPROVE_OPTION){
            file = chooser.getSelectedFile();
        }
        if (resp == JFileChooser.CANCEL_OPTION){
            return null;
        }
        return file;
    }
}