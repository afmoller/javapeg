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
menu.help          = Hj�lp
menu.configuration = Inst�llningar

#############################################################################################################
# M E N U  I T E M S                                                                                        #
#############################################################################################################
menu.item.exit                       = Avsluta
menu.item.openDestinationFileChooser = �ppna Destinationsfilsv�ljaren
menu.item.startProcess               = Starta Namnbytesprocessen
menu.item.programHelp                = Programhj�lp
menu.item.versionInformation         = Versionsinformation
menu.item.about                      = Om JavaPEG 2.4
menu.item.configuration              = Inst�llningar

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
labels.sourcePath       = K�LLKATALOG
labels.destinatonPath   = S�KV�G TILL DESTINATIONSKATALOG
labels.subFolderName    = UNDERKATALOGSMALL
labels.fileNameTemplate = FILNAMNSMALL
labels.variables        = VARIABLER

##############################################################################################################
# V A R I A B L E S                                                                                          #
##############################################################################################################
variable.pictureDateVariable   = d
variable.pictureDate           = Datum d� bilden togs
variable.pictureTimeVariable   = t
variable.pictureTime           = Tidpunkt d� bilden togs
variable.cameraModelVariable   = m
variable.cameraModel           = Kameramodell
variable.shutterSpeedVariable  = s
variable.shutterSpeed          = Slutartid
variable.isoValueVariable      = i
variable.isoValue              = ISO-v�rde
variable.pictureWidthVariable  = b
variable.pictureWidth          = Bildbredd i pixlar
variable.pictureHeightVariable = h
variable.pictureHeight         = Bildh�jd i pixlar
variable.apertureValueVariable = bl
variable.apertureValue         = Bl�ndarv�rde
variable.dateOftodayVariable   = dd
variable.dateOftoday           = Dagens datum
variable.sourceNameVariable    = f
variable.sourceName            = Ursprungligt filnamn

##############################################################################################################
# V A R I A B L E S  C O M M E N T                                                                           #
##############################################################################################################
variable.comment.infoLabelA = * = Dessa variabler kan anv�ndas
variable.comment.infoLabelB = vid namngivning av undermapp
variable.comment.fileName   = Filnamn

##############################################################################################################
# C H E C K  B O X E S                                                                                       #
##############################################################################################################
checkbox.createThumbNails = SKAPA TUMNAGEL�VERSIKT

##############################################################################################################
# T O O L T I P S                                                                                            #
##############################################################################################################
tooltip.destinationPathButton                                  = V�lj s�kv�g f�r destinationskatalog
tooltip.subFolderName                                          = Ange namnet p� mappen inklusive eventuella variabler h�r
tooltip.fileNameTemplate                                       = Skriv in filnamnsmallen inklusive variabler h�r
tooltip.createThumbNails                                       = Skapa tumnaglar av omd�pta filer
tooltip.openDestinationFolder                                  = �ppna mappen d�r de omd�pta filerna hamnar efter avslutad process
tooltip.beginNameChangeProcessButton                           = Starta namnbytarprocessen
tooltip.selectSourceDirectoryWithImages                        = V�lj en katalog med bilder
tooltip.selectDestinationDirectory                             = V�lj en destinationskatalog
tooltip.selectSourceDirectoryWithImagesAndDestinationDirectory = V�lj en k�llkatalog med bilder och en destinationskatalog
tooltip.enableTemplateFields                                   = F�r att aktivera, v�lj en destinationskatalog

##############################################################################################################
# F I L E  S E L E C T I O N  D I A L O G S                                                                  #
##############################################################################################################
fileSelectionDialog.destinationPathFileChooser = V�lj destinationsmapp

##############################################################################################################
# A B O U T  D I A L O G                                                                                     #
##############################################################################################################
aboutDialog.Label    = Om JavaPEG 2.4
aboutDialog.TextRowA = JavaPEG, ver: 2.4
aboutDialog.TextRowB = Copyright � 2005 - 2009
aboutDialog.TextRowC = Detta program �r programmerat i Java (JDK 1.6).
aboutDialog.TextRowD = Programmerat mellan 2005-05-24 - 2009-11-17.
aboutDialog.TextRowE = Version 2.4 sl�ppt 2009-11-17.
aboutDialog.TextRowF = Utvecklare:
aboutDialog.TextRowG = Fredrik M�ller
aboutDialog.TextRowH = _______________________________________

