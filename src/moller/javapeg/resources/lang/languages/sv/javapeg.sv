# This is the Swedish language file for JavaPEG.

# The structure of the file is as follows:

# A line starting with "#" is interpreted as a comment.

# A valid non comment line may look like "file = Arkiv" where "file" is the variable, = the
# separator and "Arkiv" is the value of the variable, in this case the Swedish word for file.

# There might be whitespaces or tabs in between the variable and the "=". Any whitespace
# or other character after the value of the variable will be trimmed away.

# For more information regarding the rules for the syntax of this file please visit:
# http://java.sun.com/javase/6/docs/api/java/util/Properties.html

#############################################################################################################
# M E N U                                                                                                   #
#############################################################################################################
menu.file          = Arkiv
menu.help          = Hjälp
menu.configuration = Inställningar

#############################################################################################################
# M E N U  I T E M S                                                                                        #
#############################################################################################################
menu.item.exit                       = Avsluta
menu.item.openDestinationFileChooser = Öppna Destinationsfilsväljaren
menu.item.startProcess               = Starta Namnbytesprocessen
menu.item.programHelp                = Programhjälp
menu.item.versionInformation         = Versionsinformation
menu.item.about                      = Om JavaPEG 2.4
menu.item.configuration              = Inställningar

#############################################################################################################
# M E N U  M N E M O N I C                                                                                  #
#############################################################################################################
menu.mnemonic.file          = A
menu.mnemonic.help          = H
menu.mnemonic.configuration = I

#############################################################################################################
# M E N U  A C C E L E R A T O R                                                                            #
#############################################################################################################
menu.iten.openDestinationFileChooser.accelerator = D
menu.iten.startProcess.accelerator               = P
menu.iten.exit.accelerator                       = X
menu.item.versionInformation.accelerator         = V
menu.item.about.accelerator                      = O

##############################################################################################################
# L A B E L S                                                                                                #
##############################################################################################################
labels.sourcePath       = KÄLLKATALOG
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
variable.sourceNameVariable    = f
variable.sourceName            = Ursprungligt filnamn

##############################################################################################################
# V A R I A B L E S  C O M M E N T                                                                           #
##############################################################################################################
variable.comment.infoLabelA = * = Dessa variabler kan användas
variable.comment.infoLabelB = vid namngivning av undermapp
variable.comment.fileName   = Filnamn

##############################################################################################################
# C H E C K  B O X E S                                                                                       #
##############################################################################################################
checkbox.createThumbNails = SKAPA TUMNAGELÖVERSIKT

##############################################################################################################
# T O O L T I P S                                                                                            #
##############################################################################################################
tooltip.destinationPathButton                                  = Välj sökväg för destinationskatalog
tooltip.subFolderName                                          = Ange namnet på mappen inklusive eventuella variabler här
tooltip.fileNameTemplate                                       = Skriv in filnamnsmallen inklusive variabler här
tooltip.createThumbNails                                       = Skapa tumnaglar av omdöpta filer
tooltip.openDestinationFolder                                  = Öppna mappen där de omdöpta filerna hamnar efter avslutad process
tooltip.beginNameChangeProcessButton                           = Starta namnbytarprocessen
tooltip.selectSourceDirectoryWithImages                        = Välj en katalog med bilder
tooltip.selectDestinationDirectory                             = Välj en destinationskatalog
tooltip.selectSourceDirectoryWithImagesAndDestinationDirectory = Välj en källkatalog med bilder och en destinationskatalog
tooltip.enableTemplateFields                                   = För att aktivera, välj en destinationskatalog

##############################################################################################################
# F I L E  S E L E C T I O N  D I A L O G S                                                                  #
##############################################################################################################
fileSelectionDialog.destinationPathFileChooser = Välj destinationsmapp

##############################################################################################################
# A B O U T  D I A L O G                                                                                     #
##############################################################################################################
aboutDialog.Label    = Om JavaPEG 2.4
aboutDialog.TextRowA = JavaPEG, ver: 2.4
aboutDialog.TextRowB = Copyright © 2005 - 2009
aboutDialog.TextRowC = Detta program är programmerat i Java (JDK 1.6).
aboutDialog.TextRowD = Programmerat mellan 2005-05-24 - 2009-11-17.
aboutDialog.TextRowE = Version 2.4 släppt 2009-11-17.
aboutDialog.TextRowF = Utvecklare:
aboutDialog.TextRowG = Fredrik Möller
aboutDialog.TextRowH = _______________________________________

