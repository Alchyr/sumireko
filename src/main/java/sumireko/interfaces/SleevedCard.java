package sumireko.interfaces;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public interface SleevedCard {
    default void sleevedTurnStart() { }
    default void sleevedPlayCard(UseCardAction a, AbstractCard played, AbstractCreature target) { }
    default void sleevedOnAttacked(DamageInfo info) { };
}
