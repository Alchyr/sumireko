package sumireko.actions.cards.restrainingseal;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.function.Consumer;

public class DamageAllEnemiesActionWrapper extends AbstractGameAction {
    private final DamageAllEnemiesAction action;
    public Consumer<AbstractCreature> onUnblocked;

    public DamageAllEnemiesActionWrapper(DamageAllEnemiesAction a, Consumer<AbstractCreature> onUnblocked) {
        this.actionType = ActionType.DAMAGE;

        this.action = a;

        this.onUnblocked = onUnblocked;
    }

    @Override
    public void update() {
        if (!action.isDone)
            action.update();
        else {
            this.isDone = true;

            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDeadOrEscaped()) {
                    onUnblocked.accept(m);
                }
            }
        }
    }
}