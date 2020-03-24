package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.MakeTopCardOccultAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Precognition extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Precognition",
            2,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 13;
    private static final int UPG_BLOCK = 4;


    public Precognition() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block();
        addToBot(new MakeTopCardOccultAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new Precognition();
    }
}