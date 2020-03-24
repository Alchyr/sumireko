package sumireko.patches.occult;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import sumireko.SumirekoMod;

import java.lang.reflect.Field;

import static sumireko.SumirekoMod.SUMIREKO_COLOR;

@SpirePatch(
        clz = CardGlowBorder.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = { AbstractCard.class, Color.class }
)
public class OccultGlow {
    private static Field colorField;

    static {
        try {
            colorField = AbstractGameEffect.class.getDeclaredField("color");
            colorField.setAccessible(true);
        }
        catch (Exception e) {
            SumirekoMod.logger.error("Failed to initialize a Field for color patch.", e);
        }
    }

    @SpirePostfixPatch
    public static void PostFix(CardGlowBorder __instance, AbstractCard c) throws IllegalAccessException {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (OccultFields.isOccultPlayable.get(c)) {
                colorField.set(__instance, SUMIREKO_COLOR.cpy());
            }
        }
    }
}
