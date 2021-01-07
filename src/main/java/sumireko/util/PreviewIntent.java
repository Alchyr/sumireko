package sumireko.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.DebuffParticleEffect;
import com.megacrit.cardcrawl.vfx.ShieldParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.BuffParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.StunStarEffect;
import com.megacrit.cardcrawl.vfx.combat.UnknownParticleEffect;
import sumireko.effects.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import static sumireko.SumirekoMod.logger;

public class PreviewIntent {
    private static Method getIntentImg, getIntentBg;

    static {
        try
        {
            getIntentImg = AbstractMonster.class.getDeclaredMethod("getIntentImg");
            getIntentImg.setAccessible(true);

            getIntentBg = AbstractMonster.class.getDeclaredMethod("getIntentBg");
            getIntentBg.setAccessible(true);
        }
        catch (Exception e)
        {
            logger.error("REEEEEEEEEEEEEEE");
            e.printStackTrace();
        }
    }

    public boolean isValid;
    public boolean isAlive;
    public AbstractMonster.Intent intent;

    private AbstractMonster.Intent baseIntent;

    public int damage;
    public boolean multihit;
    public int numHits;

    public AbstractMonster source;
    private BobEffect bobEffect;
    private float intentParticleTimer;
    private float intentAngle;
    private Texture intentImg;
    private Texture intentBg;

    private ArrayList<AbstractGameEffect> intentVfx;

    private Color intentColor;

    private boolean isAttackIntent;

    public PreviewIntent(AbstractMonster source, PretendMonster m)
    {
        this.source = source;
        intentColor = Color.WHITE.cpy();

        isAttackIntent = false;
        baseIntent = source.intent;

        isAlive = !(m.canDie && m.currentHealth <= 0);
        if (m.split && isAlive && (float)m.currentHealth <= (float)m.maxHealth / 2.0F)
        {
            intent = AbstractMonster.Intent.UNKNOWN;
            damage = -1;
            multihit = false;
            numHits = 0;
        }
        else
        {
            intent = m.intent;

            damage = m.damage;
            multihit = m.renderMultiple;
            numHits = m.numHits;
        }

        intentParticleTimer = 0.5f;
        this.bobEffect = new BobEffect();
        if (intent == source.intent && //this will return the source's intent image, so if it's different I have to handle it myself
                intent != AbstractMonster.Intent.ATTACK &&
                intent != AbstractMonster.Intent.ATTACK_BUFF &&
                intent != AbstractMonster.Intent.ATTACK_DEBUFF &&
                intent != AbstractMonster.Intent.ATTACK_DEFEND) //attacks will change by amount so I have to handle them myself
        {
            try //The main reason for this being used at all is for compatibility with custom intents.
            {
                this.intentImg = (Texture) getIntentImg.invoke(source);
                this.intentBg = (Texture) getIntentBg.invoke(source);
            }
            catch (Exception e)
            {
                this.intentImg = this.getIntentImg();
                this.intentBg = null;
                e.printStackTrace();
            }
        }
        else
        {
            this.intentImg = this.getIntentImg();
            this.intentBg = null;
        }

        intentVfx = new ArrayList<>();

        intentColor.a = 0.0f;

        isValid = true;
    }

    public void update() {
        this.bobEffect.update();
        if (intentColor.a != 0.6F) {
            intentColor.a += Gdx.graphics.getDeltaTime();
            if (intentColor.a > 0.6f) {
                intentColor.a = 0.6f;
            }
        }

        if (isAlive)
            this.updateIntentVFX();

        Iterator<AbstractGameEffect> i = this.intentVfx.iterator();

        AbstractGameEffect e;
        while(i.hasNext()) {
            e = i.next();
            e.update();
            if (e.isDone) {
                i.remove();
            }
        }

        if (baseIntent != source.intent) //if monster intent is different from baseIntent, the enemy's intent has changed somehow. (ex. stunning byrds)
        {
            isValid = false;
        }
    }

    public void render(SpriteBatch sb)
    {
        this.renderIntentVfxBehind(sb);
        this.renderIntent(sb);
        this.renderIntentVfxAfter(sb);
        this.renderDamageRange(sb);
    }

    public void renderIntent(SpriteBatch sb) {
        sb.setColor(intentColor);
        if (this.intentBg != null) {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, intentColor.a / 2.0F));
            sb.draw(this.intentBg, source.intentHb.cX - 64.0F, source.intentHb.cY - 64.0F + this.bobEffect.y + 64.0f, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        }

