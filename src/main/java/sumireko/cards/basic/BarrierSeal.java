package sumireko.cards.basic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.SealSystem;
import sumireko.abstracts.SealCard;
import sumireko.util.CardInfo;
import sumireko.util.SealIntent;

import java.util.ArrayList;

import static sumireko.SumirekoMod.makeID;

public class BarrierSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BarrierSeal",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.BASIC);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int SEAL = 3;
    private static final int UPG_SEAL = 2;

    public BarrierSeal() {
        super(cardInfo, false);

        setSeal(SEAL, UPG_SEAL);
    }

    @Override
    public ArrayList<AbstractGameAction> triggerSealEffect(AbstractMonster target) {
        ArrayList<AbstractGameAction> actions = new ArrayList<>();
        if (this.sealValue > 0)
            actions.add(getBlockAction(this.sealValue));

        return actions;
    }

    @Override
    public void addAdjacentSealEffect(SealCard base, ArrayList<AbstractGameAction> actions, AbstractMonster target) {
        actions.add(0, getBlockAction(this.sealValue));
    }

    @Override
    public void getIntent(SealIntent i) {
        i.addIntent(SealIntent.DEFEND);
    }
    @Override
    public void applyAdjacencyIntent(SealIntent sealIntent) {
        sealIntent.addBlock();
    }

    @Override
    public int getSealBlock() {
        return SealSystem.getAdjacentCount(this) * sealValue + sealValue;
    }

    @Override
    public AbstractCard makeCopy() {
        return new BarrierSeal();
    }
}