# This is the Swedish language file for the category strings.

# The structure of the file is as follows:

# A line starting with "#" is interpreted as a comment.

# A valid non comment line may look like "file = Arkiv" where "file" is the variable, = the
# separator and "Arkiv" is the value of the variable, in this case the Swedish word for file.

# There might be whitespaces or tabs in between the variable and the "=". Any whitespace
# or other character after the value of the variable will be trimmed away.

# For more information regarding the rules for the syntax of this file please visit:
# http://java.sun.com/javase/6/docs/api/java/util/Properties.html

categoryimportexport.alreadyImportedWithAnotherName = Kategorierna som skall importeras är redan importerade men med ett annat visningsnamn.\n\nSkall det gamla visningsnamnet ("%s") fortfarande användas? (nytt visningsnamn: "%s")
categoryimportexport.newerVersionAlreadyImported = Nyare version av kategorierna är redan importerade
categoryimportexport.newerVersionExistsForThisFile = A newer version exist for the categories file for meta data base file: %s\nMake the import now?
categoryimportexport.newerVersionExistsForThisFile.label = Newer version exists

categoryimportexport.import = Import
categoryimportexport.import.long.title = Importera kategorier
categoryimportexport.import.long.title.tooltip = Importerar kategoriträdstrukturen från en annan JavaPEG-installation
categoryimportexport.import.wrongCategoriesFile = Fel kategorifil.\n\nJavaPEG klient id:t i den valda kategorifilen stämmer inte överrens med klientid:t\ni bildmetadata-filen. Se loggfilen för mer detaljer\n\nVälj en annan kategorifil?
categoryimportexport.import.wrongCategoriesFile.label = Fel kategorifil

categoryimportexport.export = Export
categoryimportexport.export.long.title = Exportera kategorier
categoryimportexport.export.long.title.tooltip = Exporterar kategoriträdstrukturen, så att den kan importeras i en annan JavaPEG-installation
categoryimportexport.export.error = Kunde inte exportera kategorier till:

categoryimportexport.export.noWriteAccess = Inga skrivrättigheter. Vänligen välj en annan destination för kategoriexporten

categoryimportexport.importFileLabel = Kategoriimport för fil:
categoryimportexport.importNameLabel = Namn

categoryimportexport.categoryImportExportImportLabel = Kategorifil att importera
categoryimportexport.categoryImportExportExportLabel = Exportera kategorifil till
categoryimportexport.categoryImportExportExport.exported = Kategorifil exporterad till:

categoryimportexport.selectCategoryFileToImport = Välj kategorifil att importera
categoryimportexport.selectDestinationForCategoryExport = Välj destination för kategoriexport

categoryimportexport.displayName.invalid.label = Ogiltigt namn
categoryimportexport.displayName.invalid.empty = Det angivna visningsnamnet är tomt, vänligen ange ett icke tomt visningsnamn
categoryimportexport.displayName.invalid.alreadyInUse = Det angivna visningsnamnet används redan, vänligen ange ett annat visningsnamn