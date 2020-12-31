package sumireko.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class ExtensionSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ExtensionSeal",
            2,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE);
    // skill

    public static final String ID = makeID(cardInfo.cardName);

    private static final int SEAL = 2;

    public ExtensionSeal() {
        super(cardInfo, true);

        setSeal(SEAL);

        tags.add(CustomCardTags.FRAGILE_SEAL);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        tags.remove(CustomCardTags.FRAGILE_SEAL);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void applyFinalBaseAdjacencyEffect(SealCard c) {
        c.multiplySealValue(this.tempSealValue);
    }

    @Override
    public void applyFinalAdjacencyEffect(SealCard c) {
        c.multiplySealValue(this.sealValue);
    }

    @Override
    public void getIntent(SealIntent i) {
        i.intent = AbstractMonster.Intent.BUFF;
        i.multihit(this.sealValue);
        i.amount = -1;
    }

    @Override
    public AbstractCard makeCopy() {
        return new ExtensionSeal();
    }
}