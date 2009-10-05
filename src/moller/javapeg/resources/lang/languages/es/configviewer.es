# This is the Spanish language file for the Config Viewer Component.

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
#                      : by Fredrik Möller 2009-09-16
#                      : by Fredrik Möller 2009-10-04
#                      : by Fredrik Möller 2009-10-05

##############################################################################################################
# W I N D O W                                                                                                #
##############################################################################################################
configviewer.window.locationError = No se puede configurar la ventana acorde a los ajustes. Revisa el fichero de logs para más detalles
configviewer.window.title         = Configuración

##############################################################################################################
# L O G G I N G                                                                                              #
##############################################################################################################
configviewer.logging.label.logLevel.text                 = Nivel de log
configviewer.logging.label.developerMode.text            = Log sin buffer
configviewer.logging.label.rotateLog.text                = Rotar los logs automáticamente
configviewer.logging.label.zipLog.text                   = Comprimir los ficheros de log rotados
configviewer.logging.label.rotateLogSize.text            = Tamaño de rotación de Logs
configviewer.logging.label.logName.text                  = Nombre del Log
configviewer.logging.label.logEntryTimeStampFormat.text  = Formato de fecha para las entradas de Log
configviewer.logging.label.logEntryTimeStampPreview.text = Previsualización de fecha en las entradas de Log

##############################################################################################################
# U P D A T E                                                                                                #
##############################################################################################################
configviewer.update.label.updateEnabled.text            = Habilitar chequeo de actualización
configviewer.update.label.attachVersionInformation.text = Añadir Información de versión para comprobar la actualización

##############################################################################################################
# R E N A M E                                                                                                #
##############################################################################################################
configviewer.rename.label.useLastModifiedDate.text = Usar la última fecha de modificación si la fecha Exif no existe
configviewer.rename.label.useLastModifiedTime.text = Usar la última hora de modificación si la hora Exif no existe

##############################################################################################################
# L A N G U A G E                                                                                            #
##############################################################################################################
configviewer.language.label.selectionMode        = MODO DE SELECCION
configviewer.language.radiobutton.manual         = Manualmente
configviewer.language.radiobutton.automatic      = Automático
configviewer.language.label.currentLanguage      = IDIOMA ACTUAL
configviewer.language.label.availableLanguages   = IDIOMAS DISPONIBLES
configviewer.language.languageNameNotFound       = nombre de idioma no encontrado
configviewer.language.information.windowlabel    = Información
configviewer.language.information.restartMessage = El idioma seleccionado no se usará hasta que JavaPEG se reinicie

##############################################################################################################
# E R R O R  M E S S A G E S                                                                                 #
##############################################################################################################
configviewer.errormessage.rotateLogSizeToLargeKiB   = Tamaño de rotación grande: El máximo permitido son 100000 KiB
configviewer.errormessage.rotateLogSizeToLargeMiB   = Tamaño de rotación grande: El máximo permitido son 100 MiB
configviewer.errormessage.rotateLogSizeToSmall      = Tamaño de rotación pequeño: El mínimo permitido son 10 KiB
configviewer.errormessage.rotateLogSizeNotAnInteger = El tamaño de rotación para el log debe ser un entero

##############################################################################################################
# T R E E  N O D E S                                                                                         #
##############################################################################################################
configviewer.tree.root          = Configuración
configviewer.tree.node.logging  = Logging
configviewer.tree.node.updates  = Actualizaciones
configviewer.tree.node.rename   = Renombrar
configviewer.tree.node.language = Idioma

##############################################################################################################
# C H A N G E D  C O N F I G U R A T I O N  N O T I F I C A T I O N                                          #
##############################################################################################################
configviewer.changed.configuration.start = Lo siguiente ha sido cambiado en la configuración:
configviewer.changed.configuration.end   = JavaPEG se tiene que reiniciar para aplicar los cambios