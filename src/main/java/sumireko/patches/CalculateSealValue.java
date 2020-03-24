package sumireko.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import sumireko.SealSystem;

@SpirePatch(
        clz = CardGroup.class,
        method = "applyPowers"
)
public class CalculateSealValue {
    @SpirePostfixPatch
    public static void apply(CardGroup __instance)
    {
        SealSystem.calculateSeals();
    }
}
