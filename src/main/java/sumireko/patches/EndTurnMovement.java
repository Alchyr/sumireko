package sumireko.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import sumireko.SealSystem;

@SpirePatch(
        clz = AbstractRoom.class,
        method = "endTurn"
)
public class EndTurnMovement {
    @SpireInsertPatch(
            rloc = 6
    )
    //right after AbstractDungeon.actionManager.addToBottom(new DiscardAtEndOfTurnAction());
    public static void seals(AbstractRoom __instance) {
        SealSystem.triggerEndOfTurnMovement();
    }
}
