# This is the Swedish language file for JavaPEG.

# The structure of the file is as follows:

# A line starting with "#" is interpreted as a comment.

# A valid non comment line may look like "file = Arkiv" where "file" is the variable, = the
# separator and "Arkiv" is the value of the variable, in this case the Swedish word for file.

# There might be whitespaces or tabs in between the variable and the "=". Any whitespace
# or other character after the value of the variable will be trimmed away.

# For more information regarding the rules for the syntax of this file please visit:
# http://java.sun.com/javase/6/docs/api/java/util/Properties.html

# This file was created: by Fredrik Möller 2005-xx-xx
# This file was updated: by Fredrik Möller 2005-11-08
#                      : by Fredrik Möller 2006-11-09
#                      : by fredrik Möller 2006-11-16
#                      : by Fredrik Möller 2006-11-27
#                      : by Fredrik Möller 2007-02-02
#                      : by Fredrik Möller 2007-03-10
#                      : by Fredrik Möller 2009-02-19
#                      : by Fredrik Möller 2009-02-24
#                      : by Fredrik Möller 2009-02-27
#                      : by Fredrik Möller 2009-03-04
#                      : by Fredrik Möller 2009-03-05
#                      : by Fredrik Möller 2009-03-06
#                      : by Fredrik Möller 2009-03-11
#                      : by Fredrik Möller 2009-03-14
#                      : by Fredrik Möller 2009-03-23
#                      : by Fredrik Möller 2009-03-26
#                      : by Fredrik Möller 2009-03-27
#                      : by Fredrik Möller 2009-04-14
#                      : by Fredrik Möller 2009-04-15
#                      : by Fredrik Möller 2009-04-19
#                      : by Fredrik Möller 2009-04-20
#                      : by Fredrik Möller 2009-04-27
#                      : by Fredrik Möller 2009-04-28
#                      : by Fredrik Möller 2009-05-04
#                      : by Fredrik Möller 2009-05-05
#                      : by Fredrik Möller 2009-05-10
#                      : by Fredrik Möller 2009-05-11
#                      : by Fredrik Möller 2009-05-13

#############################################################################################################
# M E N U                                                                                                   #
#############################################################################################################
menu.file     = Arkiv
menu.help     = Hjälp
menu.language = Språk

#############################################################################################################
# M E N U  I T E M S                                                                                        #
#############################################################################################################
menu.item.exit                       = Avsluta
menu.item.openSourceFileChooser      = Öppna Källfilsväljaren
menu.item.openDestinationFileChooser = Öppna Destinationsfilsväljaren
menu.item.startProcess               = Starta Namnbytesprocessen
menu.item.programHelp                = Programhjälp
menu.item.versionInformation         = Versionsinformation
menu.item.about                      = Om JavaPEG 2.0
menu.item.languageChoice             = Språkval

#############################################################################################################
# M E N U  M N E M O N I C                                                                                  #
#############################################################################################################
menu.mnemonic.file               = A
menu.mnemonic.help               = H
menu.mnemonic.language           = S

#############################################################################################################
# M E N U  A C C E L E R A T O R                                                                            #
#############################################################################################################
menu.iten.openSourceFileChooser.accelerator      = K
menu.iten.openDestinationFileChooser.accelerator = D
menu.iten.startProcess.accelerator               = P
menu.iten.exit.accelerator                       = X
menu.item.languageChoice.accelerator             = S
menu.item.programHelp.accelerator                = H
menu.item.versionInformation.accelerator         = V
menu.item.about.accelerator                      = O

##############################################################################################################
# L A B E L S                                                                                                #
##############################################################################################################
labels.sourcePath       = SÖKVÄG TILL BILDKATALOG
labels.destinatonPath   = SÖKVÄG TILL DESTINATIONSKATALOG
labels.subFolderName    = UNDERKATALOGSMALL
labels.fileNameTemplate = FILNAMNSMALL
labels.variables        = VARIABLER

##############################################################################################################
# V A R I A B L E S                                                                                          #
##############################################################################################################
variable.pictureDateVariable   = d
variable.pictureDate           = Datum då bilden togs
variable.pictureTimeVariable   = t
variable.pictureTime           = Tidpunkt då bilden togs
variable.cameraModelVariable   = m
variable.cameraModel           = Kameramodell
variable.shutterSpeedVariable  = s
variable.shutterSpeed          = Slutartid
variable.isoValueVariable      = i
variable.isoValue              = ISO-värde
variable.pictureWidthVariable  = b
variable.pictureWidth          = Bildbredd i pixlar
variable.pictureHeightVariable = h
variable.pictureHeight         = Bildhöjd i pixlar
variable.apertureValueVariable = bl
variable.apertureValue         = Bländarvärde
variable.dateOftodayVariable   = dd
variable.dateOftoday           = Dagens datum

