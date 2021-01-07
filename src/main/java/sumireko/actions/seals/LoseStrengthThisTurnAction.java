package sumireko.actions.seals;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class LoseStrengthThisTurnAction extends AbstractGameAction {
    public LoseStrengthThisTurnAction(AbstractCreature target, AbstractCreature source, int amount)
    {
        setValues(target, source, amount);
    }

    @Override
    public void update() {
        //why did I make this a separate action instead of just adding both of these in the seal again?
        //oh wait yeah it was so that the hasPower check occurs later, because other seals may also apply debuffs that will consume the artifact
        if (!target.hasPower(ArtifactPower.POWER_ID))
            addToTop(new ApplyPowerAction(target, source, new GainStrengthPower(target, this.amount), this.amount, true, AttackEffect.NONE));
        addToTop(new ApplyPowerAction(target, source, new StrengthPower(target, -this.amount), -this.amount, true, AttackEffect.NONE));

        this.isDone = true;
    }
}
