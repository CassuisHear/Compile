package compile_experiment;

import java.io.IOException;
import java.util.HashSet;

public class MorphologyAnalysis {

    private static final HashSet<String> keyWords;

    static {
        //在这里初始化保留字集合(不限...)
        keyWords = new HashSet<>();
        keyWords.add("byte");
        keyWords.add("short");
        keyWords.add("int");
        keyWords.add("long");
        keyWords.add("float");
        keyWords.add("double");
        keyWords.add("char");
        keyWords.add("boolean");
        keyWords.add("String");

        keyWords.add("if");
        keyWords.add("else");
        keyWords.add("do");
        keyWords.add("while");
        keyWords.add("break");
        keyWords.add("continue");
        keyWords.add("for");
        keyWords.add("void");

        keyWords.add("public");
        keyWords.add("private");
        keyWords.add("protected");
        keyWords.add("static");
        keyWords.add("final");
        keyWords.add("class");
    }

    //判断是否位操作符
    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/'
                || ch == '<' || ch == '>' || ch == '=' || ch == ':';
    }

    //判断是否为界符
    private static boolean isBoundChar(char ch) {
        return ch == ',' || ch == '.' || ch == ';' || ch == '(' || ch == ')';
    }

    //对操作符集合进行判断并输出
    private static void processOperator(String s) {
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '+': {
                    System.out.println("(加号,'+')");
                    break;
                }
                case '-': {
                    System.out.println("(减号,'-')");
                    break;
                }
                case '*': {
                    System.out.println("(乘号,'*')");
                    break;
                }
                case '/': {
                    System.out.println("(除号,'/')");
                    break;
                }
                case '=': {
                    System.out.println("(等于号,'=')");
                    break;
                }
                case ':': {
                    i++;
                    if (i < s.length() && s.charAt(i) == '=') {
                        System.out.println("(赋值号,':=')");
                    } else {
                        i--;
                        System.out.println("(非法字符,':')");
                    }
                    break;
                }
                case '<': {
                    i++;
                    if (i < s.length() && s.charAt(i) == '=') {
                        System.out.println("(小于等于号,'<=')");
                    } else {
                        i--;
                        System.out.println("(小于号,'<')");
                    }
                    break;
                }
                case '>': {
                    i++;
                    if (i < s.length() && s.charAt(i) == '=') {
                        System.out.println("(大于等于号,'>=')");
                    } else {
                        i--;
                        System.out.println("(大于号,'>')");
                    }
                    break;
                }
                default: {
                    System.out.println("(非法字符,'" + s.charAt(i) + "')");
                    break;
                }
            }
        }
    }

    //对界符集合进行判断并输出
    private static void processBoundChar(String s) {
        for (char ch : s.toCharArray()) {
            switch (ch) {
                case '(': {
                    System.out.println("(左括号,'(')");
                    break;
                }
                case ')': {
                    System.out.println("(右括号,')')");
                    break;
                }
                case ',': {
                    System.out.println("(逗号,',')");
                    break;
                }
                case ';': {
                    System.out.println("(分号,';')");
                    break;
                }
                case '.': {
                    System.out.println("(点,'.')");
                    break;
                }
                default: {
                    System.out.println("(非法字符,'" + ch + "')");
                    break;
                }
            }
        }
    }

    //输入函数
    private static String inputStr() throws IOException {
        System.out.println("请键入一个字符串(字符'#'代表输入结束):");
        StringBuilder str = new StringBuilder();
        char ch = (char) System.in.read();
        while (ch != '#') {
            str.append(ch);
            ch = (char) System.in.read();
        }
        str.append('#');
        return str.toString();
    }

    //核心处理函数
    private static void codeStart() throws IOException {
        String s = inputStr();
        char[] str = s.toCharArray();
        int n = str.length;
        int i = 0;

        //以一或多个空格为界，将整个字符串分段，
        //tempStr : 分得的段;
        //innerStr : 段内处理的临时字符串
        StringBuilder tempStr = new StringBuilder();
        StringBuilder innerStr = new StringBuilder();

        while (i < n) {
            //去除每段前导的一或多个空格
            while (i < n && str[i] == ' ') {
                i++;
            }
            //得到某一个段
            while (i < n && str[i] != ' ') {
                tempStr.append(str[i++]);
            }

            //处理得到的这个段
            int length = tempStr.length();
            int j = 0;
            while (j < length) {
                //1.段内字符串的首字符为字母
                if (Character.isLetter(tempStr.charAt(j))) {
                    //非操作符或界符时就不断添加到段内的临时字符串中
                    while (j < length && !isOperator(tempStr.charAt(j)) && !isBoundChar(tempStr.charAt(j))) {
                        innerStr.append(tempStr.charAt(j++));
                    }

                    //对段内字符串进行判断
                    String innerString = innerStr.toString();
                    //1.1是保留字
                    if (keyWords.contains(innerString)) {
                        System.out.println("(保留字,'" + innerString + "')");
                    } else {//1.2非保留字，即标识符
                        System.out.println("(标识符,'" + innerString + "')");
                    }
                } else if (Character.isDigit(tempStr.charAt(j))) {//2.段内字符串的首字符为数字
                    //后续仍为数字字符时继续追加
                    while (j < length && Character.isDigit(tempStr.charAt(j))) {
                        innerStr.append(tempStr.charAt(j++));
                    }
                    System.out.println("(数字,'" + innerStr.toString() + "')");
                } else if (isOperator(tempStr.charAt(j))) {//3.段内字符串的首字符为操作符
                    //后续仍为操作符字符时继续追加
                    while (j < length && isOperator(tempStr.charAt(j))) {
                        innerStr.append(tempStr.charAt(j++));
                    }

                    //处理操作符
                    processOperator(innerStr.toString());
                } else if (isBoundChar(tempStr.charAt(j))) {//4.段内字符串的首字符为界符
                    //后续仍为界符字符时继续追加
                    while (j < length && isBoundChar(tempStr.charAt(j))) {
                        innerStr.append(tempStr.charAt(j++));
                    }

                    //处理界符
                    processBoundChar(innerStr.toString());
                } else {//5.其他情况，输入错误
                    System.out.println("(非法输入,'" + tempStr.charAt(j) + "')");
                    j++;
                }
                //重置段内字符串
                innerStr = new StringBuilder();
            }
            //重置段字符串
            tempStr = new StringBuilder();
        }
        //将原输入的字符串进行输出，方便进行比对
        System.out.println(s);
    }

    public static void main(String[] args) {
        try {
            codeStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
