package sumireko.cards.rare;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.evacipated.cardcrawl.mod.stslib.patches.cardInterfaces.BranchingUpgradesPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.util.CardInfo;
import sumireko.util.MysteryUpgrade;
import sumireko.util.PretendMonster;
import sumireko.util.mysteryupgrades.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class MysterySeal extends SealCard implements BranchingUpgradesCard, CustomSavable<String> {
    private final static CardInfo cardInfo = new CardInfo(
            "MysterySeal",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE);
    // skill

    public static final String ID = makeID(cardInfo.cardName);

    private static ArrayList<MysteryUpgrade> possibleUpgrades;

    static {
        possibleUpgrades = new ArrayList<>();

        possibleUpgrades.add(new DamageUpgrade());
        possibleUpgrades.add(new BlockUpgrade());
        possibleUpgrades.add(new DamageAllUpgrade());
        possibleUpgrades.add(new RandomSealDamageUpgrade());
        possibleUpgrades.add(new WeakSealUpgrade());
        possibleUpgrades.add(new VulnSealUpgrade());
        possibleUpgrades.add(new StrengthDownSealUpgrade());
        possibleUpgrades.add(new AdjacentBoostUpgrade());
        possibleUpgrades.add(new OccultUpgrade());
        possibleUpgrades.add(new DrawCardUpgrade());
    }

    private ArrayList<MysteryUpgrade> upgrades; //in order of application (first upgrade, second upgrade, etc)
    private ArrayList<MysteryUpgrade> sortedUpgrades; //in order of use (for functionality.)
    private int performedUpgrades;
    private boolean copyUpgrades;

    private MysteryUpgrade normalUpgrade;
    private MysteryUpgrade altUpgrade;

    public int baseAllDamage;
    public int allDamage;
    public boolean upgradedAllDamage;
    public boolean isAllDamageModified;



    ///TODO: ODODODODEDO?
    // make rng biased towards effects you already have, causing you to cap at like 3 or 4 unique effects to avoid making text too much
    // add multiple possibities for the basic magic upgrade instead of just draw? YOu can only get one upgrade that utilizes magic number. Predicate<MysterySeal> canSpawn?

    public MysterySeal() {
        this(true);
    }

    public MysterySeal(boolean generateUpgrades)
    {
        super(cardInfo, false);

        setDamage(0);
        setBlock(0);
        setMagic(0);
        setSeal(0);
        setAllDamage(0);

        upgradedAllDamage = false;
        isAllDamageModified = false;

        upgrades = new ArrayList<>();
        sortedUpgrades = new ArrayList<>();
        performedUpgrades = 0;
        copyUpgrades = false;

        if (CardCrawlGame.isInARun() && generateUpgrades)
        {
            generateUpgrades();

            upgrades.add(normalUpgrade);

            generateUpgrades();

            refresh();
        }
    }

    private void setAllDamage(int amt)
    {
        baseAllDamage = allDamage = amt;
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        ++this.performedUpgrades;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        copyUpgrades = true;
        return super.makeStatEquivalentCopy();
    }

    @Override
    public MysterySeal makeCopy() {
        MysterySeal c = new MysterySeal(!copyUpgrades); //if this is a stat equivalent copy, don't want to roll the rng for something that won't be used
        //otherwise repeatedly opening screens could affect rng and that's bad because copies are made all over the place
        if (copyUpgrades) //when makeStatEquivalentCopy is called
        {
            c.upgrades.clear();

            c.upgrades.addAll(this.upgrades);

            c.normalUpgrade = this.normalUpgrade;
            c.altUpgrade = this.altUpgrade;
            copyUpgrades = false;

            c.refresh();
        }
        return c;
    }

    public boolean canUpgrade() {
        return true;
    }

    public void upgrade() {
        this.upgradeName();

        //only matters for display on upgrade screen. Set it to false for the normal upgrade, true on the branch upgrade.
        //If it's a random upgrade, it shouldn't be a grid select screen forUpgrade anyways.
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID && AbstractDungeon.gridSelectScreen.forUpgrade)
        {
            BranchingUpgradesPatch.BranchSelectFields.isBranchUpgrading.set(this, this.isBranchUpgrade());
        }

        if (upgrades.size() <= this.performedUpgrades)
        {
            //New upgrade.
            MysteryUpgrade m = normalUpgrade;
            if (this.isBranchUpgrade())
            {
                m = altUpgrade;
            }
            if (m != null)
            {
                upgrades.add(m);

                generateUpgrades();
            }
            else
            {
                m = getRandomUpgrade();
                upgrades.add(m);
            }
        }
        //else Upgrades.size > performedUpgrades, upgrades were copied over. No need to do anything.

        upgradeRefresh();
    }

    /*@Override
    public void setIsBranchUpgrade() {
        BranchingUpgradesPatch.BranchingUpgradeField.isBranchUpgraded.set(this, true);
        this.branchUpgrade();
    }

    @Override
    public void branchUpgrade() {
        this.upgradeName();

        upgrades.add(altUpgrade);
        altUpgrade.apply(this);

        generateUpgrades();

        upgradeRefresh();
    }

    @Override
    public void displayBranchUpgrades() {
        if (this.upgradedCost) {
            this.isCostModified = true;
        }

        if (this.upgradedBlock) {
            this.isBlockModified = true;
            this.block = this.baseBlock;
        }

        if (this.upgradedDamage) {
            this.isDamageModified = true;
            this.damage = this.baseDamage;
        }

        if (this.upgradedMagicNumber) {
            this.isMagicNumberModified = true;
            this.magicNumber = this.baseMagicNumber;
        }

        if (this.upgradedSeal) {
            this.isSealModified = true;
            this.sealValue = this.baseSealValue;
        }

        if (this.upgradedAllDamage)
        {
            this.isAllDamageModified = true;
            this.allDamage = this.baseAllDamage;
        }
    }*/

    private void refresh()
    {
        setDamage(0);
        setBlock(0);
        setMagic(0);
        setSeal(0);
        setAllDamage(0);

        for (MysteryUpgrade m : this.upgrades)
        {
            m.apply(this);
        }

        sortedUpgrades.clear();
        for (MysteryUpgrade m : upgrades)
        {
            if (!sortedUpgrades.contains(m)) //no duplicates
                sortedUpgrades.add(m);
        }

        sortedUpgrades.sort(Comparator.comparingInt(a -> a.priority)); //from low to high

        StringBuilder description = new StringBuilder();

        boolean hasSealEffect = false;

        for (MysteryUpgrade m : sortedUpgrades)
        {
            m.addNormalEffect(description);
            if (m.isSealEffect)
                hasSealEffect = true;
        }

        if (hasSealEffect)
        {
            description.append(cardStrings.EXTENDED_DESCRIPTION[0]);

            for (MysteryUpgrade m : sortedUpgrades)
            {
                m.addSealEffect(description);
            }
        }
        else
        {
            description.append(cardStrings.EXTENDED_DESCRIPTION[1]);
        }

        description.append(cardStrings.EXTENDED_DESCRIPTION[2]);

        this.rawDescription = description.toString().trim();

        this.initializeDescription();
    }

    private void upgradeRefresh() //does the same thing as refresh, but ensures only the most recent upgrade highlights its values
    {
        setDamage(0);
        setBlock(0);
        setMagic(0);
        setSeal(0);
        setAllDamage(0);

        for (int i = 0; i < this.upgrades.size(); ++i)
        {
            if (i == this.upgrades.size() - 1)
            {
                upgradedDamage = false;
                upgradedBlock = false;
                upgradedMagicNumber = false;
                upgradedSeal = false;
                upgradedAllDamage = false;
            }
            this.upgrades.get(i).apply(this);
        }

        sortedUpgrades.clear();
        for (MysteryUpgrade m : upgrades)
        {
            if (!sortedUpgrades.contains(m)) //no duplicates
                sortedUpgrades.add(m);
        }

        sortedUpgrades.sort(Comparator.comparingInt(a -> a.priority)); //from low to high

        StringBuilder description = new StringBuilder();

        boolean hasSealEffect = false;

        for (MysteryUpgrade m : sortedUpgrades)
        {
            m.addNormalEffect(description);
            if (m.isSealEffect)
                hasSealEffect = true;
        }

        if (hasSealEffect)
        {
            description.append(cardStrings.EXTENDED_DESCRIPTION[0]);

            for (MysteryUpgrade m : sortedUpgrades)
            {
                m.addSealEffect(description);
            }
        }
        else
        {
            description.append(cardStrings.EXTENDED_DESCRIPTION[1]);
        }

        description.append(cardStrings.EXTENDED_DESCRIPTION[2]);

        this.rawDescription = description.toString().trim();

        this.initializeDescription();
    }

    private void generateUpgrades()
    {
        ArrayList<MysteryUpgrade> dddddddddddddddddddddd = new ArrayList<>(possibleUpgrades);

        dddddddddddddddddddddd.removeIf((m)->!m.canStack && upgrades.contains(m));

        normalUpgrade = dddddddddddddddddddddd.remove(AbstractDungeon.cardRng.random(dddddddddddddddddddddd.size() - 1));
        altUpgrade = dddddddddddddddddddddd.remove(AbstractDungeon.cardRng.random(dddddddddddddddddddddd.size() - 1));
    }

    private MysteryUpgrade getRandomUpgrade()
    {
        ArrayList<MysteryUpgrade> dddddddddddddddddddddd = new ArrayList<>(possibleUpgrades);

        dddddddddddddddddddddd.removeIf((m)->!m.canStack && upgrades.contains(m));

        return dddddddddddddddddddddd.remove(MathUtils.random(dddddddddddddddddddddd.size() - 1));
    }

    @Override
    public void applyPowers() {
        int actualBaseDamage = this.baseDamage;

        this.baseDamage = this.baseAllDamage;
        this.isMultiDamage = true;
        super.applyPowers();

        this.isAllDamageModified = this.isDamageModified;
        this.allDamage = this.damage;

        this.isMultiDamage = false;
        this.baseDamage = actualBaseDamage;
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int actualBaseDamage = this.baseDamage;

        this.baseDamage = this.baseAllDamage;
        this.isMultiDamage = true;
        super.calculateCardDamage(mo);

        this.isAllDamageModified = this.isDamageModified;
        this.allDamage = this.damage;

        this.isMultiDamage = false;
        this.baseDamage = actualBaseDamage;
        super.calculateCardDamage(mo);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < sortedUpgrades.size(); ++i)
        {
            sortedUpgrades.get(i).use(this, p, m);
        }
        super.use(p, m);
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
        for (MysteryUpgrade m : sortedUpgrades)
        {
            m.triggerSealEffect(this, target);
        }
    }

    @Override
    public void instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        for (MysteryUpgrade m : sortedUpgrades)
        {
            m.instantSealEffect(this, target, pretendMonsters);
        }
    }

    @Override
    public void applyBaseAdjacencyEffect(SealCard c) {
        for (MysteryUpgrade m : sortedUpgrades)
        {
            m.applyBaseAdjacencyEffect(this, c);
        }
    }

    @Override
    public void applyAdjacencyEffect(SealCard c) {
        for (MysteryUpgrade m : sortedUpgrades)
        {
            m.applyAdjacencyEffect(this, c);
        }
    }

    @Override
    public void update() {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID && AbstractDungeon.gridSelectScreen.forUpgrade)
        {
            //If I don't do this, it will incorrectly assume the card is not branch upgrading due to upgrade count not being set to a negative number.
            boolean isBranchUpgrading = BranchingUpgradesPatch.BranchSelectFields.isBranchUpgrading.get(AbstractDungeon.gridSelectScreen);
            super.update();
            if (this.hb.hovered && InputHelper.justClickedLeft)
                BranchingUpgradesPatch.BranchSelectFields.isBranchUpgrading.set(AbstractDungeon.gridSelectScreen, isBranchUpgrading);
        }
        else
        {
            super.update();
        }
    }

    @Override
    public String onSave() {
        StringBuilder upgradeString = new StringBuilder();
        boolean first = true;
        for (MysteryUpgrade m : upgrades)
        {
            if (first)
                first = false;
            else
                upgradeString.append("|");
            upgradeString.append(possibleUpgrades.indexOf(m));
        }
        upgradeString.append("_").append(possibleUpgrades.indexOf(normalUpgrade));
        upgradeString.append("|").append(possibleUpgrades.indexOf(altUpgrade));
        return upgradeString.toString();
    }

    @Override
    public void onLoad(String o) {
        if (o != null)
        {
            upgrades.clear();
            String[] parts = o.split("_");
            String[] upgradeIndices = parts[0].split("\\|");
            String[] nextUpgrades = parts[1].split("\\|");

            for (String i : upgradeIndices)
            {
                int index = Integer.parseInt(i);
                upgrades.add(possibleUpgrades.get(index));
            }

            normalUpgrade = possibleUpgrades.get(Integer.parseInt(nextUpgrades[0]));
            altUpgrade = possibleUpgrades.get(Integer.parseInt(nextUpgrades[1]));

            refresh();
        }
    }
}