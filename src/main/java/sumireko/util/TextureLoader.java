package sumireko.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import sumireko.SumirekoMod;

import java.io.FileNotFoundException;
import java.util.HashMap;

import static sumireko.SumirekoMod.*;

public class TextureLoader {
    private static final HashMap<String, Texture> textures = new HashMap<>();

    /**
     * @param filePath - String path to the texture you want to load relative to resources,
     * Example: makeImagePath("missing.png")
     * @return <b>com.badlogic.gdx.graphics.Texture</b> - The texture from the path provided
     */
    public static Texture getTexture(final String filePath) {
        return getTexture(filePath, true);
    }
    public static Texture getTexture(final String filePath, boolean linear) {
        if (textures.get(filePath) == null) {
            try {
                loadTexture(filePath, linear);
            } catch (GdxRuntimeException e) {
                try
                {
                    return getTexture(makeImagePath("missing.png"));
                }
                catch (GdxRuntimeException ex) {
                    SumirekoMod.logger.info("missing.png is missing!");
                    return null;
                }
            }
        }
        return textures.get(filePath);
    }
    public static Texture getTextureNull(final String textureString) {
        if (textures.get(textureString) == null) {
            try {
                loadTexture(textureString);
            } catch (GdxRuntimeException e) {
                return null;
            }
        }
        return textures.get(textureString);
    }

    public static String getCardTextureString(final String cardName, final AbstractCard.CardType cardType)
    {
        String textureString;

        switch (cardType)
        {
            case ATTACK:
                textureString = makeImagePath("cards/attacks/" + cardName + ".png");
                break;
            case SKILL:
                textureString = makeImagePath("cards/skills/" + cardName + ".png");
                break;
            case POWER:
                textureString = makeImagePath("cards/powers/" + cardName + ".png");
                break;
            default:
                textureString = makeImagePath("missing.png");
                break;
        }

        FileHandle h = Gdx.files.internal(textureString);
        if (!h.exists())
        {
            switch (cardType) {
                case ATTACK:
                    textureString = makeImagePath("cards/attacks/default.png");
                    break;
                case SKILL:
                    textureString = makeImagePath("cards/skills/default.png");
                    break;
                case POWER:
                    textureString = makeImagePath("cards/powers/default.png");
                    break;
                default:
                    textureString = makeImagePath("missing.png");
                    break;
            }
        }

        return textureString;
    }

    private static void loadTexture(final String textureString) throws GdxRuntimeException {
        loadTexture(textureString, false);
    }

    private static void loadTexture(final String textureString, boolean linearFilter) throws GdxRuntimeException {
        Texture texture = new Texture(textureString);
        if (linearFilter)
        {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        else
        {
            texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }
        logger.info("Loaded texture " + textureString);
        textures.put(textureString, texture);
    }

    public static Texture getPowerTexture(final String powerName)
    {
        String textureString = makePowerPath(powerName + ".png");
        return getTexture(textureString);
    }
    public static Texture getHiDefPowerTexture(final String powerName)
    {
        String textureString = makeLargePowerPath(powerName + ".png");
        return getTextureNull(textureString);
    }
}