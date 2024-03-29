package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.powers.EsotericSecretsPower;
import sumireko.powers.OldEsotericSecretsPower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class EsotericSecrets extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "EsotericSecrets",
            1,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.UNCOMMON);
    // power

    public static final String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;

    public EsotericSecrets() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applySelf(new EsotericSecretsPower(p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new EsotericSecrets();
    }
}