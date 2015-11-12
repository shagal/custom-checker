import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ashagal on 12/11/15.
 */
public class HashCodeDoesntReturnConst extends Check {
    int[] subtrees = {TokenTypes.LITERAL_IF, TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE};

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.METHOD_DEF};
    }

    int check_subtree(DetailAST ast) {
        DetailAST detailAST = ast.findFirstToken(TokenTypes.SLIST);
        for (DetailAST child = detailAST.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getType() == TokenTypes.LITERAL_RETURN) {
                DetailAST expr = child.getFirstChild();
                if (expr.getFirstChild().getType() == TokenTypes.NUM_INT) {
                    String message = "HashCode probably returns contants";
                    log(ast, message);
                    return -1;
                }
            }
        }
        return 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        DetailAST name = ast.findFirstToken(TokenTypes.IDENT);
        DetailAST return_type = ast.findFirstToken(TokenTypes.TYPE);

        if (name.getText().equals("hashCode") && return_type.getFirstChild().getType() == TokenTypes.LITERAL_INT)
        {
            DetailAST detailAST = ast.findFirstToken(TokenTypes.SLIST);
            for (DetailAST child = detailAST.getFirstChild(); child != null; child = child.getNextSibling()) {
                if (child.getType() == TokenTypes.LITERAL_IF ||
                        child.getType() == TokenTypes.LITERAL_FOR ||
                            child.getType() == TokenTypes.LITERAL_WHILE) {
                    if (check_subtree(child) == -1) {
                        return;
                    }
                }
                if (child.getType() == TokenTypes.LITERAL_RETURN) {
                    DetailAST expr = child.getFirstChild();
                    if (expr.getFirstChild().getType() == TokenTypes.NUM_INT) {
                        String message = "HashCode probably returns contants";
                        log(ast, message);
                        return;
                    }
                }
            }
        }
    }
}
