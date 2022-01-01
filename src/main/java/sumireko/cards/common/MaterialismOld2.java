/*package sumireko.cards.common;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.interfaces.DamageModifierCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class MaterialismOld2 extends BaseCard implements DamageModifierCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Materialism",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.COMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 2;


    public MaterialismOld2() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);
        shuffleBackIntoDrawPile = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // :)
    }

    @Override
    public float modifyDamage(DamageInfo.DamageType type, float damage) {
        return damage + this.magicNumber;
    }

    @Override
    public AbstractCard makeCopy() {
        return new MaterialismOld2();
    }
}*/