import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by ashagal on 12/11/15.
 */
public class MethodDefModifiersCheck extends Check {
    private static final TreeMap<String, Integer> order = new TreeMap<>();
    static {
        order.put("public", 1);
        order.put("protected", 2);
        order.put("private", 3);
        order.put("abstract", 4);
        order.put("static", 5);
        order.put("final", 6);
        order.put("transient", 7);
        order.put("volatile", 8);
        order.put("synchronized", 9);
        order.put("native", 10);
        order.put("strictfp", 11);
    }
    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.METHOD_DEF, TokenTypes.VARIABLE_DEF, TokenTypes.CLASS_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        DetailAST modifiers = ast.getFirstChild();
        List<String> list = new ArrayList<>();

        for (DetailAST child = modifiers.getFirstChild(); child != null; child = child.getNextSibling()) {
            String s = child.getText();
            if (order.containsKey(s)) {
                list.add(child.getText());
            }
        }

        for (int i = 0; i < list.size() - 1; ++i) {
            if (order.get(list.get(i)) >= order.get(list.get(i + 1))) {
                String message = "Wrong order of modifiers " + list.get(i) + " " + list.get(i + 1) + " at " + modifiers.getLineNo();
                log(modifiers, message);
            }
        }
    }
}
