# Este es el fichero de castellano de JavaPEG.

# La estructura del fichero es como se indica a continuaci�n:

# Una linea que comience con "#" se interpreta como un comentario.

# Una l�nea v�lida que no sea un comentario puede ser como "file = Archivo" donde "file" es la variable, = el
# separador y "Archivo" es el valor de la variable, en este caso la palabra escrita en castellano.

# Puede haber espacios en blanco o tabulaciones entre la variable y el s�mbolo "=". Cualquier espacio en blanco
# u otro car�cter despu�s del valor de la variable tambi�n ser� omitido.

# Para m�s informaci�n acerca de las reglas de la sint�xis de este fichero , por favor, visita:
# http://java.sun.com/javase/6/docs/api/java/util/Properties.html

# Este fichero fue creado     : por Fredrik M�ller 2005-xx-xx
# Este fichero fue actualizado: por Angel Bueno    2009-07-25
#                             : por Fredrik M�ller 2009-07-26
#                             : por Fredrik M�ller 2009-07-28
#                             : por Fredrik M�ller 2009-07-29
#                             : por Fredrik M�ller 2009-07-31
#                             : por Angel Bueno    2009-07-31

#############################################################################################################
# M E N U                                                                                                   #
#############################################################################################################
menu.file     = Fichero
menu.help     = Ayuda
menu.language = Idioma

#############################################################################################################
# E L E M E N T O S  D E L  M E N U																			#
#############################################################################################################
menu.item.exit                       = Salir
menu.item.openDestinationFileChooser = Abrir selector de destino
menu.item.startProcess               = Iniciar proceso de renombrado
menu.item.programHelp                = Ayuda del programa
menu.item.versionInformation         = Informaci�n de versi�n
menu.item.about                      = Acerca de JavaPEG 2.2
menu.item.languageChoice             = Selecci�n de idioma

#############################################################################################################
# T E C L A S  R A P I D A S  D E L  M E N U																#
#############################################################################################################
menu.mnemonic.file     = F
menu.mnemonic.help     = H
menu.mnemonic.language = L

#############################################################################################################
# A C E L E R A D O R  D E L  M E N U                                                                       #
#############################################################################################################
menu.iten.openDestinationFileChooser.accelerator = D
menu.iten.startProcess.accelerator               = P
menu.iten.exit.accelerator                       = X
menu.item.languageChoice.accelerator             = L
menu.item.versionInformation.accelerator         = V
menu.item.about.accelerator                      = A

##############################################################################################################
# E T I Q U E T A S                                                                                          #
##############################################################################################################
labels.sourcePath       = DIRECTORIO ORIGEN
labels.destinatonPath   = RUTA AL DIRECTORIO DE DESTINO
labels.subFolderName    = PLANTILLA PARA EL NOMBRE DEL SUBDIRECTORIO
labels.fileNameTemplate = PLANTILLA PARA EL NOMBRE DEL FICHERO
labels.variables        = VARIABLES

##############################################################################################################
# V A R I A B L E S                                                                                          #
##############################################################################################################
variable.pictureDateVariable   = d
variable.pictureDate           = Fecha de captura de la imagen
variable.pictureTimeVariable   = t
variable.pictureTime           = Hora de captura de la imagen
variable.cameraModelVariable   = m
variable.cameraModel           = Modelo de c�mara
variable.shutterSpeedVariable  = s
variable.shutterSpeed          = Velocidad del obturador
variable.isoValueVariable      = i
variable.isoValue              = Valor ISO
variable.pictureWidthVariable  = w
variable.pictureWidth          = Ancho de la imagen en pixeles
variable.pictureHeightVariable = h
variable.pictureHeight         = Alto de la imagen en pixeles
variable.apertureValueVariable = av
variable.apertureValue         = Valor de apertura
variable.dateOftodayVariable   = td
variable.dateOftoday           = Fecha actual
variable.sourceNameVariable    = sn
variable.sourceName            = Nombre del fichero original

##############################################################################################################
# C O M E N T A R I O  D E  V A R I A B L E S                                                                #
##############################################################################################################
variable.comment.infoLabelA = * = Estas variables se pueden usar
variable.comment.infoLabelB = para nombrar los subdirectorios

