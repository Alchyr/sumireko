package sumireko.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class AffirmationAction extends AbstractGameAction {
    private final AbstractCard source;
    private final AbstractPlayer p;
    private final int block;

    public AffirmationAction(AbstractCard source, AbstractPlayer p, int block) {
        this.source = source;
        this.p = p;
        this.block = block;

        this.actionType = ActionType.DAMAGE;
    }

    @Override
    public void update() {
        this.isDone = true;
        int count = 0;

        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (!c.upgraded && !c.equals(source)) {
                ++count;
            }
        }

        for(int i = 0; i < count; ++i) {
            this.addToTop(new AttackDamageRandomEnemyAction(this.source, AttackEffect.SMASH));
            this.addToTop(new GainBlockAction(p, p, this.block));
        }
    }
}