        if (this.intentImg != null && this.intent != AbstractMonster.Intent.UNKNOWN && this.intent != AbstractMonster.Intent.STUN) {
            if (this.intent != AbstractMonster.Intent.DEBUFF && this.intent != AbstractMonster.Intent.STRONG_DEBUFF) {
                this.intentAngle = 0.0F;
            } else {
                this.intentAngle += Gdx.graphics.getDeltaTime() * 150.0F;
            }

            sb.setColor(this.intentColor);// 1079
            sb.draw(this.intentImg, source.intentHb.cX - 64.0F, source.intentHb.cY - 64.0F + this.bobEffect.y + 64.0f, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, this.intentAngle, 0, 0, 128, 128, false, false);
        }
    }

    private void renderDamageRange(SpriteBatch sb) {
        if (this.intent.name().contains("ATTACK")) {
            if (this.multihit) {
                FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, this.damage + "x" + this.numHits, source.intentHb.cX - 30.0F * Settings.scale, source.intentHb.cY + this.bobEffect.y + 64.0f - 12.0F * Settings.scale, this.intentColor);
            } else {
                FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(this.damage), source.intentHb.cX - 30.0F * Settings.scale, source.intentHb.cY + this.bobEffect.y + 64.0f - 12.0F * Settings.scale, this.intentColor);
            }
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
        if (intentColor.a > 0.0F) {
            switch (this.intent)
            {
                case ATTACK_DEBUFF:
                case DEBUFF:
                case STRONG_DEBUFF:
                case DEFEND_DEBUFF:
                    this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                    if (this.intentParticleTimer < 0.0F) {
                        this.intentParticleTimer = 1.0F;
                        this.intentVfx.add(new FadedDebuffParticleEffect(source.intentHb.cX, source.intentHb.cY + 64.0f));
                    }
                    break;
                case ATTACK_BUFF:
                case BUFF:
                case DEFEND_BUFF:
                    this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                    if (this.intentParticleTimer < 0.0F) {
                        this.intentParticleTimer = 0.1F;
                        this.intentVfx.add(new FadedBuffParticleEffect(source.intentHb.cX, source.intentHb.cY + 64.0f));
                    }
                    break;
                case ATTACK_DEFEND:
                    this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                    if (this.intentParticleTimer < 0.0F) {
                        this.intentParticleTimer = 0.5F;
                        this.intentVfx.add(new ShieldParticleEffect(source.intentHb.cX, source.intentHb.cY + 64.0f));
                    }
                    break;
                case UNKNOWN:
                    this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                    if (this.intentParticleTimer < 0.0F) {
                        this.intentParticleTimer = 0.5F;
                        this.intentVfx.add(new FadedUnknownParticleEffect(source.intentHb.cX, source.intentHb.cY + 64.0f));
                    }
                    break;
                case STUN:
                    this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                    if (this.intentParticleTimer < 0.0F) {
                        this.intentParticleTimer = 0.67F;
                        this.intentVfx.add(new FadedStunStarEffect(source.intentHb.cX, source.intentHb.cY + 64.0f));
                    }
                    break;
            }
        }
    }

    public boolean shouldRender() {
        return isAlive && (this.intent != source.intent || (isAttackIntent && this.damage != source.getIntentDmg()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreviewIntent that = (PreviewIntent) o;
        return isAlive == that.isAlive &&
                damage == that.damage &&
                multihit == that.multihit &&
                numHits == that.numHits &&
                intent == that.intent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(intent, damage, multihit, numHits);
    }

    private Texture getIntentImg() {
        switch(this.intent) {
            case ATTACK:
            case ATTACK_BUFF:
            case ATTACK_DEBUFF:
            case ATTACK_DEFEND:
                isAttackIntent = true;
                return this.getAttackIntent();
            case BUFF:
                return ImageMaster.INTENT_BUFF_L;
            case DEBUFF:
                return ImageMaster.INTENT_DEBUFF_L;
            case STRONG_DEBUFF:
                return ImageMaster.INTENT_DEBUFF2_L;
            case DEFEND:
            case DEFEND_DEBUFF:
                return ImageMaster.INTENT_DEFEND_L;
            case DEFEND_BUFF:
                return ImageMaster.INTENT_DEFEND_BUFF_L;
            case ESCAPE:
                return ImageMaster.INTENT_ESCAPE_L;
            case MAGIC:
                return ImageMaster.INTENT_MAGIC_L;
            case SLEEP:
                return ImageMaster.INTENT_SLEEP_L;
            case STUN:
                return null;
            default:
                return ImageMaster.INTENT_UNKNOWN_L;
        }
    }

    protected Texture getAttackIntent() {
        int tmp;
        if (multihit) {
            tmp = this.damage * this.numHits;
        } else {
            tmp = this.damage;
        }

        if (tmp < 5) {
            return ImageMaster.INTENT_ATK_1;
        } else if (tmp < 10) {
            return ImageMaster.INTENT_ATK_2;
        } else if (tmp < 15) {
            return ImageMaster.INTENT_ATK_3;
        } else if (tmp < 20) {
            return ImageMaster.INTENT_ATK_4;
        } else if (tmp < 25) {
            return ImageMaster.INTENT_ATK_5;
        } else {
            return tmp < 30 ? ImageMaster.INTENT_ATK_6 : ImageMaster.INTENT_ATK_7;
        }
    }
}
