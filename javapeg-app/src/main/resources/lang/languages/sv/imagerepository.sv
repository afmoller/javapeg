# This is the Swedish language file for the Image repository part of JavaPEG.

# The structure of the file is as follows:

# A line starting with "#" is interpreted as a comment.

# A valid non comment line may look like "file = Arkiv" where "file" is the variable, = the
# separator and "Arkiv" is the value of the variable, in this case the Swedish word for file.

# There might be whitespaces or tabs in between the variable and the "=". Any whitespace
# or other character after the value of the variable will be trimmed away.

# For more information regarding the rules for the syntax of this file please visit:
# http://java.sun.com/javase/6/docs/api/java/util/Properties.html

imagerepository.addDirectoryToAllwaysAddAutomaticallyList.label = Lägg till katalog till listan av kataloger där bilder läggs till i bilddatabasen automatiskt
imagerepository.addDirectoryToNeverAddAutomaticallyList.label   = Lägg till katalog till listan av kataloger där bilder inte skall läggas till automatiskt till bilddatabasen

imagerepository.repositoryfile.corrupt.1 = Bilddatabasfilen är korrupt
imagerepository.repositoryfile.corrupt.2 = Den korrupta bilddatabasfilen säkerhetskopierades korrekt:
imagerepository.repositoryfile.corrupt.3 = Kunde inte säkerhetskopiera bilddatabasfilen. Innehållet har skrivits till JavaPEGs loggfil
imagerepository.repositoryfile.corrupt.4 = Bilddatabasfilen har återställts till ursprungligt skick.
imagerepository.repositoryfile.corrupt.5 = Kunde inte återställa bilddatabasfilen till ursprungligt skick
imagerepository.repositoryfile.corrupt.6 = Vänligen mata inte följande innehåll manuellt i bilddatabasen:
imagerepository.repositoryfile.corrupt.7 = Start innehåll:
imagerepository.repositoryfile.corrupt.8 = Kunde inte hämta innehåll
imagerepository.repositoryfile.corrupt.9 = Innehåll slut:
imagerepository.repositoryfile.corrupt.10 = Se JavaPEGs loggfil för detaljer

imagerepository.model.store.error  = Kunde inte spara bilddatabasen, se loggfil för detaljer.
imagerepository.model.create.error = Kunde inte öppna bilddatabasen, se loggfil för detaljer.

imagerepository.directory.added                = Vald katalog är en del av bilddatabasen
imagerepository.directory.added.writeprotected = Vald katalog är en del av bilddatabasen, men databasfilen är skrivskyddad
imagerepository.directory.added.notCreatedByThisInstance = Bilddatabasfilen är inte skapad av denna JavaPEG installationen
imagerepository.directory.not.added            = Vald katalog är inte en del av bilddatabasen

imagerepository.directory.already.added.to.allways.add         = har redan blivit tillagd i listan av kataloger för vilka\n bilder alltid kommer att läggas till automatiskt till bilddatabasen.
imagerepository.directory.already.added.to.never.add           = har redan blivit tillagd i listan av kataloger för vilka\n bilder aldrig kommer att läggas till automatiskt till bilddatabasen.
imagerepository.directory.is.parent.to.already.added.directory = En underkatalog har redan blivit tillagd i listan av kataloger\n för vilka bilder alltid kommer att läggas till automatiskt \ntill bilddatabasen eller i listan av kataloger för vilka bilder\n aldrig kommer att läggas till automatiskt till bilddatabasen.\n\nSe JavaPEGs inställningar för detaljer.

imagerepository.missing = Bilddatabasfilen: %s saknas
imagerepository.repository.inconsistent = Åtgärden avbröts eftersom en bilddatabasfil redan existerar i den valda katalogen\nmen den kunde inte läggas till i listan med bilddatabasfiler, eftersom den är inkonsistent med innehållet\ni den valda katalogen\n\nVänligen titta i JavaPEG-hjälpen för att få tips på om hur bilddatabasfilen kan göras konsistent
imagerepository.repository.corrupt = Åtgärden avbröts eftersom en bilddatabasfil redan existerar i den valda katalogen\nmen den kunde inte läggas till i listan med bilddatabasfiler, eftersom den är korrupt\n\nVänligen titta i JavaPEG-hjälpen för att få tips på om hur bilddatabasfilen kan göras giltig

imagerepository.repositories.inconsistent = Följande bilddatabasfil(er) är inkonsistenta med innehållet på disk och de kommer att ignoreras av JavaPEG tills de är konsistenta.
imagerepository.repositories.inconsistent.repair = Vänligen titta i JavaPEG-hjälpen för att få tips på om hur en bilddatabasfil kan göras konsistent

imagerepository.repositories.corrupt = Följande bilddatabasfil(er) är korrupta och de kommer att ignoreras av JavaPEG tills de har giltigt innehåll.
imagerepository.repositories.corrupt.repair = Vänligen titta i JavaPEG-hjälpen för att få tips på om hur en bilddatabasfil kan göras giltig