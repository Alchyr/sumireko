package sumireko.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.cards.PsychometryAction;
import sumireko.actions.general.PurgeHandAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Psychometry extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Psychometry",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 2;


    public Psychometry() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PurgeHandAction());
        addToBot(new PsychometryAction(this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Psychometry();
    }
}