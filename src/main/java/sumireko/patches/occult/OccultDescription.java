package sumireko.patches.occult;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        clz = AbstractCard.class,
        method = "initializeDescription"
)
public class OccultDescription {
    public static String occultKeyword = "?????????? NL ";
    @SpirePrefixPatch
    public static void betterHaveTheKeyword(AbstractCard __instance)
    {
        if (OccultFields.isOccult.get(__instance) && !__instance.rawDescription.contains(occultKeyword))
        {
            __instance.rawDescription = occultKeyword + __instance.rawDescription;
        }
    }
}
