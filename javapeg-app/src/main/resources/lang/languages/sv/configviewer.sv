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
configviewer.thumbnail.mainWindow.label              = Huvudfönster
configviewer.thumbnail.imageSearchResultWindow.label = Bildsökresultatsfönster
configviewer.thumbnail.imageViewerWindow.label       = Bildvisarfönster
configviewer.thumbnail.grayfilter.transparency.label = Genomskinlighet
configviewer.thumbnail.grayfilter.increase.contrast.label = Öka kontrast
configviewer.thumbnail.grayfilter.selected.thumbnail.heading.label = Markerad tumnagel

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
configviewer.tag.imageRepositoriesAdditionMode.neverAdd.label           = Lägg aldrig till bilder i följande kataloger automatiskt:
configviewer.tag.imageRepositoriesContent.label                         = Bilddatabas Innehåll 
configviewer.tag.imageRepositoriesContent.table.column.path             = Sökväg
configviewer.tag.imageRepositoriesContent.table.column.status           = Status
configviewer.tag.imageRepositories.label.totalAmountMnemonic            = TA
configviewer.tag.imageRepositories.label.existsMnemonic                 = E
configviewer.tag.imageRepositories.label.notAvailableMnemonic           = IT
configviewer.tag.imageRepositories.label.doesNotExistMnemonic           = EI
configviewer.tag.imageRepositories.label.inconsistentMnemonic           = I
configviewer.tag.imageRepositories.label.corruptMnemonic                = K
configviewer.tag.imageRepositories.label.totalAmount                    = Totalt Antal
configviewer.tag.imageRepositories.label.exists                         = Existerar
configviewer.tag.imageRepositories.label.notAvailable                   = Inte Tillgänglig
configviewer.tag.imageRepositories.label.doesNotExist                   = Existerar inte
configviewer.tag.imageRepositories.label.inconsistent                   = Inkonsistent
configviewer.tag.imageRepositories.label.corrupt                        = Korrupt
configviewer.tag.imageRepositories.label.unknown                        = Okänd
configviewer.tag.imageRepositories.label.existsTooltip                  = Bilddatabassökväg existerar.
configviewer.tag.imageRepositories.label.notAvailableTooltip            = Bilddatabassökväg inte tillgänglig.
configviewer.tag.imageRepositories.label.doesNotExistTooltip            = Bilddatabassökväg existerar inte.
configviewer.tag.imageRepositories.label.inconsistentTooltip            = Bilddatabas är inkonsistent.
configviewer.tag.imageRepositories.label.corruptTooltip                 = Bilddatabas är korrupt.
configviewer.tag.imageRepositories.label.unknownTooltip                 = Bilddatabasstatusen beräknas
configviewer.tag.imageRepositories.label.pathsWillBeRemoved             = Följande sökväg(ar) kommer att tas bort från bilddatabasen:
configviewer.tag.imageRepositories.label.addAutomatically               = Lägg automatiskt till en katalog med icke tillagda bilder till bilddatabasen
configviewer.tag.imageRepositories.label.askToAdd                       = Fråga för att lägga till katalog med icke tillagda bilder till bilddatabasen 
configviewer.tag.imageRepositories.label.doNotAskToAdd                  = Lägg inte till en katalog med icke tillagda bilder till bilddatabasen
configviewer.tag.imageRepositories.label.removeSelectedPaths            = Ta bort valda sökvägar
configviewer.tag.importedCategories.menuitem.rename                     = Byt namn
configviewer.tag.importedCategories.menuitem.delete                     = Ta bort
configviewer.tag.importedCategories.removeSelectedImportedCategoriesButton.tooltip = Ta bort de valda importerade kategorierna
configviewer.tag.importedCategories.panel.border.label                             = Importerade Kategorier
configviewer.tag.importedCategories.rename.dialog.title                            = Byt namn på importerade kategorier
configviewer.tag.importedCategories.rename.dialog.text                             = Ange ett nytt namn för importerade kategorier:

##############################################################################################################
# M E T A  D A T A                                                                                           #
##############################################################################################################
configviewer.metadata.label.cameramodel    = Kameramodell
configviewer.metadata.label.configurations = Konfigurationer 
configviewer.metadata.filtertable.header.cameramodel    = Kameramodell
configviewer.metadata.filtertable.header.filter         = Filter
configviewer.metadata.isofiltertable.delete.confirmmessage = Följande ISO-filter kommer att tas bort.
configviewer.metadata.exposuretimefiltertable.delete.confirmmessage = Följande Exponeringstid(s)-filter kommer att tas bort.
configviewer.metadata.filterbuttonpanel.title = Hantera filter
configviewer.metadata.filterbuttonpanel.addnewrulebutton = Lägg till ett nytt filter eller anpassa ett existerande vald kameramodell
configviewer.metadata.scrollpane.configured.fillters = Konfigurerade filter
configviewer.metadata.filterlists.tooltip.willbe = kommer att bli
configviewer.metadata.isofilter.added = Följande ISO-filter har lagts till:
configviewer.metadata.exposuretimefilter.added = Följande Exponeringstids-filter har lagts till:
configviewer.metadata.isofilter.removed = Följande ISO-filter har tagits bort:
configviewer.metadata.exposuretimefilter.removed = Följande Exponeringstids-filter har tagits bort:

##############################################################################################################
# U S E R  I N T E R F A C E                                                                                 #
##############################################################################################################
configviewer.userinterface.tabs.main        = Huvudfönster
configviewer.userinterface.tabs.imageviewer = Bildvisare

configviewer.userinterface.imageViewerBackground.label.text = Bakgrundsfärg bildvisare

configviewer.userinterface.splitpane.title = Fönsterindelning
configviewer.userinterface.tab.title = Flikar
configviewer.userinterface.button.colourchooser = Välj färg

configviewer.userinterface.splitpanedividerthickness.thin = Tunn
configviewer.userinterface.splitpanedividerthickness.thick = Tjock

configviewer.userinterface.main.dividersize.text = Träd till Mittre
configviewer.userinterface.thumbNailsToTabs.dividersize.text = Tumnaglar till Flikar
configviewer.userinterface.thumbNailsToMetaData.dividersize.text = Tumnaglar till metadata
configviewer.userinterface.centerToImageList.dividersize.text = Mittre till bildlista

configviewer.userinterface.tabPosition.top = Topp
configviewer.userinterface.tabPosition.bottom = Botten

configviewer.userinterface.tabPosition.label.text = Position flikar
configviewer.userinterface.tabTextColor.label.text = Färg fliktext

configviewer.userinterface.imageviewer.image.to.metadata.dividersize.text = Bild til metadata

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
configviewer.tree.node.metadata  = Metadata
configviewer.tree.node.gui       = Grafiska inställningar

##############################################################################################################
# C H A N G E D  C O N F I G U R A T I O N  N O T I F I C A T I O N                                          #
##############################################################################################################
configviewer.changed.configuration.start = Följande inställningar har ändrats:
configviewer.changed.configuration.end   = JavaPEG måste startas om för att ändringarna skall ha effekt