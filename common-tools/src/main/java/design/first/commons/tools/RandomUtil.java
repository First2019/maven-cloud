package design.first.commons.tools;

import java.util.Random;

/**
 * 随机字符或数字处理工具类
 */
public class RandomUtil {

    public static void main(String[] args){
        System.out.println(digitNo());
    }

    /**
     * 通过时间戳产生数字编号
     * @return
     */
    public static synchronized Long digitNo(){
        long l = System.currentTimeMillis();//13位
        return l;
    }

    public static String RandomDigit2(int count) {//产生数字
        StringBuilder str=new StringBuilder();
        for (int i = 0; i < count; i++) {
            double d = Math.random();//它返回的是0(包含)到1(不包含)之间的double值
            int n = (int)(d*10);
            str.append(n+"");
        }
        System.out.println(str.toString());
        return str.toString();
    }
    public static String RandomDigit3(int count) {//产生字符
        StringBuilder str=new StringBuilder();
        StringBuilder str2=new StringBuilder();
        for (int i = 0; i < count; i++) {
            double d = Math.random();//它返回的是0(包含)到1(不包含)之间的double值
            int n = (int)(d*25)+65;//大写
            int x=  (int)(d*25)+97;//小写
            str.append((char)n);
            str2.append((char)x);
        }
        System.out.println(str.toString());
        System.out.println(str2.toString());
        return "";
    }
    public static String RandomDigit4(int count) {//产生大小写混合字符
        StringBuilder str=new StringBuilder();
        for (int i = 0; i < count; i++) {
            double d = Math.random();//它返回的是0(包含)到1(不包含)之间的double值
            int n = (int)(d*58)+65;//大写
            if(n>90&&n<97)
                n+=6;
            str.append((char)n);
        }
        System.out.println(str.toString());
        return str.toString();
    }

    /**
     * 产生n个数字字母混合字符
     * @param n
     * @return
     */
    public static String code(int n){
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<n;i++) {
            double d = Math.random();//它返回的是0(包含)到1(不包含)之间的double值
            int k = (int) (d * 62);
            int c;
            if (k < 10)//数字
                c = k + 48;
            else if (k < 36)//大写字母
                c = k + 55;
            else//小写字母
                c = k + 61;
            builder.append((char)c);
        }
        System.out.println(builder.toString());
        return builder.toString();
    }

    public void RandomDigit() {//无效尝试
        Random r = new Random(1);
        for (int i = 0; i < 5; i++) {
            int ran1 = r.nextInt(100);
            System.out.println(ran1);
        }
    }
}
