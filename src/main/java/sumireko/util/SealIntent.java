package sumireko.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BobEffect;
import sumireko.abstracts.SealCard;
import sumireko.effects.*;

import java.util.ArrayList;
import java.util.Iterator;

public class SealIntent {
    public SealCard current;
    public float x, y;


    //public AbstractMonster.Intent intent;
    ///alright fuck fixed intents i'm doing something else
    private int intent;
    public static final int ATTACK =            0b00000001;
    public static final int DEFEND =            0b00000010;
    public static final int BUFF =              0b00000100;
    public static final int DEFEND_BUFF =       0b00000110;
    public static final int DEBUFF =            0b00001000;
    public static final int STRONG_DEBUFF =     0b00010000;
    public static final int ANY_DEBUFF =        0b00011000;
    public static final int MAGIC =             0b00100000;
    public boolean intentActive(int test) {
        return (intent & test) > 0;
    }
    public boolean fullyActive(int test) {
        return (intent & test) == test;
    }
    public void addIntent(int add) {
        intent |= add;
    }


    //TEXT:
    /*
        bottom number will be damage.
        Top number will be a non-damage effect number.
        Block is not displayed numerically as an intent, with a block preview instead.

        Any damage dealing uses `addAttackEffect` when combined with something else, because attack+defend intend looks gross.
     */

    //For damage:
    public int baseDamage;
    public int addedDamage;
    public boolean multihit;
    public int numHits;

    //For non-damage
    private boolean bonusEffect;
    private String bonusText;

    private final BobEffect bobEffect;
    private float debuffParticleTimer;
    private float buffParticleTimer;
    private float intentAngle;

    private final ArrayList<AbstractGameEffect> intentVfx = new ArrayList<>();

    private final Color intentColor;
    private final Color nonDamageColor;

    public void refresh()
    {
        current = null;
        resetValues();

        intentVfx.clear();

        debuffParticleTimer = 0.2f;
        buffParticleTimer = 0.1f;
        intentColor.a = 0.0f;
    }
    private void resetValues() {
        intent = 0;
        bonusEffect = false;

        baseDamage = -1;
        addedDamage = 0;
        bonusText = "";
        multihit = false;
        numHits = -1;
    }

    public SealIntent()
    {
        intentColor = Color.WHITE.cpy();
        nonDamageColor = new Color(0.2F, 1.0F, 1.0F, 1.0f);

        this.bobEffect = new BobEffect();
        refresh();
    }

    public void update() {
        this.bobEffect.update();
        this.intentAngle += Gdx.graphics.getDeltaTime() * 150.0F;
        if (intent != 0) {
            if (intentColor.a != 1.0F) {
                intentColor.a += Gdx.graphics.getDeltaTime() * 2;
                if (intentColor.a > 1.0F) {
                    intentColor.a = 1.0F;
                }
            }

            this.updateIntentVFX();
        }

        Iterator<AbstractGameEffect> i = this.intentVfx.iterator();

        AbstractGameEffect e;
        while(i.hasNext()) {
            e = i.next();
            e.update();
            if (e.isDone) {
                i.remove();
            }
        }
    }

    public void render(SpriteBatch sb)
    {
        this.renderIntentVfxBehind(sb);
        if (this.intent != 0)
            this.renderIntent(sb);
        this.renderIntentVfxAfter(sb);
        if (this.intent != 0)
            this.renderValue(sb);
    }

