package sumireko.actions.general;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.List;

public class AllEnemyStrengthDownAction extends AbstractGameAction {
    public AllEnemyStrengthDownAction(AbstractCreature src, int amt) {
        this.source = src;
        this.amount = amt;
        this.actionType = ActionType.DEBUFF;
    }

    @Override
    public void update() {
        List<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;

        for (AbstractMonster m : monsters) {
            if (!m.hasPower(ArtifactPower.POWER_ID))
                addToTop(new ApplyPowerAction(m, source, new StrengthPower(m, -this.amount), -this.amount, true));
        }
        for (AbstractMonster m : monsters) {
            addToTop(new ApplyPowerAction(m, source, new StrengthPower(m, -this.amount), -this.amount, true));
        }

        this.isDone = true;
    }
}
