package sumireko.actions.general;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.actions.cards.restrainingseal.DamageActionWrapper;
import sumireko.actions.cards.restrainingseal.DamageAllEnemiesActionWrapper;
import sumireko.actions.seals.LoseStrengthThisTurnAction;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DamageRandomConditionalEnemyAction extends AbstractGameAction {
    private Predicate<AbstractMonster> condition;
    private DamageInfo damage;

    private boolean applyPowers;

    private Consumer<AbstractCreature> onUnblocked = null;

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

    public void useWrapper(Consumer<AbstractCreature> onUnblocked) {
        if (this.onUnblocked == null) {
            this.onUnblocked = onUnblocked;
        }
        else {
            Consumer<AbstractCreature> old = this.onUnblocked;
            this.onUnblocked = (m)->{
                onUnblocked.accept(m);
                old.accept(m);
            };
        }
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

            DamageAction a = new DamageAction(validTargets.get(0), damage, attackEffect);
            if (onUnblocked != null) {
                AbstractDungeon.actionManager.addToTop(new DamageActionWrapper(a, onUnblocked));
            }
            else
            {
                AbstractDungeon.actionManager.addToTop(a);
            }
            this.isDone = true;
            return;
        }

        AbstractMonster t = validTargets.get(AbstractDungeon.cardRandomRng.random(validTargets.size() - 1));
        if (applyPowers)
            damage.applyPowers(damage.owner, t);

        DamageAction a = new DamageAction(t, damage, attackEffect);
        if (onUnblocked != null) {
            AbstractDungeon.actionManager.addToTop(new DamageActionWrapper(a, onUnblocked));
        }
        else
        {
            AbstractDungeon.actionManager.addToTop(a);
        }
        this.isDone = true;
    }
}
