package sumireko.util;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class HealthBarRender {
    public AbstractCreature target;
    public int amount;

    public Color c;

    public HealthBarRender sub = null;

    public HealthBarRender(AbstractCreature target, int amount)
    {
        this.target = target;
        this.amount = amount;

        this.c = Color.PURPLE.cpy();
    }

    public void combine(HealthBarRender other)
    {
        if (other.target.equals(this.target) && other.c.equals(this.c))
        {
            this.amount += other.amount;
        }
        else if (sub != null)
        {
            sub.combine(other);
        }
        else
        {
            sub = other;
        }
    }
}