##############################################################################################################
# E R R O R  M E S S A G E S  M A I N G U I                                                                  #
##############################################################################################################
errormessage.maingui.errorMessageLabel             = Fel
errormessage.maingui.warningMessageLabel           = Varning
errormessage.maingui.informationMessageLabel       = Information
errormessage.maingui.locationError                 = Kunde inte sätta fönster position enligt konfigurationen. Se loggfil för detaljer.
errormessage.maingui.sameSourceAndDestination      = Käll och destinationskatalog är samma.
errormessage.maingui.notEnoughMemory               = Minnesfel - Applikationsstart avbryts.\n\nJavaPEG måste startas med sin genväg eller med -Xmx384m som JVM parameter.\n\nSe loggfil för detaljer. 
errormessage.maingui.onlyOneImageViewer            = Det är bara möjligt att starta en bildvisare.

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
errormessage.jpgrename.joptionPaneYes          = Ja
errormessage.jpgrename.joptionPaneNo           = Nej
errormessage.jpgrename.noImagesInPathLabel     = Felmeddelande
errormessage.jpgrename.noImagesInPath          = Den valda sökvägen innehåller inga bildfiler
errormessage.jpgrename.noExifInImage           = Ingen exif information kunde hittas. För att göra om processen från början, ta bort följande fil från de filer som skall döpas om:
errormessage.jpgrename.noExifInImageLabel      = Ingen Exif
errormessage.jpgrename.imageViewerMustBeClosed = Namnbytesprocessen kan inte starta när bildvisaren är startad.\nVänligen stäng bildvisaren och starta namnbytesprocessen igen.

##############################################################################################################
# E R R O R  M E S S A G E S  F I L E H A N D L E R                                                          #
##############################################################################################################
errormessage.filehandler.fileNotFoundExceptionA = Filen:
errormessage.filehandler.fileNotFoundExceptionB = kan inte hittas 
errormessage.filehandler.canNotReadFile         = Kan inte läsa filen: 
errormessage.filehandler.canNotWriteFile        = Kan inte skriva:

##############################################################################################################
# E R R O R  M E S S A G E S  U N C A U G H T  E X C E P T I O N S                                           #
##############################################################################################################
errormessage.uncaughtexceptionhandler.outOfMemoryError        = JavaPEG har fått slut på minne.\n\nSe loggfil för detaljer och se README-filen\ni installationskatalogen för en lösning.\n\nJavaPEG kommer att avslutas.
errormessage.uncaughtexceptionhandler.unexpectedErrorPartOne  = Ett oväntat fel har inträffat:
errormessage.uncaughtexceptionhandler.unexpectedErrorPartTwo  = .\n\nSe loggfil för detaljer och se README-filen\ni installationskatalogen för en lösning.\n\nJavaPEG kommer att avslutas.

##############################################################################################################
# M A I N  G U I  T A B B E D  P A N E                                                                       #
##############################################################################################################
maingui.tabbedpane.imagelist.label.list                 = BILDLISTA
maingui.tabbedpane.imagelist.label.preview              = FÖRHANDSGRANSKNING
maingui.tabbedpane.imagelist.label.numberOfImagesInList = Antal bilder i bildlistan:

maingui.tabbedpane.imagelist.button.removeSelectedImages = Ta bort markerade bilder från listan
maingui.tabbedpane.imagelist.button.removeAllImages      = Ta bort alla bilder från listan
maingui.tabbedpane.imagelist.button.openImageList        = Öpna en sparad bildlista
maingui.tabbedpane.imagelist.button.saveImageList        = Spara bildlistan
maingui.tabbedpane.imagelist.button.exportImageList      = Exportera bildlistan
maingui.tabbedpane.imagelist.button.moveUp               = Flytta vald bild uppåt i listan
maingui.tabbedpane.imagelist.button.moveDown             = Flytta vald bild nedåt i listan
maingui.tabbedpane.imagelist.button.viewImages           = Visa valda bilder i bildvisaren
maingui.tabbedpane.imagelist.button.moveToTop            = Flytta vald bild till toppen av listan
maingui.tabbedpane.imagelist.button.moveToBottom         = Flytta vald bild till botten av listan

