package sumireko.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import sumireko.SealSystem;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "render"
)
public class SealRendering {
    @SpirePostfixPatch
    public static void render(AbstractPlayer __instance, SpriteBatch sb)
    {
        SealSystem.render(sb);
    }
}
