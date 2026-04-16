package ast.decl;

import ast.ASTNode;
import ast.Type;
import ast.stmt.StmtNode;

import java.util.ArrayList;
import java.util.List;

public class FuncDeclNode extends ASTNode
{
    public Type ret;
    public String id;
    public List<ParamNode> paramList;
    public List<DeclNode> declList;
    public List<StmtNode> stmtList;

    public FuncDeclNode()
    {
        this.paramList = new ArrayList<>();
        this.declList = new ArrayList<>();
        this.stmtList = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("FuncDeclNode:");
        sb.append("\n\tReturn").append(this.ret.toString());
        sb.append("\n\tID: ").append(this.id);

        for (ParamNode param : this.paramList)
        {
            sb.append("\n\t").append(param.toString().replaceAll("\n", "\n\t"));
        }

        for (DeclNode decl : this.declList)
        {
            sb.append("\n\t").append(decl.toString().replaceAll("\n", "\n\t"));
        }

        for (StmtNode stmt : this.stmtList)
        {
            sb.append("\n\t").append(stmt.toString().replaceAll("\n", "\n\t"));
        }

        return sb.toString();
    }
}
