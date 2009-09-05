# This is the Swedish language file for the Config Viewer Component.

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
configviewer.window.locationError = Kunde inte s�tta f�nster position enligt konfigurationen. Se loggfil f�r detaljer.
configviewer.window.title         = Inst�llningar

##############################################################################################################
# L O G G I N G                                                                                              #
##############################################################################################################
configviewer.logging.label.logLevel.text                 = Loggnings Niv�
configviewer.logging.label.developerMode.text            = Obuffrad Loggning
configviewer.logging.label.rotateLog.text                = Rotera Loggen automatiskt
configviewer.logging.label.zipLog.text                   = Komprimera Roterad Loggfil
configviewer.logging.label.rotateLogSize.text            = Loggroteringsstorlek
configviewer.logging.label.logName.text                  = Loggnamm
configviewer.logging.label.logEntryTimeStampFormat.text  = Tidsst�mpelsformat f�r loggfilsnotering
configviewer.logging.label.logEntryTimeStampPreview.text = F�rhandsgranskning av tidsst�mpel f�r loggfilsnotering

##############################################################################################################
# U P D A T E                                                                                                #
##############################################################################################################
configviewer.update.label.updateEnabled.text            = Aktivera Programuppdateringskontroll
configviewer.update.label.attachVersionInformation.text = Bifoga versionsinformation vid uppdateringskontroll

##############################################################################################################
# R E N A M E                                                                                                #
##############################################################################################################
configviewer.rename.label.useLastModifiedDate.text = Anv�nd senast �ndraddatum om Efix datum saknas
configviewer.rename.label.useLastModifiedTime.text = Anv�nd senast �ndradtid om Efix tid saknas

##############################################################################################################
# L A N G U A G E                                                                                            #
##############################################################################################################
configviewer.language.label.selectionMode        = SPR�KVALSMETOD
configviewer.language.radiobutton.manual         = Manuellt
configviewer.language.radiobutton.automatic      = Automatiskt
configviewer.language.label.currentLanguage      = AKTUELLT SPR�K
configviewer.language.label.availableLanguages   = TILLG�NGLIGA SPR�K
configviewer.language.languageNameNotFound       = namn p� spr�k ej funnet
configviewer.language.information.windowlabel    = Information
configviewer.language.information.restartMessage = Valt spr�k kommer inte anv�ndas innan JavaPEG startats om

##############################################################################################################
# E R R O R  M E S S A G E S                                                                                 #
##############################################################################################################
configviewer.errormessage.rotateLogSizeToLargeKiB   = Loggroteringsstorleken �r f�r stor: Maximalt till�tna �r 100000 KiB 
configviewer.errormessage.rotateLogSizeToLargeMiB   = Loggroteringsstorleken �r f�r stor: Maximalt till�tna �r 100 MiB 
configviewer.errormessage.rotateLogSizeToSmall      = Loggroteringsstorleken �r f�r liten: Minsta till�tna �r 10 KiB
configviewer.errormessage.rotateLogSizeNotAnInteger = Logroteringsstorleken m�ste vara ett heltal