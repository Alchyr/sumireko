package sumireko.patches;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import org.clapper.util.classutil.*;
import sumireko.SumirekoMod;
import sumireko.actions.cards.WakeUpAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//Goal: Anywhere the player's hand is referenced, replace it with a switch between returning the hand or the draw pile based on isDreaming.
@SpirePatch(
        clz = AbstractPlayer.class,
        method = SpirePatch.CLASS
)
public class DeepDreamPatch {
    public static SpireField<Boolean> isDreaming = new SpireField<>(()->false);
    public static SpireField<Integer> originalHandSize = new SpireField<>(()-> BaseMod.MAX_HAND_SIZE);


    public static void startDream() {
        if (!isDreaming(AbstractDungeon.player))
        {
            SumirekoMod.logger.info("Beginning to dream. Original max hand size is " + BaseMod.MAX_HAND_SIZE);
            DeepDreamPatch.originalHandSize.set(AbstractDungeon.player, BaseMod.MAX_HAND_SIZE);
            DeepDreamPatch.isDreaming.set(AbstractDungeon.player, true);
            BaseMod.MAX_HAND_SIZE = Integer.MAX_VALUE;
        }
    }
    public static void wakeUp()
    {
        if (AbstractDungeon.player != null && isDreaming(AbstractDungeon.player))
        {
            isDreaming.set(AbstractDungeon.player, false);
            BaseMod.MAX_HAND_SIZE = originalHandSize.get(AbstractDungeon.player);
            SumirekoMod.logger.info("Waking up. Max hand size reset to " + BaseMod.MAX_HAND_SIZE);

            if (!AbstractDungeon.overlayMenu.combatPanelsShown) //if reset occurs at end of combat
            {
                for (AbstractCard c : AbstractDungeon.player.hand.group)
                {
                    c.target_y = -AbstractCard.IMG_HEIGHT;
                }
            }
        }
    }

    public static boolean isDreaming(AbstractPlayer p)
    {
        return isDreaming.get(p);
    }

    public static void triggerEndOfTurn() {
        if (isDreaming.get(AbstractDungeon.player)) {
            AbstractDungeon.actionManager.addToBottom(new WakeUpAction());
        }
    }

    public static void patch(ClassFinder finder, ClassPool pool) throws NotFoundException {
        System.out.println("- Deep Dream patch:");

        // Get ALL classes.
        ClassFilter filter = new AndClassFilter(
                new NotClassFilter(new InterfaceOnlyClassFilter()),
                new GamePackageFilter() //avoids about 4000 classes
        );

        ArrayList<ClassInfo> foundClasses = new ArrayList<>();
        finder.findClasses(foundClasses, filter);

        System.out.println("\t- Potential targets found.\n\t- Patching:");

        int skipped = 0, patched = 0;
        HandAccessEditor exprEditor = new HandAccessEditor();

        for (ClassInfo classInfo : foundClasses)
        {
            CtClass ctClass = pool.get(classInfo.getClassName());

            exprEditor.changed = false;

            try
            {
                CtMethod[] methods = ctClass.getDeclaredMethods();

                for (CtMethod m : methods)
                {
                    m.instrument(exprEditor);
                }

                if (exprEditor.changed)
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
        System.out.println("- Deep Dream patch complete. " + (patched + skipped) + " classes checked. " + patched + " classes changed. " + skipped + " classes unchanged.");
    }

    private static class HandAccessEditor extends ExprEditor {
        public boolean changed = false;

        @Override
        public void edit(FieldAccess f) throws CannotCompileException {
            if (f.getClassName().equals(AbstractPlayer.class.getName()) && f.getFieldName().equals("hand"))
            {
                if (f.isReader())
                {
                    f.replace("{" +
                                "if (" + DeepDreamPatch.class.getName() + ".isDreaming($0)) {" +
                                    "$_ = $0.drawPile;" +
                                "} else {" +
                                    "$_ = $proceed($$);" +
                                "}" +
                            "}");
                }
                else if (f.isWriter())
                {
                    f.replace("{" +
                                "if (" + DeepDreamPatch.class.getName() + ".isDreaming($0)) {" +
                                    "$0.drawPile = $1;" +
                                "} else {" +
                                    "$proceed($$);" +
                                "}" +
                            "}");
                }
                changed = true;
            }
        }
    }

    private static class GamePackageFilter implements ClassFilter {
        private static final Set<String> rejectedPackages;
        static {
            rejectedPackages = new HashSet<>();
            rejectedPackages.add("com.badlogic");
            rejectedPackages.add("com.esotericsoftware");
            rejectedPackages.add("com.fasterxml");
            rejectedPackages.add("com.gikk");
            rejectedPackages.add("com.google");
            rejectedPackages.add("com.jcraft");
            rejectedPackages.add("com.sun");
            rejectedPackages.add("de.robojumper");
            rejectedPackages.add("io.sentry");
            rejectedPackages.add("javazoom.jl");
            rejectedPackages.add("net.arikia");
            rejectedPackages.add("net.java");
            rejectedPackages.add("org.apache");
            rejectedPackages.add("org.lwjgl");
            rejectedPackages.add("org.slf4j");
        }

        public boolean accept(ClassInfo classInfo, ClassFinder classFinder) {
            String name = classInfo.getClassName();
            int secondPackage = name.indexOf('.');
            if (secondPackage >= 0)
            {
                secondPackage = name.indexOf('.', secondPackage + 1);

                if (secondPackage > 0)
                {
                    name = name.substring(0, secondPackage);

                    return !rejectedPackages.contains(name);
                }
            }

            return true;
        }
    }

    /*private static class SpecialExclusionFilter implements ClassFilter {
        private static final Set<String> rejectedClasses;
        static {
            rejectedClasses = new HashSet<>();
            rejectedClasses.add(DeepDreamAction.class.getName());
        }

        public boolean accept(ClassInfo classInfo, ClassFinder classFinder) {
            return !rejectedClasses.contains(classInfo.getClassName());
        }
    }*/
}
