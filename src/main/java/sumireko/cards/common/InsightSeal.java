package sumireko.cards.common;

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

public class InsightSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "InsightSeal",
            0,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.COMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 3;

    private static final int SEAL = 2;
    private static final int UPG_SEAL = 1;

    public InsightSeal() {
        super(cardInfo, false);

        setBlock(BLOCK);
        setSeal(SEAL, UPG_SEAL);
        tags.add(CustomCardTags.BUFF_SEAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block();
        super.use(p, m);
    }

    @Override
    public void applyBaseAdjacencyEffect(SealCard c) {
        c.modifySealValue(this.tempSealValue);
    }

    @Override
    public void applyAdjacencyEffect(SealCard c) {
        c.modifySealValue(this.sealValue);
    }

    @Override
    public void getIntent(SealIntent i) {
        i.addIntent(SealIntent.BUFF);
        i.bonusEffect("+" + this.sealValue);
    }

    @Override
    public AbstractCard makeCopy() {
        return new InsightSeal();
    }
}