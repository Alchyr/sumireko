package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.EscapeArtistAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class EscapeArtist extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "EscapeArtist",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.COMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public EscapeArtist() {
        super(cardInfo, true);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.upgraded ? this.magicNumber : 1, new EscapeArtistAction()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new EscapeArtist();
    }
}