##############################################################################################################
# C A S I L L A S  P A R A  M A R C A R                                                                      #
##############################################################################################################
checkbox.createThumbNails = CREAR VISTA EN MINIATURA

##############################################################################################################
# A V I S O S                                                                                                #
##############################################################################################################
tooltip.destinationPathButton                                  = Buscar un directorio de destino
tooltip.subFolderName                                          = Especificar un nombre de directorio con posibles variables aqu�
tooltip.fileNameTemplate                                       = Especificar un nombre de fichero con posibles variables aqu�
tooltip.createThumbNails                                       = Crear una vista en miniaturas de los ficheros renombrados
tooltip.openDestinationFolder                                  = Abrir carpeta de destino despu�s de finalizar el proceso
tooltip.beginNameChangeProcessButton                           = Iniciar proceso de renombrado
tooltip.selectSourceDirectoryWithImages                        = Selecciona un directorio con im�genes
tooltip.selectDestinationDirectory                             = Selecciona un directorio de destino
tooltip.selectSourceDirectoryWithImagesAndDestinationDirectory = Selecciona un directorio con im�genes y un directorio de destino
tooltip.enableTemplateFields                                   = Para habilitar, selecciona el directorio de destino

##############################################################################################################
# D I A L O G O S  D E  S E L E C C I O N  D E  F I C H E R O S                                              #
##############################################################################################################
fileSelectionDialog.destinationPathFileChooser = Selecciona el directorio de destino

##############################################################################################################
# D I A L O G O  A C E R C A  D E                                                                            #
##############################################################################################################
aboutDialog.Label    = Acerca de JavaPEG 2.2
aboutDialog.TextRowA = JavaPEG, ver: 2.2
aboutDialog.TextRowB = Copyright � 2005 - 2009
aboutDialog.TextRowC = Este programa est� hecho en Java (JDK 1.6).
aboutDialog.TextRowD = Programado entre 2005-05-24 - 2009-05-21.
aboutDialog.TextRowE = Version 2.2 generada el 2009-05-21.
aboutDialog.TextRowF = Desarrolador:
aboutDialog.TextRowG = Fredrik M�ller
aboutDialog.TextRowH = _______________________________________

##############################################################################################################
# M E N S A J E S  D E  E R R O R  D E  L A  V E N T A N A  P R I N C I P A L                                #
##############################################################################################################
errormessage.maingui.errorMessageLabel             = Error
errormessage.maingui.warningMessageLabel           = Advertencia
errormessage.maingui.informationMessageLabel       = Informaci�n
errormessage.maingui.locationError                 = No se pudo ajustar la ventana acorde a los ajustes. Revise el log para m�s detalles
errormessage.maingui.toLongFileNameInPreviewTable  = Con esta plantilla para el nombre, el nombre del fichero superar� los 200 caracteres.\n\nEsto puede ser un problema, porque una ruta no puede ser superior a 255 caracteres.\nPuede que no se pueda mover el fichero a otro directorio, si la ruta\ntotal: ruta al fichero + nombre del fichero es superior a 255 caracteres.
errormessage.maingui.toLongTotalPathInPreviewTable = La ruta total: ruta al directorio de destino y plantilla para el nombre del fichero seleccionada\ngenerar�n una ruta superior a 255 caracteres, y esto no est� permitido.\nO el nombre del fichero se hace m�s corto (cambiar la plantilla del nombre del fichero) o se selecciona un directorio de destino m�s corto.
errormessage.maingui.sameSourceAndDestination      = El directorio de origen y destino es el mismo

##############################################################################################################
# B A R R A  D E  P R O G R E S O                                                                            #
##############################################################################################################
progress.ThumbNailLoading.title = Cargando miniaturas

