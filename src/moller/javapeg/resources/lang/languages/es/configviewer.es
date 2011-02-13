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
# This file was updated: by Fredrik M�ller 2009-09-06
#                      : by Fredrik M�ller 2009-09-16
#                      : by Fredrik M�ller 2009-10-04
#                      : by Fredrik M�ller 2009-10-05

##############################################################################################################
# W I N D O W                                                                                                #
##############################################################################################################
configviewer.window.locationError = No se puede configurar la ventana acorde a los ajustes. Revisa el fichero de logs para m�s detalles
configviewer.window.title         = Configuraci�n

##############################################################################################################
# L O G G I N G                                                                                              #
##############################################################################################################
configviewer.logging.label.logLevel.text                 = Nivel de log
configviewer.logging.label.developerMode.text            = Log sin buffer
configviewer.logging.label.rotateLog.text                = Rotar los logs autom�ticamente
configviewer.logging.label.zipLog.text                   = Comprimir los ficheros de log rotados
configviewer.logging.label.rotateLogSize.text            = Tama�o de rotaci�n de Logs
configviewer.logging.label.logName.text                  = Nombre del Log
configviewer.logging.label.logEntryTimeStampFormat.text  = Formato de fecha para las entradas de Log
configviewer.logging.label.logEntryTimeStampPreview.text = Previsualizaci�n de fecha en las entradas de Log

##############################################################################################################
# U P D A T E                                                                                                #
##############################################################################################################
configviewer.update.label.updateEnabled.text            = Habilitar chequeo de actualizaci�n
configviewer.update.label.attachVersionInformation.text = A�adir Informaci�n de versi�n para comprobar la actualizaci�n

##############################################################################################################
# R E N A M E                                                                                                #
##############################################################################################################
configviewer.rename.label.useLastModifiedDate.text      = Usar la �ltima fecha de modificaci�n si la fecha Exif no existe
configviewer.rename.label.useLastModifiedTime.text      = Usar la �ltima hora de modificaci�n si la hora Exif no existe
configviewer.rename.label.maximumCameraModelValueLength = Maximum Length of camera model value

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
# T H U M B N A I L                                                                                          #
##############################################################################################################
configviewer.thumbnail.creation.label.missingOrCorrupt               = If embedded thumbnail is missing or corrupt, create a temporary
configviewer.thumbnail.creation.label.thumbnail.width                = Width of the created thumbnail
configviewer.thumbnail.creation.label.thumbnail.height               = Height of the created thumbnail
configviewer.thumbnail.creation.label.algorithm                      = Thumbnail Creation Algorithm
configviewer.thumbnail.creation.label                                = Thumbnail Creation
configviewer.thumbnail.creation.validation.width.integer             = The value of the thumbnail width must be an integer
configviewer.thumbnail.creation.validation.width.integerNonNegative  = The value of the thumbnail width must be a non negative integer
configviewer.thumbnail.creation.validation.height.integer            = The value of the thumbnail height must be an integer
configviewer.thumbnail.creation.validation.height.integerNonNegative = The value of the thumbnail height must be an non negative integer
configviewer.thumbnail.cache.label.enable              = Enable Thumbnail cache
configviewer.thumbnail.cache.label.size                = Thumbnail cache size
configviewer.thumbnail.cache.label.size.max            = Thumbnail cache size max
configviewer.thumbnail.cache.label.clear               = Clear Thumbnail cache:
configviewer.thumbnail.cache.label                     = Thumbnail Cache
configviewer.thumbnail.cache.validation.size.max       = The value of the thumbnail cache max size must be an non negative integer
configviewer.thumbnail.tooltip.label.disabled          = Disabled:
configviewer.thumbnail.tooltip.label.enabled           = Enabled:
configviewer.thumbnail.tooltip.label.extended          = Extended:
configviewer.thumbnail.tooltip.label                   = Thumbnail Tooltips

##############################################################################################################
# T A G                                                                                                      #
##############################################################################################################
configviewer.tag.previewimage.label.embeddedthumbnail = Use embedded thumbnail as Preview Image (Fast)
configviewer.tag.previewimage.label.scaledthumbnail   = Use scaled thumbnail as Preview Image (Slow)
configviewer.tag.previewimage.label                   = Preview Image
configviewer.tag.categories.warnWhenRemove                          = Warn when removing category
configviewer.tag.categories.warnWhenRemoveCategoryWithSubCategories = Warn when removing category with sub categories
configviewer.tag.categories.label                                   = Categories
configviewer.tag.imageRepositories.label                                = Image Repository
configviewer.tag.imageRepositories.label.removeNonExistingPaths         = Remove non existing paths
configviewer.tag.imageRepositories.label.removeNonExistingPaths.tooltip = Automatically remove paths from the image repository that does not exist
configviewer.tag.imageRepositories.label.exists                         = Exists
configviewer.tag.imageRepositories.label.notAvailable                   = Not Available
configviewer.tag.imageRepositories.label.doesNotExist                   = Does not exist

##############################################################################################################
# E R R O R  M E S S A G E S                                                                                 #
##############################################################################################################
configviewer.errormessage.rotateLogSizeToLargeKiB   = Tama�o de rotaci�n grande: El m�ximo permitido son 100000 KiB
configviewer.errormessage.rotateLogSizeToLargeMiB   = Tama�o de rotaci�n grande: El m�ximo permitido son 100 MiB
configviewer.errormessage.rotateLogSizeToSmall      = Tama�o de rotaci�n peque�o: El m�nimo permitido son 10 KiB
configviewer.errormessage.rotateLogSizeNotAnInteger = El tama�o de rotaci�n para el log debe ser un entero

##############################################################################################################
# T R E E  N O D E S                                                                                         #
##############################################################################################################
configviewer.tree.root          = Configuraci�n
configviewer.tree.node.logging  = Logging
configviewer.tree.node.updates  = Actualizaciones
configviewer.tree.node.rename   = Renombrar
configviewer.tree.node.language = Idioma

##############################################################################################################
# C H A N G E D  C O N F I G U R A T I O N  N O T I F I C A T I O N                                          #
##############################################################################################################
configviewer.changed.configuration.start = Lo siguiente ha sido cambiado en la configuraci�n:
configviewer.changed.configuration.end   = JavaPEG se tiene que reiniciar para aplicar los cambios