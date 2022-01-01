package sumireko.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class ForbiddenKnowledgeAction extends AbstractGameAction {
    private int hits;
    private DamageInfo info;

    public ForbiddenKnowledgeAction(AbstractCreature target, int hits, DamageInfo info, AttackEffect effect) {
        this.hits = hits;
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
    }

    public void update() {
        if (this.target != null && this.target.currentHealth > 0) {
            for(int i = 0; i < hits; ++i) {// 37
                this.addToTop(new DamageAction(this.target, this.info, this.attackEffect));
            }
        }
        this.isDone = true;
    }
}