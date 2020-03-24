package sumireko.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.function.Predicate;

public class DamageRandomConditionalEnemyAction extends AbstractGameAction {
    private Predicate<AbstractMonster> condition;
    private DamageInfo damage;

    private boolean applyPowers;

    public DamageRandomConditionalEnemyAction(Predicate<AbstractMonster> condition, DamageInfo info, AttackEffect effect, boolean applyPowers)
    {
        this.condition = condition;
        this.damage = info;
        this.attackEffect = effect;

        this.applyPowers = applyPowers;
    }
    public DamageRandomConditionalEnemyAction(Predicate<AbstractMonster> condition, DamageInfo info, AttackEffect effect)
    {
        this(condition, info, effect, true);
    }

    @Override
    public void update() {
        ArrayList<AbstractMonster> validTargets = new ArrayList<>();

        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
        {
            if (!m.isDeadOrEscaped() && condition.test(m))
            {
                validTargets.add(m);
            }
        }

        if (validTargets.isEmpty())
        {
            this.isDone = true;
            return;
        }
        else if (validTargets.size() == 1)
        {
            if (applyPowers)
                damage.applyPowers(damage.owner, validTargets.get(0));
            AbstractDungeon.actionManager.addToTop(new DamageAction(validTargets.get(0), damage, attackEffect));
            this.isDone = true;
            return;
        }

        AbstractMonster t = validTargets.get(AbstractDungeon.cardRandomRng.random(validTargets.size() - 1));
        if (applyPowers)
            damage.applyPowers(damage.owner, t);
        AbstractDungeon.actionManager.addToTop(new DamageAction(t, damage, attackEffect));
        this.isDone = true;
    }
}
