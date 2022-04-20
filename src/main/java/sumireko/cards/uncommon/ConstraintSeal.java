package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sumireko.abstracts.SealCard;
import sumireko.actions.seals.LoseStrengthThisTurnAction;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;
import sumireko.util.DamageUtil;
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
        //ArrayList<AbstractGameAction> result = new ArrayList<>();
        Consumer<AbstractCreature> strDown = (m)->addToTop(new LoseStrengthThisTurnAction(m, AbstractDungeon.player, m.lastDamageTaken));
        /*for (AbstractGameAction action : actions) {
            result.add(DamageWrapping.getWrappedDamageAction(action, strDown));
        }*/

        actions.replaceAll((action)-> DamageUtil.getWrappedDamageAction(action, strDown));

        /*actions.clear();
        actions.addAll(result);*/
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