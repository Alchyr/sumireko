package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sumireko.abstracts.SealCard;
import sumireko.actions.cards.restrainingseal.DamageActionWrapper;
import sumireko.actions.cards.restrainingseal.DamageAllEnemiesActionWrapper;
import sumireko.actions.general.DamageRandomConditionalEnemyAction;
import sumireko.actions.seals.LoseStrengthThisTurnAction;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.ArrayList;
import java.util.function.Consumer;

import static sumireko.SumirekoMod.makeID;

public class ConstraintSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ConstraintSeal",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    //private static final int SEAL = 6;
    //private static final int UPG_SEAL = 4;

    public ConstraintSeal() {
        super(cardInfo, true);

        //setSeal(SEAL, UPG_SEAL);

        tags.add(CustomCardTags.FRAGILE_SEAL);
    }

    @Override
    public void upgrade() {
        super.upgrade();

        this.selfRetain = true;
    }

    @Override
    public void modifyAdjacentSealEffect(SealCard base, ArrayList<AbstractGameAction> actions, AbstractMonster target) {
        ArrayList<AbstractGameAction> result = new ArrayList<>();
        for (AbstractGameAction action : actions) {
            if (action instanceof DamageAction) {
                result.add(new DamageActionWrapper((DamageAction) action, (m)->addToTop(new LoseStrengthThisTurnAction(m, AbstractDungeon.player, m.lastDamageTaken))));
            }
            else if (action instanceof DamageAllEnemiesAction) {
                result.add(new DamageAllEnemiesActionWrapper((DamageAllEnemiesAction) action, (m)->addToTop(new LoseStrengthThisTurnAction(m, AbstractDungeon.player, m.lastDamageTaken))));
            }
            else if (action instanceof DamageRandomConditionalEnemyAction) {
                ((DamageRandomConditionalEnemyAction) action).useWrapper((m)->addToTop(new LoseStrengthThisTurnAction(m, AbstractDungeon.player, m.lastDamageTaken)));
                result.add(action);
            }
            else if (action instanceof DamageActionWrapper) {
                Consumer<AbstractCreature> old = ((DamageActionWrapper) action).onUnblocked;
                ((DamageActionWrapper) action).onUnblocked = (m)->{
                    addToTop(new LoseStrengthThisTurnAction(m, AbstractDungeon.player, m.lastDamageTaken));
                    old.accept(m);
                };
                result.add(action);
            }
            else if (action instanceof DamageAllEnemiesActionWrapper) {
                Consumer<AbstractCreature> old = ((DamageAllEnemiesActionWrapper) action).onUnblocked;
                ((DamageAllEnemiesActionWrapper) action).onUnblocked = (m)->{
                    addToTop(new LoseStrengthThisTurnAction(m, AbstractDungeon.player, m.lastDamageTaken));
                    old.accept(m);
                };
                result.add(action);
            }
            else {
                result.add(action);
            }
        }

        actions.clear();
        actions.addAll(result);
    }

    @Override
    public void instantAdjacentEffectOnUnblockedDamage(SealCard base, PretendMonster m, int damage) {
        if (damage > 0) {
            pretendApplyPower(m, new StrengthPower(m, -damage), -damage);
        }
    }

    @Override
    public void getIntent(SealIntent i) {
        i.addIntent(SealIntent.BUFF);
    }

    @Override
    public void applyAdjacencyIntent(SealIntent sealIntent) {
        if (sealIntent.intentActive(SealIntent.ATTACK)) {
            sealIntent.addIntent(SealIntent.STRONG_DEBUFF);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ConstraintSeal();
    }
}