##############################################################################################################
# E R R O R  M E S S A G E S  M A I N G U I                                                                  #
##############################################################################################################
errormessage.maingui.errorMessageLabel             = Fel
errormessage.maingui.warningMessageLabel           = Varning
errormessage.maingui.informationMessageLabel       = Information
errormessage.maingui.locationError                 = Kunde inte s�tta f�nster position enligt konfigurationen. Se loggfil f�r detaljer.
errormessage.maingui.sameSourceAndDestination      = K�ll och destinationskatalog �r samma.
errormessage.maingui.notEnoughMemory               = Minnesfel - Applikationsstart avbryts.\n\nJavaPEG m�ste startas med sin genv�g eller med -Xmx384m som JVM parameter.\n\nSe loggfil f�r detaljer. 
errormessage.maingui.onlyOneImageViewer            = Det �r bara m�jligt att starta en bildvisare.

##############################################################################################################
# P R O G R E S S B A R                                                                                      #
##############################################################################################################
progress.ThumbNailLoading.title = Laddar tumnaglar

progress.RenameProcess.title.processStarting                                  = Byter namn p� bilder...
progress.RenameProcess.title.processFinished                                  = Namnbytesprocessen f�rdig
progress.RenameProcess.dismissWindowButton.mnemonic                           = S
progress.RenameProcess.dismissWindowButton.processStarting                    = v�nta..
progress.RenameProcess.dismissWindowButton.processFinished                    = St�ng
progress.RenameProcess.dismissWindowButton.processStarting.toolTip            = Namnbyte p�g�r...
progress.RenameProcess.dismissWindowButton.processFinished.toolTip            = St�ng detta f�nster
progress.RenameProcess.openDestinationDirectoryButton.mnemonic                = P
progress.RenameProcess.openDestinationDirectoryButton.processStarting         = v�nta..
progress.RenameProcess.openDestinationDirectoryButton.processFinished         = �ppna
progress.RenameProcess.openDestinationDirectoryButton.processStarting.toolTip = Namnbyte p�g�r...
progress.RenameProcess.openDestinationDirectoryButton.processFinished.toolTip = �ppna destinations katalogen i ett utforskarf�nster

##############################################################################################################
# E R R O R  M E S S A G E S  J P G R E N A M E                                                              #
##############################################################################################################
errormessage.jpgrename.joptionPaneYes          = Ja
errormessage.jpgrename.joptionPaneNo           = Nej
errormessage.jpgrename.noImagesInPathLabel     = Felmeddelande
errormessage.jpgrename.noImagesInPath          = Den valda s�kv�gen inneh�ller inga bildfiler
errormessage.jpgrename.noExifInImage           = Ingen exif information kunde hittas. F�r att g�ra om processen fr�n b�rjan, ta bort f�ljande fil fr�n de filer som skall d�pas om:
errormessage.jpgrename.noExifInImageLabel      = Ingen Exif
errormessage.jpgrename.imageViewerMustBeClosed = Namnbytesprocessen kan inte starta n�r bildvisaren �r startad.\nV�nligen st�ng bildvisaren och starta namnbytesprocessen igen.

##############################################################################################################
# E R R O R  M E S S A G E S  F I L E H A N D L E R                                                          #
##############################################################################################################
errormessage.filehandler.fileNotFoundExceptionA = Filen:
errormessage.filehandler.fileNotFoundExceptionB = kan inte hittas 
errormessage.filehandler.canNotReadFile         = Kan inte l�sa filen: 
errormessage.filehandler.canNotWriteFile        = Kan inte skriva:

