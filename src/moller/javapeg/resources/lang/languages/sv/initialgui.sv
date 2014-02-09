# This is the Swedish language file for the Initial configuration GUI.

# The structure of the file is as follows:

# A line starting with "#" is interpreted as a comment.

# A valid non comment line may look like "file = Arkiv" where "file" is the variable, = the
# separator and "Arkiv" is the value of the variable, in this case the Swedish word for file.

# There might be whitespaces or tabs in between the variable and the "=". Any whitespace
# or other character after the value of the variable will be trimmed away.

# For more information regarding the rules for the syntax of this file please visit:
# http://java.sun.com/javase/6/docs/api/java/util/Properties.html

window.title = Inledande konfiguration

button.cancel = Avbryt
button.continue = Fortsätt

lable.information = Information

help.title = Hjälp
help.text = Detta fönster visas endast första gången JavaPEG startas efter\n\
            installationen. Syftet med fönstret är att kunna göra en inledande\n\
            konfiguration av JavaPEG-applikationen.\n\n\
            Två möjligheter finns för att konfigurera applikationen:\n\
            (1) Antingen att importera en konfiguration från en tidigare\n\
            installation, eller:\n\
            (2) att specifiera vilket språk som skall användas i applikationen.\n\n\
            Hur konfigureras applikationen:\n\
            Ett val måste göras i sektion 1 (Konfigurations läge). Antingen\n\
            måste valet \"Ingen import\" väljas, då måste ett språk i sektion 2\n\
            väljas (Konfiguration).\n\n\
            Om \"Import\"-valet valts i sektion 1, så finns det två möjligheter:\n\
            (1) Antingen att välja en konfigurationsfil från listan \"Funna\n\
            konfigurationer i användarens hemkatalog\" (om den aktiva användaren\n\
            har gjort tidigare installationer av JavaPEG som är möjliga att\n\
            importera), eller\n\
            (2) att leta upp en installation på en annan plats genom att klicka\n\
            på knappen \"Importera konfiguration från annan installation\",\n\
            välja en katalog i det katalogväljarfönster som visas, och sen välja\n\
            en konfiguration, om det hittas någon i listan \"Funna\n\
            konfigurationer\".\n\n\ 
            När valen är gjorda, klicka på knappen \"Fortsätt\" eller på knappen\n\
            \"Avbryt\" för att avbryta applikationskonfigurationen och\n\
            applikationsstarten.

configuration.mode.title = 1: Konfigurations läge
configuration.mode.noimport = Ingen import
configuration.mode.import = Import

configuration.section.title = 2: Konfiguration
configuration.section.available.languages = Välj applikationsspråk:
configuration.section.available.configurations.in.user.home = Funna konfigurationer i användarens hemkatalog

configuration.section.other.import.location.title = Importera konfiguration från annan installation:
configuration.section.other.import.location.found.configurations = Funna konfigurationer

configuration.file.missing = Ingen konfigurationsfil att importera vald, vänligen starta om applikationen

configuration.search.in.directory = Söker efter konfigurationsfiler i:
configuration.search.found.configurationfiles = Konfigurationsfil(er) funna i katalog:
configuration.search.no.found.configurationfiles = Inga konfigurationsfil(er) funna i katalog:
configuration.search.aborted = Konfigurationsfilssökningen avbröts
configuration.search.filechooser.title = Importera JavaPEG Konfiguration