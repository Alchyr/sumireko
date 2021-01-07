/*package sumireko.cards.common;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.actions.general.AllEnemyLoseHPAction;
import sumireko.util.CardInfo;
import sumireko.util.HealthBarRender;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

@AutoAdd.Ignore
public class CursedSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "CursedSeal",
            1,
            CardType.SKILL,
            CardTarget.ALL_ENEMY,
            CardRarity.COMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 11;
    private static final int UPG_MAGIC = 3;


    public CursedSeal() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);
        setSeal(0, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AllEnemyLoseHPAction(p, this.magicNumber, AbstractGameAction.AttackEffect.POISON));
        super.use(p, m);
    }

    @Override
    public void applyFinalBaseAdjacencyEffect(SealCard c) { //Seals that negate other seals are not "buff" seals
        //Buff seals will be modified first, including by non-buff seals. So, this will negate all neighboring buff seals.
        c.negateSeal();
    }
    @Override
    public void applyFinalAdjacencyEffect(SealCard c) { //Seals that negate other seals are not "buff" seals
        //Buff seals will be modified first, including by non-buff seals. So, this will negate all neighboring buff seals.
        c.negateSeal();
    }

    @Override
    public void getIntent(SealIntent i) {
        i.amount = -1;
        i.intent = AbstractMonster.Intent.MAGIC;
    }

    @Override
    public AbstractCard makeCopy() {
        return new CursedSeal();
    }
}*/