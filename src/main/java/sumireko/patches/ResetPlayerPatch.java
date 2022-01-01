package sumireko.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sumireko.SealSystem;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "resetPlayer"
)
public class ResetPlayerPatch {
    @SpirePostfixPatch
    public static void resetThings() {
        SealSystem.reset();
        BlockPreview.amt = 0;
    }
}
