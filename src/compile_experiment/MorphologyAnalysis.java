package compile_experiment;

import java.io.*;
import java.util.HashSet;

public class MorphologyAnalysis {

    //保留字集合
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
        keyWords.add("System");
        keyWords.add("out");
        keyWords.add("in");
        keyWords.add("println");
        keyWords.add("print");
    }

    //用来存储输出结果
    private static final StringBuilder ans = new StringBuilder();

    //判断是否位操作符
    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/'
                || ch == '<' || ch == '>' || ch == '=' || ch == ':';
    }

    //判断是否为界符
    private static boolean isBoundChar(char ch) {
        return ch == ',' || ch == '.' || ch == ';' || ch == '(' || ch == ')' || ch == '{' || ch == '}' || ch == '"' || ch == ':';
    }

    //对操作符集合进行判断并输出
    private static void processOperator(String s) {
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '+': {
                    i++;
                    if (i < s.length() && s.charAt(i) == '=') {
                        ans.append("(加等于号,'+=')").append('\n');
                    } else if (i < s.length() && s.charAt(i) == '+') {
                        ans.append("(自增符号,'++')").append('\n');
                    } else {
                        i--;
                        ans.append("(加号,'+')").append('\n');
                    }
                    break;
                }
                case '-': {
                    i++;
                    if (i < s.length() && s.charAt(i) == '=') {
                        ans.append("(减等于号,'-=')").append('\n');
                    } else if (i < s.length() && s.charAt(i) == '-') {
                        ans.append("(自减符号,'--')").append('\n');
                    } else {
                        i--;
                        ans.append("(减号,'-')").append('\n');
                    }
                    break;
                }
                case '*': {
                    i++;
                    if (i < s.length() && s.charAt(i) == '=') {
                        ans.append("(乘等于号,'*=')").append('\n');
                    } else {
                        i--;
                        ans.append("(乘号,'*')").append('\n');
                    }
                    break;
                }
                case '/': {
                    i++;
                    if (i < s.length() && s.charAt(i) == '=') {
                        ans.append("(除等于号,'/=')").append('\n');
                    } else {
                        i--;
                        ans.append("(除号,'/')").append('\n');
                    }
                    break;
                }
                case '=': {
                    i++;
                    if (i < s.length() && s.charAt(i) == '=') {
                        ans.append("(等于号,'==')").append('\n');
                    } else {
                        i--;
                        ans.append("(赋值符号,'=')").append('\n');
                    }
                    break;
                }
                case '<': {
                    i++;
                    if (i < s.length() && s.charAt(i) == '=') {
                        ans.append("(小于等于号,'<=')").append('\n');
                    } else {
                        i--;
                        ans.append("(小于号,'<')").append('\n');
                    }
                    break;
                }
                case '>': {
                    i++;
                    if (i < s.length() && s.charAt(i) == '=') {
                        ans.append("(大于等于号,'>=')").append('\n');
                    } else {
                        i--;
                        ans.append("(大于号,'>')").append('\n');
                    }
                    break;
                }
                default: {
                    ans.append("(非法字符,'").append(s.charAt(i)).append("')").append('\n');
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
                    ans.append("(左括号,'(')").append('\n');
                    System.out.println();
                    break;
                }
                case ')': {
                    ans.append("(右括号,')')").append('\n');
                    break;
                }
                case ',': {
                    ans.append("(逗号,',')").append('\n');
                    break;
                }
                case ';': {
                    ans.append("(分号,';')").append('\n');
                    break;
                }
                case '.': {
                    ans.append("(点,'.')").append('\n');
                    break;
                }
                case '{': {
                    ans.append("(左大括号,'{')").append('\n');
                    break;
                }
                case '}': {
                    ans.append("(右大括号,'}')").append('\n');
                    break;
                }
                case '"': {
                    ans.append("(双引号,'\"')").append('\n');
                    break;
                }
                case ':': {
                    ans.append("(冒号,':')").append('\n');
                    break;
                }
                default: {
                    ans.append("(非法字符,'").append(ch).append("')").append('\n');
                    break;
                }
            }
        }
    }

    //输入函数
    private static String inputStr() throws IOException {
        StringBuilder str = new StringBuilder();
        BufferedReader br;
        br = new BufferedReader(new FileReader("src\\input.txt"));
        char ch = (char) br.read();
        while (ch != '#') {
            if (ch != '\n' && ch != '\r') {
                str.append(ch);
            }
            ch = (char) br.read();
        }
        br.close();
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
                        ans.append("(保留字,'").append(innerString).append("')").append('\n');
                    } else {//1.2非保留字，即标识符
                        ans.append("(标识符,'").append(innerString).append("')").append('\n');
                    }
                } else if (Character.isDigit(tempStr.charAt(j))) {//2.段内字符串的首字符为数字
                    //后续仍为数字字符时继续追加
                    while (j < length && Character.isDigit(tempStr.charAt(j))) {
                        innerStr.append(tempStr.charAt(j++));
                    }
                    ans.append("(数字,'").append(innerStr).append("')").append('\n');
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
                    ans.append("(非法输入,'").append(tempStr.charAt(j)).append("')").append('\n');
                    j++;
                }
                //重置段内字符串
                innerStr = new StringBuilder();
            }
            //重置段字符串
            tempStr = new StringBuilder();
        }
    }

    public static void main(String[] args) {
        try {
            codeStart();
            //将答案写入文件
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter("src\\output.text"));
            bw.write(ans.toString());
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
