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
# This file was updated: by Fredrik M�ller 2009-09-06
#                      : by Fredrik M�ller 2009-10-04

##############################################################################################################
# W I N D O W                                                                                                #
##############################################################################################################
configviewer.window.locationError = Kunde inte s�tta f�nster position enligt konfigurationen. Se loggfil f�r detaljer.
configviewer.window.title         = Inst�llningar

##############################################################################################################
# L O G G I N G                                                                                              #
##############################################################################################################
configviewer.logging.label.logLevel.text                 = Loggningsniv�
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
configviewer.rename.label.useLastModifiedDate.text = Anv�nd Senast �ndraddatum Om Exif-datum Saknas
configviewer.rename.label.useLastModifiedTime.text = Anv�nd Senast �ndradtid Om Exif-tid Saknas

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

##############################################################################################################
# T R E E  N O D E S                                                                                         #
##############################################################################################################
configviewer.tree.root          = Inst�llningar
configviewer.tree.node.logging  = Loggning
configviewer.tree.node.updates  = Uppdateringar
configviewer.tree.node.rename   = Namnbyte
configviewer.tree.node.language = Spr�k

##############################################################################################################
# C H A N G E D  C O N F I G U R A T I O N  N O T I F I C A T I O N                                          #
##############################################################################################################
configviewer.changed.configuration.start = F�ljande inst�llningar har �ndrats:
configviewer.changed.configuration.end   = JavaPEG m�ste startas om f�r att �ndringarna skall ha effekt