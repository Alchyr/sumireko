package sumireko.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import sumireko.SealSystem;

@SpirePatch(
        clz = GameActionManager.class,
        method = "callEndOfTurnActions"
)
public class TriggerEndTurnEffects {
    @SpirePrefixPatch
    public static void SEALS(GameActionManager __instance)
    {
        SealSystem.triggerEndOfTurn();
    }

    @SpirePostfixPatch
    public static void DREAMS(GameActionManager __instance) { DeepDreamPatch.triggerEndOfTurn(); }
}
