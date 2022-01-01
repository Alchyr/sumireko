/*package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class MindEye extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "MindEye",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 2;


    public MindEye() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DiscoveryAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new MindEye();
    }
}*/