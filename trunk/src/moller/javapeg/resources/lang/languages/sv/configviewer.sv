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
configviewer.rename.label.useLastModifiedDate.text                 = Anv�nd Senast �ndraddatum Om Exif-datum Saknas
configviewer.rename.label.useLastModifiedTime.text                 = Anv�nd Senast �ndradtid Om Exif-tid Saknas
configviewer.rename.label.maximumCameraModelValueLength            = Maximal l�ngd f�r v�rdet av  kameramodell
configviewer.rename.label.maximumCameraModelValueLengthNotNegative = V�rdet f�r maximal kameramdodelll�ngd kan inte vara ett negativt heltal

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
# T H U M B N A I L                                                                                          #
##############################################################################################################
configviewer.thumbnail.creation.label.missingOrCorrupt               = Om inb�ddad tumnagel saknas eller �r korrupt, skapa en tempor�r
configviewer.thumbnail.creation.label.thumbnail.width                = Bredd p� den skapade tumnageln
configviewer.thumbnail.creation.label.thumbnail.height               = H�jd p� den skapade tumnageln
configviewer.thumbnail.creation.label.algorithm                      = Tumnagelskaparalgoritm
configviewer.thumbnail.creation.label                                = Tumnagelskapande
configviewer.thumbnail.creation.validation.width.integer             = V�rdet p� tumnagelbredd m�ste var ett heltal
configviewer.thumbnail.creation.validation.width.integerNonNegative  = V�rdet p� tumnagelbredd m�ste var ett icke negativt heltal
configviewer.thumbnail.creation.validation.height.integer            = V�rdet p� tumnagelh�jd m�ste var ett heltal
configviewer.thumbnail.creation.validation.height.integerNonNegative = V�rdet p� tumnagelh�jd m�ste var ett icke negativt heltal
configviewer.thumbnail.cache.label.enable              = Aktivera tumnagel cache
configviewer.thumbnail.cache.label.size                = Tumnagel cachestorlek
configviewer.thumbnail.cache.label.size.max            = Tumnagel max cachestorlek
configviewer.thumbnail.cache.label.clear               = Rensa tumnagelcache
configviewer.thumbnail.cache.label.clear.question      = Alla minnescacheade tumnaglar kommer att rensas bort.
configviewer.thumbnail.cache.label                     = Tumnagel Cache
configviewer.thumbnail.cache.validation.size.max       = V�rdet p� maximal tumnagelcache f�r inte vara ett negativt heltal
configviewer.thumbnail.tooltip.label.disabled          = Avaktiverad:
configviewer.thumbnail.tooltip.label.enabled           = Aktiverad:
configviewer.thumbnail.tooltip.label.extended          = Ut�kad:
configviewer.thumbnail.tooltip.label                   = Tumnagel Tooltips

##############################################################################################################
# T A G                                                                                                      #
##############################################################################################################
configviewer.tag.previewimage.label.embeddedthumbnail = Anv�nd inb�ddad tumnagel som f�rhandsgranskningsbild (Snabbt)
configviewer.tag.previewimage.label.scaledthumbnail   = Anv�nd skaladtumnagel som f�rhandsgranskningsbild (L�ngsamt)
configviewer.tag.previewimage.label                   = F�rhandsgranskningsbild
configviewer.tag.categories.warnWhenRemove                          = Varna vid borttagning av kategori
configviewer.tag.categories.warnWhenRemoveCategoryWithSubCategories = Varna vid borttagning av kategori med underkategorier
configviewer.tag.categories.label                                   = Kategorier
configviewer.tag.imageRepositories.label                                = Bilddatabas
configviewer.tag.imageRepositories.label.removeNonExistingPaths         = Ta bort icke existerande s�kv�gar
configviewer.tag.imageRepositories.label.removeNonExistingPaths.tooltip = Ta automatiskt bort s�kv�gar som inte finns fr�n bilddatabasen
configviewer.tag.imageRepositories.label.exists                         = Existerar
configviewer.tag.imageRepositories.label.notAvailable                   = Inte Tillg�nglig
configviewer.tag.imageRepositories.label.doesNotExist                   = Existerar inte
configviewer.tag.imageRepositories.label.pathsWillBeRemoved             = F�ljande s�kv�g(ar) kommer att tas bort fr�n bilddatabasen:
configviewer.tag.imageRepositories.label.addAutomatically               = L�gg till automatiskt
configviewer.tag.imageRepositories.label.askToAdd                       = Fr�ga f�r att l�gga till
configviewer.tag.imageRepositories.label.doNotAskToAdd                  = Fr�ga inte

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
configviewer.tree.root           = Inst�llningar
configviewer.tree.node.logging   = Loggning
configviewer.tree.node.updates   = Uppdateringar
configviewer.tree.node.rename    = Namnbyte
configviewer.tree.node.language  = Spr�k
configviewer.tree.node.thumbnail = Tumnagel
configviewer.tree.node.tag       = Taggar

##############################################################################################################
# C H A N G E D  C O N F I G U R A T I O N  N O T I F I C A T I O N                                          #
##############################################################################################################
configviewer.changed.configuration.start = F�ljande inst�llningar har �ndrats:
configviewer.changed.configuration.end   = JavaPEG m�ste startas om f�r att �ndringarna skall ha effekt