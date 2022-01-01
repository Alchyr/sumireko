package sumireko.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.abstracts.BasePower;
import sumireko.enums.CustomCardTags;

import static sumireko.SumirekoMod.makeID;

public class OldHonedTalentPower extends BasePower {
    public static final String NAME = "OldHonedTalent";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public int currentBuff = 0;
    private Color amountRenderColor = Color.GREEN.cpy();

    public OldHonedTalentPower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + currentBuff + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(CustomCardTags.FINAL))
        {
            this.flash();
        }
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(CustomCardTags.FINAL))
        {
            this.currentBuff += this.amount;
            updateDescription();
        }
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card.hasTag(CustomCardTags.FINAL) && type == DamageInfo.DamageType.NORMAL)
            return damage + this.currentBuff;

        return damage;
    }

    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        amountRenderColor.a = c.a;
        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.currentBuff), x, y + 15.0F * Settings.scale, this.fontScale, amountRenderColor);
    }
}
