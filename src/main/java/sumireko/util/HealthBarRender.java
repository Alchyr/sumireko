package sumireko.util;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static sumireko.SumirekoMod.logger;

public class HealthBarRender {
    public AbstractCreature target;
    public int amount;

    public boolean locked = false;
    public int resultHp = 0;

    public Color c;

    //public HealthBarRender sub = null;

    public HealthBarRender(AbstractCreature target, int amount)
    {
        this.target = target;
        this.amount = amount;

        this.c = Color.PURPLE.cpy();
    }

    public void combine(HealthBarRender other)
    {
        if (other.target.equals(this.target)) // && other.c.equals(this.c))
        {
            this.amount += other.amount;
        }
        /*else if (sub != null)
        {
            sub.combine(other);
        }*/
        else
        {
            logger.error("Attempted to combine health bar renders with different targets");
            //sub = other;
        }
    }
}
