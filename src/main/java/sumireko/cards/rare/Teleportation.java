package sumireko.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.TeleportationAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Teleportation extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Teleportation",
            3,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.RARE);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 20;
    private static final int UPG_BLOCK = 5;

    private static final int MAGIC = 7;
    private static final int UPG_MAGIC = -3;


    public Teleportation() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TeleportationAction(this.magicNumber, this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Teleportation();
    }
}