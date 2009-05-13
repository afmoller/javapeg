# This is the Swedish language file for JavaPEG.

# The structure of the file is as follows:

# A line starting with "#" is interpreted as a comment.

# A valid non comment line may look like "file = Arkiv" where "file" is the variable, = the
# separator and "Arkiv" is the value of the variable, in this case the Swedish word for file.

# There might be whitespaces or tabs in between the variable and the "=". Any whitespace
# or other character after the value of the variable will be trimmed away.

# For more information regarding the rules for the syntax of this file please visit:
# http://java.sun.com/javase/6/docs/api/java/util/Properties.html

# This file was created: by Fredrik M�ller 2005-xx-xx
# This file was updated: by Fredrik M�ller 2005-11-08
#                      : by Fredrik M�ller 2006-11-09
#                      : by fredrik M�ller 2006-11-16
#                      : by Fredrik M�ller 2006-11-27
#                      : by Fredrik M�ller 2007-02-02
#                      : by Fredrik M�ller 2007-03-10
#                      : by Fredrik M�ller 2009-02-19
#                      : by Fredrik M�ller 2009-02-24
#                      : by Fredrik M�ller 2009-02-27
#                      : by Fredrik M�ller 2009-03-04
#                      : by Fredrik M�ller 2009-03-05
#                      : by Fredrik M�ller 2009-03-06
#                      : by Fredrik M�ller 2009-03-11
#                      : by Fredrik M�ller 2009-03-14
#                      : by Fredrik M�ller 2009-03-23
#                      : by Fredrik M�ller 2009-03-26
#                      : by Fredrik M�ller 2009-03-27
#                      : by Fredrik M�ller 2009-04-14
#                      : by Fredrik M�ller 2009-04-15
#                      : by Fredrik M�ller 2009-04-19
#                      : by Fredrik M�ller 2009-04-20
#                      : by Fredrik M�ller 2009-04-27
#                      : by Fredrik M�ller 2009-04-28
#                      : by Fredrik M�ller 2009-05-04
#                      : by Fredrik M�ller 2009-05-05
#                      : by Fredrik M�ller 2009-05-10
#                      : by Fredrik M�ller 2009-05-11
#                      : by Fredrik M�ller 2009-05-13

#############################################################################################################
# M E N U                                                                                                   #
#############################################################################################################
menu.file     = Arkiv
menu.help     = Hj�lp
menu.language = Spr�k

#############################################################################################################
# M E N U  I T E M S                                                                                        #
#############################################################################################################
menu.item.exit                       = Avsluta
menu.item.openSourceFileChooser      = �ppna K�llfilsv�ljaren
menu.item.openDestinationFileChooser = �ppna Destinationsfilsv�ljaren
menu.item.startProcess               = Starta Namnbytesprocessen
menu.item.programHelp                = Programhj�lp
menu.item.versionInformation         = Versionsinformation
menu.item.about                      = Om JavaPEG 2.0
menu.item.languageChoice             = Spr�kval

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
labels.sourcePath       = S�KV�G TILL BILDKATALOG
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

##############################################################################################################
# V A R I A B L E S  C O M M E N T                                                                           #
##############################################################################################################
variable.comment.infoLabelA = * = Dessa variabler kan anv�ndas
variable.comment.infoLabelB = vid namngivning av undermapp

##############################################################################################################
# C H E C K  B O X E S                                                                                       #
##############################################################################################################
checkbox.createThumbNails      = SKAPA TUMNAGEL�VERSIKT

##############################################################################################################
# T O O L T I P S                                                                                            #
##############################################################################################################
tooltip.sourcePathButton             = Leta fram r�tt s�kv�g till bildmapp
tooltip.destinationPathButton        = V�lj s�kv�g f�r destinationsmapp
tooltip.createSubFolder              = Skapa en undermapp med valfrit namn, i vilken bilderna l�ggs
tooltip.subFolderName                = Ange namnet p� mappen inklusive eventuella variabler h�r
tooltip.fileNameTemplate             = Skriv in filnamnsmallen inklusive variabler h�r
tooltip.createThumbNails             = Skapa tumnaglar av omd�pta filer
tooltip.openDestinationFolder        = �ppna mappen d�r de omd�pta filerna hamnar efter avslutad process
tooltip.beginNameChangeProcessButton = Starta namnbytarprocessen

