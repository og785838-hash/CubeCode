package codegen.block;

public class Block
{
    public int i;
    public int j;
    public String content;

    public Property.Cond cond;
    public Property.Auto auto;
    public Property.Type type;
    public Property.Dir dir;

    public Block(int i, int j, String content, Property.Cond cond, Property.Auto auto, Property.Type type, Property.Dir dir)
    {
        this.i = i;
        this.j = j;
        this.content = content;
        this.cond = cond;
        this.auto = auto;
        this.type = type;
        this.dir = dir;
    }

    @Override
    public String toString()
    {
        return "setblock ~" + this.i + " ~ ~" + this.j +
                " " + this.type.toString() + "[conditional=" + this.cond.toString() +
                ",facing=" + this.dir.toString() + "]{Command:\"" + this.content + "\",auto:" + this.auto.toString() +
                "} destroy";
    }
}
