package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Fling extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Fling",
            2,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.UNCOMMON);
    // attack

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 6;
    private static final int MAGIC = 4;

    public Fling() {
        super(cardInfo, false);

        setDamage(DAMAGE);
        setMagic(MAGIC);

        tags.add(CustomCardTags.FINAL);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; ++i) //TODO: Add VFX of some kind of small objects being thrown at high speed
            damageRandom(AbstractGameAction.AttackEffect.BLUNT_LIGHT);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Fling();
    }
}