package moller.javapeg.program.config;
/**
 * This class was created : 2009-08-09 by Fredrik Möller
 * Latest changed         : 2009-09-05 by Fredrik Möller
 *                        : 2009-11-13 by Fredrik Möller
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import moller.javapeg.program.ApplicationContext;
import moller.javapeg.program.C;
import moller.javapeg.program.language.ISO639;
import moller.javapeg.program.language.Language;

public class ConfigUtil {
	
	public static  String [] listLanguagesFiles() {
		Set<String> languageFiles = ApplicationContext.getInstance().getJarFileEmbeddedLanguageFiles();
				
		File languageFolder = new File(C.USER_HOME + C.FS + "resources" + C.FS + "lang");
		File [] files = languageFolder.listFiles();
				
		for (File file : files) {
			if (file.isFile()) {
				languageFiles.add(file.getName());
			}
		}
				
		List <String> languageNames = new ArrayList<String>(languageFiles.size());
		
		ISO639 iso639 = ISO639.getInstance();
		String code = "";
		String languageName = "";
		
		for (String fileName : languageFiles) {
			code = fileName.substring(fileName.lastIndexOf(".") + 1);
			languageName = iso639.getLanguage(code);
			if (languageName != null) {
				languageNames.add(iso639.getLanguage(code));	
			}
		}
		Collections.sort(languageNames);
				
		return (String [])languageNames.toArray(new String[0]);
	}
	
	public static String resolveCodeToLanguageName(String code) {
				
		String language = ISO639.getInstance().getLanguage(code);
		
		if (language == null) {
			return Language.getInstance().get("configviewer.language.languageNameNotFound");
		}
		return language;
	}
}