progress.RenameProcess.title.processStarting                                  = Renombrando im�genes...
progress.RenameProcess.title.processFinished                                  = Proceso de renombrado finalizado
progress.RenameProcess.dismissWindowButton.mnemonic                           = C
progress.RenameProcess.dismissWindowButton.processStarting                    = espera..
progress.RenameProcess.dismissWindowButton.processFinished                    = Cerrar
progress.RenameProcess.dismissWindowButton.processStarting.toolTip            = Renombrado en progreso...
progress.RenameProcess.dismissWindowButton.processFinished.toolTip            = Cierra esta ventana
progress.RenameProcess.openDestinationDirectoryButton.mnemonic                = O
progress.RenameProcess.openDestinationDirectoryButton.processStarting         = espera..
progress.RenameProcess.openDestinationDirectoryButton.processFinished         = Abrir
progress.RenameProcess.openDestinationDirectoryButton.processStarting.toolTip = Renombrado en progreso...
progress.RenameProcess.openDestinationDirectoryButton.processFinished.toolTip = Abrir el directorio de destino en un navegador

##############################################################################################################
# M E N S A J E S  D E  E R R O R  D E  J P G R E N A M E                                                    #
##############################################################################################################
errormessage.jpgrename.joptionPaneYes      = Si
errormessage.jpgrename.joptionPaneNo       = No
errormessage.jpgrename.noImagesInPathLabel = Mensaje de error
errormessage.jpgrename.noImagesInPath      = La ruta seleccionada no contiene im�genes
errormessage.jpgrename.noExifInImageLabel  = No Exif
errormessage.jpgrename.noExifInImage       = No se encontr� informaci�n exif. Para deshacer el proceso desde el principio, borra el siguiente fichero de los ficheros a renombrar:

##############################################################################################################
# M E N S A J E S  D E  E R R O R  D E L  M A N E J A D O R  D E  F I C H E R O S                            #
##############################################################################################################
errormessage.filehandler.fileNotFoundExceptionA = El fichero:
errormessage.filehandler.fileNotFoundExceptionB = no se encontr�
errormessage.filehandler.canNotReadFile         = No se pudo acceder al fichero:
errormessage.filehandler.canNotWriteFile        = No se pudo escribir:

##############################################################################################################
# G U I  C O N  L A S  O P C I O N E S  D E  I D I O M A                                                     #
##############################################################################################################
language.option.gui.windowTitle          = Ajustes de idioma
language.option.gui.selectionModeJLabel  = MODO DE SELECCION
language.option.gui.manualRadioButton    = Manualmente
language.option.gui.automaticRadioButton = Autom�tico
language.option.gui.currentLanguageLabel = IDIOMA ACTUAL
language.option.gui.availableLanguages   = IDIOMAS DISPONIBLES
language.option.gui.languageNameNotFound = nombre de idioma no encontrado
language.option.gui.okButton             = Ok
language.option.gui.cancelButton         = Cancelar
language.option.gui.window.locationError = No se pudo ajustar la ventana de acuerdo a los ajustes. Revise los logs para m�s detalles

##############################################################################################################
# M E N S A J E S  D E  I N F O R M A C I O N  D E L  G U I  C O N  L A S  O P C I O N E S  D E  I D I O M A #
##############################################################################################################
language.option.gui.information.windowlabel    = Informaci�n
language.option.gui.information.restartMessage = El idioma seleccionado no se usar� hasta que JavaPEG se reinicie

##############################################################################################################
# P A N E L  T A B U L A D O  D E L  G U I  P R I N C I P A L                                                #
##############################################################################################################
maingui.tabbedpane.imagelist.label.list                 = LISTA DE IMAGENES
maingui.tabbedpane.imagelist.label.preview              = PREVISUALIZACION
maingui.tabbedpane.imagelist.label.numberOfImagesInList = Numero de im�genes en la lista:

maingui.tabbedpane.imagelist.button.removeSelectedImages = Eliminar las im�genes seleccionadas de la lista
maingui.tabbedpane.imagelist.button.removeAllImages      = Eliminar todas las im�genes de la lista
maingui.tabbedpane.imagelist.button.openImageList        = Abrir y guardar la lista de im�genes
maingui.tabbedpane.imagelist.button.saveImageList        = Guardar la lista de im�genes
maingui.tabbedpane.imagelist.button.exportImageList      = Exportar la lista de im�genes
maingui.tabbedpane.imagelist.button.moveUp               = Mover la imagen seleccionada arriba en la lista
maingui.tabbedpane.imagelist.button.moveDown             = Mover la imagen seleccionada abajo en la lista