##############################################################################################################
# E R R O R  M E S S A G E S  U N C A U G H T  E X C E P T I O N S                                           #
##############################################################################################################
errormessage.uncaughtexceptionhandler.outOfMemoryError        = JavaPEG har f�tt slut p� minne.\n\nSe loggfil f�r detaljer och se README-filen\ni installationskatalogen f�r en l�sning.\n\nJavaPEG kommer att avslutas.
errormessage.uncaughtexceptionhandler.unexpectedErrorPartOne  = Ett ov�ntat fel har intr�ffat:
errormessage.uncaughtexceptionhandler.unexpectedErrorPartTwo  = .\n\nSe loggfil f�r detaljer och se README-filen\ni installationskatalogen f�r en l�sning.\n\nJavaPEG kommer att avslutas.

##############################################################################################################
# M A I N  G U I  T A B B E D  P A N E                                                                       #
##############################################################################################################
maingui.tabbedpane.imagelist.label.list                 = BILDLISTA
maingui.tabbedpane.imagelist.label.preview              = F�RHANDSGRANSKNING
maingui.tabbedpane.imagelist.label.numberOfImagesInList = Antal bilder i bildlistan:

maingui.tabbedpane.imagelist.button.removeSelectedImages = Ta bort markerade bilder fr�n listan
maingui.tabbedpane.imagelist.button.removeAllImages      = Ta bort alla bilder fr�n listan
maingui.tabbedpane.imagelist.button.openImageList        = �pna en sparad bildlista
maingui.tabbedpane.imagelist.button.saveImageList        = Spara bildlistan
maingui.tabbedpane.imagelist.button.exportImageList      = Exportera bildlistan
maingui.tabbedpane.imagelist.button.moveUp               = Flytta vald bild upp�t i listan
maingui.tabbedpane.imagelist.button.moveDown             = Flytta vald bild ned�t i listan
maingui.tabbedpane.imagelist.button.viewImages           = Visa valda bilder i bildvisaren
maingui.tabbedpane.imagelist.button.moveToTop            = Flytta vald bild till toppen av listan
maingui.tabbedpane.imagelist.button.moveToBottom         = Flytta vald bild till botten av listan

maingui.tabbedpane.imagelist.filechooser.openImageList.title                    = �ppna 
maingui.tabbedpane.imagelist.filechooser.openImageList.nonSavedImageListMessage = Bildlistan �r inte sparad, skriv �ver?
maingui.tabbedpane.imagelist.filechooser.openImageList.missingFilesErrorMessage = F�ljande filer listade i den valda filen finns inte och laddades d�rmed inte till listan:

maingui.tabbedpane.imagelist.filechooser.saveImageList.title = Spara

maingui.tabbedpane.imagelist.filechooser.exportImageList.title = Export

maingui.tabbedpane.imagelist.imagelistformat.imageList.listAlreadyExists = existerar redan. Skriva �ver?

maingui.tabbedpane.imagelist.imagelistformat.javaPEG.successfullySaved    = Bildlistan sparades korrekt.
maingui.tabbedpane.imagelist.imagelistformat.javaPEG.notSuccessfullySaved = Bildlistan kunde inte sparas, se loggfil f�r detaljer.

maingui.tabbedpane.imagelist.imagelistformat.polyView.successfullyCreated    = Bildlistan i PolyView-format skapades korrekt.
maingui.tabbedpane.imagelist.imagelistformat.polyView.notSuccessfullyCreated = Bildlistan i PolyView-format kunde inte skapas. Se loggfil f�r detaljer.

maingui.tabbedpane.imagelist.imagelistformat.irfanView.successfullyCreated    = Bildlistan i IrfanView-format skapades korrekt.
maingui.tabbedpane.imagelist.imagelistformat.irfanView.notSuccessfullyCreated = Bildlistan i IrfanView-format kunde inte skapas. Se loggfil f�r detaljer.

maingui.tabbedpane.imagelist.imagelistformat.xnView.successfullyCreated    = Bildlistan i XnView-format skapades korrekt.
maingui.tabbedpane.imagelist.imagelistformat.xnView.notSuccessfullyCreated = Bildlistan i XnView-format kunde inte skapas. Se loggfil f�r detaljer.

