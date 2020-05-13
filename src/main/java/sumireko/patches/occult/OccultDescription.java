package sumireko.patches.occult;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import sumireko.util.KeywordWithProper;

@SpirePatch(
        clz = AbstractCard.class,
        method = "initializeDescription"
)
public class OccultDescription {
    @SpirePrefixPatch
    public static void betterHaveTheKeyword(AbstractCard __instance)
    {
        if (OccultFields.isOccult.get(__instance) && !__instance.rawDescription.contains(KeywordWithProper.occult))
        {
            __instance.rawDescription = KeywordWithProper.occult + __instance.rawDescription;
        }
    }
}