maingui.tabbedpane.imagelist.filechooser.openImageList.title                    = Abrir
maingui.tabbedpane.imagelist.filechooser.openImageList.nonSavedImageListMessage = La lista de im�genes existe, �sobreescribir?
maingui.tabbedpane.imagelist.filechooser.openImageList.missingFilesErrorMessage = Los siguientes ficheros no existen, y no se agregaron a la lista:
maingui.tabbedpane.imagelist.filechooser.openImageList.couldNotReadFile         = No se pudo leer el fichero, revise el log para m�s detalles.

maingui.tabbedpane.imagelist.filechooser.saveImageList.title = Guardar

maingui.tabbedpane.imagelist.filechooser.exportImageList.title = Exportar

maingui.tabbedpane.imagelist.imagelistformat.imageList.listAlreadyExists = ya existe. �Sobreescribir?

maingui.tabbedpane.imagelist.imagelistformat.javaPEG.successfullySaved    = La lista se guard� satisfactoriamente.
maingui.tabbedpane.imagelist.imagelistformat.javaPEG.notSuccessfullySaved = La lista no se pudo guardar, revise el log para m�s detalles.

maingui.tabbedpane.imagelist.imagelistformat.polyView.successfullyCreated    = La lista de im�genes en formato PolyView se cre� correctamente.
maingui.tabbedpane.imagelist.imagelistformat.polyView.notSuccessfullyCreated = La lista de im�genes en formato PolyView no se pudo crear, revise el log para m�s detalles.

maingui.tabbedpane.imagelist.imagelistformat.irfanView.successfullyCreated    = La lista de im�genes en formato IrfanView se cre� correctamente.
maingui.tabbedpane.imagelist.imagelistformat.irfanView.notSuccessfullyCreated = La lista de im�genes en formato IrfanView no se pudo crear, revise el log para m�s detalles.

##############################################################################################################
# M E N U  E M E R G E N T E  D E L  G U I  P R I N C I P A L                                                #
##############################################################################################################
maingui.popupmenu.addImageToList     = A�adir imagen a la lista
maingui.popupmenu.addAllImagesToList = A�adir todas las im�genes a la lista

##############################################################################################################
# V E N T A N A  D E  I N F O R M A C I O N  ( A Y U D A  D E  U S U A R I O  E                              # 
# I N F O R M A C I O N  D E  V E R S I O N )      															 #
##############################################################################################################
information.window.userHelpWindowTitle           = Programa de ayuda
information.window.versionInformationWindowTitle = Informaci�n de versi�n

##############################################################################################################
# P A N E L  D E  I M A G E N E S                                                                            #
##############################################################################################################
picture.panel.pictureLabel = IMAGENES EN EL DIRECTORIO SELECCIONADO

##############################################################################################################
# V I S O R  D E  A Y U D A                                                                                  #
##############################################################################################################
helpViewerGUI.window.locationError = No se pudo ajustar la ventana conforme a la configuraci�n. Revise los logs para m�s detalles
helpViewerGUI.window.title         = Ayuda
helpViewerGUI.errorMessage         = No se pudo cargar el fichero de ayuda, revise el fichero de logs para m�s detalles.

helpViewerGUI.tree.content                    = Contenido
helpViewerGUI.tree.programHelpOverView        = DESCRIPCION DEL PROGRAMA
helpViewerGUI.tree.programHelpRename          = RENOMBRAR IMAGENES
helpViewerGUI.tree.programHelpImageList       = CREAR LISTA DE IMAGENES
helpViewerGUI.tree.programHelpOverviewCreator = CREAR VISTA EN MINIATURAS
helpViewerGUI.tree.versionInformation         = INFORMACION DE VERSION
helpViewerGUI.tree.references                 = REFERENCIAS / CREDITOS

##############################################################################################################
# P A N E L  D E  I N F O R M A C I O N                                                                      #
##############################################################################################################
information.panel.informationLabel   = INFORMACION
information.panel.columnNameFileName = Nombre del fichero
information.panel.fileNameCurrent    = Nombre de fichero actual
information.panel.fileNamePreview    = Previsualizaci�n del nombre de fichero
information.panel.metaDataLabel      = METADATOS
information.panel.previewLabel       = PREVISUALIZACION
information.panel.progressLabel      = PROCESO
information.panel.subFolderNameLabel = Nombre de subcarpeta

