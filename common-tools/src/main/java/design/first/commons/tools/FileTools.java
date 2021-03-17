package design.first.commons.tools;


import java.io.File;
import java.text.DecimalFormat;

/**
 * 文件相关处理
 */
public class FileTools {
    public static final String[] UNIT_NAMES = new String[]{"B", "kB", "MB", "GB", "TB", "EB"};

    public static void main(String[] args){
        System.out.println(getSize(1024*1024*10240L));
    }
    //显示文件大小KB，MB,GB
    public static String getSize(long size){
        return sizeFormat(size);
    }
    //is windows系统判断
    public static boolean isWindows(){
        return '\\' == File.separatorChar;
    }

    /**
     * 文件大小数值格式化
     * @param size
     * @return
     */
    public static String sizeFormat(long size) {
        if (size <= 0L) {
            return "0";
        } else {
            int digitGroups = Math.min(UNIT_NAMES.length - 1, (int)(Math.log10((double)size) / Math.log10(1024.0D)));
            return new DecimalFormat("#,##0.##").format((double)size / Math.pow(1024.0D, (double)digitGroups)) + UNIT_NAMES[digitGroups];
        }
    }
}
