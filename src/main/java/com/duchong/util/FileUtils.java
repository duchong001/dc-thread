package com.duchong.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author think
 * @desc 文件帮助类
 */
public class FileUtils {

    private static String uploadPath;

    //定义可以上传文件的后缀数组，默认"*"，代表所有
    public static String[] filePostFix = {"*"};
    public static String[] typeImages = {"gif", "jpeg", "png", "jpg", "bmp"};

    //上传文件的最大长度
    public static long maxFileSize = 1024 * 1024 * 1024 * 2L;//2G
    //一次读取多少字节
    public static int bufferSize = 1024 * 8;

    private final static void init() {
        if (bufferSize > Integer.MAX_VALUE) {
            bufferSize = 1024 * 8;
        } else if (bufferSize < 8) {
            bufferSize = 8;
        }
        if (maxFileSize < 1) {
            maxFileSize = 1024 * 1024 * 1024 * 2L;
        } else if (maxFileSize > Long.MAX_VALUE) {
            maxFileSize = 1024 * 1024 * 1024 * 2L;
        }
    }

    /**
     * 获取文件后缀，没有“.”
     *
     * @param fileName 文件名称
     * @return
     */
    public static String getType(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            String suffix = fileName.substring(index + 1);//后缀
            return suffix;
        } else {
            return null;
        }
    }

    /**
     * 传入一个文件名，得到这个文件名称的后缀 有"."
     *
     * @param fileName 文件名
     * @return 后缀名
     */
    public static String getSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            String suffix = fileName.substring(index);//后缀
            return suffix;
        } else {
            return null;
        }
    }

    /**
     * 利用uuid产生一个随机的name
     *
     * @param fileName 带后缀的文件名称
     * @return String 随机生成的name
     */
    public static String getRandomName(String fileName) {
        String randomName = UUID.randomUUID().toString();
        return getNewFileName(fileName, randomName, "jpg");
    }

    /**
     * 用当前日期、时间和1000以内的随机数组合成的文件名称
     *
     * @param fileName 文件名称
     * @return 新文件名称
     */
    public static String getNumberName(String fileName) {
        SimpleDateFormat format = new SimpleDateFormat("yyMMddhhmmss");
        int rand = new Random().nextInt(1000);
        String numberName = format.format(new Date()) + rand;
        return getNewFileName(fileName, numberName, "jpg");
    }


    /**
     * 传递一个文件名称和一个新名称，组合成一个新的带后缀文件名
     * 当传递的文件名没有后缀，会添加默认的后缀
     *
     * @param fileName   文件名称
     * @param newName    新文件名称
     * @param nullSuffix 为没有后缀的文件所添加的后缀;eg:txt
     * @return String 文件名称
     */
    public static String getNewFileName(String fileName, String newName, String nullSuffix) {
        String suffix = getSuffix(fileName);
        if (suffix != null) {
            newName += suffix;
        } else {
            newName = newName.concat(".").concat(nullSuffix);
        }
        return newName;
    }


    /**
     * 返回可用的文件名
     *
     * @param fileName 文件名
     * @param path     路径
     * @return 可用文件名
     */
    public static String getBracketFileName(String fileName, String path) {
        return getBracketFileName(fileName, fileName, path, 1);
    }


    /**
     * 递归处理文件名称，直到名称不重复（对文件名、目录文件夹都可用）
     * eg: a.txt --> a(1).txt
     * 文件夹upload--> 文件夹upload(1)
     *
     * @param fileName 文件名称
     * @param path     文件路径
     * @param num      累加数字，种子
     * @return 返回没有重复的名称
     */
    public static String getBracketFileName(String fileName, String bracketName, String path, int num) {
        boolean exist = isFileExist(bracketName, path);
        if (exist) {
            int index = fileName.lastIndexOf(".");
            String suffix = "";
            bracketName = fileName;
            if (index != -1) {
                suffix = fileName.substring(index);
                bracketName = fileName.substring(0, index);
            }
            bracketName += "(" + num + ")" + suffix;
            num++;
            bracketName = getBracketFileName(fileName, bracketName, path, num);
        }
        return bracketName;
    }


    /**
     * 判断该文件是否存在
     *
     * @param fileName 文件名称
     * @param path     目录
     * @return 是否存在
     */
    public static boolean isFileExist(String fileName, String path) {
        File file = new File(getDoPath(path) + fileName);
        return file.exists();
    }

    /**
     * 处理后的系统文件路径
     *
     * @param path 文件路径
     * @return 返回处理后的路径
     */
    public static String getDoPath(String path) {
        path = path.replace("\\", "/");
        String lastChar = path.substring(path.length() - 1);
        if (!"/".equals(lastChar)) {
            path += "/";
        }
        return path;
    }

    /**
     * 文件上传
     *
     * @param bizDir  文件业务分类路径(业务模块/子业务模块/业务对象/)
     * @param picture
     * @return
     * @author litu
     */
    public static String uploadFile(String bizDir, MultipartFile picture)
            throws Exception {

//        String savePath = getDoPath(PropertyReader.getValue("uploadPath")) + bizDir;
        String savePath = getDoPath(uploadPath) + bizDir;

        FileUtils.mkDir(savePath);//目录不存在创建

        String fileName = picture.getOriginalFilename();
        //验证文件格式
        if (FileUtils.validTypeByName4Images(fileName)) {
            String newFileName = FileUtils.getRandomName(fileName);//获取随机文件名
            String path = FileUtils.getDoPath(savePath) + newFileName;
            picture.transferTo(new File(path));
            return "/upload/" + bizDir + newFileName;
        }

        return null;
    }


    /**
     * 创建指定的path路径目录
     *
     * @param path 目录、路径
     * @return 是否创建成功
     * @throws Exception
     */
    public static boolean mkDir(String path) throws Exception {
        File file = null;
        try {
            file = new File(path);
            if (!file.exists()) {
                return file.mkdirs();
            }
        } catch (RuntimeException e) {
            throw e;
        } finally {
            file = null;
        }
        return false;
    }

    /**
     * 验证当前文件名、文件类型是否是图片类型
     * typeImages 可以设置图片类型
     *
     * @param fileName 验证文件的名称
     * @return 是否合法
     */
    public static boolean validTypeByName4Images(String fileName) {
        return validTypeByName(fileName, typeImages);
    }


    /**
     * 根据文件名和类型数组验证文件类型是否合法，flag是否忽略大小写
     *
     * @param fileName   文件名
     * @param allowTypes 类型数组
     * @param flag       是否获得大小写
     * @return 是否验证通过
     */
    public static boolean validTypeByName(String fileName, String[] allowTypes, boolean flag) {
        String suffix = getType(fileName);
        boolean valid = false;
        if (allowTypes.length > 0 && "*".equals(allowTypes[0])) {
            valid = true;
        } else {
            for (String type : allowTypes) {
                if (flag) {//不区分大小写后缀
                    if (suffix != null && suffix.equalsIgnoreCase(type)) {
                        valid = true;
                        break;
                    }
                } else {//严格区分大小写
                    if (suffix != null && suffix.equals(type)) {
                        valid = true;
                        break;
                    }
                }
            }
        }
        return valid;
    }

    //@Value("${uploadPath}")
    public void setUploadPath(String uploadPath) {
        FileUtils.uploadPath = uploadPath;
    }

    /**
     * 根据文件名称和类型数组验证文件类型是否合法
     *

     * @param fileName   文件名
     * @param allowTypes 文件类型数组
     * @return 是否合法
     */
    public static boolean validTypeByName(String fileName, String[] allowTypes) {
        return validTypeByName(fileName, allowTypes, true);
    }

    /**
     * 向文件尾部 添加内容
     * @param file
     * @param conent
     */
    public   static   void  appendFileContectAtLast(String file, String conent) {
        BufferedWriter out = null ;
        try  {
            out = new  BufferedWriter(new FileWriter(file,true));
            out.write(conent);
            out.newLine();
        } catch  (Exception e) {
            e.printStackTrace();
        } finally  {
            try  {
                out.close();
            } catch  (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 全覆盖
     * @param file
     */
    public   static   void  appendFileAll(String file,List<String> strList) {

        //更新和重启都要操作这个文件，所以加锁
        ReentrantLock lock=new ReentrantLock();
        lock.lock();
        BufferedWriter out = null ;
        try  {
            out = new  BufferedWriter(new FileWriter(file));

            for (int i = 0; i < strList.size(); i++) {

                if(i!=strList.size()-1){
                   out.write(strList.get(i));
                }
                else{
                    out.write(strList.get(i));
                    out.newLine();
                }
                out.newLine();
            }

        } catch  (Exception e) {
            e.printStackTrace();
        } finally  {
            try  {
                out.close();
            } catch  (IOException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }
    }

    /**
     * 读取文本文件 例如 *.txt
     * @param filePath 文件路径
     * @return
     */
    public static String readFileContent(String filePath){

        String result=null;
        BufferedReader bf =null;
        try {
            File file=new File(filePath);
            if(file.exists()){

                StringBuffer sb=new StringBuffer();
                bf=new BufferedReader(new FileReader(file));

                while ((result=bf.readLine())!=null){
                    sb.append(result);
                }
                result=sb.toString();
                bf.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            if (bf != null) {}
            try {
                bf.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 读取文本文件 例如 *.txt
     * @param filePath 文件路径
     * @return
     */
    public static List<String> getFileContentList(String filePath){

        String result=null;
        BufferedReader bf =null;
        List<String> contentList=new ArrayList<>();
        try {

            File file=new File(filePath);
            if(file.exists()){

                bf=new BufferedReader(new FileReader(file));

                while ((result=bf.readLine())!=null){
                    if(!result.isEmpty()){
                        contentList.add(result);
                    }
                }
                bf.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            if (bf != null) {}
            try {
                bf.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return contentList;
    }


    /**
     * 设置重启时更新 在rc.local 文件中
     * @param rcFilePath  rc.local 文件的位置
     * @param str 更新的sh 文件路径
     */
    public static void appendRcLocalFileForRebootUpdate(String rcFilePath,String str){

        int rebootFirstIndex=-1,rebootLastIndex=-1;
        boolean hasReboot=false;
        List<String> contentList=getFileContentList(rcFilePath);

        for (int m=0;m<contentList.size();m++) {

            if (contentList.get(m).contains("#RebootUpdateStart")) {
                hasReboot=true;
                rebootFirstIndex=m;
            }
            if (contentList.get(m).contains("#RebootUpdateEnd")) {
                hasReboot=true;
                rebootLastIndex=m;
            }
        }
        if(hasReboot && rebootFirstIndex>=0 && rebootLastIndex>=0){

            for(int i=rebootFirstIndex+1;i<rebootLastIndex;i++){
                contentList.remove(i);
                contentList.add(i,str);
            }
        }
        else{

            contentList.add("#RebootUpdateStart");
            contentList.add(str);
            contentList.add("#RebootUpdateEnd");
        }

        appendFileAll(rcFilePath,contentList);
    }

    /**
     * 移除重启时更新
     * @param rcFilePath
     */
    public static void removeRcLocalFileForRebootUpdate(String rcFilePath){

        int rebootFirstIndex=-1,rebootLastIndex=-1;
        boolean hasReboot=false;
        List<String> contentList=getFileContentList(rcFilePath);

        for (int m=0;m<contentList.size();m++) {

            if (contentList.get(m).contains("#RebootUpdateStart")) {
                hasReboot=true;
                rebootFirstIndex=m;
            }
            if (contentList.get(m).contains("#RebootUpdateEnd")) {
                hasReboot=true;
                rebootLastIndex=m;
            }
        }
        if(hasReboot && rebootFirstIndex>=0 && rebootLastIndex>=0){

            for(int i=rebootFirstIndex;i<=rebootLastIndex;i++){
                contentList.remove(rebootFirstIndex);
            }
        }
        else{
            //System.out.println("无可移除的重启命令");
        }

        appendFileAll(rcFilePath,contentList);
    }
    /**
     * 设置重启的crontab
     * @param cronFilePath
     * @param cronStr
     */
    public static void appendCronFileForReboot(String cronFilePath,String cronStr){

        int rebootFirstIndex=-1,rebootLastIndex=-1;
        boolean hasReboot=false;
        List<String> contentList=getFileContentList(cronFilePath);

        for (int m=0;m<contentList.size();m++) {

            if (contentList.get(m).contains("#RebootStart")) {
                hasReboot=true;
                rebootFirstIndex=m;
            }
            if (contentList.get(m).contains("#RebootEnd")) {
                hasReboot=true;
                rebootLastIndex=m;
            }
        }
        //System.out.println("hasReboot="+hasReboot+"---firstIndex="+rebootFirstIndex+"---lastIndex="+rebootLastIndex);
        if(hasReboot && rebootFirstIndex>=0 && rebootLastIndex>=0){

            for(int i=rebootFirstIndex+1;i<rebootLastIndex;i++){
                contentList.remove(i);
                contentList.add(i,cronStr);
            }
            //System.out.println("替换重启命令为"+cronStr);
        }
        else{

            contentList.add("#RebootStart");
            contentList.add(cronStr);
            contentList.add("#RebootEnd");
            //System.out.println("新增重启命令为"+cronStr);
        }

        appendFileAll(cronFilePath,contentList);
    }
    /**
     * 移除重启的crontab
     * @param cronFilePath
     */
    public static void removeCronFileForReboot(String cronFilePath){

        int rebootFirstIndex=-1,rebootLastIndex=-1;
        boolean hasReboot=false;
        List<String> contentList=getFileContentList(cronFilePath);

        for (int m=0;m<contentList.size();m++) {

            if (contentList.get(m).contains("#RebootStart")) {
                hasReboot=true;
                rebootFirstIndex=m;
            }
            if (contentList.get(m).contains("#RebootEnd")) {
                hasReboot=true;
                rebootLastIndex=m;
            }
        }
        //System.out.println("hasReboot="+hasReboot+"---firstIndex="+rebootFirstIndex+"---lastIndex="+rebootLastIndex);
        if(hasReboot && rebootFirstIndex>=0 && rebootLastIndex>=0){

            for(int i=rebootFirstIndex;i<=rebootLastIndex;i++){
                contentList.remove(rebootFirstIndex);
            }
            //System.out.println("移除重启命令成功");
        }
        else{
            //System.out.println("无可移除的重启命令");
        }

        appendFileAll(cronFilePath,contentList);
    }
    /**
     * 设置更新的crontab
     * @param cronFilePath
     * @param cronStr
     */
    public static void appendCronFileForUpdate(String cronFilePath,String cronStr){

        int updateFirstIndex=-1,updateLastIndex=-1;
        boolean hasUpdate=false;

        List<String> contentList=getFileContentList(cronFilePath);

        for (int m=0;m<contentList.size();m++) {

            if (contentList.get(m).contains("#UpdateStart")) {
                hasUpdate=true;
                updateFirstIndex=m;
            }
            if (contentList.get(m).contains("#UpdateEnd")) {
                hasUpdate=true;
                updateLastIndex=m;
            }
        }
        //System.out.println("hasUpdate="+hasReboot+"---firstIndex="+updateFirstIndex+"---lastIndex="+updateLastIndex);
        if(hasUpdate && updateFirstIndex>=0 && updateLastIndex>=0){

            for(int i=updateFirstIndex+1;i<updateLastIndex;i++){
                contentList.remove(i);
                contentList.add(i,cronStr);
            }
            //System.out.println("替换更新命令为"+cronStr);
        }
        else{

            contentList.add("#UpdateStart");
            contentList.add(cronStr);
            contentList.add("#UpdateEnd");

            //System.out.println("新增更新命令为"+cronStr);
        }

        appendFileAll(cronFilePath,contentList);
    }

    /**
     * 移除更新的crontab
     * @param cronFilePath
     */
    public static void removeCronFileForUpdate(String cronFilePath){

        int updateFirstIndex=-1,updateLastIndex=-1;
        boolean hasUpdate=false;

        List<String> contentList=getFileContentList(cronFilePath);

        for (int m=0;m<contentList.size();m++) {

            if (contentList.get(m).contains("#UpdateStart")) {
                hasUpdate=true;
                updateFirstIndex=m;
            }
            if (contentList.get(m).contains("#UpdateEnd")) {
                hasUpdate=true;
                updateLastIndex=m;
            }
        }
        //System.out.println("hasUpdate="+hasReboot+"---firstIndex="+updateFirstIndex+"---lastIndex="+updateLastIndex);
        if(hasUpdate && updateFirstIndex>=0 && updateLastIndex>=0){

            for(int i=updateFirstIndex;i<=updateLastIndex;i++){
                contentList.remove(updateFirstIndex);
            }
            //System.out.println("移除更新命令成功");
        }
        else{

            //System.out.println("无可移除的更新命令");
        }

        appendFileAll(cronFilePath,contentList);
    }
    /**
     * 将自动更新请求的网络类型写到本地服务器的/home/netType.txt 文件中
     */
    public static void setNetTypeFile(String filePath,String netType){

        BufferedWriter out = null ;
        try  {
            File file=new File(filePath);
            if(!file.exists()){
               file.createNewFile();
            }

            out = new  BufferedWriter(new FileWriter(file));
            out.write(netType);
            out.newLine();
        }
        catch  (Exception e) {
            e.printStackTrace();
        }
        finally  {
            try  {
                out.close();
            } catch  (IOException e) {
                e.printStackTrace();
            }
        }

    }



    public static List<String> getUpdateShellAsList(){

        String [] arr={

                "#!/bin/bash",
                "cd /home/",

                "echo \"[`date +%y%m%d/%H:%M:%S`]--- update start...\" >> update_log",
                "fileName=$1",
                "moveToPath=$2",
                "httpdConfFileName=$3",
                "httpdConfFileMoveTo=$4",

                "echo \"[`date +%y%m%d/%H:%M:%S`]--- fileName:$fileName ---moveToPath:$moveToPath ---httpdConfFileName:$httpdConfFileName ---httpdConfFileMoveTo:$httpdConfFileMoveTo \" >> update_log",

                "#判断升级包是否已经存在",
                "if test -e $fileName",
                " then",
                    "#转存文件",
                    "cd $moveToPath",
                    "echo \"[`date +%y%m%d/%H:%M:%S`]---cd  $moveToPath  over ...\" >> /home/update_log",
                    "rm -rf *",
                    "cp  /home/$fileName   $moveToPath",
                    "echo \"[`date +%y%m%d/%H:%M:%S`]---cp /home/$fileName to  $moveToPath over...\" >> /home/update_log",
                "fi",

                "cd /home/",
                "if [ -d star_ins ]",
                "then",
                "rm -rf star_ins ",
                "fi",
                "#判断star_ins.tar.gz 是否已经存在",
                "if test -e  $httpdConfFileName",
                    "then",
                    "tar -xvf $httpdConfFileName  >> /home/update_log",
                    "if [ -d star_ins ]",
                    "then",
                        "cd star_ins",

                        "if [ -f httpd.conf0 ]",
                        "then",
                            "# 删除/etc 下面的httpd.conf0和httpd.conf1",
                            "if [ -f /etc/httpd.conf0 ]",
                            "then",
                            "rm -f /etc/httpd.conf0",
                            "echo \"[`date +%y%m%d/%H:%M:%S`]---delete  /etc/httpd.conf0  over ...\" >> /home/update_log",
                            "fi",
                            "cp httpd.conf0  $httpdConfFileMoveTo",
                            "echo \"[`date +%y%m%d/%H:%M:%S`]---cp new httpdconf0  to  $httpdConfFileMoveTo  over ...\" >> /home/update_log",
                        "fi",

                        "if [ -f httpd.conf1 ]",
                        "then",
                            "if [ -f /etc/httpd.conf1 ]",
                            "then",
                            "rm -f /etc/httpd.conf1",
                            "echo \"[`date +%y%m%d/%H:%M:%S`]---delete  /etc/httpd.conf1  over ...\" >> /home/update_log",
                            "fi",
                        "cp httpd.conf1  $httpdConfFileMoveTo",
                        "echo \"[`date +%y%m%d/%H:%M:%S`]---cp new httpdconf1  to  $httpdConfFileMoveTo  over ...\" >> /home/update_log",
                        "fi",
                    "fi",
                "fi"
        };

        List<String> shell=Arrays.asList(arr);
        return shell;
    }

    public static List<String> getStartUpShellAsList(){

        String [] arr={

                "#!/bin/bash",
                "echo \"[`date +%y%m%d/%H:%M:%S`]--- startup start...\" >> /home/update_log",

                "fileName=$1",
                "moveToPath=$2",

                "echo \"[`date +%y%m%d/%H:%M:%S`]--- startup start--- fileName:$fileName ---moveToPath:$moveToPath \" >> /home/update_log",

                "#webapps文件存在",
                "if test -e $moveToPath$fileName",
                " then",
                    "cd $moveToPath ",
                    "cd ../bin",
                    "echo 'cd ../bin  over ...' >> /home/update_log",
                    "./startup.sh",
                    "echo './startup.sh  over ...' >> /home/update_log",
                "fi"
        };

        List<String> shell=Arrays.asList(arr);
        return shell;
    }

    public static void main(String[] args) {

        //appendFileContectAtLast("F:\\cmd\\LinuxCmd.java","reboot");
        //String[] strs=new String[]{"SHELL=/bin/bash","PATH=/sbin:/bin:/usr/sbin:/usr/bin","MAILTO=root","HOME=/","cmd"};
        //System.out.println(strs.length);
        //appendFileAll("F:\\cmd\\1.txt", Arrays.asList(strs));

        final String filePath = "F:\\cmd\\2.txt";
        final String filePath1 = "F:\\cmd1\\1.txt";
        final String finalStr = "{\"taskType\":\"updateNow\",\"hasFile\":\"true\",\"updateNowCmd\":\"sh /etc/init.d/rsync.sh &\",\"fileContent\":{\"list\":[{\"eName\":\"haijun\",\"city\":[\"深圳\",\"香港\"],\"kelunUrl\":\"/chuanxinxi/haijun.html\",\"name\":\"海钧\",\"url\":\"kelunzhongxin/zhgs/haijun\",\"sbId\":\"abcd12345678\",\"parentsName\":\"珠海高速客轮\"}]}";
        //appendCronFileForReboot(filePath,finalStr);
        //appendCronFileForUpdate(filePath,finalStr);
        //removeCronFileForUpdate(filePath);
        //removeCronFileForReboot(filePath);
        //writeFile(filePath,finalStr);
        //writeFile(filePath1,finalStr);
        //appendFileAll(filePath,getUpdateShellAsList());

        //System.out.println(downloadFileFromUrl("ROOT.war","http://g.treebear.cn/starwifi/software/starfast-local.war","F:\\"));

        //appendRcLocalFileForRebootUpdate(filePath,"abcdefg");

        removeRcLocalFileForRebootUpdate(filePath);
    }
}
