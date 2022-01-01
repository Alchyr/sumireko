package sumireko.character;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import sumireko.cards.basic.*;
import sumireko.enums.CharacterEnums;
import sumireko.patches.EnergyFontGen;
import sumireko.relics.OccultBall;
import sumireko.util.AnimationData;
import sumireko.util.SpriteAnimation;
import sumireko.util.TextureLoader;

import java.util.ArrayList;
import java.util.HashMap;

import static sumireko.SumirekoMod.*;

public class Sumireko extends CustomPlayer {
    //stats
    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 70;
    public static final int MAX_HP = 70;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    //strings
    private static final String ID = makeID("Sumireko");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    private static final String SHOULDER_1 = makeCharacterPath("shoulder.png");
    private static final String SHOULDER_2 = makeCharacterPath("shoulder2.png");
    private static final String CORPSE = makeCharacterPath("corpse.png");
    private static final String IMG = makeCharacterPath("sumireko.png");
    private static final String IMG_BACK = makeCharacterPath("color.png");


    private static final String[] frames = {
            makeCharacterPath("stand/stand0000.png"),
            makeCharacterPath("stand/stand0001.png"),
            makeCharacterPath("stand/stand0002.png"),
            makeCharacterPath("stand/stand0003.png"),
            makeCharacterPath("stand/stand0004.png"),
            makeCharacterPath("stand/stand0005.png"),
            makeCharacterPath("stand/stand0006.png"),
            makeCharacterPath("stand/stand0007.png"),
            makeCharacterPath("stand/stand0008.png"),
            makeCharacterPath("stand/stand0009.png"),
            makeCharacterPath("stand/stand0010.png"),
            makeCharacterPath("stand/stand0011.png"),
            makeCharacterPath("stand/stand0012.png"),
            makeCharacterPath("stand/stand0013.png"),
            makeCharacterPath("stand/stand0014.png"),
            makeCharacterPath("stand/stand0015.png"),
            makeCharacterPath("stand/stand0016.png"),
            makeCharacterPath("stand/stand0017.png"),
            makeCharacterPath("stand/stand0018.png"),
            makeCharacterPath("stand/stand0019.png"),
            makeCharacterPath("stand/stand0007.png"),
            makeCharacterPath("stand/stand0008.png"),
            makeCharacterPath("stand/stand0009.png"),
            makeCharacterPath("stand/stand0010.png"),
            makeCharacterPath("stand/stand0011.png"),
            makeCharacterPath("stand/stand0012.png"),
            makeCharacterPath("stand/stand0013.png")};

    public static final String[] orbTextures = {
            makeOrbPath("layer1.png"),
            makeOrbPath("layer2.png"),
            makeOrbPath("layer3.png"),
            makeOrbPath("layer4.png"),
            makeOrbPath("layer5.png"),
            makeOrbPath("layer6.png"),
            makeOrbPath("layer1d.png"),
            makeOrbPath("layer2d.png"),
            makeOrbPath("layer3d.png"),
            makeOrbPath("layer4d.png"),
            makeOrbPath("layer5d.png")};

    //private static String ATLAS = makeCharacterPath("sumirenko.atlas");
    //private static String JSON = makeCharacterPath("sumirenko.json");

    private static final String orbVfx = makeOrbPath("vfx.png");

    private HashMap<SpineAnimation, AnimationData> animationMap = new HashMap<>();

    //private static SpineAnimation stand = new SpineAnimation(SKELETON_ATLAS, STAND_JSON, 2.0f);
    //private static SpineAnimation attack = new SpineAnimation(SKELETON_ATLAS, ATTACK_JSON, 2.0f);

    public Sumireko()
    {
        super(NAMES[0], CharacterEnums.SUMIREKO, null, null, null, new SpriteAnimation(0.06f, frames, IMG_BACK));

        initializeClass(null,
                SHOULDER_1,
                SHOULDER_2,
                CORPSE,
                getLoadout(),
                20.0F, -20.0F, 200.0F, 250.0F,
                new EnergyManager(ENERGY_PER_TURN));

        //loadAnimation(ATLAS, JSON, 1.0f);

        //this.stateData.setMix("idle", "attackUp", 0.2f);
        //this.stateData.setMix("attackUp", "idle", 0.4f);

        //this.state.setAnimation(0, "idle", true);

        this.img = TextureLoader.getTexture(IMG);

        /*changeAnimation(stand, "stand");

        //load attack animation ahead of time
        loadAnimation(attack, "attack"); //if loop is false it be dumb*/

        dialogX = (drawX + 0.0F * Settings.scale); //location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale);
    }

    /*@Override
    protected void updateFastAttackAnimation() {
        if (animation.equals(attack))
        {
            if (animationMap.get(attack).trackEntry.isComplete())
            {
                changeAnimation(stand, "stand");
                this.animationTimer = 0.0f;
            }
        }
        else
        {
            changeAnimation(stand, "stand");
            this.animationTimer = 0.0f;
        }
    }*/


    /*@Override
    public void useFastAttackAnimation() {
        super.useFastAttackAnimation();
        changeAnimation(attack, "attack");
    }*/

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(StrikeSeal.ID);
        retVal.add(StrikeSeal.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(BarrierSeal.ID);
        retVal.add(Liberator.ID);

        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(OccultBall.ID);
        UnlockTracker.markRelicAsSeen(OccultBall.ID);

        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA(getCustomModeCharacterButtonSoundKey(), 1.25f);
        CardCrawlGame.sound.playA("ATTACK_MAGIC_SLOW_1", -0.2f);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return MathUtils.randomBoolean() ? "ATTACK_MAGIC_FAST_1" : "ATTACK_MAGIC_FAST_2";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return CharacterEnums.SUMIREKO_CARD_COLOR;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return EnergyFontGen.sumirekoEnergyFont;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }
    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new BarrierSeal();
    }

    @Override
    public Color getCardTrailColor() {
        return SUMIREKO_COLOR.cpy();
    }

    @Override
    public Color getCardRenderColor() {
        return SUMIREKO_COLOR.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return SUMIREKO_COLOR.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.FIRE
        };
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Sumireko();
    }


    private static boolean isDisposed(TextureAtlas atlas)
    {
        for (Texture t : atlas.getTextures())
        {
            if (t.getTextureObjectHandle() == 0)
                return true;
        }
        return false;
    }
}
