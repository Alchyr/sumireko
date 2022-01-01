/*package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.LockingCard;
import sumireko.actions.cards.MindEyeAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class MindEye extends LockingCard {
    private final static CardInfo cardInfo = new CardInfo(
            "MindEye",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    public MindEye() {
        super(cardInfo, true);

        setMagic(1, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        this.addToBot(new MindEyeAction(upgraded ? this.magicNumber : 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MindEye();
    }
}*/