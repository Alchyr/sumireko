package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import sumireko.abstracts.SealCard;
import sumireko.util.CardInfo;
import sumireko.util.SealIntent;

import java.util.ArrayList;

import static sumireko.SumirekoMod.makeID;

public class DeflectionSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "DeflectionSeal",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.COMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 2;

    private static final int SEAL = 4;
    private static final int UPG_SEAL = 1;

    public DeflectionSeal() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setSeal(SEAL, UPG_SEAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //vfx of frost orb pieces flying outwards?
        //make a new power later
        block();

        super.use(p, m);
    }

    @Override
    public ArrayList<AbstractGameAction> triggerSealEffect(AbstractMonster target) {
        ArrayList<AbstractGameAction> actions = new ArrayList<>();
        if (this.sealValue > 0)
            actions.add(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FlameBarrierPower(AbstractDungeon.player, this.sealValue), this.sealValue));

        return actions;
    }

    @Override
    public void getIntent(SealIntent i) {
        i.addIntent(SealIntent.BUFF);
        i.bonusEffect(String.valueOf(this.sealValue));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DeflectionSeal();
    }
}