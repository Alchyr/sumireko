package sumireko.patches;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import javassist.*;
import org.clapper.util.classutil.*;
import sumireko.patches.occult.OccultPatch;

import java.io.File;
import java.net.URISyntaxException;

@SpirePatch(
        clz = CardCrawlGame.class,
        method = SpirePatch.CONSTRUCTOR
)
public class DynamicPatchTrigger {
    public static void Raw(CtBehavior ctBehavior) throws NotFoundException {
        System.out.println("Starting dynamic patches.");

        ClassFinder finder = new ClassFinder();

        finder.add(new File(Loader.STS_JAR));

        for (ModInfo modInfo : Loader.MODINFOS) {
            if (modInfo.jarURL != null) {
                try {
                    finder.add(new File(modInfo.jarURL.toURI()));
                } catch (URISyntaxException e) {
                    // do nothing
                }
            }
        }

        ClassPool pool = ctBehavior.getDeclaringClass().getClassPool();
        OccultPatch.patch(finder, pool);
        DeepDreamPatch.patch(finder, pool);

        System.out.println("Dynamic patches complete.");
    }
}