maingui.tabbedpane.imagelist.filechooser.openImageList.title                    = Öppna 
maingui.tabbedpane.imagelist.filechooser.openImageList.nonSavedImageListMessage = Bildlistan är inte sparad, skriv över?
maingui.tabbedpane.imagelist.filechooser.openImageList.missingFilesErrorMessage = Följande filer listade i den valda filen finns inte och laddades därmed inte till listan:

maingui.tabbedpane.imagelist.filechooser.saveImageList.title = Spara

maingui.tabbedpane.imagelist.filechooser.exportImageList.title = Export

maingui.tabbedpane.imagelist.imagelistformat.imageList.listAlreadyExists = existerar redan. Skriva över?

maingui.tabbedpane.imagelist.imagelistformat.javaPEG.successfullySaved    = Bildlistan sparades korrekt.
maingui.tabbedpane.imagelist.imagelistformat.javaPEG.notSuccessfullySaved = Bildlistan kunde inte sparas, se loggfil för detaljer.

maingui.tabbedpane.imagelist.imagelistformat.polyView.successfullyCreated    = Bildlistan i PolyView-format skapades korrekt.
maingui.tabbedpane.imagelist.imagelistformat.polyView.notSuccessfullyCreated = Bildlistan i PolyView-format kunde inte skapas. Se loggfil för detaljer.

maingui.tabbedpane.imagelist.imagelistformat.irfanView.successfullyCreated    = Bildlistan i IrfanView-format skapades korrekt.
maingui.tabbedpane.imagelist.imagelistformat.irfanView.notSuccessfullyCreated = Bildlistan i IrfanView-format kunde inte skapas. Se loggfil för detaljer.

maingui.tabbedpane.imagelist.imagelistformat.xnView.successfullyCreated    = Bildlistan i XnView-format skapades korrekt.
maingui.tabbedpane.imagelist.imagelistformat.xnView.notSuccessfullyCreated = Bildlistan i XnView-format kunde inte skapas. Se loggfil för detaljer.

##############################################################################################################
# M A I N  G U I  P O P U P  M E N U                                                                         #
##############################################################################################################
maingui.popupmenu.addImageToList        = Lägg till bild till bildlistan
maingui.popupmenu.addAllImagesToList    = Lägg till alla bilder till bildlistan
maingui.popupmenu.copyToSystemClipboard = Kopiera till urklipp

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
# H E L P  V I E W E R                                                                                       #
##############################################################################################################
helpViewerGUI.window.locationError = Kunde inte sätta fönster position enligt konfigurationen. Se loggfil för detaljer.
helpViewerGUI.window.title         = Hjälp
helpViewerGUI.errorMessage         = Kunde inte ladda hjälpfil, se loggfil för detaljer.

helpViewerGUI.tree.content                    = Innehåll
helpViewerGUI.tree.programHelpOverView        = PROGRAMBESKRIVNING
helpViewerGUI.tree.programHelpRename          = BYT NAMN PÅ BILDER
helpViewerGUI.tree.programHelpViewImages      = VISA BILDER
helpViewerGUI.tree.programHelpImageViewer     = BILDVISARE
helpViewerGUI.tree.programHelpOverviewCreator = SKAPA TUMNAGELÖVERSIKT
helpViewerGUI.tree.versionInformation         = VERSIONSINFORMATION
helpViewerGUI.tree.references                 = REFERENSER / TACK TILL
helpViewerGUI.tree.configuration              = Inställningar
helpViewerGUI.tree.logging                    = LOGGNING
helpViewerGUI.tree.updates                    = UPPDATERINGAR
helpViewerGUI.tree.rename                     = NAMNBYTE
helpViewerGUI.tree.language                   = SPRÅK

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
rename.FileProcessor.renameFromLabel                        = Fil:
rename.FileProcessor.renameToLabel                          = Omdöpt till:

rename.PostFileProcessor.integrityCheck.starting = Integritetskontroll Av Filer Startad
rename.PostFileProcessor.integrityCheck.finished = Integritetskontroll Av Filer Avslutad
rename.PostFileProcessor.integrityCheck.error    = Alla Filer Har Inte Kopierats Korrekt, Se Loggfil För Detaljer
rename.PostFileProcessor.integrityCheck.checking = Integritet På Kopierade Filer Kontrolleras
rename.PostFileProcessor.renameFromLabel         = Fil:
rename.PostFileProcessor.copiedWithError         = Kopierad med FEL till:
rename.PostFileProcessor.copiedOK                = Kopierad OK till:
rename.PostFileProcessor.renamedWithError        = Namnbytt med FEL till:
rename.PostFileProcessor.renamedOK               = Namnbytt OK till:

##############################################################################################################
# P R E  F I L E  P R O C E S S O R  V A L I D A T O R S                                                     #
##############################################################################################################
#
# Available disk space validator
validator.availablediskspace.notEnoughDiskSpace = Det finns inte tillräckligt med lagringsutrymme i vald destination.

# Destination directory does not exist validator
validator.destinationdirectorydoesnotexist.existingSubDirectory = Destinations katalogen existerar redan.

# File and sub directory template validator
validator.fileandsubdirectorytemplate.noSubFolderNameError                  = Ett namn på underkatalogen måste anges
validator.fileandsubdirectorytemplate.invalidCharactersInSubFolderNameError = Namnet på undermappen innehåller otillåtna tecken
validator.fileandsubdirectorytemplate.invalidVariablesInSubFolderNameError  = Namnet på undermappen kan bara innehålla följande variabler:
validator.fileandsubdirectorytemplate.dotFirstInSubFolderNameTemplate       = En punkt som första tecken i undermappsnamnet är inte tillåtet
validator.fileandsubdirectorytemplate.dotLastInSubFolderNameTemplate        = En punkt som sista tecken i undermappsnamnet är inte tillåtet
validator.fileandsubdirectorytemplate.noFileNameError                       = En filnamnsmall måste definieras
validator.fileandsubdirectorytemplate.invalidCharactersInFileNameError      = Filnamnsmallen innehåller otillåtna tecken
validator.fileandsubdirectorytemplate.dotFirstInFileNameTemplate            = En punkt som första tecken i filnamnsmallen är inte tillåtet
validator.fileandsubdirectorytemplate.dotLastInFileNameTemplate             = En punkt som sista tecken i filnamnsmallen är inte tillåtet

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
validator.nonjpegtotalpathlength.toLongFileName      = Den totala sökvägen med vald destinationskatalog kommer att bli för lång för filen:
validator.nonjpegtotalpathlength.toLongDirectoryPath = Den totala sökvägen med vald destinationskatalog kommer att bli för lång för katalogen:

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

updatechecker.errormessage.uRLInvalid                  = Ogiltigt format på url:en till uppdateringsservern. Se loggfil för detaljer.
updatechecker.errormessage.uRLWrong                    = Fel url till uppdateringsservern. Se loggfil för detaljer.
updatechecker.errormessage.networkTimeOut              = Uppdateringsserven svarade inte, nytt försök sker nästa programstart.
updatechecker.errormessage.downloadError               = Kunde inte ladda ner den senaste versionen. Se loggfil för detaljer.
updatechecker.errormessage.parseException              = Kunde inte läsa hämtad fil, se loggfil för detaljer.
updatechecker.errormessage.parseConfigurationException = Kunde inte konfigurera en XML parser. Se loggfil för detaljer.

updatechecker.informationmessage.downloadFinished = Nedladdningen klar.

updatechecker.gui.title          = Ny version tillgänglig
updatechecker.gui.newVersion     = Det finns en nyare version av JavaPEG tillgänglig. Se nedan för loggen över ändringar.
updatechecker.gui.downloadButton = Ladda ner
updatechecker.gui.closeButton    = Stäng

