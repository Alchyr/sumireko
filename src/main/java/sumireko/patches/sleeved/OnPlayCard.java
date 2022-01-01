package sumireko.patches.sleeved;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import javassist.CtBehavior;
import sumireko.interfaces.SleevedCard;

import java.util.ArrayList;
import java.util.List;

@SpirePatch(
        clz = UseCardAction.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = { AbstractCard.class, AbstractCreature.class }
)
public class OnPlayCard {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = { "c" }
    ) //patches into existing iteration over draw pile
    public static void Hook(UseCardAction __instance, AbstractCard card, AbstractCreature target, AbstractCard c) {
        if (c instanceof SleevedCard) {
            ((SleevedCard) c).sleevedPlayCard(__instance, card, target);
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "triggerOnCardPlayed");
            List<Matcher> pre = new ArrayList<>();
            pre.add(finalMatcher);
            pre.add(finalMatcher);
            return LineFinder.findInOrder(ctMethodToPatch, pre, finalMatcher);
        }
    }
}