    public void renderIntent(SpriteBatch sb) {
        float a = intentColor.a;
        sb.setColor(intentColor);

        if (fullyActive(DEFEND_BUFF)) {
            sb.draw(ImageMaster.INTENT_DEFEND_BUFF_L, x - 64.0F, y + this.bobEffect.y, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0, 0, 0, 128, 128, false, false);
            intentColor.a *= 0.7f;
        }
        else {
            if (intentActive(BUFF)) {
                sb.setColor(intentColor);
                sb.draw(ImageMaster.INTENT_BUFF_L, x - 64.0F, y + this.bobEffect.y, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0, 0, 0, 128, 128, false, false);
                intentColor.a *= 0.75f;
            }
            if (intentActive(DEFEND)) {
                sb.setColor(intentColor);
                sb.draw(ImageMaster.INTENT_DEFEND_L, x - 64.0F, y + this.bobEffect.y, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0, 0, 0, 128, 128, false, false);
                intentColor.a *= 0.7f;
            }
        }

        if (intentActive(STRONG_DEBUFF)) {
            sb.setColor(intentColor);
            sb.draw(ImageMaster.INTENT_DEBUFF2_L, x - 64.0F, y + this.bobEffect.y, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, intentAngle, 0, 0, 128, 128, false, false);
            intentColor.a *= 0.7f;
        }
        else if (intentActive(DEBUFF)) {
            sb.setColor(intentColor);
            sb.draw(ImageMaster.INTENT_DEBUFF_L, x - 64.0F, y + this.bobEffect.y, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, intentAngle, 0, 0, 128, 128, false, false);
            intentColor.a *= 0.7f;
        }

        if (intentActive(MAGIC)) {
            sb.setColor(intentColor);
            sb.draw(ImageMaster.INTENT_MAGIC_L, x - 64.0F, y + this.bobEffect.y, 64.0f, 64.0f, 128.0f, 128.0f, Settings.scale, Settings.scale, intentActive(ANY_DEBUFF) ? intentAngle : 0, 0, 0, 128, 128, false, false);
        }

        intentColor.a = a; //attack is always on top at full opacity
        if (intentActive(ATTACK)) {
            //spins if debuffing :)
            sb.setColor(intentColor);
            sb.draw(getAttackIntent(), x - 64.0F, y + this.bobEffect.y, 64.0f, 64.0f, 128.0f, 128.0f, Settings.scale, Settings.scale, intentActive(ANY_DEBUFF) ? intentAngle : 0, 0, 0, 128, 128, false, false);
        }
    }

