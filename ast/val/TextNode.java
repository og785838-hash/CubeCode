package ast.val;

public class TextNode extends ValNode
{
    public String text;

    public TextNode() {}

    public TextNode(String text)
    {
        this.text = text.substring(1, text.length() - 1);
    }

    @Override
    public String toString()
    {
        return "TextNode: \"" + this.text + "\"";
    }
}
