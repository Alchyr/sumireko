package sumireko.patches;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = GameActionManager.class,
        method = "getNextAction"
)
public class PsychometryTrackerPatch {
    public static final ArrayList<AbstractCard> cardsPlayedLastTurn = new ArrayList<>();

    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void saveThem(GameActionManager __instance)
    {
        cardsPlayedLastTurn.clear();
        cardsPlayedLastTurn.addAll(__instance.cardsPlayedThisTurn);
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "applyStartOfTurnRelics");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }
}
