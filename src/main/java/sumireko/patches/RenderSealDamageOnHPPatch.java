package sumireko.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.patches.powerInterfaces.HealthBarRenderPowerPatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import javassist.CtBehavior;
import sumireko.SealSystem;
import sumireko.util.HealthBarRender;

@SpirePatch(
        clz = HealthBarRenderPowerPatch.RenderPowerHealthBar.class,
        method = "Insert"
)
public class RenderSealDamageOnHPPatch {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = { "prevPowerAmtSum" }
    )
    public static void RenderThisToo(AbstractCreature __instance, SpriteBatch sb, float x, float y,
                                     float targetHealthBarWidth, float HEALTH_BAR_HEIGHT, float HEALTH_BAR_OFFSET_Y, @ByRef int[] prevPowerAmtSum)
    {
        if (SealSystem.healthBarRenders.containsKey(__instance))
        {
            HealthBarRender h = SealSystem.healthBarRenders.get(__instance);

            if (h != null)
            {
                if (!h.locked) {
                    h.locked = true;
                    h.resultHp = __instance.currentHealth - (prevPowerAmtSum[0] + h.amount);
                }
                h.amount = __instance.currentHealth - (prevPowerAmtSum[0] + h.resultHp);

                sb.setColor(h.c);

                //renders the entire hp bar in modified color.
                //Other colors are simply rendered on top with reduced length.
                if (__instance.currentHealth > prevPowerAmtSum[0]) {
                    float w = 1.0F - (__instance.currentHealth - prevPowerAmtSum[0]) / (float)__instance.currentHealth;
                    w *= targetHealthBarWidth;
                    if (__instance.currentHealth > 0) {
                        sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
                    }
                    sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, targetHealthBarWidth - w, HEALTH_BAR_HEIGHT);
                    sb.draw(ImageMaster.HEALTH_BAR_R, x + targetHealthBarWidth - w, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
                }

                prevPowerAmtSum[0] += h.amount;

                //h = h.sub;
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "powers");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }
}
