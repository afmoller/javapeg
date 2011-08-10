package moller.util.string;

public class ParseVMArguments {

    public static long parseXmxToLong(String xmxString) {
        int beginIndex = -1;
        int endIndex = -1;

        for (int i = 0; i < xmxString.length(); i++)  {
            if (StringUtil.isInt(xmxString.substring(i, i + 1))) {
                beginIndex = i;
                break;
            }
        }

        if (beginIndex > -1) {
            for (int i = beginIndex; i < xmxString.length(); i++) {
                if (!StringUtil.isInt(xmxString.substring(i, i + 1))) {
                    endIndex = i;
                    break;
                }
            }
        }

        int intValue = Integer.parseInt(xmxString.substring(beginIndex, endIndex));

        String suffix = xmxString.substring(endIndex);

        if ("k".equalsIgnoreCase(suffix)) {
            return intValue * 1024;
        } else if ("m".equalsIgnoreCase(suffix)) {
            return intValue * 1024 * 1024;
        } else {
            return intValue;
        }
    }
}
