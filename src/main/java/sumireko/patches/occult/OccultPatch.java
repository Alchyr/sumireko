package sumireko.patches.occult;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import javassist.*;
import org.clapper.util.classutil.*;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static sumireko.patches.occult.OccultFields.notEnoughEnergy;

//hooray for dynamic patching

@SpirePatch(
        clz = CardCrawlGame.class,
        method = SpirePatch.CONSTRUCTOR
)
public class OccultPatch {
    public static void Raw(CtBehavior ctBehavior) throws NotFoundException {
        System.out.println("Starting dynamic Occult patch:");

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

        // Get all classes for AbstractCards
        ClassFilter filter = new AndClassFilter(
                new NotClassFilter(new InterfaceOnlyClassFilter()),
                new ClassModifiersClassFilter(Modifier.PUBLIC),
                new OrClassFilter(
                        new org.clapper.util.classutil.SubclassClassFilter(AbstractCard.class),
                        (classInfo, classFinder) -> classInfo.getClassName().equals(AbstractCard.class.getName())
                )
        );

        ArrayList<ClassInfo> foundClasses = new ArrayList<>();
        finder.findClasses(foundClasses, filter);

        System.out.println("\t- Done Finding Classes.\n\t- Patching:");

        for (ClassInfo classInfo : foundClasses)
        {
            CtClass ctClass = ctBehavior.getDeclaringClass().getClassPool().get(classInfo.getClassName());

            System.out.println("\t\t- Patching Class: " + ctClass.getSimpleName());
            try
            {
                CtMethod[] methods = ctClass.getDeclaredMethods();
                for (CtMethod m : methods)
                {
                    if (m.getName().equals("canUse"))
                    {
                        System.out.println("\t\t\t- Modifying Method: canUse");
                        m.insertAfter("{" +
                                "$_ = sumireko.patches.occult.OccultPatch.checkUsability($0, $_);" +
                                "}");
                    }
                    else if (m.getName().equals("hasEnoughEnergy"))
                    {
                        System.out.println("\t\t\t- Modifying Method: hasEnoughEnergy");
                        m.insertAfter("{" +
                                "$_ = sumireko.patches.occult.OccultPatch.checkEnergy($0, $_);" +
                                "}");
                    }
                }
                System.out.println("\t\t\tSuccess.\n");
            } catch(CannotCompileException e) {
                System.out.println("\t\t\tFailure.\n");
                e.printStackTrace();
            }
        }
        System.out.println("\t- Done Patching.");
    }

    public static boolean checkUsability(AbstractCard c, boolean normallyUsable)
    {
        if (OccultFields.isOccult.get(c))
        {
            OccultFields.isOccultPlayable.set(c, !normallyUsable || notEnoughEnergy.get(c));
            return true;
        }
        return normallyUsable;
    }
    public static boolean checkEnergy(AbstractCard c, boolean normallyEnoughEnergy)
    {
        if (OccultFields.isOccult.get(c))
        {
            notEnoughEnergy.set(c, !normallyEnoughEnergy);
            return true;
        }
        return normallyEnoughEnergy;
    }
}