##############################################################################################################
# V A R I A B L E S  C O M M E N T                                                                           #
##############################################################################################################
variable.comment.infoLabelA = * = Dessa variabler kan användas
variable.comment.infoLabelB = vid namngivning av undermapp

##############################################################################################################
# C H E C K  B O X E S                                                                                       #
##############################################################################################################
checkbox.createThumbNails      = SKAPA TUMNAGELÖVERSIKT

##############################################################################################################
# T O O L T I P S                                                                                            #
##############################################################################################################
tooltip.sourcePathButton             = Leta fram rätt sökväg till bildmapp
tooltip.destinationPathButton        = Välj sökväg för destinationsmapp
tooltip.createSubFolder              = Skapa en undermapp med valfrit namn, i vilken bilderna läggs
tooltip.subFolderName                = Ange namnet på mappen inklusive eventuella variabler här
tooltip.fileNameTemplate             = Skriv in filnamnsmallen inklusive variabler här
tooltip.createThumbNails             = Skapa tumnaglar av omdöpta filer
tooltip.openDestinationFolder        = Öppna mappen där de omdöpta filerna hamnar efter avslutad process
tooltip.beginNameChangeProcessButton = Starta namnbytarprocessen

##############################################################################################################
# F I L E  S E L E C T I O N  D I A L O G S                                                                  #
##############################################################################################################
fileSelectionDialog.sourcePathFileChooser      = Välj bildmapp
fileSelectionDialog.destinationPathFileChooser = Välj destinationsmapp

##############################################################################################################
# A B O U T  D I A L O G                                                                                     #
##############################################################################################################
aboutDialog.Label    = Om JavaPEG 2.0
aboutDialog.TextRowA = JavaPEG, ver: 2.0
aboutDialog.TextRowB = Copyright © 2005 - 2009
aboutDialog.TextRowC = Detta program är programmerat i Java (JDK 1.6).
aboutDialog.TextRowD = Programmerat mellan 2005-05-24 - 2009-05-05.
aboutDialog.TextRowE = Version 2.0 släppt 2009-05-05.
aboutDialog.TextRowF = Utvecklare:
aboutDialog.TextRowG = Fredrik Möller
aboutDialog.TextRowH = _______________________________________

##############################################################################################################
# E R R O R  M E S S A G E S  M A I N G U I                                                                  #
##############################################################################################################
errormessage.maingui.errorMessageLabel                       = Fel
errormessage.maingui.warningMessageLabel                     = Varning
errormessage.maingui.informationMessageLabel                 = Information
errormessage.maingui.locationError                           = Kunde inte sätta fönster position enligt konfigurationen. Se loggfil för detaljer.
errormessage.maingui.toLongFileNameInPreviewTable            = Med denna filnamnsmall kommer filnamnet att bli 200 tecken långt."\n\n"Det kan innebära framtida problem då en sökväg inte kan vara längre än 255 tecken.\nFlytt till annan katalog än nuvarande valda kan då eventuellt bli omöjlig, då den\ntotala sökvägen: sökväg till mapp plus filnamnet får en längd som övertiger 255 tecken.
errormessage.maingui.toLongTotalPathInPreviewTable           = Den totala sökvägen: sökvägen till vald destinationsmapp och vald filnamsmall\nkommer att ge en sökväg som är längre än 255 tecken, vilket inte är tillåtet.\nAntingen måste filnamnet bli kortare (förändra filnamnsmallen) eller så måste\ndestinationsmapp med kortare sökväg väljas.
errormessage.maingui.invalidVariablesInSubFolderNameError    = Namnet på undermappen kan bara innehålla följande variabler:
errormessage.maingui.sameSourceAndDestination                = Käll och destinationskatalog är samma.

##############################################################################################################
# P R O G R E S S B A R                                                                                      #
##############################################################################################################
progress.ThumbNailLoading.title = Laddar tumnaglar

