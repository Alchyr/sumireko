package sumireko.patches.occult;

import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.*;
import org.clapper.util.classutil.*;

import java.lang.reflect.Modifier;
import java.util.ArrayList;

import static sumireko.patches.occult.OccultFields.notEnoughEnergy;

//hooray for dynamic patching
public class OccultPatch {
    public static void patch(ClassFinder finder, ClassPool pool) throws NotFoundException {
        System.out.println("- Occult patch:");

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

        System.out.println("\t- Potential targets found.\n\t- Patching:");

        int skipped = 0, patched = 0;

        for (ClassInfo classInfo : foundClasses)
        {
            CtClass ctClass = pool.get(classInfo.getClassName());

            boolean changed = false;

            try
            {
                CtMethod[] methods = ctClass.getDeclaredMethods();
                for (CtMethod m : methods)
                {
                    if (m.getName().equals("canUse"))
                    {
                        //System.out.println("\t\t\t- Modifying Method: canUse");
                        m.insertAfter("{" +
                                "$_ = sumireko.patches.occult.OccultPatch.checkUsability($0, $_);" +
                                "}");

                        changed = true;
                    }
                    else if (m.getName().equals("hasEnoughEnergy"))
                    {
                        //System.out.println("\t\t\t- Modifying Method: hasEnoughEnergy");
                        m.insertAfter("{" +
                                "$_ = sumireko.patches.occult.OccultPatch.checkEnergy($0, $_);" +
                                "}");

                        changed = true;
                    }
                }

                if (changed)
                {
                    System.out.println("\t\t- Class patched: " + ctClass.getSimpleName());
                    ++patched;
                }
                else
                {
                    ++skipped;
                }
            } catch(CannotCompileException e) {
                System.out.println("\t\t- Error occurred while patching class: " + ctClass.getSimpleName() + "\n");
                e.printStackTrace();
            }
        }
        System.out.println("- Occult patch complete. " + (patched + skipped) + " classes checked. " + patched + " classes changed. " + skipped + " classes unchanged.");
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
