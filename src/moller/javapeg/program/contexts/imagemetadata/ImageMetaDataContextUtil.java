package moller.javapeg.program.contexts.imagemetadata;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import moller.javapeg.program.categories.Categories;
import moller.javapeg.program.datatype.ImageSize;
import moller.util.datatype.ShutterSpeed;

public class ImageMetaDataContextUtil {
	
	public static Set<File> performImageSearch(ImageMetaDataContextSearchParameters imageMetaDataContextSearchParameters) {
		if (containSearchParameters(imageMetaDataContextSearchParameters)) {
			return doSearch(imageMetaDataContextSearchParameters);
		} else {
			Set<String> imagePathsAsStrings = ImagePathAndIndex.getInstance().getImagePaths();
			Set<File>  imagePathsAsFiles = new HashSet<File>(imagePathsAsStrings.size());
			
			for (String imagePathAsString : imagePathsAsStrings) {
				imagePathsAsFiles.add(new File(imagePathAsString));
			}
			return imagePathsAsFiles;
		}
	}
	
	private static Set<File> doSearch(ImageMetaDataContextSearchParameters imageMetaDataContextSearchParameters) {
				
		List<Set<File>> searchResults = new ArrayList<Set<File>>(); 
		
		String cameraModel = imageMetaDataContextSearchParameters.getCameraModel();
		if(cameraModel != null) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesByCameraModel(cameraModel));
		}
		
		String comment = imageMetaDataContextSearchParameters.getComment();
		if(comment != null) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesByComment(comment));
		}
		
		Categories categories = imageMetaDataContextSearchParameters.getCategories();
		if(categories != null) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesByCategory(categories, imageMetaDataContextSearchParameters.isAndCategoriesSearch()));
		}
		
		ImageSize imageSize = imageMetaDataContextSearchParameters.getImageSize();
		if(imageSize != null) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesByImageSize(imageSize));
		}
		
		int iso = imageMetaDataContextSearchParameters.getIso();
		if(iso > -1) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesByIso(iso));
		}
		
		boolean[] ratings = imageMetaDataContextSearchParameters.getRatings();
		if(ratings != null) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesByRating(ratings));
		}
		
		ShutterSpeed shutterSpeed = imageMetaDataContextSearchParameters.getShutterSpeed();
		if(shutterSpeed != null) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesByShutterSpeed(shutterSpeed));
		}
		return compileSearchResult(searchResults); 
	}

	public static Set<File> compileSearchResult(List<Set<File>> searchResults) {

		if (searchResults.size() > 1) {
			Set<File> result = new HashSet<File>();
			
			int indexWithLowestSize = getIndexWithLowestSize(searchResults);
			
			if (Integer.MAX_VALUE == indexWithLowestSize) {
				return result;
			} else {
				Set<File> limitingSet = searchResults.get(indexWithLowestSize);
				
				for (File itemInLimitingSet : limitingSet) {
					if (existInAllSearchResults(itemInLimitingSet, searchResults, indexWithLowestSize)) {
						result.add(itemInLimitingSet);		
					}
				}
				return result;
			}	
		} else {
			return searchResults.get(0);
		}
	}
	
	private static boolean existInAllSearchResults(File itemToSearchFor, List<Set<File>> searchResults, int excludeSetAtIndex) {
		for (int index = 0; index < searchResults.size(); index++) {
			if (index != excludeSetAtIndex) {
				if (!searchResults.get(index).contains(itemToSearchFor)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * This method returns the index where the smallest (size) set is stored.
	 * If there is no stored in the list then Integer.MAX_VALUE will be 
	 * returned.
	 *  
	 * @param searchResults is the list that contains sets.
	 *  
	 * @return the index for where the set with the smallest size is stored or
	 *         Integer.MAX_VALUE if no set is stored in the list.
	 */
	private static int getIndexWithLowestSize(List<Set<File>> searchResults) {
		int lowestSize          = Integer.MAX_VALUE;
		int indexWithLowestSize = Integer.MAX_VALUE;
		
		for (int index = 0; index < searchResults.size(); index++) {
			if (searchResults.get(index).size() < lowestSize) {
				indexWithLowestSize = index;
			}
		}
		return indexWithLowestSize;
	}

	private static boolean containSearchParameters(ImageMetaDataContextSearchParameters imageMetaDataContextSearchParameters) {
		boolean containSearchParameter = false;
		
		if (imageMetaDataContextSearchParameters.getCameraModel() != null) {
			containSearchParameter = true;
		}
		if (imageMetaDataContextSearchParameters.getComment() != null) {
			containSearchParameter = true;
		}
		if (imageMetaDataContextSearchParameters.getCategories() != null) {
			containSearchParameter = true;
		}
		if (imageMetaDataContextSearchParameters.getImageSize() != null) {
			containSearchParameter = true;
		}
		if (imageMetaDataContextSearchParameters.getIso() > -1) {
			containSearchParameter = true;
		}
		if (imageMetaDataContextSearchParameters.getRatings() != null) {
			containSearchParameter = true;
		}
		if (imageMetaDataContextSearchParameters.getShutterSpeed() != null) {
			containSearchParameter = true;
		}
		return containSearchParameter;
	}
}
