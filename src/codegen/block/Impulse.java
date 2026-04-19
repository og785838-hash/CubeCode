package codegen.block;

public class Impulse extends Block
{
    public Impulse(int i, int j, String content)
    {
        super(i, j, content, Property.Cond.OFF, Property.Auto.OFF, Property.Type.IMPULSE, Property.Dir.SOUTH);
    }
}
