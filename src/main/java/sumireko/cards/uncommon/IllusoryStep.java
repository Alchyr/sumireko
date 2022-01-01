package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.general.MakeTopCardOccultAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class IllusoryStep extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "IllusoryStep",
            2,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 14;
    private static final int UPG_BLOCK = 4;


    public IllusoryStep() {
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
        return new IllusoryStep();
    }
}