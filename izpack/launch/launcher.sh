#!/bin/sh

# This file will start JavaPEG with custom memory settings for the JVM. With 
# the below settings the heap size (Available memory for the application) will
# range from 64 megabyte up to 256 megabyte.

java -jar -Xms64m -Xmx256m JavaPEG.jar