##############################################################################################################
# M E N S A J E  D E  I N F O R M A C I O N  D E L  P R O C E S O                                            #
##############################################################################################################
rename.PreFileProcessor.starting                           = Pre procesado de fichero iniciado
rename.PreFileProcessor.error                              = El pre procesado del fichero encontr� los siguientes errores:
rename.PreFileProcessor.finished                           = Pre procesado de fichero finalizado
rename.PreFileProcessor.sourceAndDestinationPath           = Validando rutas de origen y destino
rename.PreFileProcessor.fileAndSubDirectoryTemplate        = Validando plantillas de nombre para ficheros y subdirectorios
rename.PreFileProcessor.destinationDirectoryDoesNotExist   = Validando que el directorio de destino no existe
rename.PreFileProcessor.uniqueFilesInSourceDirectory       = Validando ficheros �nicos en el directorio origen
rename.PreFileProcessor.jPEGTotalPathLength                = Validando longitud total de las rutas de los ficheros JPEG
rename.PreFileProcessor.nonJPEGTotalPathLength             = Validando longitud total de las rutas de los ficheros no JPEG
rename.PreFileProcessor.availableDiskSpace                 = Validando espacio en disco disponible
rename.PreFileProcessor.fileCreationAtDestinationDirectory = Validando creaci�n de fichero en directorio de destino
rename.PreFileProcessor.externalOverviewLayout             = Validando capa para la vista en miniaturas

rename.FileProcessor.starting                               = El procesado de fichero ha iniciado
rename.FileProcessor.finished                               = El procesado de fichero ha finalizado
rename.FileProcessor.createSubDirectory                     = Creaci�n del sub directorio
rename.FileProcessor.createThumbNailsDirectory              = Creaci�n del directorio de miniaturas
rename.FileProcessor.createAndTransferContentOfJPEGFiles    = Copia y renombrado de ficheros JPEG
rename.FileProcessor.createThumbNails                       = Creaci�n de miniaturas
rename.FileProcessor.createAndTransferContentOfNonJPEGFiles = Copia de ficheros no JPEG
rename.FileProcessor.renameFromLabel                        = Fichero:
rename.FileProcessor.renameToLabel                          = Renombrado a:

rename.PostFileProcessor.integrityCheck.starting = Chequeo de integridad de fichero iniciado
rename.PostFileProcessor.integrityCheck.finished = Chequeo de integridad de fichero finalizado
rename.PostFileProcessor.integrityCheck.error    = No todos los ficheros se copiaron correctamente, revise los logs para m�s detalles
rename.PostFileProcessor.integrityCheck.checking = Integridad de ficheros copiados marcada
rename.PostFileProcessor.renameFromLabel         = Fichero:
rename.PostFileProcessor.copiedWithError         = Copiado con ERROR a:
rename.PostFileProcessor.copiedOK                = Copiado OK a:
rename.PostFileProcessor.renamedWithError        = Renombrado con ERROR a:
rename.PostFileProcessor.renamedOK               = Renombrado OK a:

##############################################################################################################
# P R E  P R O C E S A D O R  V A L I D A D O R  D E L  F I C H E R O                                        #
##############################################################################################################
#
# Available disk space validator
validator.availablediskspace.notEnoughDiskSpace = No hay espacio en disco suficiente en el destino indicado.

# Destination directory does not exist validator
validator.destinationdirectorydoesnotexist.existingSubDirectory = El directorio de destino ya existe.

# File and sub directory template validator
validator.fileandsubdirectorytemplate.noSubFolderNameError                  = El Sub directorio debe tener un nombre
validator.fileandsubdirectorytemplate.invalidCharactersInSubFolderNameError = El nombre del sub directorio contiene caracteres ilegales
validator.fileandsubdirectorytemplate.invalidVariablesInSubFolderNameError  = El nombre del subdirectorio s�lo puede tener estas variables:
validator.fileandsubdirectorytemplate.dotFirstInSubFolderNameTemplate       = Un punto no es un caracter v�lido para el comienzo del nombre de un subdirectorio
validator.fileandsubdirectorytemplate.dotLastInSubFolderNameTemplate        = Un punto no es un caracter v�lido para el final del nombre de un subdirectorio
validator.fileandsubdirectorytemplate.noFileNameError                       = Es neceasario indocar una plantilla para el nombre del fichero
validator.fileandsubdirectorytemplate.invalidCharactersInFileNameError      = La plantilla para el nombre del fichero contiene caracteres ilegales
validator.fileandsubdirectorytemplate.dotFirstInFileNameTemplate            = Un punto no es un caracter v�lido para el comienzo de la plantilla del nombre de fichero
validator.fileandsubdirectorytemplate.dotLastInFileNameTemplate             = Un punto no es un caracter v�lido para el final de la plantilla del nombre de fichero

