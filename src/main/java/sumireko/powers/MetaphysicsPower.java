package sumireko.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sumireko.abstracts.BasePower;
import sumireko.abstracts.SealCard;

import static sumireko.SumirekoMod.makeID;

public class MetaphysicsPower extends BasePower {
    public static final String NAME = "Metaphysics";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public MetaphysicsPower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    public void updateDescription() {
        this.description = descriptions()[0] + amount + descriptions()[1];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof SealCard)
        {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.amount), this.amount));
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, this.amount), this.amount));
            this.flash();
        }
    }
}