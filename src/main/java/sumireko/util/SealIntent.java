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
import sumireko.abstracts.SealCard;
import sumireko.effects.*;

import java.util.ArrayList;
import java.util.Iterator;

public class SealIntent {
    public SealCard current;
    public float x, y;

    public AbstractMonster.Intent intent;

    public String specialText;
    public int amount;
    public boolean multihit;
    public int numHits;

    private final BobEffect bobEffect;
    private float intentParticleTimer;
    private float intentAngle;
    private Texture intentImg;
    private Texture intentBg;

    private final ArrayList<AbstractGameEffect> intentVfx = new ArrayList<>();

    private final Color intentColor;

    public void refresh()
    {
        intent = null;
        intentVfx.clear();

        intentParticleTimer = 0.5f;
        intentColor.a = 0.0f;
    }

    public SealIntent()
    {
        intentColor = Color.WHITE.cpy();

        this.bobEffect = new BobEffect();
        refresh();
    }

    public void update() {
        this.bobEffect.update();
        if (current != null) {
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
        if (this.current != null)
            this.renderIntent(sb);
        this.renderIntentVfxAfter(sb);
        if (this.current != null)
            this.renderValue(sb);
    }

    public void renderIntent(SpriteBatch sb) {
        sb.setColor(intentColor);
        if (this.intentBg != null) {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, intentColor.a / 2.0F));
            sb.draw(this.intentBg, x - 64.0F, y - 64.0F + this.bobEffect.y + 64.0f, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        }

        if (this.intentImg != null && this.intent != AbstractMonster.Intent.UNKNOWN && this.intent != AbstractMonster.Intent.STUN) {
            if (this.intent != AbstractMonster.Intent.DEBUFF && this.intent != AbstractMonster.Intent.STRONG_DEBUFF) {
                this.intentAngle = 0.0F;
            } else {
                this.intentAngle += Gdx.graphics.getDeltaTime() * 150.0F;
            }

            sb.setColor(this.intentColor);// 1079
            sb.draw(this.intentImg, x - 64.0F, y - 64.0F + this.bobEffect.y + 64.0f, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, this.intentAngle, 0, 0, 128, 128, false, false);
        }
    }

    private void renderValue(SpriteBatch sb) {
        if (this.multihit) {
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, (amount == -1 ? "" : Integer.toString(this.amount)) + "x" + this.numHits, x - 30.0F * Settings.scale, y + this.bobEffect.y + 64.0f - 12.0F * Settings.scale, this.intentColor);
        } else if (amount != -1) {
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, this.amount + specialText, x - 30.0F * Settings.scale, y + this.bobEffect.y + 64.0f - 12.0F * Settings.scale, this.intentColor);
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
        if (intentColor.a > 0.0F && this.intent != null) {
            switch (this.intent)
            {
                case ATTACK_DEBUFF:
                case DEBUFF:
                case STRONG_DEBUFF:
                case DEFEND_DEBUFF:
                    this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                    if (this.intentParticleTimer < 0.0F) {
                        this.intentParticleTimer = 1.0F;
                        this.intentVfx.add(new FadedDebuffParticleEffect(x, y + 64.0f));
                    }
                    break;
                case ATTACK_BUFF:
                case BUFF:
                case DEFEND_BUFF:
                    this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                    if (this.intentParticleTimer < 0.0F) {
                        this.intentParticleTimer = 0.1F;
                        this.intentVfx.add(new FadedBuffParticleEffect(x, y + 64.0f));
                    }
                    break;
                case ATTACK_DEFEND:
                    this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                    if (this.intentParticleTimer < 0.0F) {
                        this.intentParticleTimer = 0.5F;
                        this.intentVfx.add(new FadedShieldParticleEffect(x, y + 64.0f));
                    }
                    break;
                case UNKNOWN:
                    this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                    if (this.intentParticleTimer < 0.0F) {
                        this.intentParticleTimer = 0.5F;
                        this.intentVfx.add(new FadedUnknownParticleEffect(x, y + 64.0f));
                    }
                    break;
                case STUN:
                    this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                    if (this.intentParticleTimer < 0.0F) {
                        this.intentParticleTimer = 0.67F;
                        this.intentVfx.add(new FadedStunStarEffect(x, y + 64.0f));
                    }
                    break;
            }
        }
    }

    private Texture getIntentImg() {
        switch(this.intent) {
            case ATTACK:
            case ATTACK_BUFF:
            case ATTACK_DEBUFF:
            case ATTACK_DEFEND:
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
            tmp = this.amount * this.numHits;
        } else {
            tmp = this.amount;
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

    public void setSeal(SealCard c) {
        this.amount = c.sealValue;
        this.multihit = false;
        this.specialText = "";
        c.getIntent(this);

        if (c != current)
            intentColor.a = 0.0f;

        if (this.intent == null)
        {
            return;
        }
        this.current = c;
        this.intentImg = this.getIntentImg();
        this.intentBg = null;
    }

    public void multihit(int numHits) {
        this.multihit = true;
        this.numHits = numHits;
    }
}
