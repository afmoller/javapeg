/*******************************************************************************
 * Copyright (c) JavaPEG developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package moller.javapeg.program.imagemetadata.handler;

import java.util.List;

import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.metadata.MetaDataFilter;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContext;
import moller.javapeg.program.datatype.ExposureTime;
import moller.javapeg.program.enumerations.ExposureTimeFilterMask;
import moller.javapeg.program.enumerations.IFilterMask;
import moller.javapeg.program.enumerations.ISOFilterMask;
import moller.util.datatype.Rational;

public class ImageMetaDataDataBaseHandlerUtil {

    public static void addIso(ImageMetaDataContext imdc, String javaPegIdValue, int isoValue, String imagePath, String cameraModel) {

        MetaDataFilter<ISOFilterMask> isoFilter = getIsoFilterForCameraModel(cameraModel);

        if (isoFilter == null) {
            imdc.addIso(javaPegIdValue, isoValue, imagePath);
        } else {
            int maskedISOValue = getMaskedIsoValue(isoValue, isoFilter.getFilterMask());
            imdc.addIso(javaPegIdValue, maskedISOValue, imagePath);
        }
    }

    public static void addExposureTime(ImageMetaDataContext imdc, String javaPegIdValue, ExposureTime exposureTime, String imagePath, String cameraModel) {

        MetaDataFilter<ExposureTimeFilterMask> exposureTimeFilter = getExposureTimeFilterForCameraModel(cameraModel);

        if (exposureTimeFilter == null) {
            imdc.addExposureTime(javaPegIdValue, exposureTime, imagePath);
        } else {
            ExposureTime maskedExposureTimeValue = getMaskedExposureTimeValue(exposureTime, exposureTimeFilter.getFilterMask());
            imdc.addExposureTime(javaPegIdValue, maskedExposureTimeValue, imagePath);
        }
    }

    private static MetaDataFilter<ISOFilterMask> getIsoFilterForCameraModel(String cameraModel) {
        List<MetaDataFilter<ISOFilterMask>> isoFilters = Config.getInstance().get().getMetadata().getIsoFilters();

        return returnMatchingFilter(cameraModel, isoFilters);
    }

    private static int getMaskedIsoValue(int isoValue, ISOFilterMask isoFilterMask) {

        // Pass through, which will enable the most appropriate mask to be
        // applied on the incoming isoValue.
        switch (isoFilterMask) {
        case MASK_UP_TO_POSITON_SIXTH:
            if (isoValue > ISOFilterMask.MASK_UP_TO_POSITON_SIXTH.getTriggerValue()) {
                return maskValue(isoValue, ISOFilterMask.MASK_UP_TO_POSITON_SIXTH.getTriggerValue());
            }
        case MASK_UP_TO_POSITON_FIFTH:
            if (isoValue > ISOFilterMask.MASK_UP_TO_POSITON_FIFTH.getTriggerValue()) {
                return maskValue(isoValue, ISOFilterMask.MASK_UP_TO_POSITON_FIFTH.getTriggerValue());
            }
        case MASK_UP_TO_POSITON_FOURTH:
            if (isoValue > ISOFilterMask.MASK_UP_TO_POSITON_FOURTH.getTriggerValue()) {
                return maskValue(isoValue, ISOFilterMask.MASK_UP_TO_POSITON_FOURTH.getTriggerValue());
            }
        case MASK_UP_TO_POSITON_THIRD:
            if (isoValue > ISOFilterMask.MASK_UP_TO_POSITON_THIRD.getTriggerValue()) {
                return maskValue(isoValue, ISOFilterMask.MASK_UP_TO_POSITON_THIRD.getTriggerValue());
            }
        case MASK_UP_TO_POSITON_SECOND:
            if (isoValue > ISOFilterMask.MASK_UP_TO_POSITON_SECOND.getTriggerValue()) {
                return maskValue(isoValue, ISOFilterMask.MASK_UP_TO_POSITON_SECOND.getTriggerValue());
            }
        case MASK_UP_TO_POSITON_FIRST:
            if (isoValue > ISOFilterMask.MASK_UP_TO_POSITON_FIRST.getTriggerValue()) {
                return maskValue(isoValue, ISOFilterMask.MASK_UP_TO_POSITON_FIRST.getTriggerValue());
            }
        case NO_MASK:
        default:
            return isoValue;
        }
    }

    private static int maskValue(int value, int triggerValue) {
        int reminder = value % triggerValue;

        int half = triggerValue / 2;

        if (reminder >= half) {
            return value + triggerValue - reminder;
        } else {
            return value - reminder;
        }
    }

    private static <T extends IFilterMask> MetaDataFilter<T> returnMatchingFilter(String cameraModel, List<MetaDataFilter<T>> exposureTimeFilters) {
        for (MetaDataFilter<T> exposureTimeFilter : exposureTimeFilters) {
            if (exposureTimeFilter.getCameraModel().equals(cameraModel)) {
                return exposureTimeFilter;
            }
        }

        // No matching filter found for the given camera model.
        return null;
    }

    private static MetaDataFilter<ExposureTimeFilterMask> getExposureTimeFilterForCameraModel(String cameraModel) {
        List<MetaDataFilter<ExposureTimeFilterMask>> exposureTimeFilters = Config.getInstance().get().getMetadata().getExposureTimeFilters();

        return returnMatchingFilter(cameraModel, exposureTimeFilters);
    }

    private static void filterRationalExposureTime(ExposureTime normalizedRationalExposureTime, ExposureTimeFilterMask filterMask) {

        Rational partsOfSecond = normalizedRationalExposureTime.getPartsOfSecond();
        int denominator = partsOfSecond.getDenominator();

        // Pass through, which will enable the most appropriate mask to be
        // applied on the incoming isoValue.
        switch (filterMask) {
        case MASK_UP_TO_POSITON_SIXTH:
            if (denominator > ExposureTimeFilterMask.MASK_UP_TO_POSITON_SIXTH.getTriggerValue()) {
                partsOfSecond.setDenominator(maskValue(denominator, ExposureTimeFilterMask.MASK_UP_TO_POSITON_SIXTH.getTriggerValue()));
                break;
            }
        case MASK_UP_TO_POSITON_FIFTH:
            if (denominator > ExposureTimeFilterMask.MASK_UP_TO_POSITON_FIFTH.getTriggerValue()) {
                partsOfSecond.setDenominator(maskValue(denominator, ExposureTimeFilterMask.MASK_UP_TO_POSITON_FIFTH.getTriggerValue()));
                break;
            }
        case MASK_UP_TO_POSITON_FOURTH:
            if (denominator > ExposureTimeFilterMask.MASK_UP_TO_POSITON_FOURTH.getTriggerValue()) {
                partsOfSecond.setDenominator(maskValue(denominator, ExposureTimeFilterMask.MASK_UP_TO_POSITON_FOURTH.getTriggerValue()));
                break;
            }
        case MASK_UP_TO_POSITON_THIRD:
            if (denominator > ExposureTimeFilterMask.MASK_UP_TO_POSITON_THIRD.getTriggerValue()) {
                partsOfSecond.setDenominator(maskValue(denominator, ExposureTimeFilterMask.MASK_UP_TO_POSITON_THIRD.getTriggerValue()));
                break;
            }
        case MASK_UP_TO_POSITON_SECOND:
            if (denominator > ExposureTimeFilterMask.MASK_UP_TO_POSITON_SECOND.getTriggerValue()) {
                partsOfSecond.setDenominator(maskValue(denominator, ExposureTimeFilterMask.MASK_UP_TO_POSITON_SECOND.getTriggerValue()));
                break;
            }
        case MASK_UP_TO_POSITON_FIRST:
            if (denominator > ExposureTimeFilterMask.MASK_UP_TO_POSITON_FIRST.getTriggerValue()) {
                partsOfSecond.setDenominator(maskValue(denominator, ExposureTimeFilterMask.MASK_UP_TO_POSITON_FIRST.getTriggerValue()));
                break;
            }
        case NO_MASK:
        default:
            break;
        }
    }

    private static void normalizeRationalExposureTime(ExposureTime exposureTime) {
        exposureTime.getPartsOfSecond().normalize();
    }

    private static ExposureTime getMaskedExposureTimeValue(ExposureTime exposureTime, ExposureTimeFilterMask filterMask) {
        switch (exposureTime.getExposureTimeType()) {
        case DECIMAL:
        case INTEGER:
            return exposureTime;
        case RATIONAL:
            normalizeRationalExposureTime(exposureTime);
            filterRationalExposureTime(exposureTime, filterMask);

            return exposureTime;
        default:
            return exposureTime;
        }
    }
}
