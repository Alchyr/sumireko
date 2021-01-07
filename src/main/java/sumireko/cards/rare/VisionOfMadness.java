package sumireko.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.general.RandomOccultCardsAction;
import sumireko.powers.MadnessPower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class VisionOfMadness extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "VisionOfMadness",
            3,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 7;
    private static final int UPG_MAGIC = -3;


    public VisionOfMadness() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RandomOccultCardsAction(999)); //I'm too lazy to write a new action.
        applySelf(new MadnessPower(p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new VisionOfMadness();
    }
}