progress.RenameProcess.title.processStarting                                  = Byter namn på bilder...
progress.RenameProcess.title.processFinished                                  = Namnbytesprocessen färdig
progress.RenameProcess.dismissWindowButton.mnemonic                           = S
progress.RenameProcess.dismissWindowButton.processStarting                    = vänta..
progress.RenameProcess.dismissWindowButton.processFinished                    = Stäng
progress.RenameProcess.dismissWindowButton.processStarting.toolTip            = Namnbyte pågår...
progress.RenameProcess.dismissWindowButton.processFinished.toolTip            = Stäng detta fönster
progress.RenameProcess.openDestinationDirectoryButton.mnemonic                = P
progress.RenameProcess.openDestinationDirectoryButton.processStarting         = vänta..
progress.RenameProcess.openDestinationDirectoryButton.processFinished         = Öppna
progress.RenameProcess.openDestinationDirectoryButton.processStarting.toolTip = Namnbyte pågår...
progress.RenameProcess.openDestinationDirectoryButton.processFinished.toolTip = Öppna destinations katalogen i ett utforskarfönster

##############################################################################################################
# E R R O R  M E S S A G E S  J P G R E N A M E                                                              #
##############################################################################################################
errormessage.jpgrename.joptionPaneYes                            = Ja
errormessage.jpgrename.joptionPaneNo                             = Nej
errormessage.jpgrename.noImagesInPathLabel                       = Felmeddelande
errormessage.jpgrename.noImagesInPath                            = Den valda sökvägen innehåller inga bildfiler
errormessage.jpgrename.noExifInImage                             = Ingen exif information kunde hittas. För att göra om processen från början, ta bort följande fil från de filer som skall döpas om:
errormessage.jpgrename.noExifInImageLabel                        = Ingen Exif

##############################################################################################################
# E R R O R  M E S S A G E S  F I L E H A N D L E R                                                          #
##############################################################################################################
errormessage.filehandler.fileNotFoundExceptionA = Filen:
errormessage.filehandler.fileNotFoundExceptionB = kan inte hittas 
errormessage.filehandler.canNotReadFile         = Kan inte läsa filen: 
errormessage.filehandler.canNotWriteFile        = Kan inte skriva:

##############################################################################################################
# L A N G U A G E  O P T I O N S  G U I                                                                      #
##############################################################################################################
language.option.gui.windowTitle          = Språkinställningar
language.option.gui.selectionModeJLabel  = SPRÅKVALSMETOD
language.option.gui.manualRadioButton    = Manuellt
language.option.gui.automaticRadioButton = Automatiskt
language.option.gui.currentLanguageLabel = AKTUELLT SPRÅK
language.option.gui.availableLanguages   = TILLGÄNGLIGA SPRÅK
language.option.gui.languageNameNotFound = namn på språk ej funnet
language.option.gui.okButton             = Ok
language.option.gui.cancelButton         = Avbryt
language.option.gui.window.locationError = Kunde inte sätta fönsterposition enligt konfigurationen. Se loggfil för detaljer.

##############################################################################################################
# I N F O R M A T I O N  M E S S A G E S  L A N G U A G E  O P T I O N S  G U I                              #
##############################################################################################################
language.option.gui.information.windowlabel    = Information
language.option.gui.information.restartMessage = Valt språk kommer inte användas innan JavaPEG startats om

##############################################################################################################
# I N F O R M A T I O N  W I N D O W  ( U S E R  H E L P  A N D  V E R S I O N  I N F O R M A T I O N )      #
##############################################################################################################
information.window.userHelpWindowTitle           = Programhjälp
information.window.versionInformationWindowTitle = Versionsinformation

##############################################################################################################
# P I C T U R E  P A N E L                                                                                   #
##############################################################################################################
picture.panel.pictureLabel = BILDER I VALD MAPP

##############################################################################################################
# F I L E  C H O O S E R                                                                                     #
##############################################################################################################
filechooser.window.title                  = Filväljare
filechooser.window.locationError          = Kunde inte sätta fönsterposition enligt konfigurationen. Se loggfil för detaljer.
filechooser.button.ok                     = Ok
filechooser.button.ok.mnemonic            = O
filechooser.button.cancel                 = Avbryt
filechooser.button.cancel.mnemonic        = A

##############################################################################################################
# H E L P  V I E W E R                                                                                       #
##############################################################################################################
helpViewerGUI.window.locationError    = Kunde inte sätta fönster position enligt konfigurationen. Se loggfil för detaljer.
helpViewerGUI.window.title            = Hjälp
helpViewerGUI.tree.content            = Innehåll
helpViewerGUI.tree.programHelp        = Programhjälp
helpViewerGUI.tree.versionInformation = Versionsinformation
helpViewerGUI.tree.overviewCreator    = Tumnagelöversikt
helpViewerGUI.tree.references         = Referenser

