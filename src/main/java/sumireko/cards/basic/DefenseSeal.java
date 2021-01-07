package sumireko.cards.basic;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.util.CardInfo;
import sumireko.util.HealthBarRender;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class DefenseSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "DefenseSeal",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.BASIC);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int SEAL = 4;
    private static final int UPG_SEAL = 3;

    public DefenseSeal() {
        super(cardInfo, false);

        setSeal(SEAL, UPG_SEAL);

        tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
        if (this.sealValue > 0)
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.sealValue));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DefenseSeal();
    }

    @Override
    public void getIntent(SealIntent i) {
        i.intent = AbstractMonster.Intent.DEFEND;
    }
}