package sumireko.actions.general;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.actions.cards.restrainingseal.DamageActionWrapper;
import sumireko.actions.interfaces.WrappedDamage;
import sumireko.util.DamageUtil;

import java.util.function.Consumer;

public class DamageWeakestEnemyAction extends AbstractGameAction implements WrappedDamage {
    private final DamageInfo damage;

    private final boolean applyPowers;

    private Consumer<AbstractCreature> onUnblocked = null;

    public DamageWeakestEnemyAction(DamageInfo info, AttackEffect effect, boolean applyPowers)
    {
        this.damage = info;
        this.attackEffect = effect;

        this.applyPowers = applyPowers;
    }
    public DamageWeakestEnemyAction(DamageInfo info, AttackEffect effect)
    {
        this(info, effect, true);
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
        AbstractMonster target = null;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped()) {
                if (target == null || (m.currentHealth < target.currentHealth)) {
                    target = m;
                }
            }
        }
        if (target != null) {
            if (applyPowers) {
                damage.output = DamageUtil.calcDamage(damage.base, target);
                damage.isModified = damage.output != damage.base;
            }

            DamageAction a = new DamageAction(target, damage, attackEffect);
            if (onUnblocked != null) {
                AbstractDungeon.actionManager.addToTop(new DamageActionWrapper(a, onUnblocked));
            }
            else
            {
                AbstractDungeon.actionManager.addToTop(a);
            }
        }

        this.isDone = true;
    }
}