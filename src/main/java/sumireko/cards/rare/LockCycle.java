package sumireko.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.powers.LockCyclePower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class LockCycle extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "LockCycle",
            2,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.RARE);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int UPGRADE_COST = 1;

    public LockCycle() {
        super(cardInfo, false);

        setCostUpgrade(UPGRADE_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applySelf(new LockCyclePower(p, 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new LockCycle();
    }
}