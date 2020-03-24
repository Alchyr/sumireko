package sumireko.abstracts;

import basemod.abstracts.CustomRelic;
import sumireko.util.TextureLoader;

import static sumireko.SumirekoMod.makeRelicPath;

public abstract class BaseRelic extends CustomRelic {
    public BaseRelic(String setId, String textureID, RelicTier tier, LandingSound sfx) {
        super(setId, TextureLoader.getTexture(makeRelicPath(textureID + ".png")), tier, sfx);
        outlineImg = TextureLoader.getTexture(makeRelicPath(textureID + "Outline.png"));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}