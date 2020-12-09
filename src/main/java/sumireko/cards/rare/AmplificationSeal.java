package sumireko.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class AmplificationSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "AmplificationSeal",
            2,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE);

    public static final String ID = makeID(cardInfo.cardName);

    private static final int UPG_COST = 1;

    private static final int SEAL = 2;

    public AmplificationSeal() {
        super(cardInfo, false);

        tags.add(CustomCardTags.FRAGILE_SEAL);
        setCostUpgrade(UPG_COST);
        setSeal(SEAL);
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
        // :)
    }

    @Override
    public void instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {

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
        i.intent = AbstractMonster.Intent.MAGIC;
        i.multihit(this.sealValue);
        i.amount = -1;
        //"x#"
    }

    @Override
    public AbstractCard makeCopy() {
        return new AmplificationSeal();
    }
}