##############################################################################################################
# I N F O R M A T I O N  P A N E L                                                                           #
##############################################################################################################
information.panel.informationLabel   = INFORMATION
information.panel.columnNameFileName = Filnamn
information.panel.fileNameCurrent    = Nuvarande filnamn
information.panel.fileNamePreview    = Förhandsgranskning av filnamn
information.panel.metaDataLabel      = METADATA
information.panel.previewLabel       = FÖRHANDSGRANSKNING
information.panel.progressLabel      = FÖRLOPP
information.panel.subFolderNameLabel = Undermappsnamn

##############################################################################################################
# R E N A M E  P R O C E S S  I N F O R M A T I O N  M E S S A G E                                           #
##############################################################################################################
rename.PreFileProcessor.starting                           = Proceser Före Filomdöpningsprocessen Startad
rename.PreFileProcessor.error                              = Proceser Före Filomdöpningsprocessen Fann Följande Fel:
rename.PreFileProcessor.finished                           = Proceser Före Filomdöpningsprocessen Avslutad
rename.PreFileProcessor.sourceAndDestinationPath           = Validerar Käll Och Destinations Sökvägar
rename.PreFileProcessor.fileAndSubDirectoryTemplate        = Validerar Filnams Och Underkatalognamns-mallar
rename.PreFileProcessor.destinationDirectoryDoesNotExist   = Validerar Att Destinationskatalogen Inte Existerar
rename.PreFileProcessor.uniqueFilesInSourceDirectory       = Validerar Unika Bild Filer i Källkatalogen
rename.PreFileProcessor.jPEGTotalPathLength                = Validerar Total Sökvägslängd För JPEG Filer
rename.PreFileProcessor.nonJPEGTotalPathLength             = Validerar Total Sökvägslängd För Icke JPEG Filer
rename.PreFileProcessor.availableDiskSpace                 = Validerar Tillgängligt Disk Utrymme
rename.PreFileProcessor.fileCreationAtDestinationDirectory = Validerar Filskapande I Destinationskatalogen
rename.PreFileProcessor.externalOverviewLayout             = Validerar Layout För Tumnagelöversikt

rename.FileProcessor.starting                               = Filomdöpningsprocessen Är Startad
rename.FileProcessor.finished                               = Filomdöpningsprocessen Är Avslutad
rename.FileProcessor.createSubDirectory                     = Skapande Av Underkatalog
rename.FileProcessor.createThumbNailsDirectory              = Skapande av Tumnagelkatalog
rename.FileProcessor.createAndTransferContentOfJPEGFiles    = Kopiering Och Namnbyte Av JPEG Filer
rename.FileProcessor.createThumbNails                       = Skapande Av Tumnaglar
rename.FileProcessor.createAndTransferContentOfNonJPEGFiles = Kopiering Av Icke JPEG Filer

rename.PostFileProcessor.integrityCheck.starting = Integritetskontroll Av Filer Startad
rename.PostFileProcessor.integrityCheck.finished = Integritetskontroll Av Filer Avslutad
rename.PostFileProcessor.integrityCheck.error    = Alla Filer Har Inte Kopierats Korrekt, Se Loggfil För Detaljer
rename.PostFileProcessor.integrityCheck.checking = Integritet På Kopierade Filer Kontrolleras

##############################################################################################################
# P R E  F I L E  P R O C E S S O R  V A L I D A T O R S                                                     #
##############################################################################################################
#
# Available disk space validator
validator.availablediskspace.notEnoughDiskSpace = Det finns inte tillräckligt med lagringsutrymme i vald destination.

# Destination directory does not exist validator
validator.destinationdirectorydoesnotexist.existingSubDirectory = Destinations katalogen existerar redan.

# File and sub directory template validator
validator.fileandsubdirectorytemplate.noSubFolderNameError                    = Ett namn på underkatalogen måste anges
validator.fileandsubdirectorytemplate.invalidCharactersInSubFolderNameError   = Namnet på undermappen innehåller otillåtna tecken
validator.fileandsubdirectorytemplate.invalidVariablesInSubFolderNameError    = Namnet på undermappen kan bara innehålla följande variabler:
validator.fileandsubdirectorytemplate.dotFirstInSubFolderNameTemplate         = En punkt som första tecken i undermappsnamnet är inte tillåtet
validator.fileandsubdirectorytemplate.dotLastInSubFolderNameTemplate          = En punkt som sista tecken i undermappsnamnet är inte tillåtet
validator.fileandsubdirectorytemplate.noFileNameError                         = En filnamnsmall måste definieras
validator.fileandsubdirectorytemplate.invalidCharactersInFileNameError        = Filnamnsmallen innehåller otillåtna tecken
validator.fileandsubdirectorytemplate.dotFirstInFileNameTemplate              = En punkt som första tecken i filnamnsmallen är inte tillåtet
validator.fileandsubdirectorytemplate.dotLastInFileNameTemplate               = En punkt som sista tecken i filnamnsmallen är inte tillåtet

