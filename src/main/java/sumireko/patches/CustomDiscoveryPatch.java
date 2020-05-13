package sumireko.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class CustomDiscoveryPatch {
    @SpirePatch(
            clz = DiscoveryAction.class,
            method = SpirePatch.CLASS
    )
    public static class Fields
    {
        public static SpireField<ArrayList<AbstractCard>> customDiscoveryCards = new SpireField<>(()->null);
    }

    @SpirePatch(
            clz = DiscoveryAction.class,
            method = "generateCardChoices"
    )
    public static class UseCustomDiscoveryCards
    {
        @SpirePrefixPatch
        public static SpireReturn<ArrayList<AbstractCard>> useAlt(DiscoveryAction __instance, AbstractCard.CardType type)
        {
            if (Fields.customDiscoveryCards.get(__instance) != null)
            {
                return SpireReturn.Return(Fields.customDiscoveryCards.get(__instance));
            }
            return SpireReturn.Continue();
        }
    }
}
