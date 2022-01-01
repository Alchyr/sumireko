/*package sumireko.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.powers.OldEsotericSecretsPower;

@SpirePatch(
        clz = CardGroup.class,
        method = "refreshHandLayout"
)
public class EsotericSecretsUpdatePatch {
    @SpirePostfixPatch
    public static void update(CardGroup __instance)
    {
        AbstractPower p = AbstractDungeon.player.getPower(OldEsotericSecretsPower.POWER_ID);

        if (p != null)
            p.onSpecificTrigger();
    }
}
*/