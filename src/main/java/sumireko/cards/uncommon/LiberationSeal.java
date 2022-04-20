package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.actions.general.RandomOccultCardsAction;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;
import sumireko.util.SealIntent;

import java.util.ArrayList;

import static sumireko.SumirekoMod.makeID;

public class LiberationSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "LiberationSeal",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int SEAL = 2;
    private static final int UPG_SEAL = 1;

    public LiberationSeal() {
        super(cardInfo, false);

        setSeal(SEAL, UPG_SEAL);

        tags.add(CustomCardTags.FRAGILE_SEAL);
    }

    @Override
    public ArrayList<AbstractGameAction> triggerSealEffect(AbstractMonster target) {
        ArrayList<AbstractGameAction> actions = new ArrayList<>();

        if (this.sealValue > 0)
            actions.add(new RandomOccultCardsAction(AbstractDungeon.player.drawPile, this.sealValue));

        return actions;
    }

    @Override
    public void getIntent(SealIntent i) {
        i.addIntent(SealIntent.MAGIC);
        i.bonusEffect(String.valueOf(this.sealValue));
    }

    @Override
    public AbstractCard makeCopy() {
        return new LiberationSeal();
    }
}