# File creation at destination directory validator
validator.filecreationatdestinationdirectory.couldNotCreateSubDirectory       = No se pudo crear el sub directorio
validator.filecreationatdestinationdirectory.couldNotCreateAllJPEGFiles       = No se pudieron crear todos los ficheros JPEG
validator.filecreationatdestinationdirectory.couldNotCreateThumbNailDirectory = No se pudo crear el directorio de miniaturas
validator.filecreationatdestinationdirectory.couldNotCreateAllThumbNails      = No se pudieron crear todas las miniaturas
validator.filecreationatdestinationdirectory.couldNotCreateAllNonJPEGFiles    = No se pudieros crear todos los ficheros no JPEG
validator.filecreationatdestinationdirectory.couldNotDeleateSubDirectory      = No se pudo borrar el subdirectorio

# JPEG total path length validator
validator.jpegtotalpathlength.toLongFileName               = Con la plantilla y el directorio de destino actual, la ruta total de \nlos ficheros renombrados ser� superior de lo permitido.\n\nPara evitarlo, o se cambia el directorio de destino o las plantillas.
validator.jpegtotalpathlength.noJPEGFIlesInSourceDirectory = No hay ficheros JPEG en el directorio origen indicado

# Non JPEG total path length validator
validator.nonjpegtotalpathlength.toLongFileName      = La ruta total con el directorio de destino actual ser� demasiado larga para el fichero:
validator.nonjpegtotalpathlength.toLongDirectoryPath = La ruta total con el directorio de destino actual ser� demasiado larga para el directorio:

# Source and destination path validator
validator.sourceanddestinationpath.noSourcePathError                       = Se debe indicar una ruta al directorio origen
validator.sourceanddestinationpath.noDestinationPathError                  = Se debe indicar una ruta al directorio de destino
validator.sourceanddestinationpath.invalidCharactersInSourcePathError      = La ruta del directorio origen contiene caracteres no v�lidos
validator.sourceanddestinationpath.invalidCharactersInDestinationPathError = La ruta del directorio destino contiene caracteres no v�lidos

# Thumb nail overview layout validator
validator.externalOverviewLayout.invalidXMLFile = El contenido del fichero layout.xml no es v�lido. Revisa el log para m�s informaci�n. 

##############################################################################################################
# V I S T A  E N  M I N I A T U R A                                                                          #
##############################################################################################################
thumbnailoverview.ThumbNailOverViewCreator.starting             = Iniciada la creaci�n de miniaturas
thumbnailoverview.ThumbNailOverViewCreator.finished             = Finalizada la creaci�n de miniaturas
thumbnailoverview.ThumbNailOverViewCreator.error.createCSSFile  = No se pudo crear el fichero CSS en el directorio de destino. Revise el log para m�s informaci�n.
thumbnailoverview.ThumbNailOverViewCreator.error.accessCSSFile  = No se pudo acceder al fichero CSS. Revise el log para m�s informaci�n.
thumbnailoverview.ThumbNailOverViewCreator.error.createHTMLFile = No se pudo crear el fichero HTML en el directorio de destino.
thumbnailoverview.ThumbNailOverViewCreator.error.accessHTMLFile = No se pudo acceder al fichero HTML. Revise el log para m�s detalles.

thumbnailoverview.LayoutParser.wrongElementAmount = El fichero layout.xml contiene cantidad de elemetos err�neos:
thumbnailoverview.LayoutParser.parseError         = Error cuando se analizaba layout.xml. Revise el log para m�s detalles.

