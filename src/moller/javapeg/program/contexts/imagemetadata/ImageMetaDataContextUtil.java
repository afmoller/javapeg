package moller.javapeg.program.contexts.imagemetadata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ImageMetaDataContextUtil {
	
	public static List<File> performImageSearch(ImageMetaDataContextSearchParameters imageMetaDataContextSearchParameters) {
		if (noSearchParameters(imageMetaDataContextSearchParameters)) {
			
			Set<String> imagePathsAsStrings = ImageMetaDataContext.getInstance().getImagePaths();
			List<File>  imagePathsAsFiles = new ArrayList<File>(imagePathsAsStrings.size());
			
			for (String imagePathAsString : imagePathsAsStrings) {
				imagePathsAsFiles.add(new File(imagePathAsString));
			}
			return imagePathsAsFiles;
		} else {
			return doSearch(imageMetaDataContextSearchParameters);
		}
	}
	
	private static List<File> doSearch(ImageMetaDataContextSearchParameters imageMetaDataContextSearchParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	private static boolean noSearchParameters(ImageMetaDataContextSearchParameters imageMetaDataContextSearchParameters) {
		if (imageMetaDataContextSearchParameters.getCategories() == null) {
			return true;
		}
		return false;
	}

}
