package sumireko.util;

import com.blanktheevil.blockreminder.patches.BlockPreviewFieldPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sumireko.patches.BlockPreview;

public class BlockReminderAdjust {
    public static void adjust() {
        BlockPreviewFieldPatch.blockPreview.set(AbstractDungeon.player, BlockPreviewFieldPatch.blockPreview.get(AbstractDungeon.player) + BlockPreview.amt);
    }
}