##############################################################################################################
# F I L E  S E L E C T I O N  D I A L O G S                                                                  #
##############################################################################################################
fileSelectionDialog.sourcePathFileChooser      = V�lj bildmapp
fileSelectionDialog.destinationPathFileChooser = V�lj destinationsmapp

##############################################################################################################
# A B O U T  D I A L O G                                                                                     #
##############################################################################################################
aboutDialog.Label    = Om JavaPEG 2.0
aboutDialog.TextRowA = JavaPEG, ver: 2.0
aboutDialog.TextRowB = Copyright � 2005 - 2009
aboutDialog.TextRowC = Detta program �r programmerat i Java (JDK 1.6).
aboutDialog.TextRowD = Programmerat mellan 2005-05-24 - 2009-05-05.
aboutDialog.TextRowE = Version 2.0 sl�ppt 2009-05-05.
aboutDialog.TextRowF = Utvecklare:
aboutDialog.TextRowG = Fredrik M�ller
aboutDialog.TextRowH = _______________________________________

##############################################################################################################
# E R R O R  M E S S A G E S  M A I N G U I                                                                  #
##############################################################################################################
errormessage.maingui.errorMessageLabel                       = Fel
errormessage.maingui.warningMessageLabel                     = Varning
errormessage.maingui.informationMessageLabel                 = Information
errormessage.maingui.locationError                           = Kunde inte s�tta f�nster position enligt konfigurationen. Se loggfil f�r detaljer.
errormessage.maingui.toLongFileNameInPreviewTable            = Med denna filnamnsmall kommer filnamnet att bli 200 tecken l�ngt."\n\n"Det kan inneb�ra framtida problem d� en s�kv�g inte kan vara l�ngre �n 255 tecken.\nFlytt till annan katalog �n nuvarande valda kan d� eventuellt bli om�jlig, d� den\ntotala s�kv�gen: s�kv�g till mapp plus filnamnet f�r en l�ngd som �vertiger 255 tecken.
errormessage.maingui.toLongTotalPathInPreviewTable           = Den totala s�kv�gen: s�kv�gen till vald destinationsmapp och vald filnamsmall\nkommer att ge en s�kv�g som �r l�ngre �n 255 tecken, vilket inte �r till�tet.\nAntingen m�ste filnamnet bli kortare (f�r�ndra filnamnsmallen) eller s� m�ste\ndestinationsmapp med kortare s�kv�g v�ljas.
errormessage.maingui.invalidVariablesInSubFolderNameError    = Namnet p� undermappen kan bara inneh�lla f�ljande variabler:
errormessage.maingui.sameSourceAndDestination                = K�ll och destinationskatalog �r samma.

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
errormessage.jpgrename.joptionPaneYes                            = Ja
errormessage.jpgrename.joptionPaneNo                             = Nej
errormessage.jpgrename.noImagesInPathLabel                       = Felmeddelande
errormessage.jpgrename.noImagesInPath                            = Den valda s�kv�gen inneh�ller inga bildfiler
errormessage.jpgrename.noExifInImage                             = Ingen exif information kunde hittas. F�r att g�ra om processen fr�n b�rjan, ta bort f�ljande fil fr�n de filer som skall d�pas om:
errormessage.jpgrename.noExifInImageLabel                        = Ingen Exif

##############################################################################################################
# E R R O R  M E S S A G E S  F I L E H A N D L E R                                                          #
##############################################################################################################
errormessage.filehandler.fileNotFoundExceptionA = Filen:
errormessage.filehandler.fileNotFoundExceptionB = kan inte hittas 
errormessage.filehandler.canNotReadFile         = Kan inte l�sa filen: 
errormessage.filehandler.canNotWriteFile        = Kan inte skriva:

##############################################################################################################
# L A N G U A G E  O P T I O N S  G U I                                                                      #
##############################################################################################################
language.option.gui.windowTitle          = Spr�kinst�llningar
language.option.gui.selectionModeJLabel  = SPR�KVALSMETOD
language.option.gui.manualRadioButton    = Manuellt
language.option.gui.automaticRadioButton = Automatiskt
language.option.gui.currentLanguageLabel = AKTUELLT SPR�K
language.option.gui.availableLanguages   = TILLG�NGLIGA SPR�K
language.option.gui.languageNameNotFound = namn p� spr�k ej funnet
language.option.gui.okButton             = Ok
language.option.gui.cancelButton         = Avbryt
language.option.gui.window.locationError = Kunde inte s�tta f�nsterposition enligt konfigurationen. Se loggfil f�r detaljer.