##############################################################################################################
# M A I N  G U I  P O P U P  M E N U                                                                         #
##############################################################################################################
maingui.popupmenu.addImageToList        = L�gg till bild till bildlistan
maingui.popupmenu.addAllImagesToList    = L�gg till alla bilder till bildlistan
maingui.popupmenu.copyToSystemClipboard = Kopiera till urklipp

##############################################################################################################
# I N F O R M A T I O N  W I N D O W  ( U S E R  H E L P  A N D  V E R S I O N  I N F O R M A T I O N )      #
##############################################################################################################
information.window.userHelpWindowTitle           = Programhj�lp
information.window.versionInformationWindowTitle = Versionsinformation

##############################################################################################################
# P I C T U R E  P A N E L                                                                                   #
##############################################################################################################
picture.panel.pictureLabel = BILDER I VALD MAPP

##############################################################################################################
# H E L P  V I E W E R                                                                                       #
##############################################################################################################
helpViewerGUI.window.locationError = Kunde inte s�tta f�nster position enligt konfigurationen. Se loggfil f�r detaljer.
helpViewerGUI.window.title         = Hj�lp
helpViewerGUI.errorMessage         = Kunde inte ladda hj�lpfil, se loggfil f�r detaljer.

helpViewerGUI.tree.content                    = Inneh�ll
helpViewerGUI.tree.programHelpOverView        = PROGRAMBESKRIVNING
helpViewerGUI.tree.programHelpRename          = BYT NAMN P� BILDER
helpViewerGUI.tree.programHelpViewImages      = VISA BILDER
helpViewerGUI.tree.programHelpImageViewer     = BILDVISARE
helpViewerGUI.tree.programHelpOverviewCreator = SKAPA TUMNAGEL�VERSIKT
helpViewerGUI.tree.versionInformation         = VERSIONSINFORMATION
helpViewerGUI.tree.references                 = REFERENSER / TACK TILL
helpViewerGUI.tree.configuration              = Inst�llningar
helpViewerGUI.tree.logging                    = LOGGNING
helpViewerGUI.tree.updates                    = UPPDATERINGAR
helpViewerGUI.tree.rename                     = NAMNBYTE
helpViewerGUI.tree.language                   = SPR�K

##############################################################################################################
# I N F O R M A T I O N  P A N E L                                                                           #
##############################################################################################################
information.panel.informationLabel   = INFORMATION
information.panel.columnNameFileName = Filnamn
information.panel.fileNameCurrent    = Nuvarande filnamn
information.panel.fileNamePreview    = F�rhandsgranskning av filnamn
information.panel.metaDataLabel      = METADATA
information.panel.previewLabel       = F�RHANDSGRANSKNING
information.panel.progressLabel      = F�RLOPP
information.panel.subFolderNameLabel = Undermappsnamn

##############################################################################################################
# R E N A M E  P R O C E S S  I N F O R M A T I O N  M E S S A G E                                           #
##############################################################################################################
rename.PreFileProcessor.starting                           = Proceser F�re Filomd�pningsprocessen Startad
rename.PreFileProcessor.error                              = Proceser F�re Filomd�pningsprocessen Fann F�ljande Fel:
rename.PreFileProcessor.finished                           = Proceser F�re Filomd�pningsprocessen Avslutad
rename.PreFileProcessor.sourceAndDestinationPath           = Validerar K�ll Och Destinations S�kv�gar
rename.PreFileProcessor.fileAndSubDirectoryTemplate        = Validerar Filnams Och Underkatalognamns-mallar
rename.PreFileProcessor.destinationDirectoryDoesNotExist   = Validerar Att Destinationskatalogen Inte Existerar
rename.PreFileProcessor.uniqueFilesInSourceDirectory       = Validerar Unika Bild Filer i K�llkatalogen
rename.PreFileProcessor.jPEGTotalPathLength                = Validerar Total S�kv�gsl�ngd F�r JPEG Filer
rename.PreFileProcessor.nonJPEGTotalPathLength             = Validerar Total S�kv�gsl�ngd F�r Icke JPEG Filer
rename.PreFileProcessor.availableDiskSpace                 = Validerar Tillg�ngligt Disk Utrymme
rename.PreFileProcessor.fileCreationAtDestinationDirectory = Validerar Filskapande I Destinationskatalogen
rename.PreFileProcessor.externalOverviewLayout             = Validerar Layout F�r Tumnagel�versikt

