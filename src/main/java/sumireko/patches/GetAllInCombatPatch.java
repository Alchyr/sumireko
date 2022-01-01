package sumireko.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import sumireko.SealSystem;

import java.util.HashSet;
import java.util.UUID;

@SpirePatch(
        clz = GetAllInBattleInstances.class,
        method = "get"
)
public class GetAllInCombatPatch {
    @SpirePostfixPatch
    public static HashSet<AbstractCard> sealCheck(HashSet<AbstractCard> __result, UUID uuid) {
        for (int i = 0; i < 4; ++i) {
            if (SealSystem.aroundCards[i] == null)
                break;

            if (SealSystem.aroundCards[i].uuid.equals(uuid)) {
                __result.add(SealSystem.aroundCards[i]);
            }
        }

        if (SealSystem.centerCard != null && SealSystem.centerCard.uuid.equals(uuid))
            __result.add(SealSystem.centerCard);

        return __result;
    }
}
