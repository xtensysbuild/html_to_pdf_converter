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

/**
 * Exception thrown when the configuration is invalid
 */
public class HtmlToPdfConfigurationException extends Exception{
    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message
     */
    public HtmlToPdfConfigurationException(String message) {
        super(message);
    }
}