rename.FileProcessor.starting                               = Filomd�pningsprocessen �r Startad
rename.FileProcessor.finished                               = Filomd�pningsprocessen �r Avslutad
rename.FileProcessor.createSubDirectory                     = Skapande Av Underkatalog
rename.FileProcessor.createThumbNailsDirectory              = Skapande av Tumnagelkatalog
rename.FileProcessor.createAndTransferContentOfJPEGFiles    = Kopiering Och Namnbyte Av JPEG Filer
rename.FileProcessor.createThumbNails                       = Skapande Av Tumnaglar
rename.FileProcessor.createAndTransferContentOfNonJPEGFiles = Kopiering Av Icke JPEG Filer
rename.FileProcessor.renameFromLabel                        = Fil:
rename.FileProcessor.renameToLabel                          = Omd�pt till:

rename.PostFileProcessor.integrityCheck.starting = Integritetskontroll Av Filer Startad
rename.PostFileProcessor.integrityCheck.finished = Integritetskontroll Av Filer Avslutad
rename.PostFileProcessor.integrityCheck.error    = Alla Filer Har Inte Kopierats Korrekt, Se Loggfil F�r Detaljer
rename.PostFileProcessor.integrityCheck.checking = Integritet P� Kopierade Filer Kontrolleras
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
validator.availablediskspace.notEnoughDiskSpace = Det finns inte tillr�ckligt med lagringsutrymme i vald destination.

# Destination directory does not exist validator
validator.destinationdirectorydoesnotexist.existingSubDirectory = Destinations katalogen existerar redan.

# File and sub directory template validator
validator.fileandsubdirectorytemplate.noSubFolderNameError                  = Ett namn p� underkatalogen m�ste anges
validator.fileandsubdirectorytemplate.invalidCharactersInSubFolderNameError = Namnet p� undermappen inneh�ller otill�tna tecken
validator.fileandsubdirectorytemplate.invalidVariablesInSubFolderNameError  = Namnet p� undermappen kan bara inneh�lla f�ljande variabler:
validator.fileandsubdirectorytemplate.dotFirstInSubFolderNameTemplate       = En punkt som f�rsta tecken i undermappsnamnet �r inte till�tet
validator.fileandsubdirectorytemplate.dotLastInSubFolderNameTemplate        = En punkt som sista tecken i undermappsnamnet �r inte till�tet
validator.fileandsubdirectorytemplate.noFileNameError                       = En filnamnsmall m�ste definieras
validator.fileandsubdirectorytemplate.invalidCharactersInFileNameError      = Filnamnsmallen inneh�ller otill�tna tecken
validator.fileandsubdirectorytemplate.dotFirstInFileNameTemplate            = En punkt som f�rsta tecken i filnamnsmallen �r inte till�tet
validator.fileandsubdirectorytemplate.dotLastInFileNameTemplate             = En punkt som sista tecken i filnamnsmallen �r inte till�tet

# File creation at destination directory
validator.filecreationatdestinationdirectory.couldNotCreateSubDirectory       = Kunde inte skapa underkatalog
validator.filecreationatdestinationdirectory.couldNotCreateAllJPEGFiles       = Kunde inte skapa alla JPEG filer
validator.filecreationatdestinationdirectory.couldNotCreateThumbNailDirectory = Kunde inte skapa tumnagels katalogen
validator.filecreationatdestinationdirectory.couldNotCreateAllThumbNails      = Kunde inte skapa alla tumnaglar
validator.filecreationatdestinationdirectory.couldNotCreateAllNonJPEGFiles    = Kunde inte skapa alla icke JPEG filer
validator.filecreationatdestinationdirectory.couldNotDeleateSubDirectory      = Kunde inte ta bort underkatalogen

