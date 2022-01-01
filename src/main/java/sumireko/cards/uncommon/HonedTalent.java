package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.powers.HonedTalentPower;
import sumireko.powers.OldHonedTalentPower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class HonedTalent extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "HonedTalent",
            1,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    public HonedTalent() {
        super(cardInfo, false);

        setMagic(4, -1);
        //setInnate(false, true);
        //setCostUpgrade(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applySelf(new HonedTalentPower(p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new HonedTalent();
    }
}