##############################################################################################################
# M E T A  D A T A  P A N E L                                                                                #
##############################################################################################################
metadatapanel.titleDefaultText     = METADATA FÖR BILD:
metadatapanel.tableheader.type     = TYP
metadatapanel.tableheader.property = EGENSKAP
metadatapanel.tableheader.value    = VÄRDE

##############################################################################################################
# S T A T U S  B A R  M E S S A G E S                                                                        #
##############################################################################################################

statusbar.message.amountOfRows              = Antal rader
statusbar.message.amountOfColumns           = Antal kolumner
statusbar.message.amountOfImagesInDirectory = Antal bilder i katalogen
statusbar.message.selectedPath              = Vald sökväg:

##############################################################################################################
# T A B B E D  P A N E (M A I N  F U N C T I O N S)                                                          #
##############################################################################################################
tabbedpane.imageRename = BYT NAMN PÅ BILDER
tabbedpane.imageView   = SÖK / VISA BILDER
tabbedpane.imageTag    = TAGGA BILDER

##############################################################################################################
# F I L E  R E T R I E V E R                                                                                 #
##############################################################################################################
fileretriever.canNotFindFile     = Kan inte hitta filen. Se loggfil för detaljer.
fileretriever.canNotReadFromFile = Kan inte läsa från filen. Det kan vara ett annat program som låst filen. Se loggfil för detaljer.

metadata.field.name.APERTURE_VALUE = BLÄNDARVÄRDE
metadata.field.name.CAMERA_MODEL = KAMERAMODELL
metadata.field.name.IMAGE_SIZE = BILDSTORLEK 
metadata.field.name.ISO = ISO
metadata.field.name.SHUTTER_SPEED = SLUTARTID
metadata.field.name.YEAR = ÅR
metadata.field.name.MONTH = MÅNAD
metadata.field.name.DAY = DAG
metadata.field.name.HOUR = TIMME
metadata.field.name.MINUTE = MINUT
metadata.field.name.SECOND = SEKUND

##############################################################################################################
# F I N D  I M A G E  S E C T I O N                                                                          #
##############################################################################################################
findimage.categories.label                                = KATEGORIER
findimage.categories.andRadioButton.label                 = OCH
findimage.categories.andRadioButton.tooltip               = <html>En bild måste ha alla valda kategorier<br/>tilldelade för att adderas till sökresultateta.</html>
findimage.categories.orRadioButton.label                  = ELLER
findimage.categories.orRadioButton.tooltip                = <html>En bild kan ha valfri kombination av valda<br/>katergorier tilldelad för att adderas till sökresultatet.</html>
findimage.categories.clearCategoriesSelectionButton.label = Rensa valda kategorier
findimage.categories.removeAllCategoriesAndSubCategories1 = Ta bort kategori:
findimage.categories.removeAllCategoriesAndSubCategories2 = och alla underkategorier?
findimage.categories.removeAllCategoriesAndSubCategories3 = ?
findimage.categories.addNewTopLevelCategory               = Lägg till ny toppnivåkategori
findimage.categories.collapseTopLevelCategories           = Fäll ihop toppnivåkategorier
findimage.categories.expandTopLevelCategories             = Expandera toppnivåkategorier
findimage.categories.collapseCategory                     = Fäll ihop kategori:
findimage.categories.expandCategory                       = Expandera kategori:
findimage.categories.addNewSubCategoryToCategory          = Lägg till ny underkategori till kategori:
findimage.categories.renameSelectedCategory               = Byt namn på vald kategori:
findimage.categories.removeSelectedCategory               = Ta bort vald kategori:
findimage.imagemetadata.label = BILDMETADATA
findimage.rating.label        = KLASSIFICERING
findimage.rating.tooltip.bad  = Dålig
findimage.rating.tooltip.good = Bra
findimage.comment.label              = KOMMENTAR
findimage.comment.defaultCommentText = Skriv kommentar här
findimage.preview.label = FÖRHANDSGRANSKNING
findimage.clearAllMetaDataParameters.tooltip = Rensa alla valda metadataparametrar
findimage.searchImages.tooltip = Sök bilder
findimage.searchImages.result = No images found