    private void renderValue(SpriteBatch sb) {
        if (this.multihit) {
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, (baseDamage == -1 ? "" : Integer.toString(this.baseDamage)) + "x" + this.numHits + (addedDamage > 0 ? "+" + addedDamage : ""), x - 30.0F * Settings.scale, y + this.bobEffect.y + 64.0f - 12.0F * Settings.scale, this.intentColor);
        } else if (baseDamage != -1) {
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, this.baseDamage + (addedDamage > 0 ? "+" + addedDamage : ""), x - 30.0F * Settings.scale, y + this.bobEffect.y + 64.0f - 12.0F * Settings.scale, this.intentColor);
        }
        else if (addedDamage > 0) {
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, "+" + addedDamage, x - 30.0F * Settings.scale, y + this.bobEffect.y + 64.0f - 12.0F * Settings.scale, this.intentColor);
        }

        if (bonusEffect) {
            nonDamageColor.a = intentColor.a;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, bonusText, x + 30.0F * Settings.scale, y + this.bobEffect.y + 64.0f + 30.0F * Settings.scale, this.nonDamageColor);
        }
    }

    private void renderIntentVfxBehind(SpriteBatch sb) {
        for (AbstractGameEffect e : this.intentVfx) {
            if (e.renderBehind) {
                e.render(sb);
            }
        }
    }

    private void renderIntentVfxAfter(SpriteBatch sb) {
        for (AbstractGameEffect e : this.intentVfx) {
            if (!e.renderBehind) {
                e.render(sb);
            }
        }
    }

    private void updateIntentVFX() {
        this.debuffParticleTimer -= Gdx.graphics.getDeltaTime();
        this.buffParticleTimer -= Gdx.graphics.getDeltaTime();
        if (intentActive(ANY_DEBUFF) && debuffParticleTimer <= 0) {
            this.debuffParticleTimer = intentActive(STRONG_DEBUFF) ? 0.6F : 0.9F;
            this.intentVfx.add(new FadedDebuffParticleEffect(x, y + 64.0f));
        }
        if (intentActive(BUFF) && buffParticleTimer <= 0) {
            this.buffParticleTimer = 0.1F;
            this.intentVfx.add(new FadedBuffParticleEffect(x, y + 64.0f));
        }
        /*case UNKNOWN:
        this.intentParticleTimer = 0.5F;
        this.intentVfx.add(new FadedUnknownParticleEffect(x, y + 64.0f));
        break;
        case STUN:
        this.intentParticleTimer = 0.67F;
        this.intentVfx.add(new FadedStunStarEffect(x, y + 64.0f));
        break;*/
    }
    
    protected Texture getAttackIntent() {
        int tmp;
        if (multihit) {
            tmp = this.baseDamage * this.numHits;
        } else {
            tmp = this.baseDamage;
        }

        if (addedDamage > 0)
            tmp += addedDamage;
        
        return getAttackIntent(tmp);
    }

    protected Texture getAttackIntent(int amt) {
        if (amt < 5) {
            return ImageMaster.INTENT_ATK_1;
        } else if (amt < 10) {
            return ImageMaster.INTENT_ATK_2;
        } else if (amt < 15) {
            return ImageMaster.INTENT_ATK_3;
        } else if (amt < 20) {
            return ImageMaster.INTENT_ATK_4;
        } else if (amt < 25) {
            return ImageMaster.INTENT_ATK_5;
        } else {
            return amt < 30 ? ImageMaster.INTENT_ATK_6 : ImageMaster.INTENT_ATK_7;
        }
    }

    public void setSeal(SealCard c) {
        resetValues();
        this.current = c;
        c.getIntent(this);

        if (c != current || this.intent == 0) {
            intentColor.a = 0.0f;
        }
    }

    public void multihit(int numHits) {
        this.multihit = true;
        this.numHits = numHits;
    }
    public void baseDamage(int amt) {
        addIntent(ATTACK);
        this.baseDamage = amt;
    }
    public void baseDamage(int amt, int hits) {
        baseDamage(amt);
        multihit(hits);
    }
    public void bonusEffect(String effect) {
        bonusEffect = true;
        bonusText = effect;
    }

    public void addBlock() {//, int amt) {
        addIntent(DEFEND);
        /*if (intent == null) {
            if (current == null) {
                this.current = c;
            }
            this.intent = AbstractMonster.Intent.DEFEND;
            intentColor.a = 0;
            return;
        }
        switch (intent) {
            case ATTACK: //turns into defend intent + attack intent layered on top
                this.intent = AbstractMonster.Intent.DEFEND;
                addAttackEffect = true;
                break;
            case ATTACK_BUFF:
                this.intent = AbstractMonster.Intent.DEFEND_BUFF;
                addAttackEffect = true;
                break;
            case ATTACK_DEBUFF:
                this.intent = AbstractMonster.Intent.DEFEND_DEBUFF;
                addAttackEffect = true;
                break;
            case BUFF:
                this.intent = AbstractMonster.Intent.DEFEND_BUFF;
                break;
            case DEBUFF:
                this.intent = AbstractMonster.Intent.DEFEND_DEBUFF;
                break;
            case DEFEND:
            case DEFEND_BUFF:
            case DEFEND_DEBUFF:
                //Already a defend intent.
                break;
            case STRONG_DEBUFF:
            case ESCAPE:
            case MAGIC:
            case SLEEP:
            case STUN:
                addDefendEffect = true;
                break;
        }*/
    }

    public void addDamage(int amt) {
        addIntent(ATTACK);
        addedDamage += amt;
        /*if (intent == null) {
            if (current == null) {
                this.current = c;
            }
            addedDamage += amt;
            this.intent = AbstractMonster.Intent.ATTACK;
        }
        else {
            addAttackEffect = true;
            addedDamage += amt;
        }*/
    }
}
