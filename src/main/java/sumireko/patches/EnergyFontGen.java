package sumireko.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CtBehavior;

import java.lang.reflect.Method;

@SpirePatch(
        clz = FontHelper.class,
        method = "initialize"
)
public class EnergyFontGen {
    public static BitmapFont sumirekoEnergyFont = null;

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {
                    "param"
            }
    )
    public static void generateGoldFont(FreeTypeFontGenerator.FreeTypeFontParameter param)
    {
        //prep font using reflection hacks to invoke method
        Color previousBorderColor = param.borderColor;

        try
        {
            Method m = FontHelper.class.getDeclaredMethod("prepFont", float.class, boolean.class);

            m.setAccessible(true);

            param.borderColor = new Color(0.4F, 0.1F, 0.72F, 1.0F);
            sumirekoEnergyFont = (BitmapFont) m.invoke(null,36.0F, true);
        }
        catch (Exception e)
        {
            //failed to generate font
            System.out.println(e.getMessage());
        }

        param.borderColor = previousBorderColor;
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(FreeTypeFontGenerator.FreeTypeFontParameter.class, "borderColor");
            return new int[] { LineFinder.findAllInOrder(ctBehavior, finalMatcher)[12] };
        }
    }
}
