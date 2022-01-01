package sumireko.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.abstracts.LockingCard;
import sumireko.powers.AdditionalTurnPower;
import sumireko.powers.FatiguePower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class DejaVu extends LockingCard {
    private final static CardInfo cardInfo = new CardInfo(
            "DejaVu",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.RARE);

    public static final String ID = makeID(cardInfo.cardName);


    public DejaVu() {
        super(cardInfo, true);

        setExhaust(true, false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applySelf(new FatiguePower(p, 2));
        applySelf(new AdditionalTurnPower(p, 1));
    }

    @Override
    public void lockCard() {
        if (upgraded) {
            super.lockCard();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DejaVu();
    }
}