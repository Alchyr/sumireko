package sumireko.patches.sleeved;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import sumireko.interfaces.SleevedCard;

import java.util.ArrayList;
import java.util.List;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "damage",
        paramtypez = { DamageInfo.class }
)
public class OnAttacked {
    @SpireInsertPatch(
            locator = Locator.class
    ) //patches into existing iteration over draw pile
    public static void Hook(AbstractPlayer __instance, DamageInfo info) {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
            if (c instanceof SleevedCard)
                ((SleevedCard) c).sleevedOnAttacked(info);
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
            List<Matcher> pre = new ArrayList<>();
            pre.add(finalMatcher);
            pre.add(finalMatcher);
            pre.add(finalMatcher);
            return LineFinder.findInOrder(ctMethodToPatch, pre, finalMatcher);
        }
    }
}