##############################################################################################################
# I N F O R M A T I O N  M E S S A G E S  L A N G U A G E  O P T I O N S  G U I                              #
##############################################################################################################
language.option.gui.information.windowlabel    = Information
language.option.gui.information.restartMessage = Valt spr�k kommer inte anv�ndas innan JavaPEG startats om

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
# F I L E  C H O O S E R                                                                                     #
##############################################################################################################
filechooser.window.title                  = Filv�ljare
filechooser.window.locationError          = Kunde inte s�tta f�nsterposition enligt konfigurationen. Se loggfil f�r detaljer.
filechooser.button.ok                     = Ok
filechooser.button.ok.mnemonic            = O
filechooser.button.cancel                 = Avbryt
filechooser.button.cancel.mnemonic        = A

##############################################################################################################
# H E L P  V I E W E R                                                                                       #
##############################################################################################################
helpViewerGUI.window.locationError    = Kunde inte s�tta f�nster position enligt konfigurationen. Se loggfil f�r detaljer.
helpViewerGUI.window.title            = Hj�lp
helpViewerGUI.tree.content            = Inneh�ll
helpViewerGUI.tree.programHelp        = Programhj�lp
helpViewerGUI.tree.versionInformation = Versionsinformation
helpViewerGUI.tree.overviewCreator    = Tumnagel�versikt
helpViewerGUI.tree.references         = Referenser

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

rename.PostFileProcessor.integrityCheck.starting = Integritetskontroll Av Filer Startad
rename.PostFileProcessor.integrityCheck.finished = Integritetskontroll Av Filer Avslutad
rename.PostFileProcessor.integrityCheck.error    = Alla Filer Har Inte Kopierats Korrekt, Se Loggfil F�r Detaljer
rename.PostFileProcessor.integrityCheck.checking = Integritet P� Kopierade Filer Kontrolleras

##############################################################################################################
# P R E  F I L E  P R O C E S S O R  V A L I D A T O R S                                                     #
##############################################################################################################
#
# Available disk space validator
validator.availablediskspace.notEnoughDiskSpace = Det finns inte tillr�ckligt med lagringsutrymme i vald destination.

# Destination directory does not exist validator
validator.destinationdirectorydoesnotexist.existingSubDirectory = Destinations katalogen existerar redan.

# File and sub directory template validator
validator.fileandsubdirectorytemplate.noSubFolderNameError                    = Ett namn p� underkatalogen m�ste anges
validator.fileandsubdirectorytemplate.invalidCharactersInSubFolderNameError   = Namnet p� undermappen inneh�ller otill�tna tecken
validator.fileandsubdirectorytemplate.invalidVariablesInSubFolderNameError    = Namnet p� undermappen kan bara inneh�lla f�ljande variabler:
validator.fileandsubdirectorytemplate.dotFirstInSubFolderNameTemplate         = En punkt som f�rsta tecken i undermappsnamnet �r inte till�tet
validator.fileandsubdirectorytemplate.dotLastInSubFolderNameTemplate          = En punkt som sista tecken i undermappsnamnet �r inte till�tet
validator.fileandsubdirectorytemplate.noFileNameError                         = En filnamnsmall m�ste definieras
validator.fileandsubdirectorytemplate.invalidCharactersInFileNameError        = Filnamnsmallen inneh�ller otill�tna tecken
validator.fileandsubdirectorytemplate.dotFirstInFileNameTemplate              = En punkt som f�rsta tecken i filnamnsmallen �r inte till�tet
validator.fileandsubdirectorytemplate.dotLastInFileNameTemplate               = En punkt som sista tecken i filnamnsmallen �r inte till�tet

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
validator.nonjpegtotalpathlength.toLongFileName      = The total path with the current destinaton directory will be to long for the file:
validator.nonjpegtotalpathlength.toLongDirectoryPath = The total path with the current destinaton directory will be to long for the directory:

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

updatechecker.newVersion     = Det finns en nyare version av JavaPEG tillg�nglig. Bes�k http://javapeg.sourceforge.net f�r att finna nedladdningsl�nk
updatechecker.uRLInvalid     = Ogiltigt format p� url:en till uppdateringsservern. Se loggfil f�r detaljer.
updatechecker.uRLWrong       = Fel url till uppdateringsservern. Se loggfil f�r detaljer.
updatechecker.networkTimeOut = Uppdateringsserven svarade inte, nytt f�rs�k sker n�sta programstart.