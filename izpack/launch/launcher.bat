REM This file will start JavaPEG with custom memory settings for the JVM. With 
REM the below settings the heap size (Available memory for the application) will
REM range from 64 megabyte up to 384 megabyte.

javaw -jar -Xms64m -Xmx384m JavaPEG.jar