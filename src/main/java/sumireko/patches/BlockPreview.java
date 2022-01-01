package sumireko.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;
import sumireko.util.BlockReminderAdjust;

//hi blonk
public class BlockPreview {
    public static boolean adjustReminder = false;

    public static int amt = 0;

    @SpirePatch(
            clz = AbstractRoom.class,
            method = "update"
    )
    public static class Update {
        //This occurs almost immediately after player's update method, which is where block preview patches update.
        @SpirePostfixPatch
        public static void update(AbstractRoom __instance) {
            if (adjustReminder) {
                BlockReminderAdjust.adjust();
            }
        }
    }

    @SpirePatch(
            clz = AbstractCreature.class,
            method = "renderHealth"
    )
    public static class Render {
        private static final int BLOCK_W = 64;
        private static final float VERT_OFFSET = (BLOCK_W / 2.0f) * Settings.scale;
        private static final float BLOCK_ICON_ADJUST = -14 * Settings.scale;
        private static final Color PREVIEW_COLOR = Color.CYAN.cpy();
        static {
            PREVIEW_COLOR.a = 0.5f;
        }

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {
                        "x",
                        "y",
                        "blockOffset"
                }
        )
        public static void render(AbstractCreature __instance, SpriteBatch sb, float x, float y, float blockOffset) {
            if (!adjustReminder && amt > 0 && __instance == AbstractDungeon.player && AbstractDungeon.overlayMenu.endTurnButton.enabled) {
                float yOffset = __instance.currentBlock > 0 ? VERT_OFFSET : 0;

                sb.setColor(PREVIEW_COLOR);
                sb.draw(
                        ImageMaster.BLOCK_ICON,
                        x + BLOCK_ICON_ADJUST - BLOCK_W / 2f,
                        (y + BLOCK_ICON_ADJUST - BLOCK_W / 2f) + blockOffset + yOffset,
                        BLOCK_W / 2f,
                        BLOCK_W / 2f,
                        BLOCK_W,
                        BLOCK_W,
                        Settings.scale,
                        Settings.scale,
                        0f,
                        0,
                        0,
                        BLOCK_W,
                        BLOCK_W,
                        false,
                        false);
                FontHelper.renderFontCentered(
                        sb,
                        FontHelper.blockInfoFont,
                        "+" + amt,
                        x + BLOCK_ICON_ADJUST,
                        (y - 16f * Settings.scale) + yOffset,
                        Color.WHITE.cpy(),
                        1f);
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "currentBlock");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
