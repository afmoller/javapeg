# This is the Spanish language file for the Config Viewer Component.

# The structure of the file is as follows:

# A line starting with "#" is interpreted as a comment.

# A valid non comment line may look like "file = Arkiv" where "file" is the variable, = the
# separator and "Arkiv" is the value of the variable, in this case the Swedish word for file.

# There might be whitespaces or tabs in between the variable and the "=". Any whitespace
# or other character after the value of the variable will be trimmed away.

# For more information regarding the rules for the syntax of this file please visit:
# http://java.sun.com/javase/6/docs/api/java/util/Properties.html

# This file was created: by Fredrik M�ller 2009-09-05
# This file was updated: 

##############################################################################################################
# W I N D O W                                                                                                #
##############################################################################################################
configviewer.window.locationError = Could not set window location according to settings. See logs for details
configviewer.window.title         = Configuration

##############################################################################################################
# L O G G I N G                                                                                              #
##############################################################################################################
configviewer.logging.label.logLevel.text                 = Log Level
configviewer.logging.label.developerMode.text            = Unbuffered Logging
configviewer.logging.label.rotateLog.text                = Rotate Log Automatically
configviewer.logging.label.zipLog.text                   = Compress Rotated Log File
configviewer.logging.label.rotateLogSize.text            = Rotate Log Size
configviewer.logging.label.logName.text                  = Log Name
configviewer.logging.label.logEntryTimeStampFormat.text  = Log Entry Timestamp Format
configviewer.logging.label.logEntryTimeStampPreview.text = Log Entry Timestamp Preview

##############################################################################################################
# U P D A T E                                                                                                #
##############################################################################################################
configviewer.update.label.updateEnabled.text            = Enable Application Update Check
configviewer.update.label.attachVersionInformation.text = Add Version Information To Update Check

##############################################################################################################
# R E N A M E                                                                                                #
##############################################################################################################
configviewer.rename.label.useLastModifiedDate.text = Use Last Modified Date if Exif date is missing
configviewer.rename.label.useLastModifiedTime.text = Use Last Modified Time if Exif time is missing

##############################################################################################################
# L A N G U A G E                                                                                            #
##############################################################################################################
configviewer.language.label.selectionMode        = MODO DE SELECCION
configviewer.language.radiobutton.manual         = Manualmente
configviewer.language.radiobutton.automatic      = Autom�tico
configviewer.language.label.currentLanguage      = IDIOMA ACTUAL
configviewer.language.label.availableLanguages   = IDIOMAS DISPONIBLES
configviewer.language.languageNameNotFound       = nombre de idioma no encontrado
configviewer.language.information.windowlabel    = Informaci�n
configviewer.language.information.restartMessage = El idioma seleccionado no se usar� hasta que JavaPEG se reinicie

##############################################################################################################
# E R R O R  M E S S A G E S                                                                                 #
##############################################################################################################
configviewer.errormessage.rotateLogSizeToLargeKiB   = The log rotate size is to large: Maximum allowed is 100000 KiB 
configviewer.errormessage.rotateLogSizeToLargeMiB   = The log rotate size is to large: Maximum allowed is 100 MiB 
configviewer.errormessage.rotateLogSizeToSmall      = The log rotate size is to small: Minimum allowed is 10 KiB
configviewer.errormessage.rotateLogSizeNotAnInteger = The log rotate size must be an integer