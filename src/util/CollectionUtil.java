package util;

import java.util.Collection;

public class CollectionUtil {

    public static boolean isEmpty(Collection collection) {
        return !ObjUtil.isEmpty(collection) && collection.isEmpty();
    }

}