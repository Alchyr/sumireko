package sumireko.abstracts;

import basemod.abstracts.CustomRelic;
import sumireko.util.TextureLoader;

import static sumireko.SumirekoMod.makeRelicPath;

public abstract class BaseRelic extends CustomRelic {
    public BaseRelic(String id, String textureName, RelicTier tier, LandingSound sfx) {
        super(id, TextureLoader.getTexture(makeRelicPath(textureName + ".png")), tier, sfx);
        outlineImg = TextureLoader.getTextureNull(makeRelicPath(textureName + "Outline.png"));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}