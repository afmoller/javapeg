# This is the Swedish language file for the Config Viewer Component.

# The structure of the file is as follows:

# A line starting with "#" is interpreted as a comment.

# A valid non comment line may look like "file = Arkiv" where "file" is the variable, = the
# separator and "Arkiv" is the value of the variable, in this case the Swedish word for file.

# There might be whitespaces or tabs in between the variable and the "=". Any whitespace
# or other character after the value of the variable will be trimmed away.

# For more information regarding the rules for the syntax of this file please visit:
# http://java.sun.com/javase/6/docs/api/java/util/Properties.html

# This file was created: by Fredrik Möller 2009-09-05
# This file was updated: by Fredrik Möller 2009-09-06
#                      : by Fredrik Möller 2009-10-04

##############################################################################################################
# W I N D O W                                                                                                #
##############################################################################################################
configviewer.window.locationError = Kunde inte sätta fönster position enligt konfigurationen. Se loggfil för detaljer.
configviewer.window.title         = Inställningar

##############################################################################################################
# L O G G I N G                                                                                              #
##############################################################################################################
configviewer.logging.label.logLevel.text                 = Loggningsnivå
configviewer.logging.label.developerMode.text            = Obuffrad Loggning
configviewer.logging.label.rotateLog.text                = Rotera Loggen automatiskt
configviewer.logging.label.zipLog.text                   = Komprimera Roterad Loggfil
configviewer.logging.label.rotateLogSize.text            = Loggroteringsstorlek
configviewer.logging.label.logName.text                  = Loggnamm
configviewer.logging.label.logEntryTimeStampFormat.text  = Tidsstämpelsformat för loggfilsnotering
configviewer.logging.label.logEntryTimeStampPreview.text = Förhandsgranskning av tidsstämpel för loggfilsnotering

##############################################################################################################
# U P D A T E                                                                                                #
##############################################################################################################
configviewer.update.label.updateEnabled.text            = Aktivera Programuppdateringskontroll
configviewer.update.label.attachVersionInformation.text = Bifoga versionsinformation vid uppdateringskontroll

##############################################################################################################
# R E N A M E                                                                                                #
##############################################################################################################
configviewer.rename.label.useLastModifiedDate.text = Använd Senast Ändraddatum Om Exif-datum Saknas
configviewer.rename.label.useLastModifiedTime.text = Använd Senast Ändradtid Om Exif-tid Saknas

##############################################################################################################
# L A N G U A G E                                                                                            #
##############################################################################################################
configviewer.language.label.selectionMode        = SPRÅKVALSMETOD
configviewer.language.radiobutton.manual         = Manuellt
configviewer.language.radiobutton.automatic      = Automatiskt
configviewer.language.label.currentLanguage      = AKTUELLT SPRÅK
configviewer.language.label.availableLanguages   = TILLGÄNGLIGA SPRÅK
configviewer.language.languageNameNotFound       = namn på språk ej funnet
configviewer.language.information.windowlabel    = Information
configviewer.language.information.restartMessage = Valt språk kommer inte användas innan JavaPEG startats om

##############################################################################################################
# E R R O R  M E S S A G E S                                                                                 #
##############################################################################################################
configviewer.errormessage.rotateLogSizeToLargeKiB   = Loggroteringsstorleken är för stor: Maximalt tillåtna är 100000 KiB 
configviewer.errormessage.rotateLogSizeToLargeMiB   = Loggroteringsstorleken är för stor: Maximalt tillåtna är 100 MiB 
configviewer.errormessage.rotateLogSizeToSmall      = Loggroteringsstorleken är för liten: Minsta tillåtna är 10 KiB
configviewer.errormessage.rotateLogSizeNotAnInteger = Logroteringsstorleken måste vara ett heltal

##############################################################################################################
# T R E E  N O D E S                                                                                         #
##############################################################################################################
configviewer.tree.root          = Inställningar
configviewer.tree.node.logging  = Loggning
configviewer.tree.node.updates  = Uppdateringar
configviewer.tree.node.rename   = Namnbyte
configviewer.tree.node.language = Språk

##############################################################################################################
# C H A N G E D  C O N F I G U R A T I O N  N O T I F I C A T I O N                                          #
##############################################################################################################
configviewer.changed.configuration.start = Följande inställningar har ändrats:
configviewer.changed.configuration.end   = JavaPEG måste startas om för att ändringarna skall ha effekt