# File creation at destination directory
validator.filecreationatdestinationdirectory.couldNotCreateSubDirectory       = Kunde inte skapa underkatalog
validator.filecreationatdestinationdirectory.couldNotCreateAllJPEGFiles       = Kunde inte skapa alla JPEG filer
validator.filecreationatdestinationdirectory.couldNotCreateThumbNailDirectory = Kunde inte skapa tumnagels katalogen
validator.filecreationatdestinationdirectory.couldNotCreateAllThumbNails      = Kunde inte skapa alla tumnaglar
validator.filecreationatdestinationdirectory.couldNotCreateAllNonJPEGFiles    = Kunde inte skapa alla icke JPEG filer
validator.filecreationatdestinationdirectory.couldNotDeleateSubDirectory      = Kunde inte ta bort underkatalogen

# JPEG total path length
validator.jpegtotalpathlength.toLongFileName               = Med nuvarande destinationskatalog och mallar så kommer den totala sökvägen\nför de omdöpta filerna bli längre än vad som är tillåtet.\n\nFör att undvika detta, ändra antingen destinationskatalog eller mallarna. 
validator.jpegtotalpathlength.noJPEGFIlesInSourceDirectory = Inga JPEG filer i den valda källkatalogen

# Non JPEG total path length
validator.nonjpegtotalpathlength.toLongFileName      = The total path with the current destinaton directory will be to long for the file:
validator.nonjpegtotalpathlength.toLongDirectoryPath = The total path with the current destinaton directory will be to long for the directory:

# Source and destination path validator
validator.sourceanddestinationpath.noSourcePathError                       = En sökväg till bildmappen måste anges
validator.sourceanddestinationpath.invalidCharactersInSourcePathError      = Sökvägen till bildmappen innehåller otillåtna tecken
validator.sourceanddestinationpath.noDestinationPathError                  = En sökväg till destinationsmappen måste anges
validator.sourceanddestinationpath.invalidCharactersInDestinationPathError = Sökvägen till destinationsmappen innehåller otillåtna tecken

# Thumb nail overview layout validator
validator.externalOverviewLayout.invalidXMLFile = Innehållet i filen layout.xml är inte giltigt. Se loggfil för detaljer.

##############################################################################################################
# T H U M B N A I L  O V E R V I E W                                                                         #
##############################################################################################################

thumbnailoverview.ThumbNailOverViewCreator.starting             = Skapande Av Tumnagelöversikt Startad
thumbnailoverview.ThumbNailOverViewCreator.finished             = Skapande Av Tumnagelöversikt Avslutad
thumbnailoverview.ThumbNailOverViewCreator.error.createCSSFile  = Kunde inte skapa CSS fil i destinationskatalogen. Se loggfil för detaljer.
thumbnailoverview.ThumbNailOverViewCreator.error.accessCSSFile  = Atkomst nekad till CSS fil. Se loggfil för detaljer.
thumbnailoverview.ThumbNailOverViewCreator.error.createHTMLFile = Kunde inte skapa HTML file i destinationskatalogen.
thumbnailoverview.ThumbNailOverViewCreator.error.accessHTMLFile = Åtkomst nekad till HTML fil. Se loggfil för detaljer.

thumbnailoverview.LayoutParser.wrongElementAmount = Filen layout.xml innehåller fel antal av element:
thumbnailoverview.LayoutParser.parseError         = Fel vid inläsning av layout.xml. Se loggfil för detaljer.

##############################################################################################################
# U P D A T E  C H E C K E R                                                                                 #
##############################################################################################################

updatechecker.newVersion     = Det finns en nyare version av JavaPEG tillgänglig. Besök http://javapeg.sourceforge.net för att finna nedladdningslänk
updatechecker.uRLInvalid     = Ogiltigt format på url:en till uppdateringsservern. Se loggfil för detaljer.
updatechecker.uRLWrong       = Fel url till uppdateringsservern. Se loggfil för detaljer.
updatechecker.networkTimeOut = Uppdateringsserven svarade inte, nytt försök sker nästa programstart.