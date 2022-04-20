package sumireko.actions.cards.restrainingseal;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sumireko.actions.interfaces.WrappedDamage;
import sumireko.actions.seals.LoseStrengthThisTurnAction;

import java.util.function.Consumer;

public class DamageActionWrapper extends AbstractGameAction implements WrappedDamage {
    private final DamageAction action;
    public Consumer<AbstractCreature> onUnblocked;

    public DamageActionWrapper(DamageAction a, Consumer<AbstractCreature> onUnblocked) {
        this.actionType = ActionType.DAMAGE;

        this.action = a;
        this.target = a.target;

        this.onUnblocked = onUnblocked;
    }

    @Override
    public void useWrapper(Consumer<AbstractCreature> onUnblocked) {
        Consumer<AbstractCreature> old = this.onUnblocked;
        this.onUnblocked = (m)->{
            onUnblocked.accept(m);
            old.accept(m);
        };
    }

    @Override
    public void update() {
        if (!action.isDone)
            action.update();
        else {
            this.isDone = true;

            //should be impossible for anything else to interfere
            if (!this.target.isDeadOrEscaped())
                onUnblocked.accept(this.target);
        }
    }
}
