package sumireko.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import sumireko.SealSystem;


@SpirePatch(
        clz = ApotheosisAction.class,
        method = "update"
)
public class SealApotheosisPatch {
    @SpireInsertPatch(
            rloc = 5
    )
    public static void upgradeSeals(ApotheosisAction __instance) {

    }

    private void upgradeAllSeals() {
        for (AbstractCard c : SealSystem.aroundCards) {
            if (c.canUpgrade()) {
                c.superFlash();
                c.upgrade();
            }
        }
        if (SealSystem.centerCard != null && SealSystem.centerCard.canUpgrade()) {
            SealSystem.centerCard.superFlash();
            SealSystem.centerCard.upgrade();
        }

        SealSystem.calculateSeals();
    }
}
