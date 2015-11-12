import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.annotation.PackageAnnotationCheck;

/**
 * Created by ashagal on 10/11/15.
 */
public class StringEqulityCheck extends Check
{
    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.EQUAL, TokenTypes.NOT_EQUAL};
    }

    @Override
    public void visitToken(DetailAST ast) {
        DetailAST f = ast.getFirstChild();
        DetailAST s = f.getNextSibling();

        if (f == null || s == null) {
            return;
        }

        if (f.getType() == TokenTypes.STRING_LITERAL || s.getType() == TokenTypes.STRING_LITERAL) {
            String message = "String equality check with ==/!= operator";
            log(ast, message);
            return;
        }
    }


}
