# This is the Swedish language file for ImageViewer.

# The structure of the file is as follows:

# A line starting with "#" is interpreted as a comment.

# A valid non comment line may look like "file = Arkiv" where "file" is the variable, = the
# separator and "Arkiv" is the value of the variable, in this case the Swedish word for file.

# There might be whitespaces or tabs in between the variable and the "=". Any whitespace
# or other character after the value of the variable will be trimmed away.

# For more information regarding the rules for the syntax of this file please visit:
# http://java.sun.com/javase/6/docs/api/java/util/Properties.html

##############################################################################################################
# I M A G E  V I E W E R                                                                                     #
##############################################################################################################
imageviewer.button.back.toolTip                         = Föregående (Vänsterpil) 
imageviewer.button.forward.toolTip                      = Nästa (Högerpil) 
imageviewer.button.automaticAdjustToWindowSize.toolTip  = Automatisk anpassning till fönsterstorlek (Alt + C) 
imageviewer.button.automaticAdjustToWindowSize.mnemonic = C
imageviewer.button.adjustToWindowSize.toolTip           = Anpassa till fönsterstorlek (Alt + A) 
imageviewer.button.adjustToWindowSize.mnemonic          = A
imageviewer.button.help.toolTip                         = Hjälp (Alt + H) 
imageviewer.button.help.mnemonic                        = H
imageviewer.button.rotateLeft.toolTip                   = Rotera bild vänster (Alt + Vänsterpil)
imageviewer.button.rotateRight.toolTip                  = Rotera bild höger (Alt + Högerpil)
imageviewer.button.rotateAutomatic                      = Rotera bild automatiskt (Alt + Pil Upp)
imageviewer.button.center.toolTip                       = Centrera bild
imageviewer.button.toggleNavigationImage.toolTip        = Visa / Göm navigationsbild
imageviewer.button.zoomIn.toolTip                       = Förstora bild
imageviewer.button.zoomOut.toolTip                      = Förminska bild
imageviewer.button.startSlideShow.toolTip               = Starta automatisk bildvisning
imageviewer.button.stopSlideShow.toolTip                = Stoppa automatisk bildvisning
imageviewer.button.slideShowDelay.toolTip               = Fördröjning, i sekunder, mellan två bilder i den automatiska bildvisningen

imageviewer.popupmenu.back.text               = (Vänsterpil) Föregående
imageviewer.popupmenu.forward.text            = (Högerpil) Nästa
imageviewer.popupmenu.adjustToWindowSize.text = (Alt-A) Anpassa till fönsterstorlek

imageviewer.statusbar.pathToPicture    = Sökväg till bildfilen
imageviewer.statusbar.sizeLabel        = S:
imageviewer.statusbar.sizeLabelImage   = Bildstorlek i 
imageviewer.statusbar.widthLabel       = B:
imageviewer.statusbar.widthLabelImage  = Bildbredd i pixlar
imageviewer.statusbar.heightLabel      = H:
imageviewer.statusbar.heightLabelImage = Bildhöjd i pixlar

##############################################################################################################
# M E T A  D A T A  P A N E L                                                                                #
##############################################################################################################
metadatapanel.titleDefaultText     = METADATA FÖR BILD:
metadatapanel.tableheader.type     = TYP
metadatapanel.tableheader.property = EGENSKAP
metadatapanel.tableheader.value    = VÄRDE

##############################################################################################################
# I M A G E  S E A R C H  R E S U L T  V I E W E R                                                           #
##############################################################################################################
imagesearchresultviewer.title = Sökresultat (%s - %s av %s)
imagesearchresultviewer.menuitem.selectAll = Markera alla bilder
imagesearchresultviewer.menuitem.deSelectAll = Avmarkera alla markerade bilder
imagesearchresultviewer.menuitem.addSelectedImagesToViewList = Lägg valda bilder till bildlistan
imagesearchresultviewer.menuitem.copySelectedImagesToSystemClipboard = Kopiera valda bilder till systemets urklippshanterare
imagesearchresultviewer.menuitem.copyAllImagesToSystemClipboard = Kopiera alla bilder till systemets urklippshanterare
imagesearchresultviewer.statusMessage.amountOfImagesInSearchResult = Antal bilder i sökresultatet
imagesearchresultviewer.button.numberOfImagesToDisplayPerTab.tooltip = Antal bilder per flik
imagesearchresultviewer.button.loadPreviousImage.tooltip = Visa föregående bilder
imagesearchresultviewer.button.loadNextImage.tooltip = Visa nästa bilder

##############################################################################################################
# I M A G E  R E S I Z E  Q U A L I T Y                                                                      #
##############################################################################################################
imageviewer.combobox.resize.quality.automatic    = Automatisk
imageviewer.combobox.resize.quality.speed        = Snabb
imageviewer.combobox.resize.quality.balanced     = Balanserd
imageviewer.combobox.resize.quality.quality      = Kvalitet
imageviewer.combobox.resize.quality.ultraquality = Ultrakvalitet

##############################################################################################################
# E R R O R  M E S S A G E S                                                                                 #
##############################################################################################################
imageviewer.could.nor.read.image = Kunde in läsa bildfilen: %s Se loggfil för feldetaljer