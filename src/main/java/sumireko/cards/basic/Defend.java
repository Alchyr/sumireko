package sumireko.cards.basic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Defend extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Defend",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.BASIC);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;

    public Defend() {
        super(cardInfo, false);

        //setSeal(SEAL, UPG_SEAL);
        setBlock(BLOCK, UPG_BLOCK);

        tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        block();
    }

    /*@Override
    public ArrayList<AbstractGameAction> triggerSealEffect(AbstractMonster target) {
        ArrayList<AbstractGameAction> actions = new ArrayList<>();
        if (this.sealValue > 0)
            actions.add(getBlockAction(this.sealValue));

        return actions;
    }*/

    @Override
    public AbstractCard makeCopy() {
        return new Defend();
    }

    /*@Override
    public void getIntent(SealIntent i) {
        i.intent = AbstractMonster.Intent.DEFEND;
    }*/
}