# JPEG total path length
validator.jpegtotalpathlength.toLongFileName               = Med nuvarande destinationskatalog och mallar s� kommer den totala s�kv�gen\nf�r de omd�pta filerna bli l�ngre �n vad som �r till�tet.\n\nF�r att undvika detta, �ndra antingen destinationskatalog eller mallarna. 
validator.jpegtotalpathlength.noJPEGFIlesInSourceDirectory = Inga JPEG filer i den valda k�llkatalogen

# Non JPEG total path length
validator.nonjpegtotalpathlength.toLongFileName      = Den totala s�kv�gen med vald destinationskatalog kommer att bli f�r l�ng f�r filen:
validator.nonjpegtotalpathlength.toLongDirectoryPath = Den totala s�kv�gen med vald destinationskatalog kommer att bli f�r l�ng f�r katalogen:

# Source and destination path validator
validator.sourceanddestinationpath.noSourcePathError                       = En s�kv�g till bildmappen m�ste anges
validator.sourceanddestinationpath.invalidCharactersInSourcePathError      = S�kv�gen till bildmappen inneh�ller otill�tna tecken
validator.sourceanddestinationpath.noDestinationPathError                  = En s�kv�g till destinationsmappen m�ste anges
validator.sourceanddestinationpath.invalidCharactersInDestinationPathError = S�kv�gen till destinationsmappen inneh�ller otill�tna tecken

# Thumb nail overview layout validator
validator.externalOverviewLayout.invalidXMLFile = Inneh�llet i filen layout.xml �r inte giltigt. Se loggfil f�r detaljer.

##############################################################################################################
# T H U M B N A I L  O V E R V I E W                                                                         #
##############################################################################################################

thumbnailoverview.ThumbNailOverViewCreator.starting             = Skapande Av Tumnagel�versikt Startad
thumbnailoverview.ThumbNailOverViewCreator.finished             = Skapande Av Tumnagel�versikt Avslutad
thumbnailoverview.ThumbNailOverViewCreator.error.createCSSFile  = Kunde inte skapa CSS fil i destinationskatalogen. Se loggfil f�r detaljer.
thumbnailoverview.ThumbNailOverViewCreator.error.accessCSSFile  = Atkomst nekad till CSS fil. Se loggfil f�r detaljer.
thumbnailoverview.ThumbNailOverViewCreator.error.createHTMLFile = Kunde inte skapa HTML file i destinationskatalogen.
thumbnailoverview.ThumbNailOverViewCreator.error.accessHTMLFile = �tkomst nekad till HTML fil. Se loggfil f�r detaljer.

thumbnailoverview.LayoutParser.wrongElementAmount = Filen layout.xml inneh�ller fel antal av element:
thumbnailoverview.LayoutParser.parseError         = Fel vid inl�sning av layout.xml. Se loggfil f�r detaljer.

##############################################################################################################
# U P D A T E  C H E C K E R                                                                                 #
##############################################################################################################

updatechecker.errormessage.uRLInvalid                  = Ogiltigt format p� url:en till uppdateringsservern. Se loggfil f�r detaljer.
updatechecker.errormessage.uRLWrong                    = Fel url till uppdateringsservern. Se loggfil f�r detaljer.
updatechecker.errormessage.networkTimeOut              = Uppdateringsserven svarade inte, nytt f�rs�k sker n�sta programstart.
updatechecker.errormessage.downloadError               = Kunde inte ladda ner den senaste versionen. Se loggfil f�r detaljer.
updatechecker.errormessage.parseException              = Kunde inte l�sa h�mtad fil, se loggfil f�r detaljer.
updatechecker.errormessage.parseConfigurationException = Kunde inte konfigurera en XML parser. Se loggfil f�r detaljer.

updatechecker.informationmessage.downloadFinished = Nedladdningen klar.

updatechecker.gui.title          = Ny version tillg�nglig
updatechecker.gui.newVersion     = Det finns en nyare version av JavaPEG tillg�nglig. Se nedan f�r loggen �ver �ndringar.
updatechecker.gui.downloadButton = Ladda ner
updatechecker.gui.closeButton    = St�ng

