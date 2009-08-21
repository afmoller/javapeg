package moller.util.logger;

/**
 * This class was created : 2009-03-02 by Fredrik Möller
 * Latest changed         : 2009-03-03 by Fredrik Möller
 *                        : 2009-03-13 by Fredrik Möller
 */

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