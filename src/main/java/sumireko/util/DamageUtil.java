package sumireko.util;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.actions.cards.restrainingseal.DamageActionWrapper;
import sumireko.actions.cards.restrainingseal.DamageAllEnemiesActionWrapper;
import sumireko.actions.interfaces.WrappedDamage;
import sumireko.cards.basic.Strike;

import java.util.function.Consumer;

public class DamageUtil {
    private static final AbstractCard damageCalc = new Strike();

    public static int calcDamage(int base, AbstractMonster target) {
        damageCalc.baseDamage = damageCalc.damage = base;
        damageCalc.calculateCardDamage(target);
        return damageCalc.isDamageModified ? damageCalc.damage : damageCalc.baseDamage;
    }

    public static AbstractGameAction getWrappedDamageAction(AbstractGameAction toWrap, Consumer<AbstractCreature> onUnblocked) {
        if (toWrap instanceof DamageAction) {
            return new DamageActionWrapper((DamageAction) toWrap, onUnblocked);
        }
        else if (toWrap instanceof DamageAllEnemiesAction) {
            return new DamageAllEnemiesActionWrapper((DamageAllEnemiesAction) toWrap, onUnblocked);
        }
        else if (toWrap instanceof WrappedDamage) {
            ((WrappedDamage) toWrap).useWrapper(onUnblocked);
        }
        return toWrap;
    }
}
