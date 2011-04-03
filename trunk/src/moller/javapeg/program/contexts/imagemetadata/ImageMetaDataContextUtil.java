package moller.javapeg.program.contexts.imagemetadata;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import moller.javapeg.program.categories.Categories;

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
		
		String year = imageMetaDataContextSearchParameters.getYear();
		if(year != null) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesByYear(year));
		}
		
		String month = imageMetaDataContextSearchParameters.getMonth();
		if(month != null) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesByMonth(month));
		}
		
		String day = imageMetaDataContextSearchParameters.getDay();
		if(day != null) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesByDay(day));
		}
		
		String hour = imageMetaDataContextSearchParameters.getHour();
		if(hour != null) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesByHour(hour));
		}
		
		String minute = imageMetaDataContextSearchParameters.getMinute();
		if(minute != null) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesByMinute(minute));
		}
		
		String second = imageMetaDataContextSearchParameters.getSecond();
		if(second != null) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesBySecond(second));
		}
		
		String apertureValue = imageMetaDataContextSearchParameters.getApertureValue();
		if(apertureValue != null) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesByApertureValue(apertureValue));
		}
		
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
		
		String imageSize = imageMetaDataContextSearchParameters.getImageSize();
		if(imageSize != null) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesByImageSize(imageSize));
		}
		
		String iso = imageMetaDataContextSearchParameters.getIso();
		if(iso != null) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesByIso(iso));
		}
		
		boolean[] ratings = imageMetaDataContextSearchParameters.getRatings();
		if(ratings != null) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesByRating(ratings));
		}
		
		String exposureTime = imageMetaDataContextSearchParameters.getExposureTime();
		if(exposureTime != null) {
			searchResults.add(ImageMetaDataContext.getInstance().findImagesByExposureTime(exposureTime));
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
		
		if (imageMetaDataContextSearchParameters.getYear() != null) {
			return true;
		}
		if (imageMetaDataContextSearchParameters.getMonth() != null) {
			return true;
		}
		if (imageMetaDataContextSearchParameters.getDay() != null) {
			return true;
		}
		if (imageMetaDataContextSearchParameters.getHour() != null) {
			return true;
		}
		if (imageMetaDataContextSearchParameters.getMinute() != null) {
			return true;
		}
		if (imageMetaDataContextSearchParameters.getSecond() != null) {
			return true;
		}
		if (imageMetaDataContextSearchParameters.getApertureValue() != null) {
			return true;
		}
		if (imageMetaDataContextSearchParameters.getCameraModel() != null) {
			return true;
		}
		if (imageMetaDataContextSearchParameters.getComment() != null) {
			return true;
		}
		if (imageMetaDataContextSearchParameters.getCategories() != null) {
			return true;
		}
		if (imageMetaDataContextSearchParameters.getImageSize() != null) {
			return true;
		}
		if (imageMetaDataContextSearchParameters.getIso() != null) {
			return true;
		}
		if (imageMetaDataContextSearchParameters.getRatings() != null) {
			return true;
		}
		if (imageMetaDataContextSearchParameters.getExposureTime() != null) {
			return true;
		}
		return false;
	}
}
