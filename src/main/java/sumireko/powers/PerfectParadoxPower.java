package sumireko.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sumireko.abstracts.BasePower;
import sumireko.patches.occult.OccultFields;

import java.util.Iterator;

import static sumireko.SumirekoMod.makeID;

public class PerfectParadoxPower extends BasePower {
    public static final String NAME = "PerfectParadox";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public PerfectParadoxPower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (OccultFields.isOccultPlayable.get(card))
        {
            //this.addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount, Settings.FAST_MODE));

            AbstractMonster m = AbstractDungeon.getRandomMonster();

            if (m != null) {
                this.addToBot(new ApplyPowerAction(m, this.owner, new StrengthPower(m, -this.amount), -this.amount, true, AbstractGameAction.AttackEffect.NONE));

                if (!m.hasPower(ArtifactPower.POWER_ID)) {
                    this.addToBot(new ApplyPowerAction(m, this.owner, new GainStrengthPower(m, this.amount), this.amount, true, AbstractGameAction.AttackEffect.NONE));
                }
            }

            /*for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                this.addToBot(new ApplyPowerAction(mo, this.owner, new StrengthPower(mo, -this.amount), -this.amount, true, AbstractGameAction.AttackEffect.NONE));
            }

            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.hasPower(ArtifactPower.POWER_ID)) {
                    this.addToBot(new ApplyPowerAction(mo, this.owner, new GainStrengthPower(mo, this.amount), this.amount, true, AbstractGameAction.AttackEffect.NONE));
                }
            }*/

            this.flash();
        }
    }
}