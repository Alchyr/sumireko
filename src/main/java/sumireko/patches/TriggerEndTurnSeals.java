package sumireko.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import sumireko.SealSystem;

@SpirePatch(
        clz = GameActionManager.class,
        method = "callEndOfTurnActions"
)
public class TriggerEndTurnSeals {
    @SpirePrefixPatch
    public static void SEALS(GameActionManager __instance)
    {
        SealSystem.triggerEndOfTurn();
    }
}
