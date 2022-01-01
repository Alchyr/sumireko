package sumireko.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.abstracts.BasePower;
import sumireko.abstracts.SealCard;

import static sumireko.SumirekoMod.makeID;

public class HonedTalentPower extends BasePower implements NonStackablePower, CloneablePowerInterface {
    public static final String NAME = "HonedTalent";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public int cardsRequired;
    private final Color amountRenderColor = Color.GREEN.cpy();

    public HonedTalentPower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
        cardsRequired = amount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof SealCard) {
            --this.amount;
            if (this.amount == 0) {
                this.flash();
                this.amount = this.cardsRequired;
                addToBot(new ApplyPowerAction(this.owner, this.owner, new ProwessPower(this.owner, 1), 1));
            }
            updateDescription();
        }
    }

    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        amountRenderColor.a = c.a;
        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.cardsRequired), x, y + 15.0F * Settings.scale, this.fontScale, amountRenderColor);
    }

    @Override
    public AbstractPower makeCopy() {
        return new HonedTalentPower(owner, amount);
    }
}