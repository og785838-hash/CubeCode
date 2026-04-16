package codegen.block;

public class Chain extends Block
{
    public Chain(int i, int j, String content)
    {
        super(i, j, content, Property.Cond.OFF, Property.Auto.ON, Property.Type.CHAIN, Property.Dir.SOUTH);
    }
}