##############################################################################################################
# M E T A  D A T A  P A N E L                                                                                #
##############################################################################################################
metadatapanel.titleDefaultText     = METADATA F�R BILD:
metadatapanel.tableheader.type     = TYP
metadatapanel.tableheader.property = EGENSKAP
metadatapanel.tableheader.value    = V�RDE

##############################################################################################################
# S T A T U S  B A R  M E S S A G E S                                                                        #
##############################################################################################################

statusbar.message.amountOfRows              = Antal rader
statusbar.message.amountOfColumns           = Antal kolumner
statusbar.message.amountOfImagesInDirectory = Antal bilder i katalogen
statusbar.message.selectedPath              = Vald s�kv�g:

##############################################################################################################
# T A B B E D  P A N E (M A I N  F U N C T I O N S)                                                          #
##############################################################################################################
tabbedpane.imageRename = BYT NAMN P� BILDER
tabbedpane.imageView   = S�K / VISA BILDER
tabbedpane.imageTag    = TAGGA BILDER

##############################################################################################################
# F I L E  R E T R I E V E R                                                                                 #
##############################################################################################################
fileretriever.canNotFindFile     = Kan inte hitta filen. Se loggfil f�r detaljer.
fileretriever.canNotReadFromFile = Kan inte l�sa fr�n filen. Det kan vara ett annat program som l�st filen. Se loggfil f�r detaljer.

metadata.field.name.APERTURE_VALUE = BL�NDARV�RDE
metadata.field.name.CAMERA_MODEL = KAMERAMODELL
metadata.field.name.IMAGE_SIZE = BILDSTORLEK 
metadata.field.name.ISO = ISO
metadata.field.name.SHUTTER_SPEED = SLUTARTID
metadata.field.name.YEAR = �R
metadata.field.name.MONTH = M�NAD
metadata.field.name.DAY = DAG
metadata.field.name.HOUR = TIMME
metadata.field.name.MINUTE = MINUT
metadata.field.name.SECOND = SEKUND

##############################################################################################################
# F I N D  I M A G E  S E C T I O N                                                                          #
##############################################################################################################
findimage.categories.label                                = KATEGORIER
findimage.categories.andRadioButton.label                 = OCH
findimage.categories.andRadioButton.tooltip               = <html>En bild m�ste ha alla valda kategorier<br/>tilldelade f�r att adderas till s�kresultateta.</html>
findimage.categories.orRadioButton.label                  = ELLER
findimage.categories.orRadioButton.tooltip                = <html>En bild kan ha valfri kombination av valda<br/>katergorier tilldelad f�r att adderas till s�kresultatet.</html>
findimage.categories.clearCategoriesSelectionButton.label = Rensa valda kategorier
findimage.categories.removeAllCategoriesAndSubCategories1 = Ta bort kategori:
findimage.categories.removeAllCategoriesAndSubCategories2 = och alla underkategorier?
findimage.categories.removeAllCategoriesAndSubCategories3 = ?
findimage.categories.addNewTopLevelCategory               = L�gg till ny toppniv�kategori
findimage.categories.collapseTopLevelCategories           = F�ll ihop toppniv�kategorier
findimage.categories.expandTopLevelCategories             = Expandera toppniv�kategorier
findimage.categories.collapseCategory                     = F�ll ihop kategori:
findimage.categories.expandCategory                       = Expandera kategori:
findimage.categories.addNewSubCategoryToCategory          = L�gg till ny underkategori till kategori:
findimage.categories.renameSelectedCategory               = Byt namn p� vald kategori:
findimage.categories.removeSelectedCategory               = Ta bort vald kategori:
findimage.imagemetadata.label = BILDMETADATA
findimage.rating.label        = KLASSIFICERING
findimage.rating.tooltip.bad  = D�lig
findimage.rating.tooltip.good = Bra
findimage.comment.label              = KOMMENTAR
findimage.comment.defaultCommentText = Skriv kommentar h�r
findimage.preview.label = F�RHANDSGRANSKNING
findimage.clearAllMetaDataParameters.tooltip = Rensa alla valda metadataparametrar
findimage.searchImages.tooltip = S�k bilder
findimage.searchImages.result = No images found