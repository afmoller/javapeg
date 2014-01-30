/*******************************************************************************
 * Copyright (c) JavaPEG developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package moller.javapeg.program.enumerations;

/**
 * This enumeration is a collection of different log levels, severity levels,
 * the names and meaning of levels are borrowed from the log4j project
 * (http://jakarta.apache.org/log4j)
 *
 * DEBUG - The DEBUG Level designates fine-grained informational events that are most useful to debug an application.
 * INFO  - The INFO level designates informational messages that highlight the progress of the application at coarse-grained level.
 * WARN  - The WARN level designates potentially harmful situations.
 * ERROR - The ERROR level designates error events that might still allow the application to continue running.
 * FATAL - The FATAL level designates very severe error events that will presumably lead the application to abort.
 *
 * @author Fredrik
 */
public enum Level {
    DEBUG,
    INFO,
    WARN,
    ERROR,
    FATAL;
}
