package sumireko.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;
import sumireko.util.SealIntent;

import static sumireko.SumirekoMod.makeID;

public class AmplificationSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "AmplificationSeal",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE);

    public static final String ID = makeID(cardInfo.cardName);

    private static final int SEAL = 2;

    public AmplificationSeal() {
        super(cardInfo, true);

        tags.add(CustomCardTags.FRAGILE_SEAL);
        setSeal(SEAL);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        tags.remove(CustomCardTags.FRAGILE_SEAL);
    }

    @Override
    public float modifyDamage(DamageInfo.DamageType type, float damage) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * this.sealValue;
        }

        return damage;
    }

    @Override
    public void getIntent(SealIntent i) {
        i.addIntent(SealIntent.MAGIC);
        i.bonusEffect("x" + this.sealValue);
    }

    @Override
    public AbstractCard makeCopy() {
        return new AmplificationSeal();
    }
}