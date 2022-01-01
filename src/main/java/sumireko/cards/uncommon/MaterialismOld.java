/*package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.cards.MaterialismAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Materialism extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Materialism",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    public Materialism() {
        super(cardInfo, true);

        setExhaust(true, false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MaterialismAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new Materialism();
    }
}*/