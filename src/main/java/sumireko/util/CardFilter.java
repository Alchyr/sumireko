package sumireko.util;

import org.clapper.util.classutil.ClassFilter;
import org.clapper.util.classutil.ClassFinder;
import org.clapper.util.classutil.ClassInfo;

import static sumireko.SumirekoMod.modID;

public class CardFilter implements ClassFilter {
    private static final String PACKAGE = modID + ".cards";

    @Override
    public boolean accept(ClassInfo classInfo, ClassFinder classFinder)
    {
        return classInfo.getClassName().startsWith(PACKAGE);
    }
}