##############################################################################################################
# C H E Q U E A D O R  D E  A C T U A L I Z A C I O N                                                        #
##############################################################################################################
updatechecker.errormessage.uRLInvalid                  = Formato de la url del servidor de actualizaciones inv�lido. Revise el log para m�s detalles.
updatechecker.errormessage.uRLWrong                    = Url del servidor de actualizaciones err�nea. Revise el log para m�s detalles.
updatechecker.errormessage.networkTimeOut              = El servidor de actualizaciones no respondi�, se reintentar� la pr�xima vez que se inicie la aplicaci�n.
updatechecker.errormessage.downloadError               = No se pudo descargar la �ltima versi�n. Revise el log para m�s detalles.
updatechecker.errormessage.parseException              = No se pudo leer el fichero obtenido. Revise el log para m�s detalles.
updatechecker.errormessage.parseConfigurationException = No se pudo configurar un analizador XML. Revise el log para m�s detalles.

updatechecker.informationmessage.downloadFinished = Descarga finalizada

updatechecker.gui.title          = Nueva versi�n disponible
updatechecker.gui.newVersion     = Hay una nueva versi�n de JavaPEG disponible. Revise el historial de cambios.
updatechecker.gui.downloadButton = Descargar
updatechecker.gui.closeButton    = Cerrar

##############################################################################################################
# P A N E L  D E  M E T A D A T O S                                                                          #
##############################################################################################################
metadatapanel.titleDefaultText     = META DATOS PARA LA IMAGEN:
metadatapanel.tableheader.type     = TIPO
metadatapanel.tableheader.property = PROPIEDAD
metadatapanel.tableheader.value    = VALOR

##############################################################################################################
# M E N S A J E S  D E  L A  B A R R A  D E  E S T A D O                                                     #
##############################################################################################################
statusbar.message.amountOfRows              = N�mero de filas
statusbar.message.amountOfColumns           = N�mero de columnas
statusbar.message.amountOfImagesInDirectory = Cantidad de im�genes en directorio
statusbar.message.selectedPath              = Ruta seleccionada:

##############################################################################################################
# P A N E L  T A B U L A D O  (F U N C I O N E S  P R I N C I P A L E S)                                     #
##############################################################################################################
tabbedpane.imageRename = RENOMBRAR IMAGENES
tabbedpane.imageView   = CREAR LISTA DE IMAGENES

##############################################################################################################
# V I S O R  D E  I M A G E N E S                                                                            #
##############################################################################################################
imageviewer.button.back.toolTip                         = Anterior (Alt + Z) 
imageviewer.button.back.mnemonic                        = Z
imageviewer.button.forward.toolTip                      = Siguiente (Alt + X) 
imageviewer.button.forward.mnemonic                     = X
imageviewer.button.automaticAdjustToWindowSize.toolTip  = Ajustar autom�ticamente al tama�o de ventana (Alt + C) 
imageviewer.button.automaticAdjustToWindowSize.mnemonic = C
imageviewer.button.adjustToWindowSize.toolTip           = Ajustar al tama�o de ventana (Alt + A) 
imageviewer.button.adjustToWindowSize.mnemonic          = A
imageviewer.button.help.toolTip                         = Ayuda (Alt + H) 
imageviewer.button.help.mnemonic                        = H

imageviewer.popupmenu.back.text               = (Alt-Z) Anterior
imageviewer.popupmenu.forward.text            = (Alt-X) Siguiente
imageviewer.popupmenu.adjustToWindowSize.text = (Alt-A) Ajustar al tama�o de ventana

imageviewer.statusbar.pathToPicture    = Ruta al fichero
imageviewer.statusbar.sizeLabel        = S:
imageviewer.statusbar.sizeLabelImage   = Tama�o de imagen en
imageviewer.statusbar.widthLabel       = W:
imageviewer.statusbar.widthLabelImage  = Ancho de imagen en pixeles
imageviewer.statusbar.heightLabel      = H:
imageviewer.statusbar.heightLabelImage = Alto de imagen en pixeles

##############################################################################################################
# O B T E N E R  F I C H E R O S                                                                             #
##############################################################################################################
fileretriever.canNotFindFile     = No se pudo encontrar el fichero, revise el log para m�s detalles.
fileretriever.canNotReadFromFile = No se puede leer el fichero, puede que otra aplicaci�n tenga el fichero bloqueado. Revise el log para m�s detalles.