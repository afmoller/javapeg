# This is the Swedish language file for the Config Viewer Component.

# The structure of the file is as follows:

# A line starting with "#" is interpreted as a comment.

# A valid non comment line may look like "file = Arkiv" where "file" is the variable, = the
# separator and "Arkiv" is the value of the variable, in this case the Swedish word for file.

# There might be whitespaces or tabs in between the variable and the "=". Any whitespace
# or other character after the value of the variable will be trimmed away.

# For more information regarding the rules for the syntax of this file please visit:
# http://java.sun.com/javase/6/docs/api/java/util/Properties.html

##############################################################################################################
# W I N D O W                                                                                                #
##############################################################################################################
configviewer.window.locationError = Kunde inte sätta fönsterposition enligt konfigurationen. Se loggfil för detaljer.
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
configviewer.rename.label.useLastModifiedDate.text                 = Använd Senast ändraddatum Om Exif-datum Saknas
configviewer.rename.label.useLastModifiedTime.text                 = Använd Senast ändradtid Om Exif-tid Saknas
configviewer.rename.label.maximumCameraModelValueLength            = Maximal längd för värdet av kameramodell
configviewer.rename.label.maximumCameraModelValueLengthNotNegative = Värdet för maximal kameramdodelllängd kan inte vara ett negativt heltal

##############################################################################################################
# L A N G U A G E                                                                                            #
##############################################################################################################
configviewer.language.label.selectionMode        = Språkvalsmetod
configviewer.language.radiobutton.manual         = Manuellt
configviewer.language.radiobutton.automatic      = Automatiskt
configviewer.language.label.currentLanguage      = Aktuellt Språk
configviewer.language.label.availableLanguages   = Tillgängliga Språk
configviewer.language.languageNameNotFound       = namn på språk ej funnet
configviewer.language.information.windowlabel    = Information

##############################################################################################################
# T H U M B N A I L                                                                                          #
##############################################################################################################
configviewer.thumbnail.creation.label.missingOrCorrupt               = Om inbäddad tumnagel saknas eller är korrupt, skapa en temporär
configviewer.thumbnail.creation.label.thumbnail.width                = Bredd på den skapade tumnageln
configviewer.thumbnail.creation.label.thumbnail.height               = Höjd på den skapade tumnageln
configviewer.thumbnail.creation.label.algorithm                      = Tumnagelskaparalgoritm
configviewer.thumbnail.creation.label                                = Tumnagelskapande
configviewer.thumbnail.creation.validation.width.integer             = Värdet på tumnagelbredd måste var ett heltal
configviewer.thumbnail.creation.validation.width.integerNonNegative  = Värdet på tumnagelbredd måste var ett icke negativt heltal
configviewer.thumbnail.creation.validation.height.integer            = Värdet på tumnagelhöjd måste var ett heltal
configviewer.thumbnail.creation.validation.height.integerNonNegative = Värdet på tumnagelhöjd måste var ett icke negativt heltal
configviewer.thumbnail.cache.label.enable              = Aktivera tumnagel cache
configviewer.thumbnail.cache.label.size                = Tumnagel cachestorlek
configviewer.thumbnail.cache.label.size.max            = Tumnagel max cachestorlek
configviewer.thumbnail.cache.label.clear               = Rensa tumnagelcache
configviewer.thumbnail.cache.label.clear.question      = Alla minnescacheade tumnaglar kommer att rensas bort.
configviewer.thumbnail.cache.label                     = Tumnagel Cache
configviewer.thumbnail.cache.validation.size.max       = Värdet på maximal tumnagelcache får inte vara ett negativt heltal
configviewer.thumbnail.tooltip.label.disabled          = Avaktiverad:
configviewer.thumbnail.tooltip.label.enabled           = Aktiverad:
configviewer.thumbnail.tooltip.label.extended          = Utökad:
configviewer.thumbnail.tooltip.label                   = Tumnagel Tooltips

##############################################################################################################
# T A G                                                                                                      #
##############################################################################################################
configviewer.tag.previewimage.label.embeddedthumbnail = Använd inbäddad tumnagel som förhandsgranskningsbild (Snabbt)
configviewer.tag.previewimage.label.scaledthumbnail   = Använd skalad tumnagel som förhandsgranskningsbild (Långsamt)
configviewer.tag.previewimage.label                   = Förhandsgranskningsbild
configviewer.tag.categories.warnWhenRemove                          = Varna vid borttagning av kategori
configviewer.tag.categories.warnWhenRemoveCategoryWithSubCategories = Varna vid borttagning av kategori med underkategorier
configviewer.tag.categories.label                                   = Kategorier
configviewer.tag.imageRepositoriesAdditionMode.label                    = Bilddatabas Tillägg
configviewer.tag.imageRepositoriesAdditionMode.allwaysAdd.label         = Lägg alltid till bilder i följande kataloger automatiskt:
configviewer.tag.imageRepositoriesAdditionMode.neverAdd.label           = Lägg aldrig till bilder i fäljande kataloger automatiskt:
configviewer.tag.imageRepositoriesContent.label                         = Bilddatabas Innehåll 
configviewer.tag.imageRepositories.label.removeNonExistingPaths         = Ta bort icke existerande sökvägar
configviewer.tag.imageRepositories.label.removeNonExistingPaths.tooltip = Ta automatiskt bort sökvägar som inte finns från bilddatabasen
configviewer.tag.imageRepositories.label.exists                         = Existerar
configviewer.tag.imageRepositories.label.notAvailable                   = Inte Tillgänglig
configviewer.tag.imageRepositories.label.doesNotExist                   = Existerar inte
configviewer.tag.imageRepositories.label.pathsWillBeRemoved             = Följande sökväg(ar) kommer att tas bort från bilddatabasen:
configviewer.tag.imageRepositories.label.addAutomatically               = Lägg automatiskt till en katalog med icke tillagda bilder till bilddatabasen
configviewer.tag.imageRepositories.label.askToAdd                       = Fråga för att lägga till katalog med icke tillagda bilder till bilddatabasen 
configviewer.tag.imageRepositories.label.doNotAskToAdd                  = Lägg inte till en katalog med icke tillagda bilder till bilddatabasen
configviewer.tag.imageRepositories.label.removeSelectedPaths            = Ta bort valda sökvägar

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
configviewer.tree.root           = Inställningar
configviewer.tree.node.logging   = Loggning
configviewer.tree.node.updates   = Uppdateringar
configviewer.tree.node.rename    = Namnbyte
configviewer.tree.node.language  = Språk
configviewer.tree.node.thumbnail = Tumnagel
configviewer.tree.node.tag       = Taggar

##############################################################################################################
# C H A N G E D  C O N F I G U R A T I O N  N O T I F I C A T I O N                                          #
##############################################################################################################
configviewer.changed.configuration.start = Följande inställningar har ändrats:
configviewer.changed.configuration.end   = JavaPEG måste startas om för att ändringarna skall ha effekt