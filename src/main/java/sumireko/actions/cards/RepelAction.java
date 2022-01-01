package sumireko.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sumireko.effects.RepelEffect;

public class RepelAction extends AbstractGameAction {
    private final DamageInfo info;

    public RepelAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = this.startDuration;
    }

    public void update() {
        if (!this.shouldCancelAction()) {
            this.target.damage(this.info);

            if (this.target.lastDamageTaken > 0) {
                this.addToTop(new GainBlockAction(this.source, this.target.lastDamageTaken));
                if (this.target.lastDamageTaken > 2 && this.target.hb != null) {
                    this.addToTop(new VFXAction(new RepelEffect(this.target.lastDamageTaken, this.target.hb.cX, this.target.hb.cY)));
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        this.isDone = true;
    }
}